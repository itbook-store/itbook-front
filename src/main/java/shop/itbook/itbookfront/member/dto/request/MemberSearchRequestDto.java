package shop.itbook.itbookfront.member.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime end;

    @NotNull(message = "검색조건은 null일 수 없습니다.")
    private String searchRequirement;

    private String searchWord;
}
