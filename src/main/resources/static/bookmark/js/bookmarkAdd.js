async function bookmarkAdd(memberNo, productNo) {

    console.log(memberNo);
    console.log(productNo);

    if (!checkMemberNoAndProductNo(memberNo, productNo)) {
        alert("잘못된 요청입니다.")
        return;
    }

    data = {
        "memberNo": memberNo,
        "productNo": productNo
    };

    await fetch(`async/bookmark/add`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if (data === false) {
                alert("이미 즐겨찾기에 등록된 상품입니다.");
            } else {
                alert("즐겨찾기에 등록 하였습니다.")
            }
        })
}

async function bookmarkDelete(memberNo, productNo) {

    if (!checkMemberNoAndProductNo(memberNo, productNo)) {
        alert("잘못된 요청입니다.")
        return;
    }

    if (window.confirm("해당 상품을 삭제하시겠습니까?")) {
        data = {
            "memberNo": memberNo,
            "productNo": productNo
        };

        await fetch(`async/bookmark/delete`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response)
            .then(data => data);
    }
}

async function bookmarkDeleteAll(memberNo) {

    if (memberNo === null || memberNo === undefined) {
        alert("잘못된 요청입니다.")
        return;
    }

    if (window.confirm("모든 상품을 삭제 하시겠습니까?")) {
        await fetch(`async/bookmark/delete/${memberNo}`, {
            method: "POST",
        })
            .then(response => response)
            .then(data => data);
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