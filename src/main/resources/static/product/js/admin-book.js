/* [spinnerStart 시작 이벤트 호출] */
function spinnerStart() {

    // [로딩 부모 컨테이너 동적 생성]
    var createLayDiv = document.createElement("div");
    createLayDiv.setAttribute("id", "spinnerLay1000");
    var createLayDivStyle = "width:100%; height:100%; margin:0 auto; padding:0; border:none;";
    createLayDivStyle = createLayDivStyle + " float:top; position:fixed; top:0%; z-index:1000;";
    createLayDivStyle = createLayDivStyle + " background-color:rgba(0, 0, 0, 0.3);"; // 불투명도 설정 >> 자식에게 적용 안됨
    createLayDiv.setAttribute("style", createLayDivStyle);
    document.body.appendChild(createLayDiv); //body에 추가 실시


    // [실제 스핀 수행 컨테이너 동적 생성]
    var createSpinDiv = document.createElement("div");
    createSpinDiv.setAttribute("id", "spinnerContainer1000");
    var createSpinDivStyle = "width:100px; height:100px; margin:0 auto; padding:0; border:none;"; // 스핀 컨테이너 크기 조절
    createSpinDivStyle = createSpinDivStyle + " float:top; position:relative; top:40%;";
    //createSpinDivStyle = createSpinDivStyle + " background-color:#ff0000;";
    createSpinDiv.setAttribute("style", createSpinDivStyle);
    document.getElementById("spinnerLay1000").appendChild(createSpinDiv); //spinnerLay에 추가 실시


    // [스핀 옵션 지정 실시]
    var opts = {
        lines: 10, // 그릴 선의 수 [20=원형 / 10=막대] [The number of lines to draw]
        length: 10, // 각 줄의 길이 [0=원형 / 10=막대] [The length of each line]
        width: 15, // 선 두께 [The line thickness]
        radius: 42, // 내부 원의 반지름 [The radius of the inner circle]
        scale: 0.85, // 스피너의 전체 크기 지정 [Scales overall size of the spinner]
        corners: 1, // 모서리 라운드 [Corner roundness (0..1)]
        color: '#6868ab', // 로딩 CSS 색상 [CSS color or array of colors]
        fadeColor: 'transparent', // 로딩 CSS 색상 [CSS color or array of colors]
        opacity: 0.05, // 선 불투명도 [Opacity of the lines]
        rotate: 0, // 회전 오프셋 각도 [The rotation offset]
        direction: 1, // 회전 방향 시계 방향, 반시계 방향 [1: clockwise, -1: counterclockwise]
        speed: 1, // 회전 속도 [Rounds per second]
        trail: 74, // 꼬리 잔광 비율 [Afterglow percentage]
        fps: 20, // 초당 프레임 수 [Frames per second when using setTimeout() as a fallback in IE 9]
        zIndex: 2e9 // 인덱스 설정 [The z-index (defaults to 2000000000)]
    };


    // [스피너 매핑 및 실행 시작]
    var target = document.getElementById("spinnerContainer1000");
    var spinner = new Spinner(opts).spin(target);
}


/* [spinnerStop 중지 이벤트 호출] */
function spinnerStop() {
    try {
        // [로딩 부모 컨테이너 삭제 실시]
        var tagId = document.getElementById("spinnerLay1000");
        document.body.removeChild(tagId); //body에서 삭제 실시
    } catch (exception) {
        // console.error("catch : " + "not find spinnerLay1000");
    }

}

function selectEbook() {
    let ebookDiv = document.getElementById("selectEbook");
    ebookDiv.style.display = "block";
}

function notSelectEbook() {
    let ebookDiv = document.getElementById("selectEbook");
    ebookDiv.style.display = "none";
}

