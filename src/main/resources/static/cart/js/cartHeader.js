var profileBtn = document.getElementById('trigger');
var menu = document.getElementById('menu');


profileBtn.addEventListener('click', openCloseMenu)

function openCloseMenu() {
    menu.classList.toggle('closed');
}


document.addEventListener('click', closeOnClickOutside);

function closeOnClickOutside(e) {
    if (!e.target.matches('#trigger, .bxs-user, .user')) {
        menu.classList.add('closed');
    }
}


document.addEventListener('keydown', closeOnEscape);

function closeOnEscape(e) {
    if (e.keyCode === 27) {
        menu.classList.add('closed');
    }
}

