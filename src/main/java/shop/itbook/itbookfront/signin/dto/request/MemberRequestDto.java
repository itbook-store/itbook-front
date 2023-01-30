package shop.itbook.itbookfront.signin.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotBlank(message = "멤버쉽 번호는 null값 및 공백을 허용하지 않습니다.")
    private Integer membershipNo;

    @NotBlank(message = "멤버 상태 번호는 null값 및 공백을 허용하지 않습니다.")
    private Integer memberStatusNo;

    @Length(min = 2, max = 15, message = "아이디는 최소 2자부터 시작하며 최대 15자까지 작성해야합니다.")
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

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
        message = "비밀번호는 영문 대소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함되어야하고 길이는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 null값 및 공백을 허용하지 않습니다.")
    private String password;

    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 null값 및 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식을 갖춰야 합니다.")
    private String email;

    @NotNull(message = "소셜여부는 null값을 허용하지 않습니다.")
    private Boolean isSocial;
}
