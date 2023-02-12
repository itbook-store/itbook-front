package shop.itbook.itbookfront.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.cart.adaptor.CartAdaptor;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
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
    public boolean addCartProduct(String cookieValue, Integer productNo) {
        CartProductDetailsResponseDto cartProductDetailsResponseDto =
            cartAdaptor.addCartProduct(productNo);

        return redisTemplate.opsForHash()
            .putIfAbsent(cookieValue, String.valueOf(productNo), cartProductDetailsResponseDto);
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

        List<CartProductDetailsResponseDto> allCart = cartAdaptor.getProductListMember(memberNo);

        allCart.forEach(
            cart -> redisTemplate.opsForHash()
                .putIfAbsent(
                    cookieValue,
                    String.valueOf(cart.getProductDetailsResponseDto().getProductNo()),
                    cart)
        );
    }
}
