window.addEventListener("load", () => {
})

function clickPointMenu(eventTarget) {

    eventTarget.outerHTML =
        `
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false">
                포인트
            </button><br />
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/point-histories/show-content/my-point-list" style="text-decoration: none; color:inherit;">
                    포인트 내역조회
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/mypage/members/me/point-gift" style="text-decoration: none; color:inherit;">
                    포인트 선물하기
                </a>
            </button>
        `;
}

function clickReviewMenu(eventTarget) {
    eventTarget.outerHTML =
        `
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    리뷰관리
            </button><br />
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/review/mypage/list" style="text-decoration: none; color:inherit;">
                    나의 리뷰 목록
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/review/mypage/unwritten-list" style="text-decoration: none; color:inherit;">
                    작성가능한 리뷰 목록
                </a>
            </button>
        `;
}

function clickProductInquiry(eventTarget) {
    eventTarget.outerHTML =
        `
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    상품문의관리
            </button><br />
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/product-inquiries/mypage/list" style="text-decoration: none; color:inherit;">
                    나의 상품문의 목록
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/product-inquiries/writable/list" style="text-decoration: none; color:inherit;">
                    작성가능한 상품문의 목록
                </a>
            </button>
        `;
}

function clickOrderMenu(eventTarget) {
    eventTarget.outerHTML =
        `
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    주문/배송 조회
            </button><br />
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/orders/mypage/list" style="text-decoration: none; color:inherit;">
                    일반상품 주문/배송 조회
                </a>
            </button>
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                <a href="/orders/mypage/list/subscription" style="text-decoration: none; color:inherit;">
                    구독상품 주문/배송 조회
                </a>
            </button>
        `;
}