package shop.itbook.itbookfront.auth.service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.dto.request.MemberOAuthRequestDto;
import shop.itbook.itbookfront.auth.exception.MemberNotFountException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;

/**
 * OAuth Login 에서 받아온 유저정보를 컨트롤하기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomOauthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AuthAdaptor authAdaptor;

    private final GatewayConfig gatewayConfig;

    private final PasswordEncoder passwordEncoder;

    private static final String SHOP_API_URL = "/api/members/oauth/login/find";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        /* OAuth2UserService 를 기본으로 구현하고 처리하는 객체 */
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /* OAuth Server 에서 넘어온 권한 */
        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
        log.info("oauth2User info {}", oAuth2User);

        /* OAuth 서비스 이름 (GitHub, Naver, Kakao) */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId {}", registrationId);

        /* OAuth 로그인 시 Key가 되는 값 */
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        /* OAuth 서버에서 넘겨준 유저 정보 */
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("attributes {}", attributes);

        String email = String.valueOf(attributes.get("email"));

        ResponseEntity<CommonResponseBody<Boolean>> userExistResult =
            authAdaptor.postShopServerOAuthUserSignUp(
                gatewayConfig.getGatewayServer() + SHOP_API_URL,
                new MemberOAuthRequestDto(
                    email,
                    passwordEncoder.encode(email)
                )
            );

        /* exchange 로 부터 Boolean 값이 넘어옴 */
        if (!Objects.requireNonNull(userExistResult.getBody()).getResult()) {
            throw new MemberNotFountException();
        }

        return new DefaultOAuth2User(
            authorities,
            attributes,
            userNameAttributeName
        );
    }
}
