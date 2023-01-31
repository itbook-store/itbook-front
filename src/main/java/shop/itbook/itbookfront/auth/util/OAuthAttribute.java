package shop.itbook.itbookfront.auth.util;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shop.itbook.itbookfront.auth.exception.InvalidOAuthServerException;

/**
 * OAuth 서버에 대한 각각의 처리를 해주는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthAttribute {

    private String registrationId;

    private Map<String, Object> attributes;

    private String userNameAttributeName;

    private String name;

    private String email;

    private static final String OAUTH_NAVER = "naver";

    private static final String OAUTH_KAKAO = "kakao";

    private static final String OAUTH_GITHUB = "github";

    private OAuthAttribute() {}

    /**
     * 각 OAuth 서비스에 대한 세부 구현으로 분리하기 위한 static 메서드 입니다.
     * 지원하지 않는 서비스가 들어온 경우 Exception 처리를 하였습니다.
     *
     * @param registrationId OAuth 서비스 이름
     * @param attributes OAuth 서버로 부터 받은 정보
     * @param userNameAttributeName OAuth 로그인 시 Key가 되는 고유 식별번호
     * @return OAuthAttribute
     * @author 강명관
     */
    public static OAuthAttribute of(String registrationId,
                                    Map<String, Object> attributes,
                                    String userNameAttributeName) {
        switch (registrationId) {
            case (OAUTH_NAVER):
                return ofNaver(attributes, userNameAttributeName);
            case (OAUTH_KAKAO):
                return ofKakao(attributes, userNameAttributeName);
            case (OAUTH_GITHUB):
                return ofGithub(attributes, userNameAttributeName);
            default:
                throw new InvalidOAuthServerException();
        }
    }

    /**
     * Naver OAuth 서버에 대한 원하는 값을 얻기위한 메서드 입니다.
     *
     * @param attributes OAuth 서버로 부터 받은 정보
     * @param userNameAttributeName OAuth 로그인 시 Key가 되는 고유 식별번호
     * @return OAuthAttribute
     * @author 강명관
     */
    private static OAuthAttribute ofNaver(Map<String, Object> attributes,
                                          String userNameAttributeName) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttribute.builder()
            .name((String) response.get(AuthConstant.NAME))
            .email((String) response.get(AuthConstant.EMAIL))
            .attributes(response)
            .userNameAttributeName(userNameAttributeName)
            .build();
    }

    /**
     * Kakao OAuth 서버에 대한 원하는 값을 얻기위한 메서드 입니다.
     *
     * @param attributes OAuth 서버로 부터 받은 정보
     * @param userNameAttributeName OAuth 로그인 시 Key가 되는 고유 식별번호
     * @return OAuthAttribute
     * @author 강명관
     */
    private static OAuthAttribute ofKakao(Map<String, Object> attributes,
                                          String userNameAttributeName) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttribute.builder()
            .name((String) profile.get(AuthConstant.NICKNAME))
            .email((String) kakaoAccount.get(AuthConstant.EMAIL))
            .attributes(attributes)
            .userNameAttributeName(userNameAttributeName)
            .build();
    }

    /**
     * Github OAuth 서버에 대한 원하는 값을 얻기위한 메서드 입니다.
     *
     * @param attributes OAuth 서버로 부터 받은 정보
     * @param userNameAttributeName OAuth 로그인 시 Key가 되는 고유 식별번호
     * @return OAuthAttribute
     * @author 강명관
     */
    private static OAuthAttribute ofGithub(Map<String, Object> attributes,
                                           String userNameAttributeName) {
        return OAuthAttribute.builder()
            .name((String) attributes.get(AuthConstant.NAME))
            .email((String) attributes.get(AuthConstant.EMAIL))
            .attributes(attributes)
            .userNameAttributeName(userNameAttributeName)
            .build();
    }

    public static Map<String, Object> convertToMap(OAuthAttribute oAuthAttribute) {
        Map<String, Object> map = new HashMap<>();
        map.put(oAuthAttribute.getUserNameAttributeName(), oAuthAttribute.getAttributes());
        map.put(AuthConstant.EMAIL, oAuthAttribute.getEmail());
        map.put(AuthConstant.NAME, oAuthAttribute.getName());

        return map;
    }
}