async function showSearchResults(event) {

    event.preventDefault();

    let numberExpression = /[0-9]/;
    let isbn = document.getElementById('isbn').value;

    if (!numberExpression.test(isbn)) {
        alert("isbn 입력 형식이 올바르지 않습니다. 다시 입력해주세요!"); //false
    } else {

        let isExistsInDb = false;
        let isExistsInAladin = false;

        spinnerStart();

        await fetch(`/async/books/exist-db?isbn=${isbn}`, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {
                isExistsInDb = data.isExists;
            });

        await fetch(`/async/books/exist-aladin?isbn=${isbn}`, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {
                isExistsInAladin = data.isExists;
            });

        document.getElementById('search-results').style.display = "block";

        // 알라딘에 존재하고 db에 없으면 등록 가능
        if (isExistsInAladin && !isExistsInDb) {
            let bookTitle;
            await fetch(`/async/books?isbn=${isbn}`, {
                method: "GET"
            })
                .then(response => response.json())
                .then(data => {
                    document.getElementById('productName').value = data.title;
                    document.getElementById('authorName').value = data.author;
                    document.getElementById('simpleDescription').value = unEntity(data.description);
                    document.getElementById('fixedPrice').value = data.priceStandard;
                    document.getElementById('publisherName').value = data.publisher;
                    document.getElementById('pageCount').value = data.subInfo.itemPage;
                    document.getElementById('bookCreatedAt').value = data.pubDate;

                });
            document.getElementById('isbnFailed').style.display = 'none';
            document.getElementById('isbnSuccessful').style.display = 'block';
            document.getElementById('confirmBook').disabled = true;
            document.getElementById('isbn').readOnly = true;
            document.getElementById('isbnRetypeBtn').style.visibility = 'visible';
        } else { // 등록 실패
            document.getElementById('isbn').value = '';
            document.getElementById('isbnFailed').style.display = 'block';
            document.getElementById('isbnSuccessful').style.display = 'none';
            document.getElementById('confirmBook').disabled = false;
            document.getElementById('isbn').readOnly = false;
            document.getElementById('isbnRetypeBtn').style.visibility = 'visible';
        }
        spinnerStop();
    }
}

function retypeFn(text, existMsg, notExistMsg, checkBtn, retypeBtn) {
    document.getElementById('search-results').style.display = "none";
    document.getElementById(text).value = '';
    document.getElementById(text).readOnly = false;
    document.getElementById(existMsg).style.display = 'none';
    document.getElementById(notExistMsg).style.display = 'none';
    document.getElementById(checkBtn).disabled = false;
    document.getElementById(retypeBtn).style.visibility = 'hidden';
    document.getElementById('productName').value = '';
    document.getElementById('authorName').value = '';
    document.getElementById('simpleDescription').value = '';
    document.getElementById('fixedPrice').value = '';
    document.getElementById('publisherName').value = '';
    document.getElementById('pageCount').value = '';
    document.getElementById('bookCreatedAt').value = '';
}

function unEntity(str) {
    return str.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
}

