package carpedm.sign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import carpedm.dto.MemberDTO;

@Controller
public class S_Find_id_emailController {

	private static final Logger logger = LoggerFactory.getLogger(S_Find_id_emailController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/find_id_email", method = RequestMethod.GET)
	protected String find_id_email(Locale locale, Model model) throws ServletException, IOException {

//		List list = sqlSession.selectList("mapper.carpedm.sign.find_id_email");

		List<String> email_list = new ArrayList<String>();
		email_list.add("직접입력");
		email_list.add("naver.com");
		email_list.add("daum.net");
		email_list.add("google.com");
		model.addAttribute("emailList", email_list);

		return "sign/find_id_email.jsp";
	}

	@RequestMapping(value = "/find_id_email", method = RequestMethod.POST)
	@ResponseBody
	protected String find_id_email_post(Locale locale, Model model,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "useremail", defaultValue = "") String useremail)
			throws ServletException, IOException {

		String result = "";
		Map map = new HashMap();
		map.put("username", username);
		map.put("useremail", useremail);

		List<MemberDTO> list = sqlSession.selectList("mapper.carpedm.sign.find_id_email", map);
		
//		logger.info(list.get(0).getM_id());
		if (list.size() > 0) {
			result = "{\"message\": \""+list.get(0).getM_id()+"\"}";
		}
		else{
			result = "{\"message\": \"fail\"}";
		}
		
		return result;
	}
}