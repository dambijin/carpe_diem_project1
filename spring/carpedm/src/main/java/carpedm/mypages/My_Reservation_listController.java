package carpedm.mypages;

import java.io.IOException;
import java.util.ArrayList;
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
public class My_Reservation_listController {

	MypageService mypageService;

	My_Reservation_listController() {
		System.out.println("Reservation_listController 입장");
	}
	
		
	private final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private SqlSession sqlSession;

	// 예약페이지
		@RequestMapping(value = "/mypage_reservation_list", method = RequestMethod.GET)
		protected String reservation_list(Locale locale, Model model,
				@RequestParam(value = "search", defaultValue = "") String keyword 
				) throws ServletException, IOException {

			Map<String, String> map = new HashedMap();
			
			//m_pid 자리 넘보지 마셈
			map.put("m_pid", 15+"");
			map.put("keyword", keyword);
			
			
			List list = sqlSession.selectList("mapper.carpedm.mypage.reservationlist", map);
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
			//페이징관련
			//book_count는 총 검색 게시글 수
			//단순계산용(모델에 안넣음)
			int currentPage = Integer.parseInt(page);
			int itemsPerPage = Integer.parseInt(perPage);
			int startRow = (currentPage - 1) * itemsPerPage + 1;
			int endRow = currentPage * itemsPerPage;
			
			// 페이지 처리를 위한 계산(모델에 넣어야함)
			int startPage = Math.max(currentPage - 2, 1);
			int totalPages = book_count > 0 ? (int) Math.ceil(book_count / Double.parseDouble(perPage)) : 1;
			int endPage = Math.min(startPage + 4, totalPages); 
		    startPage = Math.max(1, endPage - 4);
			
					model.addAttribute("diff", diff);
					model.addAttribute("myInfo", myInfo);

			System.out.println(list);
			model.addAttribute("list", list);

			return "mypages/mypage_reservation_list.jsp";
		}
		
		// 취소 메소드 
				@RequestMapping(value = "/mypage_reservation_list", method = RequestMethod.POST)
				protected String reservation_cancle(Locale locale, Model model,
						@RequestParam(value = "m_pid", defaultValue = "") String m_pid, 
						@RequestParam(value = "ids", defaultValue = "") String r_id	
						) throws ServletException, IOException {

							
					        Map<String, String> map = new HashMap();
					        map.put("m_pid", 15+""); // 숫자를 문자열로 변환하여 넣어줌
					        map.put("r_id", r_id); // 공백 제거 후 추가
			
					  
					        int update = sqlSession.insert("mapper.carpedm.mypage.reservationUpdate", map);
					        logger.info("업데이트 렁공 :" + update);
					       

					        int delete = sqlSession.insert("mapper.carpedm.mypage.reservationCancle", map);
					        logger.info("딜리트 렁공 :" + delete);
			
					    return "mypages/mypage_reservation_list.jsp";
					}
		
		

}
