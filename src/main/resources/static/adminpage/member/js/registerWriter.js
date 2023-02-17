function registerWriter() {

    if(document.getElementById('findBtn').disabled == false) {
        alert("아이디 찾기 버튼을 눌러 유효한 아이디를 입력해주세요.")
        return false;
    }

    const name = findName();

    console.log(name);

    const getData = () => {
        name.then((isExists) => {
            if(isExists === false) {
                alert("입력하신 이름과 해당 계정의 이름과 맞지 않습니다.")
                return false;
            } else {
                alert("성공적으로 작가 등록을 마쳤습니다.");
                //document.getElementById('registerWriterForm').submit();
            }
        });
    };

    getData();
}

async function findName() {

    let name = document.getElementById('writerName').value;
    let memberId = document.getElementById('findMemberId').value;

    let isExists = false;

    const request = {
        method: "GET"
    };

    await fetch(`/async/check/name/${memberId}/${name}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });

    console.log(isExists);

    return isExists;
}

async function findMember() {

    document.getElementById("sendBtn").disabled = false;

    let memberId = document.getElementById('findMemberId').value;
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
        document.getElementById("findMemberId").value = '';
        document.getElementById('notExistsMemberMsg').style.display = 'block';
    } else {
        document.getElementById('notExistsMemberMsg').style.display = 'none';
        document.getElementById("findMemberId").readOnly = true;
        document.getElementById('existsMemberMsg').style.display = 'block';
        document.getElementById('findBtn').disabled = true;
        document.getElementById('reFindBtn').style.display = 'block';

    }
}

function reFindMember() {
    document.getElementById('findBtn').disabled = false;
    document.getElementById('reFindBtn').style.display = 'none';
    document.getElementById('existsMemberMsg').style.display = 'none';
    document.getElementById("findMemberId").readOnly = false;
    document.getElementById("findMemberId").value = '';
}