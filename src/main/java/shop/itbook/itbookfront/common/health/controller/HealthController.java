package shop.itbook.itbookfront.common.health.controller;

import static org.springframework.http.ResponseEntity.internalServerError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
public class HealthController {

    private Boolean isHealth = Boolean.TRUE;

    @GetMapping("/monitor/l7check")
    public ResponseEntity<Void> health() {

        if (Boolean.TRUE.equals(isHealth)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/monitor/l7check")
    public ResponseEntity<Void> healthStatusModify() {
        this.isHealth = !isHealth;
        return ResponseEntity.ok().build();
    }
}
