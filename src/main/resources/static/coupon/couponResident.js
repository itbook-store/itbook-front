
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