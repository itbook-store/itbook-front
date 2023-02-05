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

async function showSearchResults(event) {

    event.preventDefault();

    let isbn = document.getElementById('isbn').value;
    console.log(isbn);

    let isExistsInDb = false;
    let isExistsInAladin = false;

    await fetch(`/async/books/exist-db?isbn=${isbn}`, { method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            isExistsInDb = data.isExists;
        });

    await fetch(`/async/books/exist-aladin?isbn=${isbn}`, { method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            isExistsInAladin = data.isExists;
        });

    document.getElementById('search-results').style.display = "block";

    // 알라딘에 존재하고 db에 없으면 등록 가능
    if( isExistsInAladin && !isExistsInDb) {
        console.log('등록 성공')
        let bookTitle;
        await fetch(`/async/books?isbn=${isbn}`, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {
                bookTitle = data.item[0].title
            });
        console.log(bookTitle);
        document.getElementById('isbnFailed').style.display = 'none';
        document.getElementById('isbnSuccessful').style.display = 'block';
        document.getElementById('isbnSuccessful').innerText = '등록 가능한 isbn입니다. 검색 결과: '.concat(bookTitle);
        document.getElementById('confirmBook').disabled = true;
        document.getElementById('isbn').readOnly = true;
        document.getElementById('isbnRetypeBtn').style.visibility = 'visible';
    }

    else{
        console.log('등록 실패')
        document.getElementById('isbn').value = '';
        document.getElementById('isbnFailed').style.display = 'block';
        document.getElementById('isbnSuccessful').style.display = 'none';
        document.getElementById('confirmBook').disabled = false;
        document.getElementById('isbn').readOnly = false;
        document.getElementById('isbnRetypeBtn').style.visibility = 'visible';
    }
}

function retypeFn(text, existMsg, notExistMsg, checkBtn, retypeBtn) {
    document.getElementById('search-results').style.display = "none";
    document.getElementById(text).value = '';
        document.getElementById(text).readOnly = false;
        document.getElementById(existMsg).style.display = 'none';
        document.getElementById(notExistMsg).style.display = 'none';
        document.getElementById(checkBtn).disabled = false;
        document.getElementById(retypeBtn).style.visibility = 'hidden';
}

function showBookForm(mainCategoryName) {
    let bookFormDiv = document.getElementById("form-book");
    if (mainCategoryName.includes("도서")) {
        bookFormDiv.style.display = "block";
    } else {
        bookFormDiv.style.display = "none";
    }
}