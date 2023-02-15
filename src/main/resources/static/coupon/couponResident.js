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

async function checkMemberId() {
    let memberId = document.getElementById('memberId').value;
    let isExists = false;

    const request = {
        method: "GET"
    };

    await fetch(`/async/signup/memberId/${memberId}`, request)
        .then(response => response.json())
        .then(data => {
            isExists = data.isExists;
        });

    console.log(isExists);

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

function retypeFn(text, existMsg, notExistMsg, checkBtn, retypeBtn) {
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
    let userId = document.getElementById("userId").value;
    let selectMembership = document.getElementById("selectMembership").textContent;
    let name = document.getElementById("name").value;
    let point = document.getElementById("point").value;
    let percent = document.getElementById("percent").value;
    let amount = document.getElementById("amount").value;
    let couponCreatedAt = document.getElementById("couponCreatedAt").value;
    let couponExpiredAt = document.getElementById("couponExpiredAt").value;
    let allProductRadio = $('input[name="allProductRadio"]:checked').val();
    let categoryRadio = $('input[name="categoryRadio"]:checked').val();
    let oneProductRadio = $('input[name="oneProductRadio"]:checked').val();
    let pointCoverageRadio = $('input[name="pointCoverageRadio"]:checked').val();
    let pointRadio = $('input[name="pointRadio"]:checked').val();
    let percentRadio = $('input[name="percentRadio"]:checked').val();
    let amountRadio = $('input[name="amountRadio"]:checked').val();

    console.log(name);

    if (selectCoupon == null) {
        Swal.fire('쿠폰 이름 길이는 20자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (!checkStringLengthDownToNum(20, name)) {
        Swal.fire('쿠폰 이름 길이는 20자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (pointRadio === "true") {

        if (!checkNumberUpToNum(1,point)) {
            Swal.fire('포인트 적립액은 1원 이상 이어야 합니다!', '', 'error');
            return false;
        }

        return true;
    }

    if (amountRadio === "true") {

        if (!checkNumberUpToNum(1,amount)) {
            Swal.fire('쿠폰의 할인 금액은 1원 이상 이어야 합니다!', '', 'error');
            return false;
        }

        return true;
    }

    if (percentRadio === "true") {

        if (!checkNumberOfPercentBetween(1, 101, percent)) {
            Swal.fire('퍼센트 할인액은 1% 이상 이어야 합니다!', '', 'error');
            return false;
        }

        return true;
    }

    if (pointCoverageRadio === "true") {

        if (allProductRadio === "true") {

            return true;
        }
        if (categoryRadio === "true") {

            if (!checkCheckBoxCountUpTo0(categoryRadioBox)) {
                Swal.fire('카테고리는 최소 1개를 지정해야만 합니다!', '', 'error');
                return false;
            }

            return true;
        }
        if (oneProductRadio === "true") {

            if (!checkCheckBoxCountUpTo0(productRadioBox)) {
                Swal.fire('상품은 최소 1개를 지정해야만 합니다!', '', 'error');
                return false;
            }

            return true;
        }
        return true;
    }

    if (couponCreatedAt == null) {
        Swal.fire('쿠폰 생성날짜는 설정되어야합니다!', '', 'error');
        return false;
    }

    if (couponExpiredAt == null) {
        Swal.fire('쿠폰 만료 날짜는 설정되어야합니다!', '', 'error');
        return false;
    }

    Swal.fire('쿠폰 등록 성공!', '', 'success');
    return true;

}