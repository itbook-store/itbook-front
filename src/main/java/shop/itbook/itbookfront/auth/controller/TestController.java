package shop.itbook.itbookfront.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.auth.exception.MemberNotFountException;
import shop.itbook.itbookfront.auth.parser.AuthenticationParser;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test-auth")
    public String testAuth(@AuthenticationPrincipal UserDetailsDto userDetailsDto) throws JsonProcessingException {
//        UserDetailsDto userDetailsDto = Optional.ofNullable
//        (AuthenticationParser.getUserDetailsDto())
//            .orElseThrow(MemberNotFountException::new);

        log.info("userDetailsDto {}", userDetailsDto);

        log.info("userDetailsDto.getMemberNo() {}", userDetailsDto.getMemberNo());
        log.info("userDetailsDto.getMemberId() {}", userDetailsDto.getMemberId());

        return userDetailsDto.toString();
    }


}
