package carpedm.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import carpedm.dto.MemberDTO;

@Controller
public class A_MemberController {

	A_MemberController(){
		System.out.println("MemberController 생성자");
	}
	
	@Autowired  // Spring이 MemberService의 인스턴스를 이 필드에 자동으로 주입해야 함을 나타냄
	A_MemberService memberService;
	
	@RequestMapping("/admin_member_list")
	public String listMembers(Model model, @ModelAttribute MemberDTO dto) {
		System.out.println("MemberController > listMembers 실행");
		
		List list = memberService.listMembers(dto);  // memberService를 통해 회원 목록을 가져옵니다.
		
		// 왜 null 이지?
		System.out.println("keyword : " + dto.getKeyword());
		
		// 연체상태 계산 로직
		for (int i = 0; i < list.size(); i++) {
	        MemberDTO member = (MemberDTO) list.get(i);
	        System.out.println("Index " + i + ": " + member);
	        
		    String m_limitdate_text = "정상";
		    Date m_limitdate = ((MemberDTO) list.get(i)).getM_limitdate();
		    
		    if (m_limitdate != null) {
		    	// db에서 가져온 날짜와 오늘날짜 차이 계산
		        long millisecondsDiff = m_limitdate.getTime() - new Date().getTime();
		        // 두 날짜 간의 시간 차이를 일 단위로 변환
		        long daysDiff  = millisecondsDiff / (1000 * 60 * 60 * 24);
		    	
		        if (daysDiff > 0) {
		            m_limitdate_text = "<font color='red'>" + daysDiff  + "일"; // 날짜 차이가 양수일 때
		        } else {
		            m_limitdate_text = "정상"; // 오늘 날짜까지의 차이
		        }
		    }
		    ((MemberDTO) list.get(i)).setM_limitdate_text(m_limitdate_text);
		}

		System.out.println("list : "+ list);
		model.addAttribute("member_list", list); // 모델에 회원 목록을 속성으로 추가합니다.
		
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("type", type);
//		
//		model.addAttribute("orderColumn", orderColumn);
//		model.addAttribute("orderType", orderType);
		
		return "admin/admin_member_list.jsp.admin"; // "admin/admin_member_list" 뷰를 반환합니다.
		
	}
}
