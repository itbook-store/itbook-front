window.addEventListener("load", () => {
})

function selectMemberSideMenu(eventTarget) {

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