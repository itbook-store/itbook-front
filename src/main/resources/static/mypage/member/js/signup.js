async function checkMemberIdDuplicate() {
    let memberId = document.getElementById('memberId').value;
    let isExists = false;

    const request = {
        method : "GET"
    };

    await fetch(`/async/signup/memberId/${memberId}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });

    console.log(isExists);

    if(!isExists) {
        document.getElementById('memberIdExists').style.display = 'none';
        document.getElementById('memberIdNotExists').style.display = 'block';
        document.getElementById('memberIdCheckBtn').disabled = true;
        document.getElementById('memberId').readonly = true;
        document.getElementById('memberIdRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('memberId').value = '';
        document.getElementById('memberIdExists').style.display = 'block';
    }
}

async function checkNicknameDuplicate() {
    let nickname = document.getElementById('nickname').value;
    let isExists = false;

    const request = {
        method : "GET"
    };

    await fetch(`/async/signup/nickname/${nickname}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });

    if(!isExists) {
        document.getElementById('nicknameExists').style.display = 'none';
        document.getElementById('nicknameNotExists').style.display = 'block';
        document.getElementById('nicknameCheckBtn').disabled = true;
        document.getElementById('nickname').readonly = true;
        document.getElementById('nicknameRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('nickname').value = '';
        document.getElementById('nicknameExists').style.display = 'block';
    }
}

async function checkPhoneNumberDuplicate() {
    let phoneNumber = document.getElementById('phoneNumber').value;
    let isExists = false;

    const request = {
        method : "GET"
    };

    await fetch(`/async/signup/phoneNumber/${phoneNumber}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });
    if(!isExists) {
        document.getElementById('phoneNumberExists').style.display = 'none';
        document.getElementById('phoneNumberNotExists').style.display = 'block';
        document.getElementById('phoneNumberCheckBtn').disabled = true;
        document.getElementById('phoneNumber').readonly = true;
        document.getElementById('phoneNumberRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('phoneNumber').value = '';
        document.getElementById('phoneNumberExists').style.display = 'block';
    }
}

async function checkEmailDuplicate() {
    let email = document.getElementById('email').value;
    let isExists = false;

    const request = {
        method : "GET"
    };

    await fetch(`/async/signup/email/${email}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });
    if(!isExists) {
        document.getElementById('emailExists').style.display = 'none';
        document.getElementById('emailNotExists').style.display = 'block';
        document.getElementById('emailCheckBtn').disabled = true;
        document.getElementById('email').readonly = true;
        document.getElementById('emailRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('email').value = '';
        document.getElementById('emailExists').style.display = 'block';
    }
}

function retypeFn(text, existMsg, notExistMsg, checkBtn, retypeBtn) {
    document.getElementById(text).value = '';
    document.getElementById(text).readonly = false;
    document.getElementById(existMsg).style.display = 'none';
    document.getElementById(notExistMsg).style.display = 'none';
    document.getElementById(checkBtn).disabled = false;
    document.getElementById(retypeBtn).style.visibility = 'hidden';
}