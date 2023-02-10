package shop.itbook.itbookfront.pointhistory.service.serviceapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.pointhistory.adapter.PointHistoryPageGetAdaptor;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookfront.pointhistory.service.serviceapi.PointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryPageGetAdaptor pointHistoryPageGetAdaptor;

    @Override
    public PageResponse<PointHistoryListDto> findPointHistoryList(String apiUrl) {

        return pointHistoryPageGetAdaptor.findMyPointHistoryList(apiUrl);
    }
}
