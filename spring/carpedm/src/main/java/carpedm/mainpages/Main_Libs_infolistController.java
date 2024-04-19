package carpedm.mainpages;

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

@Controller
public class Main_Libs_infolistController extends HttpServlet {
	
	private final Logger logger = LoggerFactory.getLogger(Main_Libs_infolistController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/libs_infolist", method = RequestMethod.GET)
	protected String libs_infolist(Locale locale, Model model)
			throws ServletException, IOException {
		
		List list = sqlSession.selectList("mapper.carpedm.mainpages.selectLib_libinfo");
//		System.out.println("list : " + list);
//		
//		if (list != null) {
//			System.out.println("list.isze : " + list.size());
//			logger.error("list.size : " + list.size());
//		}

		model.addAttribute("list", list);		
		
		return "mainpages/libs_infolist.jsp";
	}		
	
}
