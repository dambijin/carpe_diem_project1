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
public class Loan_statusController {

	MypageService mypageService;

	Loan_statusController() {
		System.out.println("loan_statusController 입장");
	}
	
		
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;

	// 대출 현황 페이지
	@RequestMapping(value = "/mypage_loan_status", method = RequestMethod.GET)
	protected String loan_status(Locale locale, Model model) throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.mypage.loanstatus");
		MemberDTO myInfo = sqlSession.selectOne("mapper.carpedm.mypage.myInfo","2");
		String limitDate = myInfo.getM_limitdate()+"";

		// 현재 날짜를 가져오기
				java.util.Date currentDate = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd"); // 출력 형식 지정
				String formattedDate = sdf.format(currentDate); // 현재 날짜를 지정한 형식으로 변환

				// limitDate와 formattedDate의 차이 계산
				long diff = 0;
				try {
					java.util.Date limitDateObj = sdf.parse(limitDate);
					java.util.Date formattedDateObj = sdf.parse(formattedDate);

					long diffInMillies = limitDateObj.getTime() - formattedDateObj.getTime(); // 두 날짜의 밀리초 단위 차이
					diff = diffInMillies / (1000 * 60 * 60 * 24); // 밀리초를 일로 변환

				} catch (Exception e) {
					e.printStackTrace();
				}
		
		
				model.addAttribute("diff", diff);
		
		model.addAttribute("list", list);
		model.addAttribute("myInfo", myInfo);

		return "mypages/mypage_loan_status.jsp";
	}

	

	

	

	

}
