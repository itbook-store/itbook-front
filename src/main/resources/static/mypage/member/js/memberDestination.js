function memberDestinationRegister() {
    if (document.getElementById("registerMemberDestination").style.display == 'none') {
        document.getElementById("registerBtn").value = '취소하기';
        document.getElementById("registerBtn").className = 'btn btn-dark';
        document.getElementById("registerMemberDestination").style.display = 'block';
    } else {
        document.getElementById("registerBtn").value = '주소 등록하기';
        document.getElementById("registerBtn").className = 'btn btn-primary';
        document.getElementById("registerMemberDestination").style.display = 'none';
    }
}

function memberDestinationModify() {


    /*document.getElementById("registerMemberDestination").style.display='none';
    document.getElementById("registerBtn").value = '주소 등록하기';
    document.getElementById("registerBtn").className = 'btn btn-primary';*/

    if(document.getElementById("modifyMemberDestination").style.display =='none') {
        /*document.getElementById("registerBtn").style.display = 'none';*/
        document.getElementById("modifyBtn").value = '취소하기';
        document.getElementById("modifyBtn").className = 'btn btn-dark';
        /*document.getElementById("modifyMemberDestination").style.display = 'block';*/
    } else {
        /*document.getElementById("registerBtn").style.display = 'block';*/
        document.getElementById("modifyBtn").value = '배송지 수정하기';
        document.getElementById("modifyBtn").className = 'btn btn-primary';
        /*document.getElementById("modifyMemberDestination").style.display = 'none';*/
    }
}

function validationCheck() {

    let recipientName = document.getElementById("recipientName").value;
    let recipientPhoneNumber = document.getElementById("recipientPhoneNumber").value;
    let postcode = document.getElementById("postcode").value;
    let roadAddress = document.getElementById("roadAddress").value;
    let detailAddress = document.getElementById("detailAddress").value;

    if (!checkRecipientName(recipientName)
        || !checkRecipientPhoneNumber(recipientPhoneNumber)
        || !checkPostCode(postcode)
        || !checkRoadNameAddress(roadAddress)
        || !checkRecipientAddressDetails(detailAddress)) {
        return false;
    } else {
        alert("배송지를 등록하였습니다.");
        document.getElementById("memberDestinationForm").submit();
    }
}

function modifyValidationCheck() {

    let recipientName = document.getElementById("recipientNameModify").value;
    let recipientPhoneNumber = document.getElementById("recipientPhoneNumberModify").value;
    let postcode = document.getElementById("postcodeModify").value;
    let roadAddress = document.getElementById("roadAddressModify").value;
    let detailAddress = document.getElementById("detailAddressModify").value;

    if (!checkRecipientName(recipientName)
        || !checkRecipientPhoneNumber(recipientPhoneNumber)
        || !checkPostCode(postcode)
        || !checkRoadNameAddress(roadAddress)
        || !checkRecipientAddressDetails(detailAddress)) {
        return false;
    } else {
        alert("배송지가 수정되었습니다.");
        document.getElementById("modifyMemberDestinationForm").submit();
    }
}

function checkRecipientName(str) {
    if (str.length < 1 || str.length > 20) {
        alert("수령인 이름은 1자부터 20자 사이로 입력해야 합니다.");
        return false;
    }

    return true;
}

function checkRecipientPhoneNumber(str) {
    const regExp = /[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]/g;
    if (regExp.test(str) || str.length < 1) {
        alert("전화번호 형식으로 입력해야 합니다.");
        return false;
    }

    return true;
}

function checkPostCode(str) {
    if (str == null) {
        alert("우편번호를 입력해야 합니다.");
        return false;
    }

    return true;
}

function checkRoadNameAddress(str) {
    if (str.length < 1 || str.length > 255) {
        alert("도로명 주소는 1자부터 255자 사이로 입력해야 합니다.");
        return false;
    }

    return true;
}

function checkRecipientAddressDetails(str) {
    if (str.length < 1 || str.length > 255) {
        alert("상세 주소는 1자부터 255자 사이로 입력해야 합니다.");
        return false;
    }

    return true;
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            let roadAddr = data.roadAddress; // 도로명 주소 변수
            let extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("roadAddress").value = roadAddr;

        }
    }).open();
}

let openWin;

function openDestinationModifyWindow() {
    window.name = "memberDestinationModify";
    openWin = window.open("modify/memberDestination", "passwordModify", "width=600, height=400, resizable = no, scrollbars = no");
}
