let orderRealAmountPrice = 0;
let totalDiscountPrice = 0;
let orderProductTotalPriceBeforeDiscount = 0;
const deliveryFeePolicy = 20000;
let deliveryFee = Number(document.querySelector("#deliveryFee").innerHTML.replaceAll(',', ''));

document.addEventListener("DOMContentLoaded", function () {
    orderProductTotalPriceBeforeDiscount = getOrderProductTotalPriceBeforeDiscount();
    setDeliveryPrice();
    orderRealAmountPrice = orderProductTotalPriceBeforeDiscount;
    // orderRealAmountPrice += deliveryFee;
    setOrderRealAmountTag();
    setTotalDiscountPriceTag();
});

/* 새로운 쿠폰 로직 모달 새롭게 생성해서 사용 */
function couponModalFunc(trigger) {
    let url = $(trigger).data("url");
    let productNo = Number($(trigger).data("no"));
    let productSelledPrice = Number($(trigger).data("price"));
    let productCount = Number($(trigger).data("count"));

    let couponInfoTagList = trigger.parentNode.children;
    let couponNoInput = couponInfoTagList[0];
    let couponApplyPriceInput = couponInfoTagList[1];
    let couponApplyNameInput = couponInfoTagList[2];

    let totalPriceTag = trigger.parentNode.parentNode.parentNode.children[4].children[0];

    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (res) {

            document.querySelector("#modalBox").innerHTML = "";

            let temp = '';

            for (let i = 0; i < res.length; i++) {

                let html;

                if (res[i].amount === 0) {
                    html =
                        `
                        <tr>
                            <td>
                                <input type="radio" name="selected_coupon" value="${res[i].couponIssueNo}">
                            </td>
                            <td class="coupon_name">${res[i].name}</td>
                            <td class="coupon_discount coupon_percent">
                                <div class="coupon_discount_price_box">
                                    <p class="percent">${res[i].percent}</p>
                                    <p style="margin-left: 10px !important;">%</p>
                                </div>
                            </td>
                        </tr>
                    `;
                } else {
                    html =
                        `
                        <tr>
                            <td>
                                <input type="radio" name="selected_coupon" value="${res[i].couponIssueNo}">
                            </td>
                            <td class="coupon_name">${res[i].name}</td>
                            <td class="coupon_discount coupon_percent">
                                <div class="coupon_discount_price_box">
                                    <p class="won">${res[i].amount}</p>
                                    <p style="margin-left: 10px !important;">원</p>
                                </div>
                            </td>
                        </tr>
                    `;
                }

                temp += html;

            }

            let couponModal = `
                <div class="modal fade" id="product_coupon_modal" tabindex="-1" role="dialog">
                        <div class="modal-dialog modal-dialog-scrollable modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="product_coupon_modalLabel">잇북 쿠폰 목록</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <table class="table table-hover table-md align-middle">
                                        <thead>
                                        <tr>
                                            <th style="width: 80px"></th>
                                            <th style="width: 470px">쿠폰 이름</th>
                                            <th style="text-align: center">할인 금액</th>
                                        </tr>
                                        </thead>

                                        <tbody class="coupon_list_table_body">
                                        ${temp}
                
                                        </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                    <button type="button" id="coupon_select_btn" class="btn btn-primary">선택하기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                `

            let div = document.createElement("div");
            div.innerHTML = couponModal;
            document.querySelector("#modalBox").appendChild(div);

            setTimeout(1000);

            $('#product_coupon_modal').modal('show');


            let couponSelectBtn = document.querySelector("#coupon_select_btn");

            couponSelectBtn.addEventListener("click", function () {

                let selectedRadioInput = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked");

                if (selectedRadioInput === null || selectedRadioInput === undefined) {
                    Swal.fire({
                        icon: 'error',
                        title: '쿠폰이 선택되지 않았습니다.',
                        text: '다시 선택해 주세요.',
                    })
                    return;
                }

                let selectedCouponInfoTagList = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked").parentNode.parentNode.children;

                let couponName = selectedCouponInfoTagList[1].innerText;
                let couponDiscountPolicyTag = selectedCouponInfoTagList[2].children[0].children[0];

                let discountPolicy;
                if (couponDiscountPolicyTag.classList.contains("won")) {
                    discountPolicy = "won";
                } else {
                    discountPolicy = "percent";
                }

                let couponDiscountAmount = Number(couponDiscountPolicyTag.innerText.replace(',', ''));

                let originTotalPrice = productSelledPrice * productCount;

                let discountApplyPrice;
                if (discountPolicy === "won") {
                    discountApplyPrice = couponDiscountAmount;
                    totalPriceTag.innerHTML = Number(originTotalPrice - couponDiscountAmount);
                } else {
                    if (productCount === 1) {
                        discountApplyPrice = Number(originTotalPrice * (couponDiscountAmount / 100));
                        totalPriceTag.innerHTML = Number(originTotalPrice - discountApplyPrice);
                    } else {
                        discountApplyPrice = Number(productSelledPrice * (couponDiscountAmount / 100));
                        totalPriceTag.innerHTML = Number(productSelledPrice * (1 - couponDiscountAmount / 100) + (productSelledPrice * (productCount - 1)));
                    }
                }

                totalDiscountPrice -= couponApplyPriceInput.value;
                totalDiscountPrice += discountApplyPrice;

                setPointApplyTag();

                couponNoInput.value = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked").value;
                couponApplyNameInput.value = couponName;
                couponApplyPriceInput.value = discountApplyPrice;

                setTotalDiscountPriceTag();
                setOrderRealAmountTag();

                $('#product_coupon_modal').modal('hide');

            });

        },
        error: function (request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    });
}


