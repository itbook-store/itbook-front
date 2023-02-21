window.addEventListener("load", () => {
})

function clickPointMenu(eventTarget) {

    eventTarget.outerHTML =
        `   <ul class="list-unstyled ps-0">        
            <li>
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false">
                포인트
            </button>
            </li>
            <li onclick="location.href='/point-histories/show-content/my-point-list'">
                <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    포인트 내역조회
                </button>
            </li>
            <li onclick="location.href='/mypage/members/me/point-gift'">
                <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    포인트 선물하기
                   </button>
            </li>
                    </ul>
`;
}

function clickReviewMenu(eventTarget) {
    eventTarget.outerHTML =
        `   <ul class="list-unstyled ps-0">        
<li>
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    리뷰관리
            </button></li>
                        <li onclick="location.href='/review/mypage/list'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    나의 리뷰 목록
            </button>
            </li>
                        <li onclick="location.href='/review/mypage/unwritten-list'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    작성가능한 리뷰 목록
            </button>
            </li>
            </ul>
        `;
}

function clickProductInquiry(eventTarget) {
    eventTarget.outerHTML =
        `   <ul class="list-unstyled ps-0">        
<li>
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    상품문의관리
            </button></li>
                        <li onclick="location.href='/product-inquiries/mypage/list'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    나의 상품문의 목록
            </button>
            </li>
                        <li onclick="location.href='/product-inquiries/writable/list'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    작성가능한 상품문의 목록
            </button>
            </li>
            </ul>
        `;
}

function clickOrderMenu(eventTarget) {
    eventTarget.outerHTML =
        `   <ul class="list-unstyled ps-0">        
            <li>

            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    주문/배송 조회
            </button></li>
                        <li onclick="location.href='/orders/mypage/list'">

            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    일반상품 주문/배송 조회
            </button>
            </li>
                        <li onclick="location.href='/orders/mypage/list/subscription'">

            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    구독상품 주문/배송 조회
            </button>
            </li>
            
            </ul>
        `;
}