package carpedm.mypages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

@Controller
public class My_Reservation_listController {

	

	My_Reservation_listController() {
		System.out.println("Reservation_listController 입장");
	}
	
		
	private final Logger logger = LoggerFactory.getLogger(My_Reservation_listController.class);

	@Autowired
	private SqlSession sqlSession;

	// 예약페이지
		@RequestMapping(value = "/mypage_reservation_list", method = RequestMethod.GET)
		protected String reservation_list(Locale locale, Model model,
				@RequestParam(value = "search", defaultValue = "") String keyword,
				@RequestParam(value = "page", defaultValue = "1") String page,
				@RequestParam(value = "perPage", defaultValue = "10") String perPage,
				HttpServletRequest request
				) throws ServletException, IOException {
			HttpSession session = request.getSession();
			String m_pid = (String) session.getAttribute("m_pid") + "";
			logger.info("로그인 id : " + m_pid);
			
			if (m_pid == null || "".equals(m_pid) || "null".equals(m_pid)) {
				
				return "sign/sign_in.jsp";
			}

			Map<String, String> map = new HashMap();
			
			//페이징관련
			//book_count는 총 검색 게시글 수
			//단순계산용(모델에 안넣음)
			int currentPage = Integer.parseInt(page);
			int itemsPerPage = Integer.parseInt(perPage);
			int startRow = (currentPage - 1) * itemsPerPage + 1;
			int endRow = currentPage * itemsPerPage;
			
			//m_pid 자리 넘보지 마셈
			map.put("m_pid", m_pid+"");
			map.put("keyword", keyword);
			map.put("startRow", startRow +"");
			map.put("endRow", endRow+"");
			
			
			List list = sqlSession.selectList("mapper.carpedm.mypage.reservationlist", map);
			
			
			MemberDTO myInfo = sqlSession.selectOne("mapper.carpedm.mypage.myInfo",m_pid);
			String limitDate = myInfo.getM_limitdate() + "";

			System.out.println(limitDate);
			// 현재 날짜를 가져오기
			java.util.Date currentDate = new java.util.Date();
			java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
					Locale.ENGLISH);
			java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat("yyyy-MM-dd"); // 출력 형식 지정
			long diff = 0;
			try {
				// limitDate 문자열을 파싱하여 Date 객체로 변환
				java.util.Date limitDateObj = sdfInput.parse(limitDate);

				// Date 객체를 "yyyy-MM-dd" 형식의 문자열로 변환
				String formattedDate = sdfOutput.format(limitDateObj);

				// 변환된 문자열 출력
				System.out.println(formattedDate);

				// 현재 날짜를 "yyyy-MM-dd" 형식으로 변환
				String currentDateStr = sdfOutput.format(currentDate);

				// limitDate와 formattedDate의 차이 계산
				long diffInMillies = limitDateObj.getTime() - currentDate.getTime(); // 두 날짜의 밀리초 단위 차이
				diff = diffInMillies / (1000 * 60 * 60 * 24); // 밀리초를 일로 변환

				// 결과 출력
				System.out.println("날짜 차이: " + diff + "일");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
					model.addAttribute("list", list);
					model.addAttribute("diff", diff);
					model.addAttribute("myInfo", myInfo);
					
			
		
			
			Map res_count_temp = sqlSession.selectOne("mapper.carpedm.mypage.resCount", map);
			System.out.println(res_count_temp);
			int res_count = Integer.parseInt(String.valueOf(res_count_temp.get("count")));
			logger.info("북카운트:" + res_count);
			
			// 페이지 처리를 위한 계산(모델에 넣어야함)
			int startPage = Math.max(currentPage - 2, 1);
			int totalPages = res_count > 0 ? (int) Math.ceil(res_count / Double.parseDouble(perPage)) : 1;
			int endPage = Math.min(startPage + 4, totalPages); 
		    startPage = Math.max(1, endPage - 4);
		    
		    model.addAttribute("page", page);
			model.addAttribute("perPage", perPage);
		    model.addAttribute("res_count", res_count);
		    model.addAttribute("start_page", startPage);
			model.addAttribute("end_page", endPage);
			model.addAttribute("total_pages", totalPages);

			return "mypages/mypage_reservation_list.jsp";
		}
		
		
				// 취소 메소드 
				@RequestMapping(value = "/mypage_reservation_list", method = RequestMethod.POST)
				protected String reservation_cancle(Locale locale, Model model,
						HttpServletRequest request, 
						@RequestParam(value = "ids", defaultValue = "") String r_id	
						) throws ServletException, IOException {

					HttpSession session = request.getSession();
					String m_pid = (String) session.getAttribute("m_pid") + "";
					logger.info("로그인 id : " + m_pid);
							
					        Map<String, String> map = new HashMap();
					        map.put("m_pid", m_pid); // 숫자를 문자열로 변환하여 넣어줌
					        map.put("r_id", r_id); // 공백 제거 후 추가
			
					  
					        int update = sqlSession.insert("mapper.carpedm.mypage.reservationUpdate", map);
					        logger.info("업데이트 성공 :" + update);
					       

					        int delete = sqlSession.insert("mapper.carpedm.mypage.reservationCancle", map);
					        logger.info("딜리트 성공 :" + delete);
			
					    return "mypages/mypage_reservation_list.jsp";
					}
		
		

}
