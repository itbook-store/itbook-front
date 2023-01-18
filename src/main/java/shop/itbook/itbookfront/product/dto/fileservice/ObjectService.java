package shop.itbook.itbookfront.product.dto.fileservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import lombok.Data;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

/**
 * @author 이하늬
 * @since 1.0
 */
@Data
public class ObjectService {
    private String tokenId;
    private String storageUrl;

    private RestTemplate restTemplate;

    public ObjectService(String storageUrl, String tokenId) {
        this.setStorageUrl(storageUrl);
        this.setTokenId(tokenId);

        this.restTemplate = new RestTemplate();
    }

    private String getUrl(@NonNull String containerName, @NonNull String folderPath,
                          @NonNull String objectName) {
        return this.storageUrl + "/" + containerName + "/" + folderPath + "/" + objectName;
    }

    public InputStream downloadObject(String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", tokenId);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<byte[]> response
            = this.restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);

        return new ByteArrayInputStream(response.getBody());
    }

}
