function withdrawSelect() {
    let returnValue = confirm("탈퇴하면 저희 서비스를 이용하실 수 없습니다. 그래도 탈퇴하시겠습니까?");
    if(returnValue) {
        Swal.fire('탈퇴되었습니다.', '', 'success');
        document.getElementById("memberWithdraw").submit();
    } else {
        location.replace("/mypage");
    }
}