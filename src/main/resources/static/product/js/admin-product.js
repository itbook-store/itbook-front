function selectPointApplying() {
    let pointPercentDiv = document.getElementById("increasingPoint-div");
    let pointBasedDiv = document.getElementById("increasingPointBase-div");
    pointPercentDiv.style.display = "block";
    pointBasedDiv.style.display = "block";
}



function notSelectPointApplying() {
    let pointPercentDiv = document.getElementById("increasingPoint-div");
    let pointBasedDiv = document.getElementById("increasingPointBase-div");
    pointPercentDiv.style.display = "none";
    pointBasedDiv.style.display = "none";

}

async function showSubCategory(event, categoryNoList) {

    event.preventDefault();

    let mainCategoryNo = document.getElementById("mainCategory").value;
    let target = document.getElementById("mainCategory");
    // let mainCategoryName = target.options[target.selectedIndex].text;

    let subCategoryCheckBoxDiv = document.getElementById("categoryCheckBox");
    while (subCategoryCheckBoxDiv.hasChildNodes()) {
        subCategoryCheckBoxDiv.removeChild(subCategoryCheckBoxDiv.firstChild);
    }
    subCategoryCheckBoxDiv.style.display = "block";

    // showBookForm(mainCategoryName);
    await fetch(`/async/${mainCategoryNo}/sub-categories`, {
        method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            data.forEach(checkBoxList => {
                if (checkBoxList.level !== 0) {
                    let checkBox = document.createElement("input");
                    let checkBoxText = document.createElement("label");
                    checkBox.type = "checkbox";
                    checkBox.name = "categoryNoList";
                    checkBox.value = checkBoxList.categoryNo;
                    checkBox.addEventListener("click", function () {
                        checkLimitCategory(this);
                    });
                    checkBox.style = "margin-right : 4px";
                    checkBoxText.style = "margin-right : 7px";
                    if (categoryNoList && categoryNoList.length != 0)
                        if (categoryNoList.some(no => no == checkBoxList.categoryNo)) {
                            checkBox.checked = true;
                        }
                    checkBoxText.innerText = checkBoxList.categoryName;
                    subCategoryCheckBoxDiv.appendChild(checkBox);
                    subCategoryCheckBoxDiv.appendChild(checkBoxText);
                }
            });
        });
}

function checkLimitCategory(element) {
    let checkBox = document.getElementsByName("categoryNoList");
    let cnt = 0;
    let check = false;

    checkBox.forEach((cb) => {
        if (cb.checked) {
            check = true;
            cnt++;
        }
    })

    if (!check) {
        return false;
    } else {
        if (cnt > 3) {
            Swal.fire("??????????????? ?????? 3????????? ?????? ???????????????.", '', 'error');
            element.checked = false;
            return false;
        } else
            return true;
    }
}



function previewImage(input) {
    if (input.files && input.files[0]) {
        document.getElementById('imagePreview').style.display = "block";

        let reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('imagePreview').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById('imagePreview').src = "";
    }
}


