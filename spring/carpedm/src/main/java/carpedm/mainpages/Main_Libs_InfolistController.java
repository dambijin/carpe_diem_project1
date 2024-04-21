package carpedm.mainpages;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.dto.BookDTO;

@Controller
public class Main_Libs_InfolistController {

	private final Logger logger = LoggerFactory.getLogger(Main_Libs_InfolistController.class);

	@Autowired
	private Main_Libs_InfolistService libInfoService;

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/libs_infolist", method = RequestMethod.GET)
	protected String libs_infolist(Locale locale, Model model) throws ServletException, IOException {
		logger.info("도서관정보 접근");
		model.addAttribute("list", libInfoService.getLibInfoList());
		return "mainpages/libs_infolist.jsp";
	}

//	@RequestMapping(value = "/parseletsgo", method = RequestMethod.GET)
	protected String yes24Parse(Locale locale, Model model) throws ServletException, IOException {
		logger.info("도서관정보 접근");

		Main_Book_Recommend mbr = new Main_Book_Recommend();
		List<BookDTO> parsing_book_list = mbr.HttpParseYes24(
				"https://www.yes24.com/Product/Category/BestSeller?categoryNumber=001&pageNumber=6&pageSize=120");
		for (int i = 0; i < parsing_book_list.size(); i++) {
			try {
				System.out.println(sqlSession.insert("mapper.carpedm.admin.insertBook", parsing_book_list.get(i)));
			} catch (Exception e) {
			}
		}

		return "mainpages/libs_infolist.jsp";
	}
}
