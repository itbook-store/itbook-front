
function addCategorySubmit() {
    let categoryName = document.getElementById("categoryName1").value;

    if (checkStringLengthDown(categoryName, 20)) {
        return true;
    }

    Swal.fire('카테고리 이름 길이는 20자 이하여야 합니다!', '', 'error');
    return false;
}