function addProductSubmit() {

    let checkBox = document.getElementsByName("categoryNoList");
    let name = document.getElementById("productName").value;
    let simpleDescription = document.getElementById("simpleDescription").textContent;
    let detailsDescription = document.getElementById("detailsDescription").textContent;
    let increasePointPercent = document.getElementById("increasePointPercent").value;
    let rawPrice = document.getElementById("rawPrice").value;
    let fixedPrice = document.getElementById("fixedPrice").value;
    let discountPercent = document.getElementById("discountPercent").value;
    let stock = document.getElementById("stock").value;
    let isPointApplyingValue = $('input[name="isPointApplying"]:checked').val();
    let isPointApplyingBasedSellingPriceValue = $('input[name="isPointApplyingBasedSellingPrice"]:checked').val();
    let thumbnailsFile = document.getElementById("fileThumbnails").value;

    console.log(discountPercent);
    console.log(discountPercent.length);

    if (!checkCheckBoxCountUpTo0(checkBox)) {
        Swal.fire('??????????????? ?????? 1?????? ??????????????? ?????????!', '', 'error');
        return false;
    }

    if (!thumbnailsFile) {
        Swal.fire('????????? ????????? ???????????????!', '', 'error');
        return false;
    }

    if (!checkStringLengthDown(name, 255)) {
        Swal.fire('?????? ?????? ????????? 255??? ???????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkStringLengthDown(simpleDescription, 255)) {
        Swal.fire('?????? ?????? ????????? 255??? ???????????? ?????????!', '', 'error');
        return false;
    }

    if (isPointApplyingValue == "true") {

        if (!checkNumberOfPercent(increasePointPercent)) {
            Swal.fire('????????? ???????????? 0 ~ 100%????????? ?????????!', '', 'error');
            return false;
        }

        if (increasePointPercent == null) {
            Swal.fire('????????? ?????? ?????? ??? ????????? ????????? ????????? ?????? ???????????????!', '', 'error');
            return false;
        }

        if (isPointApplyingBasedSellingPriceValue == null) {
            Swal.fire('????????? ?????? ??? ????????? ?????? ?????? ????????? ?????? ???????????????!', '', 'error');
            return false;
        }

    }

    // if (bookCreatedAt!="") {
    //     if(bookCreatedAt)
    //     let currentDate = new Date;
    //     let maxDate = new Date;
    //     let minDate = new Date;
    //     maxDate.setDate(currentDate.getDate() + 18250);
    //     minDate.setDate(currentDate.getDate() - 36500);
    //     bookCreatedAt.value = currentDate.toISOString().slice(0, 10);
    //     bookCreatedAt.min = minDate.toISOString().slice(0, 10);
    //     bookCreatedAt.max = maxDate.toISOString().slice(0, 10);
    // }


    if (!checkNumberUpTo0(rawPrice)) {
        Swal.fire('????????? 0??? ??????????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkNumberUpTo0(fixedPrice)) {
        Swal.fire('????????? 0??? ??????????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkNumberOfPercent(discountPercent)) {
        console.log("?????????")
        Swal.fire('???????????? 0 ~ 100%????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkNumberUpTo0(stock)) {
        Swal.fire('????????? 0??? ??????????????? ?????????!', '', 'error');
        return false;
    }

    Swal.fire('?????? ?????? ??????!', '', 'success');
    return true;

}

function handleInputLength(inputText, max) {

    if(inputText.value.length > max) {
        inputText.value = inputText.value.substr(0, max);
    }
}

function showInputLength(inputText, max) {
    let inputLengthDiv = document.getElementById("inputLength");
    inputLengthDiv.innerText = inputText.value.length + "/" + max + "???";

    if(inputText.value.length > max) {
        inputText.value = inputText.value.substr(0, max);
    }
}



function modifyProductSubmit() {

    console.log("??????");

    let checkBox = document.getElementsByName("categoryNoList");
    let name = document.getElementById("productName").value;
    let simpleDescription = document.getElementById("simpleDescription").textContent;
    let detailsDescription = document.getElementById("detailsDescription").textContent;
    let increasePointPercent = document.getElementById("increasePointPercent").value;
    let rawPrice = document.getElementById("rawPrice").value;
    let fixedPrice = document.getElementById("fixedPrice").value;
    let discountPercent = document.getElementById("discountPercent").value;
    let stock = document.getElementById("stock").value;
    let isPointApplyingValue = $('input[name="isPointApplying"]:checked').val();
    let isPointApplyingBasedSellingPriceValue = $('input[name="isPointApplyingBasedSellingPrice"]:checked').val();


    if (!checkCheckBoxCountUpTo0(checkBox)) {
        Swal.fire('??????????????? ?????? 1?????? ??????????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkStringLengthDown(name, 255)) {
        Swal.fire('?????? ?????? ????????? 255??? ???????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkStringLengthDown(simpleDescription, 255)) {
        Swal.fire('?????? ?????? ????????? 255??? ???????????? ?????????!', '', 'error');
        return false;
    }

    if (isPointApplyingValue == "true") {

        if (!checkNumberOfPercent(increasePointPercent)) {
            Swal.fire('????????? ???????????? 0 ~ 100%????????? ?????????!', '', 'error');
            return false;
        }

        if (increasePointPercent == null) {
            Swal.fire('????????? ?????? ?????? ??? ????????? ????????? ????????? ?????? ???????????????!', '', 'error');
            return false;
        }

        if (isPointApplyingBasedSellingPriceValue == null) {
            Swal.fire('????????? ?????? ??? ????????? ?????? ?????? ????????? ?????? ???????????????!', '', 'error');
            return false;
        }

    }

    if (!checkNumberUpTo0(rawPrice)) {
        Swal.fire('????????? 0??? ??????????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkNumberUpTo0(fixedPrice)) {
        Swal.fire('????????? 0??? ??????????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkNumberOfPercent(discountPercent)) {
        Swal.fire('???????????? 0 ~ 100%????????? ?????????!', '', 'error');
        return false;
    }

    if (!checkNumberUpTo0(stock)) {
        console.log("ddddddd");
        Swal.fire('????????? 0??? ??????????????? ?????????!', '', 'error');
        return false;
    }


    Swal.fire('?????? ?????? ??????!', '', 'success');
    return true;

}