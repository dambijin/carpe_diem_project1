package carpedm.mainpages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

@Controller
public class Main_Controller extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(Main_Controller.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	protected String main(Locale locale, Model model)
			throws ServletException, IOException {
		String now_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		List library_list = sqlSession.selectList("mapper.carpedm.mainpages.selectLib_libinfo");
		List notice_list = sqlSession.selectList("mapper.carpedm.mainpages.selectNotice_main");
		List book_list = sqlSession.selectList("mapper.carpedm.mainpages.selectBook_main");
		List banner_list = sqlSession.selectList("mapper.carpedm.mainpages.selectBanner_main",now_date);

		model.addAttribute("library_list", library_list);		
		model.addAttribute("notice_list", notice_list);		
		model.addAttribute("book_list", book_list);
		model.addAttribute("banner_list", banner_list);
		
		return "mainpages/main.jsp";
	}			
}
