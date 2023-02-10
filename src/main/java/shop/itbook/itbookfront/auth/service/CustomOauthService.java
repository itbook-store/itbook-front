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
import shop.itbook.itbookfront.auth.exception.AlreadySignupMemberInSelfCompanyException;
import shop.itbook.itbookfront.auth.util.OAuthAttribute;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;

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

    private final PasswordEncoder passwordEncoder;

    /**
     * OAuth2UserService의 메소드를 Override 한 메서드 입니다.
     * OAuth Server 로 부터 넘어온 유저 정보를 컨트롤 하기 위한 메서드 입니다.
     *
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttribute oAuthAttribute =
            OAuthAttribute.of(registrationId, attributes, userNameAttributeName);

        Map<String, Object> oauthAttributeMap = OAuthAttribute.convertToMap(oAuthAttribute);

        ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> checkValidAuthUser =
            authAdaptor.postShopServerCheckValidAuthUser(
                new MemberOAuthRequestDto(
                    oAuthAttribute.getEmail(),
                    passwordEncoder.encode(oAuthAttribute.getEmail())
                )
            );

        CommonResponseBody<SuccessfulResponseDto> commonResponseBody =
            Objects.requireNonNull(checkValidAuthUser.getBody());

        if (!Objects.requireNonNull(commonResponseBody.getResult()).getIsSuccessful()) {
            throw new AlreadySignupMemberInSelfCompanyException();
        }

        return new DefaultOAuth2User(
            authorities,
            oauthAttributeMap,
            userNameAttributeName
        );
    }
}
