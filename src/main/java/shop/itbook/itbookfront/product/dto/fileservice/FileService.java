package shop.itbook.itbookfront.product.dto.fileservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@Slf4j
public class FileService {
    @Value("${object.storage.storage-url}")
    private String storageUrl;
    @Value("${object.storage.token-id}")
    private String tokenId;
    @Value("${object.storage.container-name}")
    private String containerName;
    @Value("${object.storage.folder-path.download}")
    private String downloadPath;

    public byte[] download(String url) {
        ObjectService objectService = new ObjectService(storageUrl, tokenId);
        try {
            InputStream inputStream = objectService.downloadObject(url);
            byte[] buffer = new byte[inputStream.available()];
            return buffer;
//            InputStream inputStream = objectService.downloadObject(url);
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            String uuid = url.substring(url.lastIndexOf("/") + 1);
//            File target = new File(downloadPath + "/" + uuid);
//            OutputStream outputStream = new FileOutputStream(target);
//            outputStream.write(buffer);
//            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
