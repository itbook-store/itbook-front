window.addEventListener("load", () => {
})

function selectHowToShowCategories(eventTarget) {

    eventTarget.outerHTML =
        `                          
                          <ul class="list-unstyled ps-0">        
                                <li>
<button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false">
                        카테고리 관리
                    </button>
                    </li>
                                            <li onclick="location.href='/admin/categories'">

                    <button style="margin-left: 10%" class="btn btn-toggle" >카테고리 전체 조회</button>
                    </li>
                                            <li onclick="location.href='/admin/categories/main-categories'">

                    <button style="margin-left: 10%" class="btn btn-toggle mt-1" >카테고리 분류별 조회</button>
                    </li>
                                            <li onclick="location.href='/admin/category-addition'">

                    <button style="margin-left: 10%" class="btn btn-toggle mt-1" >카테고리 추가</button>
                    </li>
</ul>        
`;
}

function showProductSubMenu(eventTarget) {

    eventTarget.outerHTML =
        `   
   <ul class="list-unstyled ps-0">        
            <li ><button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false">
                상품(도서) 관리
            </button></li>  
            <li onclick="location.href='/admin/products'">
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
            상품 목록
            </button></li>  

            <li onclick="location.href='/admin/products/relation'">
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    연관상품 관리
            </button></li>  

            <li onclick="location.href='/admin/products/sales-rank'">
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    상품 판매현황
            </button></li>
            </ul>
        `;
}

function selectHowToShowCoupon(eventTarget) {

    eventTarget.outerHTML =
        `   <ul class="list-unstyled ps-0">        
            <li>            
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-right: 20%">
                        쿠폰 관리
                    </button>
                    </li>
                                            <li onclick="location.href='/admin/coupons/list'">
<button class="btn btn-toggle mt-1" style="margin-left: 10%">쿠폰 전체 조회</a></button>
</li>                        <li onclick="location.href='/admin/coupon/coupon-issues/list'">

                    <button class="btn btn-toggle mt-1" style="margin-left: 10%">쿠폰 발급 내역 조회</a></button>
                    </li>
                                            <li onclick="location.href='/admin/coupons/coupon-addition'">

                    <button class="btn btn-toggle mt-1" style="margin-left: 10%" >쿠폰 추가</a></button>
                    </li>
</li>
</ul>        
`;
}

``

function selectMemberSideMenu(eventTarget) {

    eventTarget.outerHTML =
        `
<ul class="list-unstyled ps-0">        
            <li>
<button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false">
                회원 관리
            </button>
            </li>
                                    <li onclick="location.href='/admin/members'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    정상회원목록
            </button>
            </li>
                                    <li onclick="location.href='/admin/members/block'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%;">
                    차단회원목록
            </button>
            </li>
                                    <li onclick="location.href='/admin/members/withdraw'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    탈퇴회원목록
            </button>
            </li>
                                    <li onclick="location.href='/admin/members/count'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    회원통계
            </button>
            </li>
                                    <li onclick="location.href='/admin/members/membership/count'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    회원등급별통계
            </button>
            </li>
            </ul>
        `;
}

function selectWriterSideMenu(eventTarget) {

    eventTarget.outerHTML =
        `<ul class="list-unstyled ps-0">        
            <li>
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    작가 관리
            </button>
            </li>
                                    <li onclick="location.href='/admin/members/writer'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    작가목록
            </button>
            </li>
                        <li onclick="location.href='/admin/members/register/writer'">
            <button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    작가등록
            </button>
            </li>
            </ul>
           
        `;
}

function selectOrderSideMenu(eventTarget) {
    eventTarget.outerHTML =
        `<ul class="list-unstyled ps-0">        
            <li>
            <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#dashboard-collapse" aria-expanded="false">
                    주문 관리
            </button></li>
                                    <li onclick="location.href='/admin/orders/list'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    일반상품 주문 관리
            </button>
            </li>
            
                                    <li onclick="location.href='/admin/orders/list/subscription'">
<button class="btn btn-toggle" data-bs-toggle="collapse"
                data-bs-target="#dashboard-collapse" aria-expanded="false" style="margin-left: 20%">
                    구독상품 주문 관리
            </button>
            </li>
            </ul>
        `;
}