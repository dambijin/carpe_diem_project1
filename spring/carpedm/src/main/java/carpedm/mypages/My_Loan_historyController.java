package carpedm.mypages;

import java.io.IOException;
import java.util.HashMap;
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

import carpedm.dto.MemberDTO;
import carpedm.test222.HomeController;

@Controller
public class My_Loan_historyController {

	MypageService mypageService;
	
	My_Loan_historyController(){
		System.out.println("Loan_historyController 입장");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;
	
	
	
	// 대출 내역 페이지
		@RequestMapping(value = "/mypage_loan_history", method = RequestMethod.GET)
		protected String loan_history(Locale locale, Model model, 
				@RequestParam(value = "search", defaultValue = "") String keyword 
				) throws ServletException, IOException {

			Map<String, String> temp = new HashedMap();
			
			temp.put("keyword", keyword);
			temp.put("m_pid", 15+"");
			
			List <Map<String, String>> list = sqlSession.selectList("mapper.carpedm.mypage.loanhistory", temp);
			
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
					model.addAttribute("myInfo", myInfo);
					

			System.out.println(list);
			model.addAttribute("list", list);

			return "mypages/mypage_loan_history.jsp";
		}
}
