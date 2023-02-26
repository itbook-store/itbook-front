package shop.itbook.itbookfront.cart.listener;

import static shop.itbook.itbookfront.cart.util.CartConstant.SUF_FIX;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
public class CartRedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private final CartService cartService;

    public CartRedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer,
                                       CartService cartService
                                       ) {
        super(listenerContainer);
        this.cartService = cartService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        log.debug("redis expire key {}", message.toString());

        String expiredKey = message.toString();

        if (!expiredKey.endsWith(SUF_FIX)) {
            return;
        }

        String redisKey = expiredKey.replaceAll(SUF_FIX, "");

        cartService.saveAllCartDataByRedis(redisKey);

    }
}
