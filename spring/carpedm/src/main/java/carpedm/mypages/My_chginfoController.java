package carpedm.mypages;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.test222.HomeController;

@Controller
public class My_chginfoController {

	MypageService mypageService;
	
	My_chginfoController(){
		System.out.println("Mypage_chginfoController 입장");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;
	
	// 대출 내역 페이지
		@RequestMapping(value = "/mypage_chginfo", method = RequestMethod.GET)
		protected String mypage_chginfo(Locale locale, Model model
					
				) throws ServletException, IOException {

			
			
			List<Map<String, Object>> myInfo = sqlSession.selectList("mapper.carpedm.mypage.myInfo1", "15");
			
					
					model.addAttribute("myInfo", myInfo);

			
			

			return "mypages/mypage_chginfo.jsp.noTiles";
		}
		
		@RequestMapping(value = "/mypage_chginfo", method = RequestMethod.POST)
		    public String handleFormSubmit(
		                                   @RequestParam("password") String password,
		                                   @RequestParam("phonenumber") String phoneNumber,
		                                   @RequestParam("email_id") String emailId,
		                                   @RequestParam("email_domain") String emailDomain,
		                                   @RequestParam("email") String emailAgree,
		                                   @RequestParam("sample6_postcode") String postcode,
		                                   @RequestParam("sample6_address") String address,
		                                   @RequestParam("sample6_address2") String address2) {
		        // 폼 데이터 처리 로직 작성
			Map<String, String> chgInfo = new HashedMap();
			
			
			chgInfo.put("password", password);
			chgInfo.put("phonenumber", phoneNumber);
			chgInfo.put("email_id", emailId);
			chgInfo.put("email_domain", emailDomain);
			chgInfo.put("email", emailAgree);
			chgInfo.put("sample6_postcode", postcode);
			chgInfo.put("sample6_address", address);
			chgInfo.put("sample6_address2", address2);

			int succhk = sqlSession.insert("mapper.carpedm.mypage.updateInfo", chgInfo);
	
		        return "mypage_loan_status.jsp"; 
		    }
		
}