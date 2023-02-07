async function findMember() {

    let memberId = document.getElementById('receiveMemberId').value;
    let isExists = false;

    if(memberId === document.getElementById('checkMemberId').value) {
        alert("자신의 아이디는 입력할 수 없습니다.")
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

    if(!isExists) {
        document.getElementById('existsMemberMsg').style.display = 'none';
        document.getElementById("receiveMemberId").value = '';
        document.getElementById('notExistsMemberMsg').style.display = 'block';
    } else {
        document.getElementById('notExistsMemberMsg').style.display = 'none';
        document.getElementById("receiveMemberId").readOnly = true;
        document.getElementById('existsMemberMsg').style.display = 'block';
        document.getElementById('findBtn').disabled = true;
        document.getElementById('reFindBtn').style.display = 'block';

    }
}

function reFindMember() {
    document.getElementById('findBtn').disabled = false;
    document.getElementById('reFindBtn').style.display = 'none';
    document.getElementById('existsMemberMsg').style.display = 'none';
    document.getElementById("receiveMemberId").readOnly = false;
    document.getElementById("receiveMemberId").value = '';
}

function sendPoint() {

    let regExp = /^[0-9]+$/;
    let recentlyPoint = document.getElementById('recentlyPoint').innerText * 1;
    let inputPoint = document.getElementById('giftPoint').value * 1;

    console.log(recentlyPoint);
    console.log(inputPoint)

    if(document.getElementById('findBtn').disabled == false) {
        alert("아이디 찾기 버튼을 눌러 유효한 아이디를 입력해주세요.")
        return false;
    }

    if(inputPoint === 0) {
        alert("선물할 포인트를 입력해주세요.")
        return false;
    }

    if(!regExp.test(inputPoint)) {
        alert("유효한 포인트 값만 입력해야 합니다.")
        return false;
    }

    if(recentlyPoint < inputPoint) {
        alert("소지하신 포인트보다 더 큰 포인트는 선물할 수 없습니다.")
        return false;
    }

    document.getElementById('giftPointForm').submit();
}

function writePoint() {
    document.getElementById("sendBtn").disabled = false;
}