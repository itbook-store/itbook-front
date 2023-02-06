function selectDateOfRegistration() {
    let id = document.getElementById("searchRequirement");

    if(id.options[id.selectedIndex].value == 'dateOfJoining') {
        document.getElementById("date").style.display='block';
        document.getElementById("searchWord").style.display ='none';
    }
    else {
        document.getElementById("date").style.display='none';
        document.getElementById("searchWord").style.display ='block';
    }
}