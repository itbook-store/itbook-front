let addCartBtn = document.querySelectorAll(".cart_add_btn");

addCartBtn.forEach(btn => function () {
    btn.addEventListener("click", function () {
        console.log(btn.dataset.productNo);
    });
});

async function addProduct(productNo) {

    await fetch(`/async/cart/add-product?productNo=${productNo}`, {
        method: "GET"
    })
        .then(response => response.json())
        .then(data => {

            if (data === true) {
                alert("상품을 장바구니에 담았습니다.");
            } else {
                alert("이미 장바구니에 담았습니다.")
            }
        });
}
