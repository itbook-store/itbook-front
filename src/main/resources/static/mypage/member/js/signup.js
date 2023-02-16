async function checkMemberIdDuplicate() {
    let memberId = document.getElementById('memberId').value;
    let isExists = false;

    if(!checkKor(memberId)) {
        return false;
    }

    if(!blankCheck(memberId)) {
        return false;
    }

    if(!checkMemberId(memberId)) {
        return false;
    }

    const request = {
        method: "GET"
    };

    await fetch(`/async/signup/memberId/${memberId}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });

    if (!isExists) {
        document.getElementById('memberIdExists').style.display = 'none';
        document.getElementById('memberIdNotExists').style.display = 'block';
        document.getElementById('memberIdCheckBtn').disabled = true;
        document.getElementById('memberId').readOnly = true;
        document.getElementById('memberIdRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('memberId').value = '';
        document.getElementById('memberIdExists').style.display = 'block';
    }
}

async function checkNicknameDuplicate() {
    let nickname = document.getElementById('nickname').value;
    let isExists = false;

    if(!blankCheck(nickname)) {
        return false;
    }
    if(!checkNickname(nickname)) {
        return false;
    }

    const request = {
        method: "GET"
    };

    await fetch(`/async/signup/nickname/${nickname}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });

    if (!isExists) {
        document.getElementById('nicknameExists').style.display = 'none';
        document.getElementById('nicknameNotExists').style.display = 'block';
        document.getElementById('nicknameCheckBtn').disabled = true;
        document.getElementById('nickname').readOnly = true;
        document.getElementById('nicknameRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('nickname').value = '';
        document.getElementById('nicknameExists').style.display = 'block';
    }
}

async function checkPhoneNumberDuplicate() {
    let phoneNumber = document.getElementById('phoneNumber').value;
    let isExists = false;

    if(!checkSpecial(phoneNumber)) {
        return false;
    }

    if(!checkPhoneNumber(phoneNumber)) {
        return false;
    }

    blankCheck(phoneNumber);

    const request = {
        method: "GET"
    };

    await fetch(`/async/signup/phoneNumber/${phoneNumber}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });
    if (!isExists) {
        document.getElementById('phoneNumberExists').style.display = 'none';
        document.getElementById('phoneNumberNotExists').style.display = 'block';
        document.getElementById('phoneNumberCheckBtn').disabled = true;
        document.getElementById('phoneNumber').readOnly = true;
        document.getElementById('phoneNumberRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('phoneNumber').value = '';
        document.getElementById('phoneNumberExists').style.display = 'block';
    }
}

async function checkEmailDuplicate() {
    let email = document.getElementById('email').value;
    let isExists = false;

    if(!checkKor(email)) {
        return false;
    }

    if(!checkEmail(email)) {
        return false;
    }

    blankCheck(email);

    const request = {
        method: "GET"
    };

    await fetch(`/async/signup/email/${email}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });
    if (!isExists) {
        document.getElementById('emailExists').style.display = 'none';
        document.getElementById('emailNotExists').style.display = 'block';
        document.getElementById('emailCheckBtn').disabled = true;
        document.getElementById('email').readOnly = true;
        document.getElementById('emailRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('email').value = '';
        document.getElementById('emailExists').style.display = 'block';
    }
}

function retypeFn(text, existMsg, notExistMsg, checkBtn, retypeBtn) {
    document.getElementById(text).value = '';
    document.getElementById(text).readOnly = false;
    document.getElementById(existMsg).style.display = 'none';
    document.getElementById(notExistMsg).style.display = 'none';
    document.getElementById(checkBtn).disabled = false;
    document.getElementById(retypeBtn).style.visibility = 'hidden';
}

function signUpSubmit() {

    if(!checkName()) {
        return false;
    }

    if(!checkPassword(document.getElementById("password").value)) {
        Swal.fire('비밀번호 형식에 맞지 않습니다.', '', 'error');
        return false;
    }

    if(document.getElementById("password").value != document.getElementById("passwordCheck").value) {
        Swal.fire('비밀번호가 맞지 않습니다. 다시 한번 입력해주세요.', '', 'error');
        return false;
    }

    if(!checkGender()) {
        Swal.fire('성별을 체크해야 합니다.', '', 'error');
        return false;
    }

    if(!checkBirth()) {
        Swal.fire('생일 입력 형식에 맞지 않습니다.', '', 'error');
        return false;
    }

    if (document.getElementById("memberIdCheckBtn").disabled == false
        || document.getElementById("nicknameCheckBtn").disabled == false
        || document.getElementById("phoneNumberCheckBtn").disabled == false
        || document.getElementById("emailCheckBtn").disabled == false) {
        Swal.fire('중복체크가 되지 않은 곳이 있습니다.', '', 'error');

    } else {
        Swal.fire('회원가입을 축하합니다!', '', 'success');
        document.getElementById("signupForm").submit();
    }
}

