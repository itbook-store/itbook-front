package shop.itbook.itbookfront.common.health.controller;

import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
public class HealthController {

    private Boolean isOk = Boolean.TRUE;
    private static final Boolean OK = Boolean.TRUE;
    private static final Boolean INTERNAL = Boolean.FALSE;

    @GetMapping("/monitor/l7check")
    public ResponseEntity<Void> health() {

        if (Boolean.TRUE.equals(isOk)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping(value = "/monitor/l7check")
    public ResponseEntity<String> healthStatusModify(@RequestParam String healthStatus) {

        if (Objects.isNull(healthStatus)) {
            this.isOk = OK;
            return ResponseEntity.ok("{ \"result\" : \"now server status is OK 200\"");
        }

        this.isOk = INTERNAL;
        return ResponseEntity.ok("{ \"result\" : \"now server status is INTERNAL 500\"");
    }
}
