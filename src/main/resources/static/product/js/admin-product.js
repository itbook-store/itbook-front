function selectEbook() {
    let ebookDiv = document.getElementById("selectEbook");
    ebookDiv.style.display = "block";
}

function notSelectEbook() {
    let ebookDiv = document.getElementById("selectEbook");
    ebookDiv.style.display = "none";
}

async function showSubCategoryAndForm(event, productCategoryList) {

    event.preventDefault();

    let categoryNoList = getCategoryNoList(productCategoryList);

    let mainCategoryNo = document.getElementById("mainCategory").value;

    let subCategoryCheckBoxDiv = document.getElementById("categoryCheckBox");
    while (subCategoryCheckBoxDiv.hasChildNodes()) {
        subCategoryCheckBoxDiv.removeChild(subCategoryCheckBoxDiv.firstChild);
    }
    subCategoryCheckBoxDiv.style.display = "block";

    showBookForm(mainCategoryNo);

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
                    if(categoryNoList.includes(checkBox.value)) {
                        checkBox.checked = true;
                    }
                    checkBoxText.innerText = checkBoxList.categoryName;
                    subCategoryCheckBoxDiv.appendChild(checkBoxText);
                    checkBoxText.appendChild(checkBox);
                }
            });
        });

}

function showBookForm(mainCategoryNo) {
    let bookFormDiv = document.getElementById("form-book");

    if (mainCategoryNo == 2) {
        bookFormDiv.style.display = "block";
    } else {
        bookFormDiv.style.display = "none";
    }
}

function getCategoryNoList(productCategoryList) {
    let categoryNoList = [];
    productCategoryList.forEach(element => {
        categoryNoList.push(element.categoryNo);
    })
    return categoryNoList;
}