/* 주문 총액 쿠폰 함수 */
$('#order_amount_coupon_btn').click(function () {
    let url = $(this).data("url");

    let orderTotalCouponNoInput = document.querySelector("#orderTotalCouponNo");
    let orderTotalCouponNameInput = document.querySelector("#order_total_coupon_name");
    let orderTotalCouponAmountInput = document.querySelector("#orderTotalCouponAmount");

    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function(res) {

            let couponTableBody = document.querySelector(".order_total_coupon_list_table_body");
            couponTableBody.innerHTML = "";

            for (let i = 0; i < res.length; i++) {

                let html;

                if (res[i].amount === 0) {
                    html =
                        `
                        <tr>
                            <td>
                                <input type="radio" name="selected_coupon" value="${res[i].couponIssueNo}">
                            </td>
                            <td class="coupon_name">${res[i].name}</td>
                            <td class="coupon_discount coupon_percent">
                                <div class="coupon_discount_price_box">
                                    <p class="percent">${res[i].percent}</p>
                                    <p style="margin-left: 10px !important;">%</p>
                                </div>
                            </td>
                        </tr>
                    `;
                } else {
                    html =
                        `
                        <tr>
                            <td>
                                <input type="radio" name="selected_coupon" value="${res[i].couponIssueNo}">
                            </td>
                            <td class="coupon_name">${res[i].name}</td>
                            <td class="coupon_discount coupon_percent">
                                <div class="coupon_discount_price_box">
                                    <p class="won">${res[i].amount}</p>
                                    <p style="margin-left: 10px !important;">원</p>
                                </div>
                            </td>
                        </tr>
                    `;
                }

                let tr = document.createElement("tr");
                tr.innerHTML = html;

                couponTableBody.appendChild(tr);
            }

            $('#order_total_coupon_modal').modal('show');

            let orderTotalCouponSelectBtn = document.querySelector("#order_total_coupon_select_btn");

            orderTotalCouponSelectBtn.addEventListener("click", function () {

                let selectedRadioInput = document.querySelector("#order_total_coupon_modal input[name='selected_coupon']:checked");

                if (selectedRadioInput === null || selectedRadioInput === undefined) {
                    Swal.fire({
                        icon: 'error',
                        title: '쿠폰이 선택되지 않았습니다.',
                        text: '다시 선택해 주세요.',
                    })
                    return;
                }

                let productTotalPriceAmount = 0;
                let productTotalPriceList = document.querySelectorAll(".product_total_price");

                for (let i = 0; i < productTotalPriceList.length; i++) {
                    productTotalPriceAmount += Number(productTotalPriceList[i].innerHTML);
                }

                let selectedCouponInfoTagList = document.querySelector("#order_total_coupon_modal input[name='selected_coupon']:checked").parentNode.parentNode.children;

                let couponName = selectedCouponInfoTagList[1].innerText;
                let couponDiscountPolicyTag = selectedCouponInfoTagList[2].children[0].children[0];

                let discountPolicy;
                if (couponDiscountPolicyTag.classList.contains("won")) {
                    discountPolicy = "won";
                } else {
                    discountPolicy = "percent";
                }

                let couponDiscountAmount = Number(couponDiscountPolicyTag.innerText.replace(',', ''));

                let discountApplyPrice;
                if (discountPolicy === "won") {
                    discountApplyPrice = couponDiscountAmount;
                } else {
                    discountApplyPrice = Number(productTotalPriceAmount * (couponDiscountAmount / 100)).toFixed();
                }

                totalDiscountPrice -= Number(orderTotalCouponAmountInput.value);
                totalDiscountPrice += Number(discountApplyPrice);

                setPointApplyTag();

                orderTotalCouponNoInput.value = document.querySelector("#order_total_coupon_modal input[name='selected_coupon']:checked").value;
                orderTotalCouponNameInput.value = couponName;
                orderTotalCouponAmountInput.value = Number(discountApplyPrice);

                setTotalDiscountPriceTag();
                setOrderRealAmountTag();

                $('#order_total_coupon_modal').modal('hide');

            });

        },
        error:function(request, status, error) {
            console.log("ajax call went wrong:" + request.responseText);
        }
    })

});


