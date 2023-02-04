window.addEventListener("load", () => {
})

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