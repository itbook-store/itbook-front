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

            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/admin/products/sales-rank" style="text-decoration: none; color:inherit;">
                    상품 판매순위
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

function selectMemberSideMenu(eventTarget) {

    eventTarget.outerHTML =
        `
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false">
                회원 관리
            </button><br />
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/admin/members" style="text-decoration: none; color:inherit;">
                    정상회원목록
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/admin/members/block" style="text-decoration: none; color:inherit;">
                    차단회원목록
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                <a href="/admin/members/withdraw" style="text-decoration: none; color:inherit;">
                    탈퇴회원목록
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                <a href="/admin/members/count" style="text-decoration: none; color:inherit;">
                    회원통계
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                <a href="/admin/members/membership/count" style="text-decoration: none; color:inherit;">
                    회원등급별통계
                </a>
            </button>
        `;
}

function selectWriterSideMenu(eventTarget) {

    eventTarget.outerHTML =
        `
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    작가 관리
            </button><br/>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                <a href="/admin/members/writer" style="text-decoration: none; color:inherit;">
                    작가목록
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                <a href="/admin/members/register/writer" style="text-decoration: none; color:inherit;">
                    작가등록
                </a>
            </button>
        `;
}