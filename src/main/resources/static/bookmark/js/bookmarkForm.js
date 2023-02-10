async function bookmarkDelete(memberNo, productNo) {

    if (!checkMemberNoAndProductNo(memberNo, productNo)) {
        alert("잘못된 요청입니다.")
        return;
    }

    if (window.confirm("즐겨찾기 해당 상품을 삭제하시겟습니까?")) {
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

    }
}

async function bookmarkDeleteAll(memberNo) {

    if (window.confirm("즐겨찾기 모든 상품을 삭제 하시겠습니까?")) {
        await fetch(`../async/bookmark/delete/${memberNo}`, {
            method: "POST"
        })
            .then(data => {
                location.reload();
            })
    }
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