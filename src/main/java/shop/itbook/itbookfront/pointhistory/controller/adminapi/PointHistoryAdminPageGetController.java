package shop.itbook.itbookfront.pointhistory.controller.adminapi;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookfront.pointhistory.pointincreasedecreasecontentenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookfront.pointhistory.service.serviceapi.PointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/point-histories")
@RequiredArgsConstructor
public class PointHistoryAdminPageGetController {

    public static final String DIRECTORY_PATH = "adminpage/pointhistory";

    private final PointHistoryService pointHistoryService;

    @GetMapping("/{pointHistoryNo}")
    public String pointDetails(@PathVariable Long pointHistoryNo, @RequestParam String content, HttpSession session, @PageableDefault Pageable pageable) {

        PointIncreaseDecreaseContentEnum pointContentEnum =
            PointIncreaseDecreaseContentEnum.stringToEnum(content);

        session.setAttribute("adminPointHistoryPage", pageable.getPageNumber());
        String format = String.format(pointContentEnum.getAdminRedirectUrl(), pointHistoryNo);
        return Strings.concat("redirect:", format);
    }

    @GetMapping("/show-content/admin-point-list")
    public String pointHistoryList(@PageableDefault Pageable pageable, @RequestParam(required = false) String content, Model model) {

        String format = String.format("/api/admin/point-histories?page=%d&size=%d&content=%s", pageable.getPageNumber(),
            pageable.getPageSize(), content);

        PageResponse<PointHistoryListDto> pageResponse = pointHistoryService.findPointHistoryList(
            format);

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/point-histories/show-content/admin-point-list");
        model.addAttribute("content", content);
        return Strings.concat(DIRECTORY_PATH, "/adminPointHistoryList");
    }

    @GetMapping("/show-content/admin-point-list/search")
    public String pointHistoryList(@PageableDefault Pageable pageable,
                                   @RequestParam(required = false) String content, @RequestParam(required = false) String searchWord,
                                   Model model) {

        String urlFormat = String.format("/api/admin/point-histories/search?page=%d&size=%d&content=%s&searchWord=%s", pageable.getPageNumber(),
            pageable.getPageSize(), content, searchWord);

        PageResponse<PointHistoryListDto> pageResponse = pointHistoryService.findPointHistoryList(
            urlFormat);

        String paginationUrlFormat = String.format("/admin/point-histories/show-content/admin-point-list/search?content=%s&searchWord=%s", content, searchWord);

        model.addAttribute("paginationUrl", paginationUrlFormat);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("content", content);
        model.addAttribute("searchWord", searchWord);
        return Strings.concat(DIRECTORY_PATH, "/adminPointHistoryList");
    }
}
