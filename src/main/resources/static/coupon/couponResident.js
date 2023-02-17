function divBlock(name) {
    document.getElementById(name + 'Div').style.display = 'block';
    document.getElementById(name).value = null;
}

function divNone(name) {
    document.getElementById(name + 'Div').style.display = 'none';
    document.getElementById(name).value = 0;
}

function divNull(name) {
    document.getElementById(name + 'Div').style.display = 'none';
    document.getElementById(name).value = null;
}

function radioCheck(name) {
    return document.getElementById(name + 'Radio').checked === true;
}

function selectCouponOption() {
    if (radioCheck('point')) {
        divBlock('point');

        divNull('min');
        divNull('max');
        divNone('amount');
        divNone('percent');
        document.getElementById("couponCoverageDiv").style.display = 'none';
        document.getElementById('pointCoverageRadio').checked = true;
    } else if (radioCheck('percent')) {
        divBlock('percent');
        divBlock('min');
        divBlock('max');

        divNone('point');
        divNone('amount');
        document.getElementById("couponCoverageDiv").style.display = 'block';
        document.getElementById('pointCoverageRadio').checked = false;


    } else {
        divBlock('amount');
        divBlock('min');

        divNone('point');
        divNone('percent');
        divNull('max');
        document.getElementById("couponCoverageDiv").style.display = 'block';
        document.getElementById('pointCoverageRadio').checked = false;


    }
}

function selectCouponCoverage() {
    if (radioCheck('allProduct')) {

        document.getElementById("selectCategoryDiv").style.display = 'none';
        document.getElementById("selectProductDiv").style.display = 'none';
    } else if (radioCheck('category')) {

        document.getElementById("selectCategoryDiv").style.display = 'block';
        document.getElementById("selectProductDiv").style.display = 'none';
    } else {

        document.getElementById("selectCategoryDiv").style.display = 'none';
        document.getElementById("selectProductDiv").style.display = 'block';
    }
}

function selectCouponDiv(select) {
    document.getElementById("normalCouponDiv").style.display = 'none';
    document.getElementById("membershipMonthlyCouponDiv").style.display = 'none';

    if (select.value === "일반쿠폰") {
        document.getElementById("normalCouponDiv").style.display = 'block';

    } else if (select.value === "이달의쿠폰등급형") {
        document.getElementById("membershipMonthlyCouponDiv").style.display = 'block';
    } else {

    }
}

async function checkMemberIdDuplicateInCoupon() {
    let memberId = document.getElementById('memberId').value;
    let isExists = false;

    if(!checkKor(memberId)) {
        return false;
    }

    blankCheck(memberId);

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

    if (isExists) {
        document.getElementById('memberIdExists').style.display = 'block';
        document.getElementById('memberIdNotExists').style.display = 'none';
        document.getElementById('memberIdCheckBtn').disabled = true;
        document.getElementById('memberId').readonly = true;
        document.getElementById('memberIdRetypeBtn').style.visibility = 'visible';
    } else {
        document.getElementById('memberId').value = '';
        document.getElementById('memberIdNotExists').style.display = 'block';
    }
}

function retypeFnCheck(text, existMsg, notExistMsg, checkBtn, retypeBtn) {
    document.getElementById(text).value = '';
    document.getElementById(text).readOnly = false;
    document.getElementById(existMsg).style.display = 'none';
    document.getElementById(notExistMsg).style.display = 'none';
    document.getElementById(checkBtn).disabled = false;
    document.getElementById(retypeBtn).style.visibility = 'hidden';
}

async function showSubCategory(event, categoryNoList) {

    event.preventDefault();

    let mainCategoryNo = document.getElementById("mainCategory").value;

    let subCategoryCheckBoxDiv = document.getElementById("categoryCheckBox");
    while (subCategoryCheckBoxDiv.hasChildNodes()) {
        subCategoryCheckBoxDiv.removeChild(subCategoryCheckBoxDiv.firstChild);
    }
    subCategoryCheckBoxDiv.style.display = "block";

    console.log(categoryNoList);
    await fetch(`/async/${mainCategoryNo}/sub-categories`, {
        method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)
            data.forEach(checkBoxList => {
                if (checkBoxList.level !== 0) {
                    let checkBox = document.createElement("input");
                    let checkBoxText = document.createElement("label");
                    checkBox.type = "radio";
                    checkBox.name = "categoryNo";
                    checkBox.value = checkBoxList.categoryNo;
                    checkBox.style = "margin-right : 4px";
                    checkBoxText.style = "margin-right : 7px";
                    if (categoryNoList && categoryNoList.length != 0)
                        if (categoryNoList.some(no => no == checkBoxList.categoryNo)) {
                            checkBox.checked = true;
                            console.log(categoryNoList);
                        }
                    checkBoxText.innerText = checkBoxList.categoryName;
                    subCategoryCheckBoxDiv.appendChild(checkBox);
                    subCategoryCheckBoxDiv.appendChild(checkBoxText);
                }
            });
        });

}


