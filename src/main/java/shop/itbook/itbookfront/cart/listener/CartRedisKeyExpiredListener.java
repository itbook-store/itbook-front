package shop.itbook.itbookfront.cart.listener;

import static shop.itbook.itbookfront.cart.util.CartConstant.MEMBER_NO;
import static shop.itbook.itbookfront.cart.util.CartConstant.SUF_FIX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import shop.itbook.itbookfront.cart.adaptor.CartAdaptor;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
public class CartRedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CartAdaptor cartAdaptor;

    public CartRedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer,
                                       RedisTemplate<String,Object> redisTemplate,
                                       CartAdaptor cartAdaptor) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
        this.cartAdaptor = cartAdaptor;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        log.info("redis expire key {}", message.toString());

        String expiredKey = message.toString();

        /* 지정해놓은 형식의 키가 들어오지 않는다면 return */
        if (!expiredKey.endsWith(SUF_FIX)) {
            return;
        }

        String redisKey = expiredKey.replaceAll(SUF_FIX, "");

        log.info("cartRedisKey {}", redisKey);

        Map<Object, Object> redisHashMapData = redisTemplate.opsForHash().entries(redisKey);
        log.info("entries {}", redisHashMapData);

        Integer memberNo = (Integer) redisHashMapData.remove(MEMBER_NO);

        List<CartMemberRequestDto>
            cartMemberRequestDtoList = getCartMemberRequestDtoList(redisHashMapData, memberNo);

        redisTemplate.expire(redisKey, 1, TimeUnit.SECONDS);

        try {
            cartAdaptor.saveAllCart(cartMemberRequestDtoList);
        } catch (Exception e) {
            log.error("cart saveAllCart Error {}", e.getMessage());
            e.printStackTrace();
        }
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
