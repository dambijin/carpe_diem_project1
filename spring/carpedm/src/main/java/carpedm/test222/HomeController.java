package carpedm.test222;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);


		List list = sqlSession.selectList("mapper.carpedm.mainpages.selectLibList");
		System.out.println("list : " + list);
		
		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}

		model.addAttribute("list", list);		
		model.addAttribute("serverTime", formattedDate );
		
		return "home.jsp";
//		return "home.noTiles";
	}
	
}