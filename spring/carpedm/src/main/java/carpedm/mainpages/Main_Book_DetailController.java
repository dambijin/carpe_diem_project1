package carpedm.mainpages;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import carpedm.dto.BookCartDTO;
import carpedm.dto.ChaseBookDTO;

@Controller
public class Main_Book_DetailController {

	private final Logger logger = LoggerFactory.getLogger(Main_Book_DetailController.class);

	@Autowired
	private Main_Book_DetailService main_Book_DetailService;

	ClassLoader loader = Main_Book_DetailController.class.getClassLoader();

	@RequestMapping(value = "/book_detail", method = RequestMethod.GET)
	protected String book_detail(Locale locale, Model model, HttpServletRequest request,
			@RequestParam(value = "id", defaultValue = "") String b_id,
			@RequestParam(value = "si_id", defaultValue = "") String si_id) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);

		ChaseBookDTO updateParams = new ChaseBookDTO();
		Date now_date = new Date();
		updateParams.setB_id(b_id);
		updateParams.setSi_id(si_id);
		updateParams.setM_pid(m_pid);
		updateParams.setB_time(now_date);
		List<Object> bookDetailResult = main_Book_DetailService.getBookDetail(updateParams);

		Map<String, String> bookDetailMap = (Map<String, String>) bookDetailResult.get(0);
		List<Map<String, String>> sameISBNbookDetailList = (List<Map<String, String>>) bookDetailResult.get(1);
		List<Map<String, String>> bookRecommendList = (List<Map<String, String>>) bookDetailResult.get(2);

		model.addAttribute("bookdetail_map", bookDetailMap);
		model.addAttribute("sameISBNbookDetailList", sameISBNbookDetailList);
		model.addAttribute("bookrecommend_list", bookRecommendList);

		// 현재 페이지의 URL을 생성합니다.
		String requestURL = request.getRequestURL().toString() + "?id=" + b_id;
		logger.info(requestURL);
		QR qr = new QR();
		qr.create(requestURL, b_id, "png");
		model.addAttribute("qr_img", b_id + ".png");

		return "mainpages/book_detail.jsp.noTiles";
	}

	@RequestMapping(value = "/goCart", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	public String goCart_book_detail(HttpServletRequest request, 
			@RequestParam("b_id") String b_id,
			@RequestParam("m_pid") String m_pid) {

		String result = "{\"message\": \"fail\"}";

		HttpSession session = request.getSession();
		String login_m_pid = (String) session.getAttribute("m_pid") + "";
		logger.info("로그인 id : " + m_pid);

		if (!login_m_pid.equals(m_pid)) {
			m_pid = "";
		}

		if (m_pid != null && !m_pid.isEmpty() && !"null".equals(m_pid)) {
			BookCartDTO bcdto = new BookCartDTO();
			Date now_date = new Date();
			bcdto.setB_id(b_id);
			bcdto.setM_pid(login_m_pid);
			bcdto.setBc_date(now_date);
			int succhk = main_Book_DetailService.insertBookCart(bcdto);
			logger.info("인서트 : " + succhk);
			result = "{\"message\": \"success\"}";
		} else {
			result = "{\"message\": \"fail\"}";
		}
		
		return result;
	}

	@RequestMapping("/download")
	public void download(HttpServletResponse response, @RequestParam("fileName") String fileName) {
		FileDownload fileDownload = new FileDownload();
		try {
			fileDownload.download(response, fileName);
		} catch (Exception e) {
		}

	}
}
