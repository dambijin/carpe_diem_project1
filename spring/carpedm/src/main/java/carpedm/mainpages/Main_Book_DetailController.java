package carpedm.mainpages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main_Book_DetailController {

	private final Logger logger = LoggerFactory.getLogger(Main_Book_DetailController.class);

	@Autowired
	private Main_Book_DetailService main_Book_DetailService;

	@RequestMapping(value = "/book_detail", method = RequestMethod.GET)
	protected String book_detail(Locale locale, Model model, HttpServletRequest request,
			@RequestParam(value = "id", defaultValue = "") String b_id,
			@RequestParam(value = "si_id", defaultValue = "") String si_id) throws ServletException, IOException {

		List<Object> bookDetailResult = main_Book_DetailService.getBookDetail(b_id, si_id);

		Map<String, String> bookDetailMap = (Map<String, String>) bookDetailResult.get(0);
		List<Map<String, String>> bookRecommendList = (List<Map<String, String>>) bookDetailResult.get(1);

		model.addAttribute("bookdetail_map", bookDetailMap);
		model.addAttribute("bookrecommend_list", bookRecommendList);

		return "mainpages/book_detail.jsp.noTiles";
	}
}
