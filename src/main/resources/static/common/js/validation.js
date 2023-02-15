function checkStringLengthDown(obj, size) {
    if (obj.length > size) {
        return false;
    } else {
        return true;
    }
}

function checkNumberUpTo0(obj) {
    if (obj < 0 || isNaN(obj)) {
        return false;
    } else {
        return true;
    }
}

function checkNumberOfPercent(obj) {
    if (obj < 0 || obj > 100 || isNaN(obj)) {
        return false;
    } else {
        return true;
    }
}

function checkCheckBoxCountUpTo0(obj) {

    let check = false;

    obj.forEach((cb) => {
        if (cb.checked) {
            check = true;
        }
    })

    if (!check) {
        return false;
    } else {
        return true;
    }
}