async function showSearchProductList(event) {

    event.preventDefault();

    let productName = document.getElementById("selectProduct").value;

    let productBox = document.getElementById("productRadioBox");
    while (productBox.hasChildNodes()) {
        productBox.removeChild(productBox.firstChild);
    }
    productBox.style.display = "block";

    console.log(productName)
    await fetch(`/async/products/search?name=`+productName, {
        method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            data.forEach(productList => {
                    let radioBox = document.createElement("input");
                    let radioBoxText = document.createElement("label");
                    radioBox.type = "radio";
                    radioBox.name = "productNo";
                    radioBox.value = productList.productNo;
                    radioBox.style = "margin-right : 4px";
                    radioBoxText.style = "margin-right : 7px";
                    radioBoxText.innerText = productList.name;
                productBox.appendChild(radioBox);
                productBox.appendChild(radioBoxText);

            });
        });

}

function addCouponSubmit() {

    let categoryRadioBox = document.getElementsByName("categoryNo");
    let productRadioBox = document.getElementsByName("productNo");
    let selectCoupon = document.getElementById("selectCoupon").value;
    let memberId = document.getElementById("memberId").value;
    let selectMembership = document.getElementById("selectMembership").textContent;
    let name = document.getElementById("name").value;
    let point = document.getElementById("point").value;
    let percent = document.getElementById("percent").value;
    let amount = document.getElementById("amount").value;
    let couponCreatedAt = document.getElementById("couponCreatedAt").value;
    let couponExpiredAt = document.getElementById("couponExpiredAt").value;
    let usagePeriod = document.getElementById("usagePeriod").value;
    let pointRadio = document.getElementById("pointRadio").checked;
    let percentRadio = document.getElementById("percentRadio").checked;
    let amountRadio = document.getElementById("amountRadio").checked;
    let allProductRadio = document.getElementById("allProductRadio").checked;
    let categoryRadio = document.getElementById("categoryRadio").checked;
    let oneProductRadio = document.getElementById("oneProductRadio").checked;
    let pointCoverageRadio = document.getElementById("pointCoverageRadio").checked;


    if (selectCoupon === "") {
        Swal.fire('쿠폰 종류를 선택해 주세요!', '', 'error');
        return false;
    }
    if (selectCoupon === "일반쿠폰") {
        if (memberId === "") {
            Swal.fire('유저 아이디를 입력해주세요!', '', 'error');
            return false;
        }
    }
    if (selectCoupon === "이달의쿠폰등급형") {
        if (selectMembership === "") {
            Swal.fire('등급을 선택해주세요!', '', 'error');
            return false;
        }
    }
    if(name === ""){
        Swal.fire('쿠폰 이름을 입력해 주세요.', '', 'error');
        return false;

    }
    if (!checkStringLengthDown(name, 20)) {

        Swal.fire('쿠폰 이름 길이는 20자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (pointRadio === true) {

        if (!checkNumberUpToNum(1,point)) {

            Swal.fire('포인트 쿠폰 적립액은 1원 이상 이어야 합니다!', '', 'error');
            return false;
        }
        if (!checkNumberDownToNum(10000000,point)) {

            Swal.fire('포인트 쿠폰 적립액은 최대 천만원까지 입니다.', '', 'error');
            return false;
        }
    }
    else if (amountRadio === true) {

        if (!checkNumberUpToNum(1,amount)) {
            Swal.fire('쿠폰의 할인 금액은 1원 이상 이어야 합니다!', '', 'error');
            return false;
        }

        if (!checkNumberDownToNum(10000000,amount)) {
            Swal.fire('쿠폰의 할인 금액은 최대 천만원까지입니다!', '', 'error');
            return false;
        }

    }

    else if (percentRadio === true) {

        if (!checkNumberOfPercentBetween(1, 101, percent)) {
            Swal.fire('퍼센트 할인액은 1% 이상 이어야 합니다!', '', 'error');
            return false;
        }

    }
    else {

        Swal.fire('쿠폰 종류를 선택해주세요', '', 'error');
        return false;

    }

    if (pointCoverageRadio === true || allProductRadio === true) {

    }
    else if (categoryRadio === true) {

        if (!checkCheckBoxCountUpTo0(categoryRadioBox)) {
            Swal.fire('카테고리는 최소 1개를 지정해야만 합니다!', '', 'error');
            return false;
        }

    }
    else if (oneProductRadio === true) {

        if (!checkCheckBoxCountUpTo0(productRadioBox)) {
            Swal.fire('상품은 최소 1개를 지정해야만 합니다!', '', 'error');
            return false;
        }

    } else {
        Swal.fire('쿠폰 적용 범위를 지정해 주세요!', '', 'error');
        return false;
    }

    if (couponCreatedAt === "") {
        Swal.fire('쿠폰 생성날짜는 설정되어야합니다!', '', 'error');
        return false;
    }

    if (couponExpiredAt === "") {
        Swal.fire('쿠폰 만료 날짜는 설정되어야합니다!', '', 'error');
        return false;
    }

    if(new Date(couponCreatedAt)>new Date(couponExpiredAt)){
        Swal.fire('쿠폰 생성일은 만료일보다 늦을수 없습니다', '', 'error');
        return false;
    }

    Swal.fire('쿠폰 발급 성공!', '', 'success');
    return true;

}