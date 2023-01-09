package shop.itbook.itbookfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 게이트웨이 환경을 전역으로 사용하기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Configuration
public class GatewayConfig {

    @Value("${itbook.gateway.server}")
    private String gatewayServer;

    public String getGatewayServer() {
        return gatewayServer;
    }
}
