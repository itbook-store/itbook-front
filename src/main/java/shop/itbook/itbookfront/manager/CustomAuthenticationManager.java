//package shop.itbook.itbookfront.manager;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.Base64;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.StringTokenizer;
//import java.util.stream.Collectors;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import shop.itbook.itbookfront.adaptor.RestTemplateAdaptor;
//import shop.itbook.itbookfront.common.response.CommonResponseBody;
//import shop.itbook.itbookfront.config.GatewayConfig;
//import shop.itbook.itbookfront.login.dto.MemberAuthRequestDto;
//
///**
// * Auth 서버와 통신하여 로그인에 대한 처리에 대한 Response 를 담당하는 Custom AuthenticationManager 입니다.
// *
// * @author 강명관
// * @since 1.0
// */
//@Slf4j
//@RequiredArgsConstructor
//public class CustomAuthenticationManager implements AuthenticationManager {
//
//    private final RestTemplateAdaptor restTemplateAdaptor;
//    private final GatewayConfig gatewayConfig;
//    private final RedisTemplate<String, String> redisTemplate;
//
//    private static final String AUTH_LOGIN_PROCESSING_URL = "/auth/login";
//
//    /**
//     * AuthenticationManager 의 custom 구현 메서드로, Auth서버로 부터 RestTemplate로 인증을 요청하고,
//     * JWT AccessToken을 받아 Front Server 의 Security Context로부터 관리하기 위한 메서드 입니다.
//     *
//     * @param authentication the authentication request object
//     * @return 인증이 완료된 AuthenticationToken
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication)
//        throws AuthenticationException {
//
//        ResponseEntity<CommonResponseBody<Void>> exchange =
//            restTemplateAdaptor.postAuthServerForLogin(
//                gatewayConfig.getGatewayServer() + AUTH_LOGIN_PROCESSING_URL,
//                new MemberAuthRequestDto((String) authentication.getPrincipal(),
//                    (String) authentication.getCredentials())
//            );
//
////        ResponseChecker.checkFail(exchange.getBody().getHeader());
//
//        String accessToken = exchange.getHeaders().get("Authorization").get(0);
//        log.info("accessToken {}", accessToken);
//
//        redisTemplate.opsForHash().put("accessToken", authentication.getPrincipal(), accessToken);
//
//        String payload = getPayloadByAccessToken(accessToken);
//        log.info("payload {}", payload);
//
//        List<SimpleGrantedAuthority> grantedAuthorities = getAuthorityByPayload(payload).stream()
//            .map(role -> new SimpleGrantedAuthority(role.get("authority")))
//            .collect(Collectors.toList());
//
//        log.info("grantedAuthorities {}", grantedAuthorities);
//
//        return new UsernamePasswordAuthenticationToken(
//            authentication.getPrincipal(),
//            null,
//            grantedAuthorities
//        );
//
//
//    }
//
//    /**
//     * json 으로 된 payload 안에 담겨있는 권한 리스트를 가져오는 메서드 입니다.
//     *
//     * @param payload JWT 토큰의 Payload jsonString
//     * @return payload 안에 담겨있는 권한들 리스트 입니다.
//     */
//    private List<LinkedHashMap<String, String>> getAuthorityByPayload(String payload) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> result;
//        List<LinkedHashMap<String, String>> roleList;
//
//        try {
//            result = objectMapper.readValue(payload, Map.class);
//            roleList = (List<LinkedHashMap<String, String>>) result.get("role");
//
//            log.info("role {}", roleList);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return roleList;
//    }
//
//    /**
//     * JWT 토큰의 payload 를 파싱하여 가져오는 메서드 입니다.
//     *
//     * @param accessToken auth 서버로 부터 발급 받은 JWT AccessToken 입니다.
//     * @return Payload Json String
//     */
//    private String getPayloadByAccessToken(String accessToken) {
//        StringTokenizer stringTokenizer = new StringTokenizer(accessToken, ".");
//        stringTokenizer.nextToken();
//
//        return base64Decoding(stringTokenizer.nextToken());
//    }
//
//    /**
//     * Base64로 인코딩된 JWT Payload를 Decoding 하는 메서드 입니다.
//     *
//     * @param payload Base64로 인코딩된 JWT Payload 입니다.
//     * @return Payload Json String
//     */
//    private String base64Decoding(String payload) {
//        return new String(Base64.getDecoder().decode(payload));
//    }
//
//
//
//}
