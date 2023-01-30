
function divBlock(name){
    document.getElementById(name+'Div').style.display = 'block';
    document.getElementById(name).value = null;
}

function divNone(name){
    document.getElementById(name+'Div').style.display = 'none';
    document.getElementById(name).value = 0;
}

function divNull(name){
    document.getElementById(name+'Div').style.display = 'none';
    document.getElementById(name).value = null;
}
function radioCheck(name) {
    return document.getElementById(name + 'Radio').checked === true;
}
function selectCouponOption(){
    if(radioCheck('point')) {
        divBlock('point');

        divNull('min');
        divNull('max');
        divNone('amount');
        divNone('percent');

    }else if(radioCheck('percent')){
        divBlock('percent');
        divBlock('min');
        divBlock('max');

        divNone('point');
        divNone('amount');

    } else {
        divBlock('amount');
        divBlock('min');

        divNone('point');
        divNone('percent');
        divNull('max');

    }
}

function selectCouponCoverage(){
    if(radioCheck('allProduct')) {

        document.getElementById("selectCategoryDiv").style.display = 'none';
        document.getElementById("selectProductDiv").style.display = 'none';
    } else if(radioCheck('category')){

        document.getElementById("selectCategoryDiv").style.display = 'block';
        document.getElementById("selectProductDiv").style.display = 'none';
    } else{

        document.getElementById("selectCategoryDiv").style.display = 'none';
        document.getElementById("selectProductDiv").style.display = 'block';
    }
}

function selectCheck(name){
    let select = document.getElementById('selectCoupon');
    return select.options[select.selectedIndex].value === name;
}

function selectCouponDiv(select){
    // let select = document.getElementById('selectCoupon');
    // select.options[select.selectedIndex].value === name

    if(select.value === "일반쿠폰"){
        document.getElementById("normalCouponDiv").style.display = 'block';
    }else{
        document.getElementById("normalCouponDiv").style.display = 'none';

    }
}
async function checkMemberId() {
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
            data.forEach(checkBoxList => {
                if (checkBoxList.level !== 0) {
                    let checkBox = document.createElement("input");
                    let checkBoxText = document.createElement("label");
                    checkBox.type = "checkbox";
                    checkBox.name = "categoryNoList";
                    checkBox.value = checkBoxList.categoryNo;
                    checkBox.style = "margin-right : 4px";
                    checkBoxText.style = "margin-right : 7px";
                    if(categoryNoList && categoryNoList.length !=0)
                        if (categoryNoList.some(no => no == checkBoxList.categoryNo)) {
                            checkBox.checked = true;
                        }
                    checkBoxText.innerText = checkBoxList.categoryName;
                    subCategoryCheckBoxDiv.appendChild(checkBox);
                    subCategoryCheckBoxDiv.appendChild(checkBoxText);
                }
            });
        });

}


async function showSearchProductList(name) {

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
            data.forEach(checkBoxList => {
                if (checkBoxList.level !== 0) {
                    let checkBox = document.createElement("input");
                    let checkBoxText = document.createElement("label");
                    checkBox.type = "checkbox";
                    checkBox.name = "categoryNoList";
                    checkBox.value = checkBoxList.categoryNo;
                    checkBox.style = "margin-right : 4px";
                    checkBoxText.style = "margin-right : 7px";
                    if(categoryNoList && categoryNoList.length !=0)
                        if (categoryNoList.some(no => no == checkBoxList.categoryNo)) {
                            checkBox.checked = true;
                        }
                    checkBoxText.innerText = checkBoxList.categoryName;
                    subCategoryCheckBoxDiv.appendChild(checkBox);
                    subCategoryCheckBoxDiv.appendChild(checkBoxText);
                }
            });
        });

}