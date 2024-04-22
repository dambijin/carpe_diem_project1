package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.dto.WishlistDTO;

@Controller // MVC컨트롤러로 선언
public class wishbook_addController extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(wishbook_addController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/wishbook_add", method = RequestMethod.GET)
	protected String wishbook_add(Locale locale, Model model,
			HttpServletRequest request)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		if(mpid==null || mpid.equals("")) {
			return "redirect:/sign_in";
		}
		
		List library = sqlSession.selectList("mapper.carpedm.board.library_list");
		List mem = sqlSession.selectList("mapper.carpedm.board.login_mpid",mpid);
		System.out.println("list : " + library);
		
		if (library != null) {
			System.out.println("list.isze : " + library.size());
			logger.error("list.size : " + library.size());
		}

		model.addAttribute("library", library);		
		model.addAttribute("mem", mem);		
		
		return "board/wishbook_add.jsp";
	}		
	
	
	// 인서트 메소드
	@RequestMapping(value = "/wishbook_add", method = RequestMethod.POST)
	protected String wishbookAddInsert(Locale locale, Model model, 
			@ModelAttribute WishlistDTO dto) throws ServletException, IOException {
		sqlSession.insert("mapper.carpedm.board.wishbook_add_insert", dto);

		return "redirect:/QnA_board";
	}
}
