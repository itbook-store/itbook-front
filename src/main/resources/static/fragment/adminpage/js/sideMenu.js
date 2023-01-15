window.addEventListener("load", () => {
})

function selectHowToShowCategories(eventTarget) {

    eventTarget.outerHTML =
        `                     
                    <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#dashboard-collapse" aria-expanded="false">
                        카테고리 관리
                    </button>
                    <button style="margin-left: 20%"><a href="/admin/categories">카테고리 전체 조회</a></button>
                    <button style="margin-left: 20%"><a href="/admin/categories/main-categories">카테고리 분류별 조회</a></button>
        `;
}

