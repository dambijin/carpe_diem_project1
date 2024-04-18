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
public class A_book_overduePopupController {

	A_book_overduePopupController(){
		System.out.println("A_book_overdueController 생성자");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;
	
	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/admin_book_overdue", method = RequestMethod.GET)
	protected String overdue_book(Locale locale, Model model,
				@RequestParam("m_pid") String m_pid
			) 
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectOverdue", m_pid);
			
		model.addAttribute("list", list);

		return "admin/admin_book_overdue.jsp.noTiles";
		
	}
	
	
}
