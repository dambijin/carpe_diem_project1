package carpedm.sign;

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

@Controller
public class S_Find_pwController {

private static final Logger logger = LoggerFactory.getLogger(S_Find_pwController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/find_pw", method = RequestMethod.GET)
	protected String find_pw(Locale locale, Model model)
			throws ServletException, IOException {
		
		List list = sqlSession.selectList("mapper.carpedm.sign.find_pw");
		System.out.println("list : " + list);
		
		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}

		model.addAttribute("list", list);		
		
		return "sign/find_pw.jsp";
	}		
}
