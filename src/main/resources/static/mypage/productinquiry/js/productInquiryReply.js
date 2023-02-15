function submitProductInquiryReply() {

    let title = document.getElementById("title").value;
    let content = document.getElementById("content").value;

    let blank_pattern1 = /^\s+|\s+$/g;

    if(content.replace(blank_pattern1, '' ) == "" || title.replace(blank_pattern1, '') == ""){
        alert('공백만 입력되었습니다.');
        return false;
    }

    alert("리뷰 작성 완료하였습니다.");
    document.getElementById("productInquiryForm").submit();
}