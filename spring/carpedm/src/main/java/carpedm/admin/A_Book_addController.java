package carpedm.admin;

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
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.test222.HomeController;

@Controller
public class A_Book_addController {
	
	A_Book_addController(){
		System.out.println("A_Book_addController 생성자");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;
	
	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/admin_book_add", method = RequestMethod.GET)
	protected String bookAdd(Locale locale, Model model) 
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectBooKAdd");
				
		model.addAttribute("list", list);

		return "admin/admin_book_add.jsp.noTiles";
			
	}
	
}



