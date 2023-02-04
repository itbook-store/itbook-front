function selectEbook() {
    let ebookDiv = document.getElementById("selectEbook");
    ebookDiv.style.display = "block";
}

function notSelectEbook() {
    let ebookDiv = document.getElementById("selectEbook");
    ebookDiv.style.display = "none";
}

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

async function showSubCategoryAndForm(event, categoryNoList) {

    event.preventDefault();

    let mainCategoryNo = document.getElementById("mainCategory").value;
    let target = document.getElementById("mainCategory");
    let mainCategoryName = target.options[target.selectedIndex].text;

    let subCategoryCheckBoxDiv = document.getElementById("categoryCheckBox");
    while (subCategoryCheckBoxDiv.hasChildNodes()) {
        subCategoryCheckBoxDiv.removeChild(subCategoryCheckBoxDiv.firstChild);
    }
    subCategoryCheckBoxDiv.style.display = "block";

    showBookForm(mainCategoryName);
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
                    checkBox.style = "margin-right : 4px";
                    checkBoxText.style = "margin-right : 7px";
                    if(categoryNoList && categoryNoList.length !=0)
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

function showBookForm(mainCategoryName) {
    let bookFormDiv = document.getElementById("form-book");
    if (mainCategoryName.includes("도서")) {
        bookFormDiv.style.display = "block";
    } else {
        bookFormDiv.style.display = "none";
    }
}