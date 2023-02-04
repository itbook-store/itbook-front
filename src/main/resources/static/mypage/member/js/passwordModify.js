function sendPassword() {

    if(!checkPassword(document.getElementById("modifiedPassword").value)) {
        return false;
    }

    if(document.getElementById("modifiedPassword").value !== document.getElementById("modifiedPasswordCheck").value) {
        alert("비밀번호가 맞지 않습니다.");
        return false;
    }

    opener.document.getElementById("memberPassword").value = document.getElementById("modifiedPassword").value;

    window.close();
}

function checkPassword(str) {
    const regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,10}$/i;

    if(!regExp.test(str)) {
        alert("비밀번호 형식이 아닙니다.");
        return false;
    }

    return true;
}