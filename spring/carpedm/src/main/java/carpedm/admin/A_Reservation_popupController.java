package carpedm.admin;

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

import carpedm.dto.MemberDTO;
import carpedm.test222.HomeController;

@Controller
public class A_Reservation_popupController {

//	ReservationService reservationService;
	
	A_Reservation_popupController() {
		System.out.println("Reservation_listController 입장");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;

	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/admin_reservation_list", method = RequestMethod.GET)
	protected String wishbook_list(Locale locale, Model model,
				@RequestParam("m_pid") String m_pid
			) 
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectReservation", m_pid);
		
		model.addAttribute("list", list);

		return "admin/admin_reservation_list.jsp.noTiles";
	}
	
	
}

