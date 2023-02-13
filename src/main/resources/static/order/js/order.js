let orderRealAmountPrice = 0;
let totalDiscountPrice = 0;

document.addEventListener("DOMContentLoaded", function () {
    orderRealAmountPrice = getOrderProductTotalPriceBeforeDiscount();
    setOrderRealAmountTag();
    setTotalDiscountPriceTag();
    console.log(orderRealAmountPrice);
});

$('.coupon_btn').click(function(){
    var url = $(this).data("url");
    console.log(url);
    $.ajax({
        type: "GET",
        url: '/async/cart/test',
        dataType: 'json',
        success: function(res) {

            console.log(res);

            console.log(res.memberNo);

            // get the ajax response data
            // var data = res.body;

            // console.log(data);
            //
            // console.log(data.memberNo);

            // update modal content
            $('.modal-body.couponList').text(res.memberNo);

            // $('.modal-body').text(data.someval);
            // show modal
            $('#product_coupon_modal').modal('show');

        },
        error:function(request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
});

/* 할인 전 주문 총 금액 구하는 함수*/
function getOrderProductTotalPriceBeforeDiscount() {
    let discountPriceList = document.querySelectorAll(".discount_price");
    let productCountList = document.querySelectorAll(".product_count");
    let orderOriginPrice = document.querySelector("#product_origin_price");

    if (discountPriceList.length != productCountList.length) {
        invalidOrderRedirectFunc();
        return;
    }

    let temp = 0;

    for (let i = 0; i < discountPriceList.length; i++) {

        let productCount = Number(productCountList[i].innerText);

        if (productCount <= 0) {
            invalidOrderRedirectFunc();
            return;
        }

        temp += Number(discountPriceList[i].innerText) * productCount;
    }

    if (temp < 0) {
        invalidOrderRedirectFunc();
        return;
    }

    orderOriginPrice.innerHTML = String(temp.toLocaleString());
    return temp;
}


/* 포인트 적용 */

let pointApplyBtn = document.querySelector("#point_apply_btn");

pointApplyBtn.addEventListener("click", function () {
    Swal.fire({
        title: '모든 포인트를 사용하시겠습니까?',
        text: '포인트는 사용가능한 최대 금액이 적용됩니다.',
        confirmButtonText: 'Save',
        showCancelButton: true,
    }).then((result) => {
        if (result.isConfirmed) {

            let myPointTag = document.querySelector("#myPoint");
            let myPoint = Number(myPointTag.innerHTML.replace(',', ''));

            let pointApplyInput = document.querySelector("#point_apply_input");

            if (myPoint >= orderRealAmountPrice) {
                myPoint = myPoint - orderRealAmountPrice;
                pointApplyInput.value = Number(orderRealAmountPrice.toLocaleString());

                /* 총 할인 금액 계산 */
                totalDiscountPrice = orderRealAmountPrice;

                myPointTag.innerText = myPoint.toLocaleString();
                orderRealAmountPrice = 0;

            } else {
                orderRealAmountPrice = orderRealAmountPrice - myPoint;
                pointApplyInput.value = myPoint.toLocaleString();

                /* 총 할인 금액 계산 */
                totalDiscountPrice = myPoint;

                myPoint = 0;
                myPointTag.innerText = myPoint;
            }


            setOrderRealAmountTag();
            setTotalDiscountPriceTag();

            Swal.fire('포인트 적용 성공!', '', 'success');

        }
    })
});

/* 포인트 적용 해제 */

let pointCancelBtn = document.querySelector("#point_cancel_btn");

pointCancelBtn.addEventListener("click", function () {
    Swal.fire({
        title: '포인트 적용을 해제하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: 'Save',
    }).then((result) => {
        if (result.isConfirmed) {

            let myPointTag = document.querySelector("#myPoint");
            let pointApplyInput = document.querySelector("#point_apply_input");

            let applyPoint = Number(pointApplyInput.value.replace(',', ''));

            /* 총 할인 금액 계산 */
            totalDiscountPrice -= applyPoint;

            orderRealAmountPrice += applyPoint;
            pointApplyInput.value = 0;

            let existingMyPoint = Number(myPointTag.innerText.replace(',', ''));
            myPointTag.innerText = Number(existingMyPoint + applyPoint).toLocaleString();

            setOrderRealAmountTag();
            setTotalDiscountPriceTag();

            Swal.fire('포인트 적용 해제 성공!', '', 'success')
        }
    })

});


function setOrderRealAmountTag() {

    let orderRealAmountTag = document.querySelector("#order_real_amount");

    if (orderRealAmountPrice < 0) {
        orderRealAmountPrice = 0;
    }

    orderRealAmountTag.innerHTML = orderRealAmountPrice.toLocaleString();
}


function setTotalDiscountPriceTag() {

    let totalDiscountPriceTag = document.querySelector("#total_discount_price");

    if (totalDiscountPrice < 0) {
        totalDiscountPriceTag = 0;
    }

    totalDiscountPriceTag.innerHTML = totalDiscountPrice.toLocaleString();

}

function invalidOrderRedirectFunc() {
    Swal.fire({
        title: '주문정보가 잘못되었습니다.',
        text: '다시 시도해 주세요.',
        showDenyButton: true,
        showConfirmButton: false,
        denyButtonText: `돌아가기`,
    }).then((result) => {
        location.href = "/";
    })
}








/* 배송지 선택 */
let destinationListBtn = document.querySelector("#destination_list_btn");

destinationListBtn.addEventListener("click", function () {

    $('#destinationModal').modal('show');

    let addressArray = [];

    $('input[type=radio][name="destination_info"]').on('change', function() {
        let selectedInfo = document.querySelector("#destinationModal input[name='destination_info']:checked").parentElement.children[1].children;


        for (let i = 0; i < selectedInfo.length; i++) {
            console.log(selectedInfo[i]);
            console.log(selectedInfo[i].childNodes[3].value)
            addressArray.push(selectedInfo[i].childNodes[3].value);
        }

    });

    let destinationSelectBtn = document.querySelector("#destination_select_btn");

    destinationSelectBtn.addEventListener("click", function () {

        let selectedRadioInput = document.querySelector("#destinationModal input[name='destination_info']:checked");

        if (selectedRadioInput === null || selectedRadioInput === undefined) {
            Swal.fire({
                icon: 'error',
                title: '배송지가 선택되지 않았습니다.',
                text: '다시 선택해 주세요.',
            })
            return;
        }

        let actualDestinationInfoList = document.querySelector(".actual_destination").children;

        for (let i = 0; i < actualDestinationInfoList.length; i++) {
            actualDestinationInfoList[i].children[1].readOnly = true;
            actualDestinationInfoList[i].children[1].value = addressArray[i];
        }

        document.querySelector("#postcodeSearchBtn").disabled = true;

        $('#destinationModal').modal('hide');

    });

});
