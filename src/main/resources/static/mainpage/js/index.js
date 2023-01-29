

// IMG.src 에 이미지 url을 넣으면 브라우저에서 이미지를 다운하게 되고 로드가 다되면 이벤트 발생
IMG.addEventListener('load', function() {
    const IMG = new Image();
    IMG.src = '이미지url';
})