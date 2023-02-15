package shop.itbook.itbookfront.pointhistory.controller.adminapi;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.exception.BadRequestException;
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
@Slf4j
public class PointHistoryAdminPageGetController {

    public static final String DIRECTORY_PATH = "adminpage/pointhistory";

    private final PointHistoryService pointHistoryService;

    @GetMapping("/{pointHistoryNo}")
    public String pointDetails(@PathVariable Long pointHistoryNo, @RequestParam String content, HttpSession session, @PageableDefault Pageable pageable, RedirectAttributes redirectAttributes) {

        try {
            PointIncreaseDecreaseContentEnum pointContentEnum =
                PointIncreaseDecreaseContentEnum.stringToEnum(content);

            session.setAttribute("adminPointHistoryPage", pageable.getPageNumber());
            String format = String.format(pointContentEnum.getAdminPagePointHistoryDetailsRedirectUrl(), pointHistoryNo);
            return Strings.concat("redirect:", format);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/point-histories/show-content/admin-point-list";
        }
    }

    @GetMapping("/show-content/admin-point-list")
    public String pointHistoryList(@PageableDefault Pageable pageable, @RequestParam(required = false) String content, Model model, RedirectAttributes redirectAttributes) {

        try {
            String format = String.format("/api/admin/point-histories?page=%d&size=%d&content=%s", pageable.getPageNumber(),
                pageable.getPageSize(), content);

            PageResponse<PointHistoryListDto> pageResponse = pointHistoryService.findPointHistoryList(
                format);

            model.addAttribute("pageResponse", pageResponse);
            model.addAttribute("paginationUrl", "/admin/point-histories/show-content/admin-point-list");
            model.addAttribute("content", content);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/adminpage";
        }

        return Strings.concat(DIRECTORY_PATH, "/adminPointHistoryList");
    }

    @GetMapping("/show-content/admin-point-list/search")
    public String pointHistoryList(@PageableDefault Pageable pageable,
                                   @RequestParam(required = false) String content, @RequestParam(required = false) String searchWord,
                                   Model model, RedirectAttributes redirectAttributes) {

        try {
            String urlFormat = String.format("/api/admin/point-histories/search?page=%d&size=%d&content=%s&searchWord=%s", pageable.getPageNumber(),
                pageable.getPageSize(), content, searchWord);

            PageResponse<PointHistoryListDto> pageResponse = pointHistoryService.findPointHistoryList(
                urlFormat);

            String paginationUrlFormat = String.format("/admin/point-histories/show-content/admin-point-list/search?content=%s&searchWord=%s", content, searchWord);

            model.addAttribute("paginationUrl", paginationUrlFormat);
            model.addAttribute("pageResponse", pageResponse);
            model.addAttribute("content", content);
            model.addAttribute("searchWord", searchWord);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/point-histories/show-content/admin-point-list";
        }

        return Strings.concat(DIRECTORY_PATH, "/adminPointHistoryList");
    }
}
