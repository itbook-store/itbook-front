const generalBtn = document.querySelector(".cart_type_general");
const subscriptionBtn =document.querySelector(".cart_type_subscription");

const generalProduct = document.querySelector(".general_product");
const subscriptionProduct = document.querySelector(".subscription_product")

generalBtn.addEventListener("click", function() {
    generalBtn.classList.add("btn-primary");
    generalBtn.classList.remove("btn-light");

    subscriptionBtn.classList.remove("btn-primary");
    subscriptionBtn.classList.add("btn-light");

    generalProduct.style.display = "block";
    subscriptionProduct.style.display = "none";

})

subscriptionBtn.addEventListener("click", function() {
    subscriptionBtn.classList.add("btn-primary");
    subscriptionBtn.classList.remove("btn-light");

    generalBtn.classList.remove("btn-primary");
    generalBtn.classList.add("btn-light");

    subscriptionProduct.style.display = "block";
    generalProduct.style.display = "none";
})

