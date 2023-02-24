// 환불, 취소 비동기 요청 메서드.
function getInputCancelReason(orderNo, isSubscription) {

    Swal.fire({
        title: '취소 사유를 입력해주세요.',
        input: 'text',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: '확인',
        showLoaderOnConfirm: true,
        preConfirm: async (cancelReason) => {

            let paymentCanceledRequestDto = {
                "orderNo": orderNo,
                "cancelReason": cancelReason
            }

            let url = `/async/payment/cancel?isSubscription=` + isSubscription;

            await fetch(url, {

                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(paymentCanceledRequestDto)
            })
                .then(response => {
                    if (response==null) {
                        throw new Error(response.statusText)
                    }
                    return response.json()
                })
                .catch(error => {
                    Swal.showValidationMessage(
                        `결제 취소에 실패하였습니다.`
                    )
                })

            // return fetch(requestUrl)
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: `결제 취소가 성공적으로 완료되었습니다.`,
            })
        }

        location.reload();
    })
}

// 구매 확정 비동기 요청 메서드.
async function purchaseComplete(orderNo) {

    Swal.fire({
        title: '구매를 확정 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '구매확정',
        preConfirm: async () => {

            await fetch(`/async/orders/purchase-complete/${orderNo}`, {
                method: "POST",
            })
                .then((response) => {

                    return response.json();
                })
                .then((data) => {
                    console.log(data);

                    if (!data.isSuccessful) {
                        Swal.fire({
                            title: '구매확정 실패!',
                            text: '배송완료 상품만 구매확정 하실 수 있습니다.',
                            icon: "error"
                        })
                    } else {
                        Swal.fire({
                            title: '구매 확정'
                        });
                        location.reload();
                    }
                })
                .catch((err) => {
                    Swal.fire({
                        title: '구매확정 실패!',
                        icon: "error"
                    })
                })

        }

    })
}