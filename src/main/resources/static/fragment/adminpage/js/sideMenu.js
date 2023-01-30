window.addEventListener("load", () => {
})

function selectHowToShowCategories(eventTarget) {

    eventTarget.outerHTML =
        `                     
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false">
                        카테고리 관리
                    </button>
                    <button style="margin-left: 20%" class="btn btn-toggle" onclick="location.href='/admin/categories'">카테고리 전체 조회</button>
                    <button style="margin-left: 20%" class="btn btn-toggle mt-1" onclick="location.href='/admin/categories/main-categories'">카테고리 분류별 조회</button>
                    <button style="margin-left: 20%" class="btn btn-toggle mt-1" onclick="location.href='/admin/category-addition'">카테고리 추가</button>
        `;
}

function selectHowToShowCoupon(eventTarget) {

    eventTarget.outerHTML =
        `                     
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false">
                        쿠폰 관리
                    </button>
                    <button class="btn btn-toggle" style="margin-left: 20%" onclick="location.href='/admin/coupon';">쿠폰 전체 조회</a></button>
                    <button class="btn btn-toggle" style="margin-left: 20%" onclick="location.href='/admin/coupon/coupon-addition';">쿠폰 추가</a></button>
        `;
}