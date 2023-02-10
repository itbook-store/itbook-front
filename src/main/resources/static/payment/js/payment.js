function doPaymentProcessByCard(data) {
    const clientKey = 'test_ck_N5OWRapdA8d7MGGao2Pro1zEqZKL';
    var tossPayments = TossPayments(clientKey);

    tossPayments.requestPayment('카드', { // 결제 수단 파라미터
                                        // 결제 정보 파라미터
        amount: data.amount,
        orderId: data.orderId,
        orderName: data.orderName,
        successUrl: data.successUrl,
        failUrl: data.failUrl,
    })
        .catch(function (error) {
            if (error.code === 'USER_CANCEL') {
            } else if (error.code === 'INVALID_CARD_COMPANY') {
                // 유효하지 않은 카드 코드에 대한 에러 처리
            }
        })
}

//
//
// function doPaymentProcess() {
//
// }
//
// const customerKey = 'itbook2023';
// const paymentWidget = PaymentWidget(clientKey, customerKey);  // 결제위젯 초기화
// paymentWidget.renderPaymentMethods('#payment-method', 15000);
//
// tossPayments.requestPayment('가상계좌', { // 결제 수단 파라미터
//                                       // 결제 정보 파라미터
//     amount: 15000,
//     orderId: 'jN8dymboXkv7Kil8Eqlrp',
//     orderName: '토스 티셔츠 외 2건',
//     customerName: '박토스',
//     successUrl: 'http://localhost:8080/success',
//     failUrl: 'http://localhost:8080/fail',
//     validHours: 24,
//     cashReceipt: {
//         type: '소득공제',
//     },
// })
//     .catch(function (error) {
//         if (error.code === 'USER_CANCEL') {
//             // 결제 고객이 결제창을 닫았을 때 에러 처리
//         } else if (error.code === 'INVALID_CARD_COMPANY') {
//             // 유효하지 않은 카드 코드에 대한 에러 처리
//         }
//     })
//
// <html lang="ko">
// <head>
//     <script src="https://js.tosspayments.com/v1"></script>
//     <title>결제하기</title>
//     <meta charSet="utf-8">
// </head>
// <body>
// <script>
//     var clientKey = 'test_key'
//     var tossPayments = TossPayments(clientKey) // 클라이언트 키로 초기화하기
// </script>
// </body>
// <script>
//     tossPayments.requestPayment('카드', {
//     amount: 150000,
//     orderId: '0299f62b-865b-4ba1-812f-15sdfs42',
//     orderName: '개인산책',
//     customerName: '홍길동',
//     successUrl: 'http://localhost:8080/success',
//     failUrl: 'http://localhost:8080/fail',
// })
// </script>
// </html>