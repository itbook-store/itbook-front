let orderRealAmountPrice = 0;
let totalDiscountPrice = 0;

document.addEventListener("DOMContentLoaded", function () {
    orderRealAmountPrice = getOrderProductTotalPriceBeforeDiscount();
    setOrderRealAmountTag();
    setTotalDiscountPriceTag();
});

// $('#product_coupon_modal').on('hidden.bs.modal', function (e) {
//     $('#product_coupon_modal').off();
//     removeEventListener("click", this, false);
//     removeEventListener("hide.bs.modal", this, false);
// })

/* 쿠폰 함수 */
// $('.coupon_btn').click(function(){
//     let url = $(this).data("url");
//     let productNo = Number($(this).data("no"));
//     let productSelledPrice = Number($(this).data("price"));
//     let productCount = Number($(this).data("count"));
//
//     let couponInfoTagList = this.parentNode.children;
//     let couponNoInput = couponInfoTagList[0];
//     let couponApplyPriceInput = couponInfoTagList[1];
//     let couponApplyNameInput = couponInfoTagList[2];
//
//     let totalPriceTag = this.parentNode.parentNode.parentNode.children[4].children[0];
//
//     $('#product_coupon_modal').off();
//
//     $.ajax({
//         type: "GET",
//         url: url,
//         dataType: 'json',
//         success: function(res) {
//
//
//             let couponTableBody = document.querySelector(".coupon_list_table_body");
//
//             couponTableBody.innerHTML = '';
//
//             for (let i = 0; i < res.length; i++) {
//
//                 let html;
//
//                 if (res[i].amount === 0) {
//                     html =
//                     `
//                         <tr>
//                             <td>
//                                 <input type="radio" name="selected_coupon" value="${res[i].couponIssueNo}">
//                             </td>
//                             <td class="coupon_name">${res[i].name}</td>
//                             <td class="coupon_discount coupon_percent">
//                                 <div class="coupon_discount_price_box">
//                                     <p class="percent">${res[i].percent}</p>
//                                     <p style="margin-left: 10px !important;">%</p>
//                                 </div>
//                             </td>
//                         </tr>
//                     `;
//                 } else {
//                     html =
//                     `
//                         <tr>
//                             <td>
//                                 <input type="radio" name="selected_coupon" value="${res[i].couponIssueNo}">
//                             </td>
//                             <td class="coupon_name">${res[i].name}</td>
//                             <td class="coupon_discount coupon_percent">
//                                 <div class="coupon_discount_price_box">
//                                     <p class="won">${res[i].amount}</p>
//                                     <p style="margin-left: 10px !important;">원</p>
//                                 </div>
//                             </td>
//                         </tr>
//                     `;
//                 }
//
//                 let tr = document.createElement("tr");
//                 tr.innerHTML = html;
//
//                 couponTableBody.appendChild(tr);
//             }
//
//             $('#product_coupon_modal').modal('show');
//
//             let couponSelectBtn = document.querySelector("#coupon_select_btn");
//
//             couponSelectBtn.addEventListener("click", function () {
//
//                 let selectedRadioInput = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked");
//
//                 if (selectedRadioInput === null || selectedRadioInput === undefined) {
//                     Swal.fire({
//                         icon: 'error',
//                         title: '쿠폰이 선택되지 않았습니다.',
//                         text: '다시 선택해 주세요.',
//                     })
//                     return;
//                 }
//
//                 let selectedCouponInfoTagList = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked").parentNode.parentNode.children;
//
//                 let couponName = selectedCouponInfoTagList[1].innerText;
//                 let couponDiscountPolicyTag = selectedCouponInfoTagList[2].children[0].children[0];
//
//                 let discountPolicy;
//                 if (couponDiscountPolicyTag.classList.contains("won")) {
//                     discountPolicy = "won";
//                 } else {
//                     discountPolicy = "percent";
//                 }
//
//                 let couponDiscountAmount = Number(couponDiscountPolicyTag.innerText.replace(',', ''));
//
//                 let originTotalPrice = productSelledPrice * productCount;
//
//                 let discountApplyPrice;
//                 if (discountPolicy === "won") {
//                     discountApplyPrice = couponDiscountAmount;
//                     totalPriceTag.innerHTML = Number(originTotalPrice - couponDiscountAmount).toLocaleString();
//                 } else {
//                     if (productCount === 1) {
//                         discountApplyPrice = Number(originTotalPrice * (couponDiscountAmount / 100));
//                         totalPriceTag.innerHTML = Number(originTotalPrice - discountApplyPrice).toLocaleString();
//                     } else {
//                         discountApplyPrice = Number(productSelledPrice * (couponDiscountAmount / 100));
//                         totalPriceTag.innerHTML = Number(productSelledPrice * (1 - couponDiscountAmount / 100) + (productSelledPrice * (productCount - 1))).toLocaleString();
//                     }
//                 }
//
//                 let couponBoxList = document.querySelectorAll(".coupon_box");
//
//                 let realCouponBox;
//
//                 for (let i = 0; i < couponBoxList.length; i++) {
//                     if (Number(couponBoxList[i].dataset.valid) === productNo) {
//                         realCouponBox = couponBoxList[i];
//                     }
//                 }
//
//                 console.log(realCouponBox);
//
//
//
//                 totalDiscountPrice -= couponApplyPriceInput.value;
//                 totalDiscountPrice += discountApplyPrice;
//
//                 couponNoInput.value = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked").value;
//                 couponApplyNameInput.value = couponName;
//                 couponApplyPriceInput.value = discountApplyPrice;
//
//                 setOrderRealAmountTag();
//                 setTotalDiscountPriceTag();
//
//                 $('#product_coupon_modal').modal('hide');
//
//             });
//
//         },
//         error:function(request, status, error) {
//             console.log("ajax call went wrong:" + request.responseText);
//         }
//     });
//
// });

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

            let temp;

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
                                    <i class="fa-solid fa-ticket"></i>
                                    <table class="table table-hover table-md align-middle">
                                        <thead>
                                        <tr>
                                            <th style="width: 80px"></th>
                                            <th style="width: 470px">쿠폰 이름</th>
                                            <th style="text-align: center">할인 금액</th>
                                        </tr>
                                        </thead>

                                        <tbody class="coupon_list_table_body">
                ` + temp + `
                
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

                couponNoInput.value = document.querySelector("#product_coupon_modal input[name='selected_coupon']:checked").value;
                couponApplyNameInput.value = couponName;
                couponApplyPriceInput.value = discountApplyPrice;

                setOrderRealAmountTag();
                setTotalDiscountPriceTag();

                $('#product_coupon_modal').modal('hide');
                return;

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
    console.log(orderTotalCouponNoInput);
    let orderTotalCouponNameInput = document.querySelector("#order_total_coupon_name");
    console.log(orderTotalCouponNameInput);
    let orderTotalCouponAmountInput = document.querySelector("#orderTotalCouponAmount");
    console.log(orderTotalCouponAmountInput);

    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function(res) {
            console.log(res);

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

                for (let i = 1; i < productTotalPriceList.length; i++) {
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

                totalDiscountPrice -= Number(orderTotalCouponAmountInput.value).toFixed();
                totalDiscountPrice += Number(discountApplyPrice).toFixed();

                orderTotalCouponNoInput.value = document.querySelector("#order_total_coupon_modal input[name='selected_coupon']:checked").value;
                orderTotalCouponNameInput.value = couponName;
                orderTotalCouponAmountInput.value = Number(discountApplyPrice);

                setOrderRealAmountTag();
                setTotalDiscountPriceTag();

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

    orderOriginPrice.innerHTML = String(temp);
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
                pointApplyInput.value = Number(orderRealAmountPrice);

                /* 총 할인 금액 계산 */
                totalDiscountPrice = orderRealAmountPrice;

                myPointTag.innerText = myPoint;
                // orderRealAmountPrice = 0;

            } else {
                // orderRealAmountPrice = orderRealAmountPrice - myPoint;
                pointApplyInput.value = myPoint;

                /* 총 할인 금액 계산 */
                totalDiscountPrice += myPoint;

                myPoint = 0;
                myPointTag.innerText = myPoint;
            }

            setTotalDiscountPriceTag();
            setOrderRealAmountTag();

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
            pointApplyInput.value = 0;

            let existingMyPoint = Number(myPointTag.innerText.replace(',', ''));
            myPointTag.innerText = Number(existingMyPoint + applyPoint);

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

    if ((orderRealAmountPrice - totalDiscountPrice) < 0) {
        orderRealAmountPrice = 0;
    }

    orderRealAmountTag.innerHTML = (orderRealAmountPrice - totalDiscountPrice);
}


function setTotalDiscountPriceTag() {

    let totalDiscountPriceTag = document.querySelector("#total_discount_price");

    if (totalDiscountPrice < 0) {
        totalDiscountPriceTag = 0;
    }

    totalDiscountPriceTag.innerHTML = totalDiscountPrice;

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
