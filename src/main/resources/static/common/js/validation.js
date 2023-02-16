function checkStringLengthDown(obj, size) {
    if (obj.length > size) {
        return false;
    } else {
        return true;
    }
}

function checkStringLengthDownTo20(obj) {
    if (obj.length > 20) {
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

function checkNumberUpToNum(num, obj) {
    if (obj < num || isNaN(obj)) {
        return false;
    } else {
        return true;
    }
}

function checkNumberOfPercentBetween(min, max, obj) {
    if (obj < min || obj > max || isNaN(obj)) {
        return false;
    } else {
        return true;
    }
}