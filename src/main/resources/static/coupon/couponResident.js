
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

function selectCoupon(){
    if(selectCheck("일반쿠폰")){
        document.getElementById("normalCouponDiv").style.display = 'block';
    }else{

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