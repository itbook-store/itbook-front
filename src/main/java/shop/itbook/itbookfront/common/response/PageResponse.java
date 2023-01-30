package shop.itbook.itbookfront.common.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.domain.Page;


/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;

    private int totalPages;

    private int number;

    private boolean previous;

    private boolean next;
}