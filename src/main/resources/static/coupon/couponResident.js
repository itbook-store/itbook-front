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
    } else if (radioCheck('percent')) {
        divBlock('percent');
        divBlock('min');
        divBlock('max');

        divNone('point');
        divNone('amount');
        document.getElementById("couponCoverageDiv").style.display = 'block';

    } else {
        divBlock('amount');
        divBlock('min');

        divNone('point');
        divNone('percent');
        divNull('max');
        document.getElementById("couponCoverageDiv").style.display = 'block';

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

    if (select.value === "일반쿠폰") {
        document.getElementById("normalCouponDiv").style.display = 'block';
    } else {
        document.getElementById("normalCouponDiv").style.display = 'none';

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
                    radioBox.name = "searchResult";
                    radioBox.value = productList.productNo;
                    radioBox.style = "margin-right : 4px";
                    radioBoxText.style = "margin-right : 7px";
                    radioBoxText.innerText = productList.name;
                productBox.appendChild(radioBox);
                productBox.appendChild(radioBoxText);

            });
        });

}