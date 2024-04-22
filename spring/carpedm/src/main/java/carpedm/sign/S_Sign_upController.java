package carpedm.sign;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import carpedm.dto.MemberDTO;

@Controller
public class S_Sign_upController {

private static final Logger logger = LoggerFactory.getLogger(S_Sign_upController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.GET)
	protected String sign_up(Locale locale, Model model)
			throws ServletException, IOException {
		
//		List list = sqlSession.selectList("mapper.carpedm.sign.login_up");
//		System.out.println("list : " + list);
		
//		if (list != null) {
//			System.out.println("list.isze : " + list.size());
//			logger.error("list.size : " + list.size());
//		}
//
//		model.addAttribute("list", list);		
		
		return "sign/sign_up.jsp";
	}
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	protected String signUpdate(
								@RequestParam("id") String id,
								@RequestParam("pw") String pw,
								@RequestParam("name") String name,
								@RequestParam("birthday") String birthday,
								@RequestParam("tel") String tel,
								@RequestParam("email1") String email1,
								@RequestParam("email2") String email2,
								@RequestParam("agree") String agree,
								@RequestParam("address1") String address1,
								@RequestParam("address2") String address2,
								@RequestParam("address3") String address3
			) {
		
		
//		Map<String, String> custom = new HashedMap();
		MemberDTO custom = new MemberDTO();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = dateFormat.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		custom.setM_id(id);
		custom.setM_pw(pw);
		custom.setM_name(name);
		custom.setM_birthday(date);
		custom.setM_tel(tel);
		custom.setM_email(email1 + "@" + email2);
		custom.setM_email_agree(agree);
		custom.setM_address(address1 +"\n"+ address2 +"\n"+ address3);
	
		
		System.out.println("이거 오는지 확인이나 좀 해보자ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ " + custom);
	
		int succhk = sqlSession.insert("mapper.carpedm.sign.signInsert", custom);
		
		System.out.println("이게 문제냐 쉬발ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ" + succhk);

		return "redirect:/main";
	}		
}
