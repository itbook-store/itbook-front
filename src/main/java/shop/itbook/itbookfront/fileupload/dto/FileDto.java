package shop.itbook.itbookfront.fileupload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 이하늬
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileDto {
    private String uuid;
    private String fileName;
    private String contentType;

}
