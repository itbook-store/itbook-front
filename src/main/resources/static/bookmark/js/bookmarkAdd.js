async function bookmarkAdd(memberNo, productNo) {

    if (memberNo === null || memberNo === undefined) {
        return;
    }

    if (productNo === null || productNo === undefined) {
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
        .then(response => response)
        .then(data => {

            if (data === false) {
                alert("이미 즐겨찾기에 등록된 상품입니다.");
            } else {
                alert("즐겨찾기에 등록 하였습니다.")
            }

        })

}