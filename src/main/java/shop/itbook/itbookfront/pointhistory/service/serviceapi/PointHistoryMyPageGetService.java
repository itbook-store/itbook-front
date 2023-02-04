package shop.itbook.itbookfront.pointhistory.service.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.pointhistory.adapter.serviceapi.PointHistoryMyPageGetAdaptor;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryListDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PointHistoryMyPageGetService {

    private final PointHistoryMyPageGetAdaptor pointHistoryMyPageGetAdaptor;

    public PageResponse<PointHistoryListDto> findMyPointHistoryList(String apiUrl) {

        return pointHistoryMyPageGetAdaptor.findMyPointHistoryList(apiUrl);
    }
}
