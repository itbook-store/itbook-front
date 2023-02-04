package shop.itbook.itbookfront.member.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.adaptor.adminapi.MemberAdminAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;
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
    public void updateMemberStatus(MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                   String memberId) {

        memberAdminAdaptor.modifyMemberStatus(memberStatusChangeRequestDto, memberId);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findMembersBySearch(String searchRequirement,
                                                            String searchWord,
                                                            String memberStatusName, String url) {

        return memberAdminAdaptor.getMembersBySearch(searchRequirement, searchWord, memberStatusName, url);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findMembers(String url) {
        return memberAdminAdaptor.getMembers(url);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findNormalMembers(String url) {
        return memberAdminAdaptor.getNormalMembers(url);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findBlockMembers(String url) {
        return memberAdminAdaptor.getBlockMembers(url);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findWithdrawMembers(String url) {
        return memberAdminAdaptor.getWithdrawMembers(url);
    }

    @Override
    public MemberBlockInfoResponseDto findBlockMember(String memberId) {
        return memberAdminAdaptor.getBlockMember(memberId);
    }

    @Override
    public List<MemberRoleResponseDto> findMemberRoles(Long memberNo) {
        return memberAdminAdaptor.getMemberRoles(memberNo);
    }

    @Override
    public void addMemberRole(String memberId, String roleName) {
        memberAdminAdaptor.addMemberRole(memberId, roleName);
    }

    @Override
    public void deleteMemberRole(Long memberNo, Integer roleNo) {
        memberAdminAdaptor.deleteMemberRole(memberNo, roleNo);
    }
}
