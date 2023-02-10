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
                data-bs-target="#dashboard-collapse" aria-expanded="false">
                상품 관리
            </button><br />
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/admin/products" style="text-decoration: none; color:inherit;">
                    상품 목록
                </a>
            </button>
            
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/admin/products/relation" style="text-decoration: none; color:inherit;">
                    연관상품 관리
                </a>
            </button>
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
}

``