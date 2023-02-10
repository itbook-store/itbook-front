package shop.itbook.itbookfront.signin.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 노수연
 * @since 1.0
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "멤버쉽 이름은 null값 및 공백을 허용하지 않습니다.")
    private String membershipName;

    @NotBlank(message = "멤버 상태 이름은 null값 및 공백을 허용하지 않습니다.")
    private String memberStatusName;

    @Pattern(regexp = "^[a-z0-9-_]{2,15}$", message = "아이디는 특수문자를 제외한 2 ~ 15자리여야 합니다.")
    @NotBlank(message = "아이디는 null값 및 공백을 허용하지 않습니다.")
    private String memberId;

    @Length(min = 2, max = 20, message = "닉네임은 최소 2자, 최대 20자까지 허용합니다.")
    @NotBlank(message = "닉네임은 null값 및 공백을 허용하지 않습니다.")
    private String nickname;

    @Length(max = 20, message = "이름은 최대 20자까지 허용합니다.")
    @NotBlank(message = "이름은 null값 및 공백을 허용하지 않습니다.")
    private String name;

    @NotNull(message = "성별은 null값을 허용하지 않습니다.")
    private Boolean isMan;

    @NotNull(message = "생일은 null값을 허용하지 않습니다.")
    private LocalDateTime birth;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,255}",
        message = "비밀번호는 영문 대소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함되어야하고 길이는 8자 ~ 255자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 null값 및 공백을 허용하지 않습니다.")
    private String password;

    @Pattern(regexp = "^[0-9]{11}$", message = "전화번호 형식에 맞춰 입력해주세요. 숫자만 입력할 수 있습니다.")
    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

    @NotNull(message = "소셜여부는 null값을 허용하지 않습니다.")
    private Boolean isSocial;
}
