package shop.itbook.itbookfront.cart.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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
@RequiredArgsConstructor
public class CartScheduler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final CartAdaptor cartAdaptor;

    private static final String MEMBER_NO = "memberNo";

    @Scheduled(cron = "0 0 0 * * *")
    public void saveAllCartByRedis() {

        Set<String> keys = redisTemplate.keys("CID=*");

        List<Map<Object, Object>> redisHashMapList = new ArrayList<>();

        if (Objects.isNull(keys) || keys.isEmpty()) {
            return;
        }

        keys.forEach(
            key -> redisHashMapList.add(redisTemplate.opsForHash().entries(key))
        );

        List<Map<Object, Object>> filteredMapList = redisHashMapList.stream()
            .filter(map -> map.containsKey(MEMBER_NO))
            .collect(Collectors.toList());

        List<CartMemberRequestDto> list = new ArrayList<>();

        if (filteredMapList.isEmpty()) {
            return;
        }

        for (Map<Object, Object> map : filteredMapList) {
             Integer memberNo = (Integer) map.get(MEMBER_NO);
            map.remove(MEMBER_NO);
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                CartProductDetailsResponseDto value =
                    (CartProductDetailsResponseDto) entry.getValue();

                list.add(new CartMemberRequestDto(
                    memberNo.longValue(),
                    value.getProductDetailsResponseDto().getProductNo(),
                    value.getProductCount()
                ));
            }
        }

        cartAdaptor.saveAllCart(list);

    }
}
