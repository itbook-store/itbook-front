let dropBtn = document.querySelector('.categoryBar__columns__menu');
let menu = document.querySelector('.dropdown');

dropBtn.addEventListener('click', function(){
    if(menu.classList.contains('activeCategory')){
        menu.classList.remove('activeCategory');
    } else {
        menu.classList.add('activeCategory');
    }
})
