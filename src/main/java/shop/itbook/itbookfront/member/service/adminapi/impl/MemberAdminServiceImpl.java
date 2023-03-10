package shop.itbook.itbookfront.member.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.adaptor.adminapi.MemberAdminAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberRegisterWriterRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberSearchRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberAdminAdaptor memberAdminAdaptor;

    @Override
    public MemberAdminResponseDto findMember(Long memberNo) {
        return memberAdminAdaptor.getMember(memberNo);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findWriterMembers(String url) {
        return memberAdminAdaptor.getWriterMembers(url);
    }

    @Override
    public void updateMemberStatus(MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                   Long memberNo) {

        memberAdminAdaptor.modifyMemberStatus(memberStatusChangeRequestDto, memberNo);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findMembersBySearch(String searchRequirement,
                                                            String searchWord,
                                                            String memberStatusName, String url) {

        return memberAdminAdaptor.getMembersBySearch(searchRequirement, searchWord, memberStatusName, url);
    }

    @Override
    public PageResponse<MemberAdminResponseDto> findMemberByDateOfJoining(
        MemberSearchRequestDto memberSearchRequestDto, String memberStatusName, String url) {

        return memberAdminAdaptor.getMembersByDateOfJoining(memberSearchRequestDto, memberStatusName, url);
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
    public MemberBlockInfoResponseDto findBlockMember(Long memberNo) {
        return memberAdminAdaptor.getBlockMember(memberNo);
    }

    @Override
    public List<MemberRoleResponseDto> findMemberRoles(Long memberNo) {
        return memberAdminAdaptor.getMemberRoles(memberNo);
    }

    @Override
    public void addMemberRole(Long memberNo, String roleName) {
        memberAdminAdaptor.addMemberRole(memberNo, roleName);
    }

    @Override
    public void deleteMemberRole(Long memberNo, Integer roleNo) {
        memberAdminAdaptor.deleteMemberRole(memberNo, roleNo);
    }

    @Override
    public MemberCountResponseDto countMemberByMemberStatus() {
        return memberAdminAdaptor.countMember();
    }

    @Override
    public MemberCountByMembershipResponseDto countMemberByMembership() {
        return memberAdminAdaptor.countMemberByMembership();
    }

    @Override
    public MemberBooleanResponseDto checkNameExists(String memberId, String name) {

        return memberAdminAdaptor.nameExists(memberId, name);
    }


    @Override
    public void modifyMemberWriter(MemberRegisterWriterRequestDto memberRegisterWriterRequestDto) {

        memberAdminAdaptor.modifyMemberWriter(memberRegisterWriterRequestDto.getMemberNo());
    }
}
