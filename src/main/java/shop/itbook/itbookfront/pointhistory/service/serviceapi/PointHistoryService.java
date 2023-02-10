package shop.itbook.itbookfront.pointhistory.service.serviceapi;

import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryListDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointHistoryService {

    PageResponse<PointHistoryListDto> findPointHistoryList(String apiUrl);
}
