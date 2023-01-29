async function checkMemberIdDuplicate() {
    let memberId = document.getElementById('memberId').value;
    let isExists = false;

    blankCheck(memberId);

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

    blankCheck(nickname);

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

function blankCheck(str) {
    //공백만 입력된 경우
    let blank_pattern1 = /^\s+|\s+$/g;
    if(str.replace(blank_pattern1, '' ) == "" ){
        alert('공백만 입력되었습니다.');
        document.getElementById(str).value = '';
        return false;
    }

    //문자열에 공백이 있는 경우
    let blank_pattern2 = /[\s]/g;
    if( blank_pattern2.test(str) == true){
        alert('공백이 입력되었습니다.');
        document.getElementById(str).value = '';
        return false;
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
    if (document.getElementById("memberIdCheckBtn").disabled == false
        || document.getElementById("nicknameCheckBtn").disabled == false
        || document.getElementById("phoneNumberCheckBtn").disabled == false
        || document.getElementById("emailCheckBtn").disabled == false) {
        alert("중복체크가 되지 않은 곳이 있습니다.");
        return false;
    } else {
        document.getElementById("signupForm").submit();
    }
}