package carpedm.mypages;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.test222.HomeController;

@Controller
public class MypageController {

	MypageService mypageService;

	MypageController() {
		System.out.println("컨드롤러 입장");
	}

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;

	// 대출 현황 페이지
	@RequestMapping(value = "/mypage_loan_status", method = RequestMethod.GET)
	protected String loan_status(Locale locale, Model model) throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.mypage.loanstatus");
//	List list = sqlSession.selectList("mapper.carpedm.mypage.selectLibList2");

		System.out.println(list);
		model.addAttribute("list", list);

		return "mypages/mypage_loan_status.jsp";
	}

	// 대출 내역 페이지
	@RequestMapping(value = "/mypage_loan_history", method = RequestMethod.GET)
	protected String loan_history(Locale locale, Model model) throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.mypage.loanhistory");

		System.out.println(list);
		model.addAttribute("list", list);

		return "mypages/mypage_loan_history.jsp";
	}

	// 예약페이지
	@RequestMapping(value = "/mypage_reservation_list", method = RequestMethod.GET)
	protected String reservation_list(Locale locale, Model model) throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.mypage.reservationlist");

		System.out.println(list);
		model.addAttribute("list", list);

		return "mypages/mypage_reservation_list.jsp";
	}

	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/mypage_wishbook_list", method = RequestMethod.GET)
	protected String wishbook_list(Locale locale, Model model) throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.mypage.wishbooklist");

		System.out.println(list);
		model.addAttribute("list", list);

		return "mypages/mypage_wishbook_list.jsp";
	}

	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/wishbook_detail", method = RequestMethod.GET)
	protected String wishbook_detail(Locale locale, @RequestParam("w_id") int w_id, Model model) {
	    // 희망 도서 상세 정보를 가져오는 코드
		List list = sqlSession.selectList("mapper.carpedm.mypage.wishboodetail", w_id);
		System.out.println("이거다 임마야" + list);
		model.addAttribute("list", list);
		
	    return "mypages/wishbook_detail.noTiles";
	}

}
