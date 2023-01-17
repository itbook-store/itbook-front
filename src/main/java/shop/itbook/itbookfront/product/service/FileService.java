package shop.itbook.itbookfront.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Value("${spring.servlet.multipart.location}")
    private String objectPath;

    @Value("${object.storage.folder-path.download}")
    private String downloadPath;


    public String uploadFile(MultipartFile multipartFile, String folderPath) {
        ObjectService objectService = new ObjectService(storageUrl, tokenId);
        String savedName = UUID.randomUUID().toString();
        try {
            File objFile = new File(multipartFile.getOriginalFilename());
            multipartFile.transferTo(objFile);
            InputStream inputStream = new FileInputStream(objFile);
            String fileStorageUrl =
                objectService.uploadObject(containerName, folderPath, savedName, inputStream);
            return fileStorageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile.getOriginalFilename();
    }

    public void download(String url) {
        ObjectService objectService = new ObjectService(storageUrl, tokenId);
        try {
            InputStream inputStream = objectService.downloadObject(url);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String uuid = url.substring(url.lastIndexOf("/") + 1);
            File target = new File(downloadPath + "/" + uuid);
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(buffer);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
