async function deleteProduct(productNo) {
    console.log("productNo {}", productNo)
    if (window.confirm("해당 상품을 삭제하시겠습니까?")) {
        await fetch(`/async/cart/delete-product?productNo=${productNo}`, {
            method: "GET",
        })
            .then(data => {
                location.reload();
            });
    }
}

async function deleteAllProduct() {

    if (window.confirm("전체 상품을 삭제하시겠습니까?")) {
        await fetch(`/async/cart/delete/all-product`, {
            method: "GET",
        })
            .then(data => {
                location.reload();
            });
    }
}

async function changeProductCount(productNo, input) {

    let productStock = Number(input.dataset.stock);
    let memberNo = document.querySelector(".memberNo");

    if(!input.value.match(/[0-9]/)) {
        input.value = '';
        alert("수량은 숫자만 입력 가능합니다.")
        return;
    }

    if (input.value > productStock){
        input.value = '';
        alert("상품의 재고보다 많이 구매하실 수 없습니다.")
        return;
    }

    let data = {
        "memberNo": Number(memberNo.value),
        "productNo": Number(productNo),
        "productCount": Number(input.value)
    };

    await fetch(`/async/cart/change/product-count`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
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
        alert("상품의 재고보다 많이 구매하실 수 없습니다.")
    }

    let totalPrice = input.parentElement.parentElement.parentElement.parentElement.querySelector(".total_price");
    let productPrice = Number(input.parentElement.parentElement.parentElement.parentElement.querySelector(".discount_price").innerHTML);
    totalPrice.value = Number(input.value) * productPrice;
}


let generalProductBody = document.getElementById("general_product");
