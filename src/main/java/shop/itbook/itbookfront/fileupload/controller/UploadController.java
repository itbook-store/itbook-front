package shop.itbook.itbookfront.fileupload.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.fileupload.ObjectService;
import shop.itbook.itbookfront.fileupload.dto.FileDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@Slf4j
public class UploadController {
    private final String storageUrl =
        "https://api-storage.cloud.toast.com/v1/AUTH_fcb81f74e379456b8ca0e091d351a7af";
    private final String tokenId =
        "gAAAAABjwZqNmeaYAsD_yr911apIG7nnIKM-pQ12Xk27YdpRW50Zcuf2Y1SvHu5BRoMV-r1OFm77i3p1ynZXB4JNuUVyH7b2MUcAV0hAC02PWo-SM8hWkBzdXpQArh48Q7GUerlVmceycZlAWAyqJ82nZqfUh9YOs4ScdsgQUFQhul2Tz8nUyvg";
    private final String containerName = "itbookTest";
    @Value("${spring.servlet.multipart.location}")
    private String objectPath;

    @Value("${spring.servlet.multipart.location}")
    private String downloadPath;

    private final String folderPath = "book/thumbnail";

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("files") List<MultipartFile> files, Model model)
        throws IOException {
        List<FileDto> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {

                String objectName = file.getOriginalFilename();

                ObjectService objectService = new ObjectService(storageUrl, tokenId);

                try {
                    // 파일로 부터 InputStream 생성
                    File objFile = new File(objectPath + "/" + objectName);
                    InputStream inputStream = new FileInputStream(objFile);

                    // 업로드
                    objectService.uploadObject(containerName, folderPath, objectName, inputStream);
                    FileDto fileDto = new FileDto(UUID.randomUUID().toString(),
                        objectName, file.getContentType());
                    fileList.add(fileDto);
                    log.info("{}", objectName + " Upload OK");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("fileList", fileList);
        return "adminpage/product/fileupload-result";
    }

    @GetMapping("/download")
    public void download(@RequestParam String uuid, @RequestParam String objectName)
        throws IOException {

        ObjectService objectService = new ObjectService(storageUrl, tokenId);

        try {
            // 오브젝트 다운로드
            InputStream inputStream =
                objectService.downloadObject(containerName, folderPath, objectName);

            // 다운로드한 데이터를 바이트 버퍼로 읽어 들임
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            // 버퍼의 데이터를 파일에 기록
            File target = new File(downloadPath + "/" + uuid + "_" + objectName);
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(buffer);
            outputStream.close();

            log.info("{}", objectName + " Download OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String uuid,
                                             @RequestParam String fileName)
        throws IOException {

        Path path = Paths.get(objectPath + "/" + uuid + "_" + fileName);
        String contentType = Files.probeContentType(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
            .filename(fileName, StandardCharsets.UTF_8).build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }*/


}
