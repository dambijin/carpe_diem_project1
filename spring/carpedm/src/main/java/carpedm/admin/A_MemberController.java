package carpedm.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.MemberDTO;

@Controller
public class A_MemberController {

	A_MemberController(){
		System.out.println("MemberController 생성자");
	}
	
	MemberDTO dto = new MemberDTO();
	
	@Autowired  // Spring이 MemberService의 인스턴스를 이 필드에 자동으로 주입해야 함을 나타냄
	A_MemberService memberService;
	
	@RequestMapping("/admin_member_list")
	public String listMembers(Model model, @ModelAttribute MemberDTO dto,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "perPage", defaultValue = "10") String perPage
			) {
		System.out.println("MemberController > listMembers 실행");
		int currentPage = Integer.parseInt(page);
		int itemsPerPage = Integer.parseInt(perPage);
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		
		dto.setStartrow(startRow);
		dto.setEndrow(endRow);
		
		List list = memberService.listMembers(dto);  // memberService를 통해 회원 목록을 가져옵니다.
		
		
		System.out.println("keyword : " + dto.getKeyword());
		
		System.out.println("list111 : " + list);
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
		

		int member_count = memberService.MembersCount(dto);

		int startPage = Math.max(currentPage - 2, 1);	
		int totalPages = member_count > 0 ? (int) Math.ceil(member_count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages);
//        끝 페이지를 기준으로 조정
		startPage = Math.max(1, endPage - 4);
		
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("type", type);
//		
//		model.addAttribute("orderColumn", orderColumn);
//		model.addAttribute("orderType", orderType);
		
		
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();

		// 날짜 한글로 변경
		for (int i = 0; i < list.size(); i++) {
		    MemberDTO dto1 = (MemberDTO) list.get(i);
		    System.out.println("asdasdasdsad : " + dto1);
		    
		    Date birthday = dto1.getM_birthday();
		    
		    System.out.println("birthday  : " + birthday);
		    
		    String formattedBirthday = null;
		    
		    if (birthday != null) {
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);
		        formattedBirthday = sdf.format(birthday);
		        
		        System.out.println("formattedBirthday : " + formattedBirthday);
		    } else {
		        formattedBirthday = "생일 정보 없음";
		    }
		    
		    dto1.setFormattedBirthday(formattedBirthday); // DTO에 저장
		    memberList.add(dto1); // 각 회원의 DTO를 List에 추가
		}

		model.addAttribute("memberList", memberList); // 모델에 회원 목록을 속성으로 추가
		
		
		model.addAttribute("allcount", member_count);
		model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
		model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);
		return "admin/admin_member_list.jsp.admin"; // "admin/admin_member_list" 뷰를 반환합니다.
		
	}
}
