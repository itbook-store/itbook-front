package shop.itbook.itbookfront.cart.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.cart.adaptor.CartAdaptor;
import shop.itbook.itbookfront.cart.dto.response.CartResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartProductNoRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    /* TODO -> 레디스에 저장된 상품 번호를 Long 타입으로 가져오면 ClassCastException이 나서 Integer 사용*/
    private final RedisTemplate<String, Integer> redisTemplate;

    private final CartAdaptor cartAdaptor;

    @Override
    public boolean addProductAnonymousToCart(String cookieValue, Integer productNo) {

        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.nonNull(productNoSet) && productNoSet.contains(productNo)) {
            return false;
        }

        redisTemplate.opsForSet().add(cookieValue, productNo);

        return true;
    }

    @Override
    public boolean addProductMemberToCart(CartMemberRequestDto cartMemberRequestDto) {
        return cartAdaptor.addProductInCart(cartMemberRequestDto);
    }

    @Override
    public List<CartResponseDto> getCartListAnonymous(String cookieValue) {
        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.isNull(productNoSet)) {
            return Collections.emptyList();
        }

        List<CartProductNoRequestDto> cartProductNoRequestDtoList = productNoSet.stream()
            .map(CartProductNoRequestDto::new)
            .collect(Collectors.toList());

        return cartAdaptor.getProductListAnonymous(cartProductNoRequestDtoList).stream()
            .filter(dto -> !dto.getIsSubscription())
            .collect(Collectors.toList());
    }

    @Override
    public List<CartResponseDto> getCartListMember(CartMemberNoRequestDto cartMemberNoRequestDto) {
        return cartAdaptor.getProductListMember(cartMemberNoRequestDto);
    }

    @Override
    public void deleteProductAnonymousToCart(String cookieValue, Integer productNo) {
        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.nonNull(productNoSet)) {
            productNoSet.remove(productNo);
        }
    }

    @Override
    public void deleteAllProductAnonymousToCart(String cookieValue) {
        Set<Integer> productNoSet = redisTemplate.opsForSet().members(cookieValue);

        if (Objects.nonNull(productNoSet)) {
            productNoSet.clear();
        }
    }

    @Override
    public void deleteProductMemberToCart(CartMemberRequestDto cartMemberRequestDto) {
        cartAdaptor.deleteProductInCart(cartMemberRequestDto);
    }

    @Override
    public void deleteAllProductMemberToCart(CartMemberNoRequestDto cartMemberNoRequestDto) {
        cartAdaptor.deleteAllProductInCart(cartMemberNoRequestDto);
    }
}
