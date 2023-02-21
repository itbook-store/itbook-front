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