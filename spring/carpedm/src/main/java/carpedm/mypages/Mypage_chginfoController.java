package carpedm.mypages;

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

import carpedm.dto.MemberDTO;
import carpedm.test222.HomeController;

@Controller
public class Mypage_chginfoController {

	MypageService mypageService;
	
	Mypage_chginfoController(){
		System.out.println("Mypage_chginfoController 입장");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;
	
	// 대출 내역 페이지
		@RequestMapping(value = "/mypage_chginfo", method = RequestMethod.GET)
		protected String loan_history(Locale locale, Model model) throws ServletException, IOException {

			
			MemberDTO myInfo = sqlSession.selectOne("mapper.carpedm.mypage.myInfo","15");
			
					model.addAttribute("myInfo", myInfo);

			
			

			return "mypages/mypage_chginfo.noTiles";
		}
}
