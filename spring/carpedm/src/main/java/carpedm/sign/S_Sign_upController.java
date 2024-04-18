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
public class S_Sign_upController {

private static final Logger logger = LoggerFactory.getLogger(S_Sign_upController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.GET)
	protected String sign_up(Locale locale, Model model)
			throws ServletException, IOException {
		
		List list = sqlSession.selectList("mapper.carpedm.sign.login_up");
		System.out.println("list : " + list);
		
		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}

		model.addAttribute("list", list);		
		
		return "sign/sign_up.jsp";
	}		
}
