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
// async function getGeneralProductList() {
//
//     await fetch(`/async/cart/general`, {
//         method: "GET",
//     })
//         .then(response => response.json())
//         .then(data => {
//             console.log(data);
//
//             data.forEach((a, i) => {
//                 let temp = document.createElement("tr");
//                 temp.classList.add("table_line");
//
//                 tmep.innerHTML =
//                     `
//                     <td>
//                             <div style="display: flex; align-items: center; justify-content: center">
//                                 <button type="button" class="btn btn-primary btn-sm me-1 mb-2" data-mdb-toggle="tooltip"
//                                         title="Remove item" th:attr="productNo=${data[i].productDetailsResponseDto.productNo}" th:onclick="deleteProduct(this.getAttribute('productNo'))">
//                                     <i class="fas fa-trash"></i>
//                                 </button>
//                                 <button type="button" class="btn btn-danger btn-sm mb-2" data-mdb-toggle="tooltip"
//                                         title="Move to the wish list">
//                                     <i class="fas fa-heart"></i>
//                                 </button>
//                             </div>
//                         </td>
//                         <td>
//                             <div class="product_titleBox">
//                                 <a th:href="@{|/products/${data[i].productDetailsResponseDto.productNo}|}" style="display: flex; align-items: center; text-decoration: none; color: black">
//                                     <img class="product_img" th:src="${data[i].productDetailsResponseDto.fileThumbnailsUrl}" alt="" >
//                                 <p class="product_title" th:text="${data[i].productDetailsResponseDto.productName}"></p>
//                                 </a>
//                             </div>
//                         </td>
//                         <td>
//                             <div class="product_priceBox">
//                                 <p class="fixed_price" th:text="${data[i].productDetailsResponseDto.fixedPrice}"></p>
//                                 <p class="discount_price" th:text="${data[i].productDetailsResponseDto.selledPrice}"></p>
//                             </div>
//                         </td>
//                         <td>
//                             <p class="product_stock" th:text="${data[i].productDetailsResponseDto.stock}"></p>
//                         </td>
//                         <td>
//                             <div class="d-flex" style="max-width: 300px; align-items: center; justify-content: center;">
//                                 <div class="form-outline">
//                                     <input type="hidden" class="memberNo" th:value="${#authentication.principal.memberNo}"/>
//                                     <input min="1" max="100" name="quantity" th:value="${data[i].productCount}"  class="quantity" type="number"
//                                            onkeydown="javascript: return ['Backspace','Delete','ArrowLeft','ArrowRight'].includes(event.code) ? true : !isNaN(Number(event.key)) && event.code!=='Space'"
//                                            th:attr="productNo=${data[i].productDetailsResponseDto.productNo}, data-stock=${data[i].productDetailsResponseDto.stock}" th:onchange="changeProductCount(this.getAttribute('productNo') , this)"/>
//                                 </div>
//                             </div>
//                         </td>
//                         <td>
//                             <input class="total_price" type="number" readonly th:value="${data[i].productDetailsResponseDto.selledPrice * dto.productCount}" />
//                         </td>
//                 `
//                 generalProductBody.append(temp);
//             })
//
//         });
// }
