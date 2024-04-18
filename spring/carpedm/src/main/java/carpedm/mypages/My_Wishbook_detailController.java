package carpedm.mypages;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class Wishbook_detailController {

	MypageService mypageService;

	Wishbook_detailController() {
		System.out.println("Wishbook_detailController 입장");
	}
	
		
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;

	// 희망 도서 신청 목록 페이지
		@RequestMapping(value = "/wishbook_detail", method = RequestMethod.GET)
		protected String wishbook_detail(Locale locale, @RequestParam("w_id") int w_id, Model model) {
		    // 희망 도서 상세 정보를 가져오는 코드
			List list = sqlSession.selectList("mapper.carpedm.mypage.wishboodetail", w_id);
			
			model.addAttribute("list", list);
			
		    return "mypages/wishbook_detail.noTiles";
		}

}
