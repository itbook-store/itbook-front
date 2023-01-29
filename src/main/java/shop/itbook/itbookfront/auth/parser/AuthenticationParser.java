package shop.itbook.itbookfront.auth.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class AuthenticationParser {

    private static final String ANONYMOUS_USER = "anonymousUser";

    private AuthenticationParser() {

    }

    public static UserDetailsDto getUserDetailsDto() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("principal {}", authentication.getPrincipal());

        if (String.valueOf(authentication.getPrincipal()).equals(ANONYMOUS_USER)) {
            return null;
        }

        return objectMapper.readValue((String) authentication.getPrincipal(), UserDetailsDto.class);
    }


}
