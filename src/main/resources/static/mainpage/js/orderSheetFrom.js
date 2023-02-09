async function createOrderForPayment() {

    let orderAddRequestDto;

    orderAddRequestDto = {
        "member": document.getElementsByName('')
    }

    await fetch(`/async/orders/payment-start`, {

        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderAddRequestDto)
    })
        .then(response => response.json())
        .then(data => data.forEach(console.log(data)))
}