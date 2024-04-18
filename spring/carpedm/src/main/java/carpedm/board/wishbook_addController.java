package carpedm.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.test222.HomeController;

@Controller // MVC컨트롤러로 선언
public class wishbook_addController extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(wishbook_addController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/wishbook_add", method = RequestMethod.GET)
	protected String wishbook_add(Locale locale, Model model)
			throws ServletException, IOException {
		
		List library = sqlSession.selectList("mapper.carpedm.board.library_list");
		List mem = sqlSession.selectList("mapper.carpedm.board.login_mpid","21");
		System.out.println("list : " + library);
		
		if (library != null) {
			System.out.println("list.isze : " + library.size());
			logger.error("list.size : " + library.size());
		}

		model.addAttribute("library", library);		
		model.addAttribute("mem", mem);		
		
		return "board/wishbook_add.jsp";
	}		
}
