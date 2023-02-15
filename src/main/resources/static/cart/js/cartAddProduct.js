let addCartBtn = document.querySelectorAll(".cart_add_btn");
addCartBtn.forEach(btn => function () {
    btn.addEventListener("click", function () {
        console.log(btn.dataset.productNo);
    });
});

async function addProduct(productNo) {

    await fetch(`/async/cart/add-product?productNo=${productNo}`, {
        method: "POST"
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);

            if (data.result === true) {
                Swal.fire({
                    icon: 'success',
                    title: data.resultMessage,
                });
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: data.resultMessage,
                })
            }
        });
}
