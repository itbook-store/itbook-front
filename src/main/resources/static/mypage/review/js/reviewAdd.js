function submitReview() {
    let content = document.getElementById("reviewContents").value;

    let blank_pattern1 = /^\s+|\s+$/g;

    if(document.getElementById("rate1").checked == false &&
        document.getElementById("rate2").checked == false &&
        document.getElementById("rate3").checked == false &&
        document.getElementById("rate4").checked == false &&
        document.getElementById("rate5").checked == false) {
        alert("별점 선택이 되지 않았습니다.");
        return false;
    }

    if(content.replace(blank_pattern1, '' ) == "" ){
        alert('공백만 입력되었습니다.');
        return false;
    }

    alert("리뷰 작성 완료하였습니다.");
    document.getElementById("reviewForm").submit();
}

function submitModifyReview() {
    let content = document.getElementById("reviewContents").value;

    let blank_pattern1 = /^\s+|\s+$/g;

    if(document.getElementById("rate1").checked == false &&
        document.getElementById("rate2").checked == false &&
        document.getElementById("rate3").checked == false &&
        document.getElementById("rate4").checked == false &&
        document.getElementById("rate5").checked == false) {
        alert("별점 선택이 되지 않았습니다.");
        return false;
    }

    if(content.replace(blank_pattern1, '' ) == "" ){
        alert('공백만 입력되었습니다.');
        return false;
    }

    alert("리뷰 작성 완료하였습니다.");
    document.getElementById("reviewModifyForm").submit();
}