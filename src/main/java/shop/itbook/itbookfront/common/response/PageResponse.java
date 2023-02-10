package shop.itbook.itbookfront.common.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


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