/* 할인 전 주문 총 금액 구하는 함수*/
function getOrderProductTotalPriceBeforeDiscount() {
    let discountPriceList = document.querySelectorAll(".discount_price");
    let productCountList = document.querySelectorAll(".product_count");
    let orderOriginPrice = document.querySelector("#product_origin_price");

    if (discountPriceList.length !== productCountList.length) {
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

    orderOriginPrice.innerHTML = String(temp);
    return temp;
}


/* 포인트 적용 */

let pointApplyBtn = document.querySelector("#point_apply_btn");

pointApplyBtn.addEventListener("click", function () {
    Swal.fire({
        title: '포인트를 사용하시겠습니까?',
        confirmButtonText: 'Save',
        showCancelButton: true,
    }).then((result) => {
        if (result.isConfirmed) {

            let myPointTag = document.querySelector("#myPoint");
            let myPoint = Number(myPointTag.innerHTML.replaceAll(',', ''));

            let pointApplyInput = document.querySelector("#point_apply_input");
            let pointApply = Number(pointApplyInput.value);

            if (pointApplyInput.value === '') {
                Swal.fire({
                    'icon': 'warning',
                    'title': '포인트를 입력해 주세요.'
                });
                return;
            }

            if (pointApply > myPoint) {
                Swal.fire({
                    'icon': 'warning',
                    'title': '보유 포인트를 초과하는 금액은 입력할 수 없습니다.'
                });
                pointApplyInput.value = '';
                return;
            }

            if (pointApply >= (orderProductTotalPriceBeforeDiscount + deliveryFee - totalDiscountPrice)) {

                // pointApply = orderRealAmountPrice;
                pointApply = orderProductTotalPriceBeforeDiscount + deliveryFee - totalDiscountPrice;
                pointApplyInput.value = Number(pointApply);
                myPointTag.innerHTML = Number(myPoint - pointApply).toLocaleString();
            } else {
                myPointTag.innerHTML = Number(myPoint - pointApply).toLocaleString();
            }

            totalDiscountPrice += Number(pointApplyInput.value);

            this.disabled = true;
            document.querySelector("#point_cancel_btn").disabled = false;
            document.querySelector("#point_apply_input").readOnly = true;


            // if (myPoint >= orderRealAmountPrice) {
            //     myPoint = myPoint - orderRealAmountPrice;
            //     pointApplyInput.value = Number(orderRealAmountPrice);
            //
            //     /* 총 할인 금액 계산 */
            //     totalDiscountPrice = orderRealAmountPrice;
            //
            //     myPointTag.innerText = myPoint;
            //     // orderRealAmountPrice = 0;
            //
            // } else {
            //     // orderRealAmountPrice = orderRealAmountPrice - myPoint;
            //     pointApplyInput.value = myPoint;
            //
            //     /* 총 할인 금액 계산 */
            //     totalDiscountPrice += myPoint;
            //
            //     myPoint = 0;
            //     myPointTag.innerText = myPoint;
            // }

            setTotalDiscountPriceTag();
            setOrderRealAmountTag();
            // setPointApplyTag();

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

            // orderRealAmountPrice += applyPoint;

            let myPoint = Number(myPointTag.innerText.replaceAll(',', ''));

            myPointTag.innerText = (myPoint + applyPoint).toLocaleString();

            pointApplyInput.value = '';
            document.querySelector("#point_apply_btn").disabled = false;
            document.querySelector("#point_apply_input").readOnly = false;

            setOrderRealAmountTag();
            setTotalDiscountPriceTag();

            Swal.fire('포인트 적용 해제 성공!', '', 'success')
        }
    })

});


function setOrderRealAmountTag() {


    let orderRealAmountTag = document.querySelector("#order_real_amount");

    let paymentBtnAmountPrice = document.querySelector("#payment_btn_amount_price");

    orderRealAmountTag.innerHTML =
        Number(orderProductTotalPriceBeforeDiscount + deliveryFee - totalDiscountPrice).toLocaleString();

    paymentBtnAmountPrice.innerHTML =
        Number(orderProductTotalPriceBeforeDiscount + deliveryFee - totalDiscountPrice).toLocaleString()
    // let productTotalPriceList = document.querySelectorAll(".product_total_price");
    //
    // console.log(productTotalPriceList);
    // let temp = 0;
    // for (let i = 0; i < productTotalPriceList.length; i++) {
    //     temp += Number(productTotalPriceList[i].innerText);
    // }

    // finalTotalPrice = temp;

    // let paymentBtnAmountPriceTag = document.querySelector("#payment_btn_amount_price");
    //
    // if (orderRealAmountPrice < 0) {
    //     orderRealAmountPrice = 0;
    // }
    //
    // if ((orderRealAmountPrice - totalDiscountPrice) < 0) {
    //     orderRealAmountPrice = 0;
    // }
    //
    // let paymentPrice = (orderRealAmountPrice - totalDiscountPrice).toLocaleString();
    // orderRealAmountTag.innerHTML = paymentPrice;
    // paymentBtnAmountPriceTag.innerHTML = paymentPrice;
}

function setPointApplyTag() {
    let pointApplyInput = document.querySelector("#point_apply_input");
    let pointApply = Number(pointApplyInput.value);
    let myPointTag = document.querySelector("#myPoint");
    let myPoint = Number(myPointTag.innerHTML.replaceAll(',', ''));

    let currentPrice = Number(orderProductTotalPriceBeforeDiscount + deliveryFee - totalDiscountPrice);
    console.log(currentPrice);

    if (currentPrice < 0) {

        pointApplyInput.value = pointApply + currentPrice;

        myPointTag.innerHTML = Number(myPoint - currentPrice).toLocaleString();

    }

}


function setTotalDiscountPriceTag() {

    let totalDiscountPriceTag = document.querySelector("#total_discount_price");

    if (totalDiscountPrice < 0) {
        totalDiscountPriceTag.innerText = 0;
    }

    if (totalDiscountPrice > (orderProductTotalPriceBeforeDiscount + deliveryFee)) {
        totalDiscountPrice = (orderProductTotalPriceBeforeDiscount + deliveryFee);
    }

    totalDiscountPriceTag.innerHTML = totalDiscountPrice;
}

/* 배송지 정책 구매 상품의 가격 2만원 이상일 경우 0원 */
function setDeliveryPrice() {

    if (orderProductTotalPriceBeforeDiscount >= deliveryFeePolicy) {
        deliveryFee = 0;
    }

    document.querySelector("#deliveryFee").innerHTML = Number(deliveryFee).toLocaleString();
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

let destinationNumberList = [];

destinationListBtn.addEventListener("click", function () {

    $('#destinationModal').modal('show');

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

        let addressArray = [];

        let selectedInfo = document.querySelector("#destinationModal input[name='destination_info']:checked").parentElement.children[2].children;

        for (let i = 0; i < selectedInfo.length; i++) {
            addressArray.push(selectedInfo[i].children[1].value);
        }

        let actualDestinationInfoList = document.querySelector(".actual_destination").children;

        for (let i = 0; i < actualDestinationInfoList.length; i++) {
            actualDestinationInfoList[i].children[1].readOnly = true;
            actualDestinationInfoList[i].children[1].value = '';
            actualDestinationInfoList[i].children[1].value = addressArray[i];
        }

        document.querySelector("#postcodeSearchBtn").disabled = true;

        $('#destinationModal').modal('hide');

    });

});

function checkValidAddress() {

    let addressInputList = document.querySelectorAll(".actual_destination input");

    let result = true;

    for (let i = 0; i < addressInputList.length; i++) {
        if (addressInputList[i].value === null || addressInputList[i].value === '') {
            result = false;
        }
    }

    if (!result) {
        Swal.fire({
            icon: 'error',
            title: '배송지가 선택되지 않았습니다.',
            text: '다시 선택해 주세요.',
        })
    }

    return result;
}

function findOrderSubmit() {

    let orderNo = document.getElementById("orderNo").value;
    let orderCode = document.getElementById("orderCode").value;

    if (!checkStringLengthDown(orderNo, 19)) {
        Swal.fire('주문번호는 19자 이하여야 합니다!', '', 'error');
        return false;
    }

    if (orderCode.length > 36) {
        Swal.fire('주문코드 길이는 36자 보다 작아야 합니다!', '', 'error');
        return false;
    }

    return true;

}