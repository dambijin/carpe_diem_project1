package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import carpedm.dto.MemberDTO;

@Controller
public class MemberController {

	MemberController(){
		System.out.println("MemberController 생성자");
	}
	
	@Autowired  // Spring이 MemberService의 인스턴스를 이 필드에 자동으로 주입해야 함을 나타냄
	MemberService memberService;
	
	@RequestMapping("/admin/admin_member_list.do")
	public String listMembers(Model model, @ModelAttribute MemberDTO dto) {
		System.out.println("MemberController > listMembers 실행");
		
		List list = memberService.listMembers(dto);  // memberService를 통해 회원 목록을 가져옵니다.
		model.addAttribute("memberList",list); // 모델에 회원 목록을 속성으로 추가합니다.
		
		return "/admin/admin_member_list"; // "admin/admin_member_list" 뷰를 반환합니다.
		
		// model
		// memberList안에는 
		// selectMember() 메소드안에 있는 list를 가지고있다
			
	}
}