package shop.itbook.itbookfront.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.servlet.ServletInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.util.ResponseChecker;

@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        InputStream is = response.getBody();
        String messageBody = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        CommonResponseBody commonResponseBody = objectMapper.readValue(messageBody, CommonResponseBody.class);
        HttpStatus statusCode = response.getStatusCode();

        String resultMessage = commonResponseBody.getHeader().getResultMessage();
        ResponseChecker.checkFail(statusCode, resultMessage);
    }
}