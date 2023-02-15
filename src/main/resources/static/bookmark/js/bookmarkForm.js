function bookmarkDelete(memberNo, productNo) {

    if (!checkMemberNoAndProductNo(memberNo, productNo)) {
        Swal.fire({
            icon: 'warning',
            title: '잘못된 요청입니다.'
        })
        return;
    }

    Swal.fire({
        title: '해당 상품을 삭제 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '삭제하기',
        preConfirm: async () => {

            data = {
                "memberNo": memberNo,
                "productNo": productNo
            };

            await fetch(`../async/bookmark/delete`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(data => {
                    location.reload()
                })
        },
    })

}

function bookmarkDeleteAll(memberNo) {

    Swal.fire({
        title: '모든 상품을 삭제 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '삭제하기',
        preConfirm: async () => {

            await fetch(`../async/bookmark/delete/${memberNo}`, {
                method: "POST"
            })
                .then(data => {
                    location.reload();
                })
        },
    })

}

function checkMemberNoAndProductNo(memberNo, productNo) {
    if (memberNo === null || memberNo === undefined) {
        return false;
    }

    if (productNo === null || productNo === undefined) {
        return false;
    }

    return true;
}