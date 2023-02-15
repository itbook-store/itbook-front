function deleteProduct(productNo) {

    Swal.fire({
        title: '해당 상품을 삭제 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '삭제하기',
        preConfirm: async () => {
            await fetch(`/async/cart/delete-product?productNo=${productNo}`, {
                method: "GET",
            })
                .then(data => {
                    location.reload();
                });
        },
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                icon: 'success',
                title: `해당 상품을 삭제하였습니다.`
            })
        }
    })

}

function deleteAllProduct() {

    Swal.fire({
        title: '장바구니 모든 상품을 삭제 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '삭제하기',
        preConfirm: async () => {
            let productNoInputList = document.querySelectorAll("input[name=productNo]");

            let productNoList = [];

            productNoInputList.forEach(
                o => productNoList.push(o.value)
            );

            await fetch(`/async/cart/delete/all-product?productNo=${productNoList}`, {
                method: "POST",
            })
                .then(data => {
                    location.reload();
                });
        },
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                icon: 'success',
                title: `장바구니 모든 상품이 삭제되었습니다.`
            })
        }
    })
}

async function changeProductCount(productNo, input) {

    let productStock = Number(input.dataset.stock);

    if(!input.value.match(/[0-9]/)) {
        input.value = '1';
        Swal.fire({
            icon: 'warning',
            title: '수량은 숫자만 입력 가능합니다.'
        })
        return;
    }

    if (input.value > productStock){
        input.value = '1';
        Swal.fire({
            icon: 'warning',
            title: '상품의 재고보다 많이 구매하실 수 없습니다.'
        })
        return;
    }

    productNo = Number(productNo);
    let productCount = Number(input.value);

    await fetch(`/async/cart/change/product-count?productNo=${productNo}&productCount=${productCount}`, {
        method: "POST"
    })
        .then(data => {
            let totalPrice = input.parentElement.parentElement.parentElement.parentElement.querySelector(".total_price");
            let productPrice = Number(input.parentElement.parentElement.parentElement.parentElement.querySelector(".discount_price").innerHTML);
            totalPrice.value = Number(input.value) * productPrice;
        });
}

function changeTotalPrice(input) {
    console.log(input)

    let stock = Number(input.dataset.stock);

    if (Number(input.value) > stock) {
        input.value = '1';
        Swal.fire({
            icon: 'warning',
            title: '상품의 재고보다 많이 구매하실 수 없습니다.'
        })
    }

    let totalPrice = input.parentElement.parentElement.parentElement.parentElement.querySelector(".total_price");
    let productPrice = Number(input.parentElement.parentElement.parentElement.parentElement.querySelector(".discount_price").innerHTML);
    totalPrice.value = Number(input.value) * productPrice;
}
