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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class A_ChginfoController {
	
	A_ChginfoController(){
		System.out.println("A_ChginfoController 생성자");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(A_ChginfoController.class);

	@Autowired
	private SqlSession sqlSession;
	
	// 회원정보 수정 페이지
	@RequestMapping(value = "/admin_member_chginfo", method = RequestMethod.GET)
	protected String chginfo(Locale locale, Model model,
				@RequestParam("m_pid") String m_pid
			)
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectChginfo", m_pid);
				
		model.addAttribute("list", list);

		return "admin/admin_member_chginfo.jsp.noTiles";
			
	}
	
	
	
	// 회원 정보 업데이트 메소드
	@RequestMapping(value = "/admin_member_chginfo", method = RequestMethod.POST)
	protected String updateMemberInfo(Locale locale, Model model, 
			@RequestParam("m_pid") String m_pid,
	        @RequestParam("name") String m_name,
	        @RequestParam("date") String m_birthday,
	        @RequestParam("id") String m_id,
	        @RequestParam("pw") String m_pw,
	        @RequestParam("phone_number") String m_tel,
	        @RequestParam("email1") String m_email,
	        @RequestParam("address1") String m_address)
	        throws ServletException, IOException {

		logger.debug("포스트접근성공");
	    // 수정된 회원 정보를 Map 형태로 생성
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("m_pid", m_pid);
	    params.put("m_name", m_name);
	    params.put("m_birthday", m_birthday);
	    params.put("m_id", m_id);
	    params.put("m_pw", m_pw);
	    params.put("m_tel", m_tel);
	    params.put("m_email", m_email);
	    params.put("m_address", m_address);

	    // 회원 정보 업데이트 쿼리 실행
	    int result = sqlSession.update("mapper.carpedm.admin.updateChginfo", params);

	    // 업데이트 결과에 따라 처리
	    if (result > 0) {
	    	System.out.println("update 결과 : " + result);
//	    	// 예: 수정 성공 메시지 전달 및 회원 목록 페이지로 리다이렉트
	        return "redirect:/admin_member_list";
	    } else {
	        // 업데이트 실패 시 처리할 내용
	        // 예: 수정 실패 메시지 전달 및 이전 페이지로 이동
	        return "admin/admin_member_chginfo.jsp.noTiles"; // 실패했을 때의 페이지 경로 설정
	    }
	}
	
}
