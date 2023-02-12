package shop.itbook.itbookfront.member.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/check")
public class MemberAsyncController {

    private final MemberAdminService memberAdminService;

    @GetMapping("/name/{memberId}/{name}")
    public MemberBooleanResponseDto emailExists(@PathVariable("memberId") String memberId,
                                                @PathVariable("name") String name) {

        return memberAdminService.checkNameExists(memberId, name);
    }
}
