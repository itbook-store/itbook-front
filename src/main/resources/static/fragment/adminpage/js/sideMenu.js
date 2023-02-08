window.addEventListener("load", () => {
})

function selectHowToShowCategories(eventTarget) {

    eventTarget.outerHTML =
        `                     
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false">
                        카테고리 관리
                    </button>
                    <button style="margin-left: 10%" class="btn btn-toggle" onclick="location.href='/admin/categories'">카테고리 전체 조회</button>
                    <button style="margin-left: 10%" class="btn btn-toggle mt-1" onclick="location.href='/admin/categories/main-categories'">카테고리 분류별 조회</button>
                    <button style="margin-left: 10%" class="btn btn-toggle mt-1" onclick="location.href='/admin/category-addition'">카테고리 추가</button>
        `;
}

function showProductSubMenu(eventTarget) {

    eventTarget.outerHTML =
        `                     
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false"> 상품 관리
                    </button>
                    <button style="margin-left: 10%" class="btn btn-toggle" onclick="location.href='/admin/products'">상품 목록 조회</button>
                    <button style="margin-left: 10%" class="btn btn-toggle" onclick="location.href='/admin/products/relation'">상품 연관상품 관리</button>
        `;

}

function selectHowToShowCoupon(eventTarget) {

    eventTarget.outerHTML =
        `                     
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-right: 20%">
                        쿠폰 관리
                    </button>
                    <button class="btn btn-toggle mt-1" style="margin-left: 10%" onclick="location.href='/admin/coupons';">쿠폰 전체 조회</a></button>
                    <button class="btn btn-toggle mt-1" style="margin-left: 10%" onclick="location.href='/admin/coupons/coupon-addition';">쿠폰 추가</a></button>
        `;
}``