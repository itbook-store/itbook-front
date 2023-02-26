package shop.itbook.itbookfront.cart.service.impl;

import static shop.itbook.itbookfront.cart.util.CartConstant.MEMBER_NO;
import static shop.itbook.itbookfront.cart.util.CartConstant.SUF_FIX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.cart.adaptor.CartAdaptor;
import shop.itbook.itbookfront.cart.dto.response.CartAddResponseDto;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * 장바구니의 비지니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final CartAdaptor cartAdaptor;

    @Override
    public List<Object> getCartList(String cookieValue) {

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(cookieValue);
        entries.remove("memberNo");

        return new ArrayList<>(entries.values());
    }


    @Override
    public CartAddResponseDto addCartProduct(String cookieValue, Integer productNo) {
        CartProductDetailsResponseDto cartProductDetailsResponseDto =
            cartAdaptor.addCartProduct(productNo);

        if (cartProductDetailsResponseDto.getProductDetailsResponseDto().getIsDeleted()) {
            return new CartAddResponseDto(false, "삭제된 상품은 장바구니에 담을 수 없습니다.");
        }

        if (cartProductDetailsResponseDto.getProductDetailsResponseDto().getIsForceSoldOut()
            || cartProductDetailsResponseDto.getProductDetailsResponseDto().getStock() == 0) {
            return new CartAddResponseDto(false, "품절된 상품은 장바구니에 담을 수 없습니다.");
        }

        if (!redisTemplate.opsForHash()
            .putIfAbsent(cookieValue, String.valueOf(productNo), cartProductDetailsResponseDto)) {
            return new CartAddResponseDto(false, "이미 장바구니에 담았습니다.");
        }

        return new CartAddResponseDto(true, "상품을 장바구니에 담았습니다.");
    }

    @Override
    public void deleteCartProduct(String cookieValue, Integer productNo) {
        redisTemplate.opsForHash().delete(cookieValue, String.valueOf(productNo));
    }

    @Override
    public void deleteAllCartProduct(String cookieValue, List<Integer> productNoList) {
        productNoList.forEach(
            productNo -> redisTemplate.opsForHash().delete(cookieValue, String.valueOf(productNo))
        );
    }

    @Override
    public void modifyCart(String cookieValue, Integer productNo, Integer productCount) {

        CartProductDetailsResponseDto cartProductDetailsResponseDto =
            (CartProductDetailsResponseDto) redisTemplate.opsForHash()
                .get(cookieValue, String.valueOf(productNo));

        if (Objects.nonNull(cartProductDetailsResponseDto)) {
            cartProductDetailsResponseDto.setProductCount(productCount);
            redisTemplate.opsForHash().put(cookieValue, String.valueOf(productNo), cartProductDetailsResponseDto);
        }

    }

    @Override
    public void loadCartProductForMember(String cookieValue, Long memberNo) {

        redisTemplate.opsForHash().putIfAbsent(cookieValue, "memberNo", memberNo);

        /* 이걸 통해 cookieValue + SUF_FIX 의 키를 생성하여, 얘를 더 먼저 만료시켜서 기존의 키를 갖고와서 다 넣는다 */
        redisTemplate.opsForValue().set(cookieValue + SUF_FIX, "");
        /* 장바구니 기능을 이용하지 않더라도, 해당 데이터는 실제 장바구니 데이터를 가르키는 것 뿐*/

        /* 따라서, 얘는 만료되지 않고 남아있는다면 메모리 낭비이다. */
        redisTemplate.expire(cookieValue + SUF_FIX, 6, TimeUnit.HOURS);

        List<CartProductDetailsResponseDto> allCart = cartAdaptor.getProductListMember(memberNo);

        allCart.forEach(
            cart -> redisTemplate.opsForHash()
                .putIfAbsent(
                    cookieValue,
                    String.valueOf(cart.getProductDetailsResponseDto().getProductNo()),
                    cart)
        );
    }

    public void saveAllCartDataByRedis(String redisKey) {

        Map<Object, Object> redisHashMapData = redisTemplate.opsForHash().entries(redisKey);
        Integer memberNo = (Integer) redisHashMapData.remove(MEMBER_NO);


        List<CartMemberRequestDto> cartMemberRequestDtoList =
            getCartMemberRequestDtoList(redisHashMapData, memberNo);

        try {
            cartAdaptor.saveAllCart(cartMemberRequestDtoList);
        } catch (Exception e) {
            log.error("CartUtil saveAllCartDataByRedis() {}", e.getMessage());
            e.printStackTrace();
        }

        redisTemplate.expire(redisKey, 1, TimeUnit.SECONDS);
        /* 팬텀키가 Expire 될 경우는 상관없지만 로그아웃 로직에서도 동일하게 적용되어야 하기 때문 */
        redisTemplate.expire(redisKey + SUF_FIX, 1, TimeUnit.SECONDS);
    }

    private static List<CartMemberRequestDto> getCartMemberRequestDtoList(
        Map<Object, Object> redisHashMapData, Integer memberNo) {

        List<CartMemberRequestDto> cartMemberRequestDtoList = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : redisHashMapData.entrySet()) {
            CartProductDetailsResponseDto value =
                (CartProductDetailsResponseDto) entry.getValue();

            cartMemberRequestDtoList.add(new CartMemberRequestDto(
                memberNo.longValue(),
                value.getProductDetailsResponseDto().getProductNo(),
                value.getProductCount()
            ));
        }

        return cartMemberRequestDtoList;
    }
}
