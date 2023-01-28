
// function selectPoint(eventTarget) {
//
//     eventTarget.show() =
//         `<input class="form-control" type="text" placeholder="Default input">
//
//         `;
// }

function selectCouponOption(){
    if(document.getElementById('pointRadio').checked === true){
        document.getElementById('pointDiv').style.display = 'block';

        document.getElementById('minDiv').style.display = 'none';
        document.getElementById('maxDiv').style.display = 'none';
        document.getElementById('amountDiv').style.display = 'none';
        document.getElementById('percentDiv').style.display = 'none';

    } else if(document.getElementById('percentRadio').checked === true){
        document.getElementById('percentDiv').style.display = 'block';
        document.getElementById('minDiv').style.display = 'block';
        document.getElementById('maxDiv').style.display = 'block';

        document.getElementById('pointDiv').style.display = 'none';
        document.getElementById('amountDiv').style.display = 'none';

    } else {
        document.getElementById('amountDiv').style.display = 'block';
        document.getElementById('minDiv').style.display = 'block';

        document.getElementById('pointDiv').style.display = 'none';
        document.getElementById('percentDiv').style.display = 'none';
        document.getElementById('maxDiv').style.display = 'none';

    }
}

function selectCouponCoverage(){
    if(document.getElementById("allProductRadio").checked === true){

        document.getElementById("selectCategoryDiv").style.display = 'none';
        document.getElementById("selectProductDiv").style.display = 'none';
    } else if(document.getElementById("categoryRadio").checked === true){

        document.getElementById("selectCategoryDiv").style.display = 'block';
        document.getElementById("selectProductDiv").style.display = 'none';
    } else{

        document.getElementById("selectCategoryDiv").style.display = 'none';
        document.getElementById("selectProductDiv").style.display = 'block';
    }
}