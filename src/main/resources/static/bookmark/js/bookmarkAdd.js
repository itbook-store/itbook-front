async function bookmarkAdd(memberNo, productNo) {

    if (!checkMemberNoAndProductNo(memberNo, productNo)) {
        Swal.fire({
            icon: 'warning',
            title: '잘못된 요청입니다.'
        })
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
                Swal.fire({
                    icon: 'warning',
                    title: "이미 즐겨찾기에 등록된 상품입니다.",
                })
            } else {
                Swal.fire({
                    icon: 'success',
                    title: '즐겨찾기에 등록 하였습니다.',
                });
            }
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