function socialLoginSubmit() {

    if(!checkGender()) {
        Swal.fire('성별을 체크해야 합니다.', '', 'error');
        return false;
    }

    if(!checkBirth()) {
        Swal.fire('생일 입력 형식에 맞지 않습니다.', '', 'error');
        return false;
    }

    if (document.getElementById("nicknameCheckBtn").disabled == false
        || document.getElementById("phoneNumberCheckBtn").disabled == false) {
        Swal.fire('중복체크가 되지 않은 곳이 있습니다.', '', 'error');
    } else {
        Swal.fire('회원가입을 축하합니다!', '', 'success');
        document.getElementById("socialLoginForm").submit();
    }
}

function blankCheck(str) {
    //공백만 입력된 경우
    let blank_pattern1 = /^\s+|\s+$/g;
    if(str.replace(blank_pattern1, '' ) === "" ){
        Swal.fire('공백만 입력되었습니다.', '', 'error');
        return false;
    }

    //문자열에 공백이 있는 경우
    let blank_pattern2 = /[\s]/g;
    if( blank_pattern2.test(str) == true){
        Swal.fire('공백이 입력되었습니다.', '', 'error');
        return false;
    }

    return true;
}

function checkKor(str) {
    const regExp = /[ㄱ-ㅎㅏ-ㅣ가-힣]/g;
    if(regExp.test(str)){
        Swal.fire('한글이 입력되었습니다.', '', 'error');
        return false;
    }
    return true;
}

function checkSpecial(str) {
    const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g;
    if(regExp.test(str)) {
        Swal.fire('특수문자가 입력되었습니다.', '', 'error');
        return false;
    }

    return true;
}

function checkNumber(str) {
    const regExp = /^[^0-9]+$/;

    if(!regExp.test(str)) {
        Swal.fire('숫자가 입력되었습니다.', '', 'error');
        return false;
    }

    return true;

}

function checkMemberId(str) {
    const regExp = /^[a-z0-9-_]{2,15}$/;
    if(!regExp.test(str)) {
        Swal.fire('아이디 형식에 맞지 않습니다.', '', 'error');
        return false;
    }

     return true;
}

function checkNickname(str) {
    if(str.length < 2 || str.length > 20) {
        Swal.fire('닉네임 형식에 맞지 않습니다.', '', 'error');
        return false;
    }

    return true;
}

function checkName() {

    let name = document.getElementById("name").value;

    if(!blankCheck(name)) {
        return false;
    }

    if(!checkNumber(name)) {
        return false;
    }

    if(!checkSpecial(name)) {
        Swal.fire('이름에 특수문자가 입력되었습니다.', '', 'error');
        return false;
    }

    if(name.length > 20) {
        Swal.fire('이름은 최대 20자까지 허용합니다.', '', 'error');
        return false;
    }

    return true;
}

function checkPhoneNumber(str) {
    const regExp = /[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]/g;

    if(regExp.test(str) || document.getElementById("phoneNumber").value.length != 11){
        Swal.fire('전화번호 형식으로 입력해야 합니다.', '', 'error');
        return false;
    }
    return true;
}

function checkGender() {
    let female = document.getElementById("femaleGender").checked;
    let male = document.getElementById("maleGender").checked;

    if(female === false && male === false) {
        return false;
    }

    return true;
}

function checkBirth() {
    let birth = document.getElementById("birth").value;

    if(!birth instanceof Date || !isNaN(birth)) {
        return false;
    }

    return true;
}

function checkEmail(str) {
    const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if(!regExp.test(str)) {
        Swal.fire('이메일 형식이 아닙니다.', '', 'error');
        return false;
    }

    return true;
}

function checkPassword(str) {
    const regExp = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*_+=-]).{9,255}$/i;
    if(!regExp.test(str)) {
        return false;
    }

    return true;
}

function inputDetection(id) {
    document.getElementById(id).style.visibility ='visible';
}

function checkMemberModifyForm() {

    let nickname = document.getElementById("nickname").value;
    let phoneNumber = document.getElementById("phoneNumber").value;
    let email = document.getElementById("email").value;

    if(!checkName()) {
        return false;
    }

    if(!checkNickname(nickname)) {
        return false;
    }

    if(!checkPhoneNumber(phoneNumber)) {
        return false;
    }

    if(!checkEmail(email)) {
        return false;
    }

    Swal.fire('성공적으로 수정되었습니다!', '', 'success');
    document.getElementById("modifyForm").submit();

}