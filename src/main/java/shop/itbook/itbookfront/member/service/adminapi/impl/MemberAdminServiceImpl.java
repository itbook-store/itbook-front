package shop.itbook.itbookfront.member.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.adaptor.adminapi.MemberAdminAdaptor;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberAdminAdaptor memberAdminAdaptor;

    @Override
    public List<MemberInfoResponseDto> findMembers() {
        return memberAdminAdaptor.getMembers();
    }
}
