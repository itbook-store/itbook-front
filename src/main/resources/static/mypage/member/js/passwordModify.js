function sendPassword() {

    if(!checkPassword(document.getElementById("modifiedPassword").value)) {
        return false;
    }

    if(document.getElementById("modifiedPassword").value !== document.getElementById("modifiedPasswordCheck").value) {
        alert("비밀번호가 맞지 않습니다.");
        return false;
    }

    opener.document.getElementById("memberPassword").value = document.getElementById("modifiedPassword").value;

    alert("회원님의 비밀번호가 수정되었습니다.");
    window.close();
}

function checkPassword(str) {
    const regExp = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*_+=-]).{9,255}$/i;

    if(!regExp.test(str)) {
        alert("비밀번호 형식이 아닙니다.");
        return false;
    }

    return true;
}