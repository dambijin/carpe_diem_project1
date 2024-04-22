package carpedm.admin;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.dto.BookDTO;
import carpedm.dto.BookgenreDTO;

@Controller
public class A_Book_addController {

	A_Book_addController() {
		System.out.println("A_Book_addController 생성자");
	}

	@Autowired
	A_BookService abookService;

	private static final Logger logger = LoggerFactory.getLogger(A_Book_addController.class);

	@Autowired
	private SqlSession sqlSession;

	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/admin_book_add", method = RequestMethod.GET)
	protected String bookAdd(Locale locale, Model model, @ModelAttribute BookgenreDTO gdto)
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectBooKAdd");

		List genre = abookService.listGenre(gdto);

		model.addAttribute("list", list);
		model.addAttribute("genre", genre);

		return "admin/admin_book_add.jsp.noTiles";

	}

	// 인서트 메소드
	@RequestMapping(value = "/admin_book_add", method = RequestMethod.POST)
	protected String BookInsert(Locale locale, Model model, 
			@ModelAttribute BookDTO dto)
			throws ServletException, IOException {
		System.out.println("책인서트 DTO"+dto);
		int booksql= sqlSession.insert("mapper.carpedm.admin.insertBook", dto);
		System.out.println("책인서트"+booksql);
		return "redirect:/admin_book_add";
	}

}
