function sendPassword() {

    if(!checkPassword(document.getElementById("modifiedPassword").value)) {
        return false;
    }

    if(document.getElementById("modifiedPassword").value !== document.getElementById("modifiedPasswordCheck").value) {
        Swal.fire('비밀번호가 맞지 않습니다.', '', 'error');
        return false;
    }

    opener.document.getElementById("memberPassword").value = document.getElementById("modifiedPassword").value;
    Swal.fire('회원님의 비밀번호가 수정되었습니다.', '', 'success');
    window.close();
}

function checkPassword(str) {
    const regExp = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*_+=-]).{9,255}$/i;

    if(!regExp.test(str)) {
        Swal.fire('비밀번호 형식이 아닙니다.', '', 'error');
        return false;
    }

    return true;
}