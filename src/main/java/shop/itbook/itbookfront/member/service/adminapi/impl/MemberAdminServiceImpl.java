package shop.itbook.itbookfront.member.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.adaptor.adminapi.MemberAdminAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateAdminRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
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
    public MemberAdminResponseDto findMember(String memberId) {
        return memberAdminAdaptor.getMember(memberId);
    }

    @Override
    public void updateMemberStatus(String memberStatusName,
                                   String memberId) {

        MemberUpdateAdminRequestDto memberUpdateAdminRequestDto = new MemberUpdateAdminRequestDto(memberStatusName);
        memberAdminAdaptor.modifyMemberStatus(memberUpdateAdminRequestDto, memberId);
    }

    @Override
    public List<MemberAdminResponseDto> findMembers() {
        return memberAdminAdaptor.getMembers();
    }
}