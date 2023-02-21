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
                    if (response.status >= 400 && response.status < 600) {
                        Swal.fire({
                            title: '구매확정 실패!',
                            icon: "error"
                        })
                        throw Error();
                    }

                    return response.json();
                })
                .then((data) => {
                    Swal.fire({
                        title: '구매 확정'
                    })
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