function addBookSubmit() {

    let checkBox = document.getElementsByName("categoryNoList");
    let name = document.getElementById("productName").value;
    let simpleDescription = document.getElementById("simpleDescription").textContent;
    let detailsDescription = document.getElementById("detailsDescription").textContent;
    let increasePointPercent = document.getElementById("increasePointPercent").value;
    let rawPrice = document.getElementById("rawPrice").value;
    let fixedPrice = document.getElementById("fixedPrice").value;
    let discountPercent = document.getElementById("discountPercent").value;
    let stock = document.getElementById("stock").value;
    let isPointApplyingValue = $('input[name="isPointApplying"]:checked').val();
    let isPointApplyingBasedSellingPriceValue = $('input[name="isPointApplyingBasedSellingPrice"]:checked').val();
    let publisherName = document.getElementById("publisherName").value;
    let authorName = document.getElementById("authorName").value;
    let isEbookValue = $('input[name="isEbook"]:checked').val();
    let pageCount = document.getElementById("pageCount").value;
    let ebookFile = document.getElementById("fileEbook").value;

    console.log(discountPercent);
    console.log(discountPercent.length);

    if (!checkCheckBoxCountUpTo0(checkBox)) {
        Swal.fire('카테고리는 최소 1개를 지정해야만 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownTo255(name)) {
        Swal.fire('상품 이름 길이는 255자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownTo255(simpleDescription)) {
        Swal.fire('간단 설명 길이는 255자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (isPointApplyingValue == "true") {

        if (!checkNumberOfPercent(increasePointPercent)) {
            Swal.fire('포인트 적립율은 0 ~ 100%이어야 합니다!', '', 'error');
            return false;
        }

        if (isPointApplyingBasedSellingPriceValue == null) {
            Swal.fire('포인트 적립 시 포인트 적립 기준 체크는 필수 항목입니다!', '', 'error');
            return false;
        }

    }

    if (isEbookValue == "true") {

        if (!ebookFile) {
            Swal.fire('ebook일 경우 ebook 첨부는 필수입니다!', '', 'error');
            return false;
        }
    }

    if (!checkNumberUpTo0(pageCount)) {
        Swal.fire('페이지는 0p 이상이어야 합니다!', '', 'error');
        return false;
    }


    if (!checkNumberUpTo0(rawPrice)) {
        Swal.fire('원가는 0원 이상이어야 합니다!', '', 'error');
        return false;
    }

    if (!checkNumberUpTo0(fixedPrice)) {
        Swal.fire('정가는 0원 이상이어야 합니다!', '', 'error');
        return false;
    }

    if (!checkNumberOfPercent(discountPercent)) {
        console.log("할인율")
        Swal.fire('할인율은 0 ~ 100%이어야 합니다!', '', 'error');
        return false;
    }

    if (document.getElementById("confirmBook").disabled == false) {
        alert("등록 가능한 isbn인지 확인이 필요합니다.");
        return false;
    }

    if (!checkNumberUpTo0(stock)) {
        Swal.fire('재고는 0개 이상이어야 합니다!', '', 'error');
        return false;
    }


    if (!checkStringLengthDownTo255(authorName)) {
        Swal.fire('저자는 255자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownTo20(publisherName)) {
        Swal.fire('출판사 명은 20자 이하여야 합니다!', '', 'error');
        return false;
    }

    Swal.fire('도서 등록 성공!', '', 'success');
    return true;

}

function modifyBookSubmit() {

    let checkBox = document.getElementsByName("categoryNoList");
    let name = document.getElementById("productName").value;
    let simpleDescription = document.getElementById("simpleDescription").textContent;
    let detailsDescription = document.getElementById("detailsDescription").textContent;
    let increasePointPercent = document.getElementById("increasePointPercent").value;
    let rawPrice = document.getElementById("rawPrice").value;
    let fixedPrice = document.getElementById("fixedPrice").value;
    let discountPercent = document.getElementById("discountPercent").value;
    let stock = document.getElementById("stock").value;
    let isPointApplyingValue = $('input[name="isPointApplying"]:checked').val();
    let isPointApplyingBasedSellingPriceValue = $('input[name="isPointApplyingBasedSellingPrice"]:checked').val();
    let publisherName = document.getElementById("publisherName").value;
    let authorName = document.getElementById("authorName").value;
    let isEbookValue = $('input[name="isEbook"]:checked').val();
    let pageCount = document.getElementById("pageCount").value;
    let ebookFile = document.getElementById("fileEbook").value;

    console.log(discountPercent);
    console.log(discountPercent.length);

    if (!checkCheckBoxCountUpTo0(checkBox)) {
        Swal.fire('카테고리는 최소 1개를 지정해야만 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownTo255(name)) {
        Swal.fire('상품 이름 길이는 255자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownTo255(simpleDescription)) {
        Swal.fire('간단 설명 길이는 255자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (isPointApplyingValue == "true") {

        if (!checkNumberOfPercent(increasePointPercent)) {
            Swal.fire('포인트 적립율은 0 ~ 100%이어야 합니다!', '', 'error');
            return false;
        }

        if (isPointApplyingBasedSellingPriceValue == null) {
            Swal.fire('포인트 적립 시 포인트 적립 기준 체크는 필수 항목입니다!', '', 'error');
            return false;
        }

    }

    if (isEbookValue == "true") {

        if (!ebookFile) {
            Swal.fire('ebook일 경우 ebook 첨부는 필수입니다!', '', 'error');
            return false;
        }
    }

    if (!checkNumberUpTo0(pageCount)) {
        Swal.fire('페이지는 0p 이상이어야 합니다!', '', 'error');
        return false;
    }


    if (!checkNumberUpTo0(rawPrice)) {
        Swal.fire('원가는 0원 이상이어야 합니다!', '', 'error');
        return false;
    }

    if (!checkNumberUpTo0(fixedPrice)) {
        Swal.fire('정가는 0원 이상이어야 합니다!', '', 'error');
        return false;
    }

    if (!checkNumberOfPercent(discountPercent)) {
        Swal.fire('할인율은 0 ~ 100%이어야 합니다!', '', 'error');
        return false;
    }

    if ($("#confirmBook").attr("disabled") == false) {
        alert("등록 가능한 isbn인지 확인이 필요합니다.");
        return false;
    }

    if (!checkNumberUpTo0(stock)) {
        Swal.fire('재고는 0개 이상이어야 합니다!', '', 'error');
        return false;
    }


    if (!checkStringLengthDownTo255(authorName)) {
        Swal.fire('저자는 255자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownTo20(publisherName)) {
        Swal.fire('출판사 명은 20자 이하여야 합니다!', '', 'error');
        return false;
    }

    Swal.fire('도서 수정 성공!', '', 'success');
    return true;

}