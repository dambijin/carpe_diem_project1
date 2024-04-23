package carpedm.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.OverdueDTO;

@Controller
public class A_book_overduePopupController {

	A_book_overduePopupController(){
		System.out.println("A_book_overdueController 생성자");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(A_book_overduePopupController.class);

	@Autowired
	private SqlSession sqlSession;
	
	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/admin_book_overdue", method = RequestMethod.GET)
	protected String overdue_book(Locale locale, Model model,
				@RequestParam("m_pid") String m_pid
			) 
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectOverdue", m_pid);
			
		model.addAttribute("list", list);

		return "admin/admin_book_overdue.jsp.noTiles";
		
	}
	
	
//	@RequestMapping(value = "/admin_book_overdue", method = RequestMethod.POST)
//	public String bookdelete(Locale locale, Model model,
//			@ModelAttribute OverdueDTO dto) throws ServletException, IOException {
//	
//		logger.debug("포스트접근성공");
//		
//		logger.info("bookdelete 실행됨");
//		    
//		System.out.println(dto.getM_pid());
//
//		
//
//	    // 회원 정보 업데이트 쿼리 실행
//	    int result = sqlSession.update("mapper.carpedm.admin.updateOverdue", params);
//
//	    // 업데이트 결과에 따라 처리
//	    if (result > 0) {
//	    	System.out.println("update 결과 : " + result);
////	    	// 예: 수정 성공 메시지 전달 및 회원 목록 페이지로 리다이렉트
//	        return "redirect:/admin_member_list";
//	    } else {
//	        // 업데이트 실패 시 처리할 내용
//	        // 예: 수정 실패 메시지 전달 및 이전 페이지로 이동
//	        return "admin/admin_book_overdue.jsp.noTiles"; // 실패했을 때의 페이지 경로 설정
//	    }
//	}
	
}
		
		
	
