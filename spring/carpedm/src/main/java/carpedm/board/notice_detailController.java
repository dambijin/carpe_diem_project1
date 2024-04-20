package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

import carpedm.dto.MemberDTO;
import carpedm.dto.NoticeBoardDTO;

@Controller // MVC컨트롤러로 선언
public class notice_detailController extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(notice_detailController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/notice_detail", method = RequestMethod.GET)
	// @RequestParam("N_ID") int N_ID :  @RequestParam("N_ID")생략 가능
	// N_ID 라는 안에 내용이 일치해서 생략이 가능 그 외에 불가능
	protected String notice_detail(Locale locale, Model model, 
			@RequestParam("N_ID") int N_ID,
			MemberDTO mdto, HttpServletRequest request)
			throws ServletException, IOException {
		NoticeBoardDTO noticeDTO = new NoticeBoardDTO();
		sqlSession.update("mapper.carpedm.board.updateNotice", N_ID);
//		System.out.println("엔아이디 "+N_ID);
		noticeDTO.setN_id(N_ID);
		List list = sqlSession.selectList("mapper.carpedm.board.notice_nid", noticeDTO);
//		System.out.println("list : " + list);
		
		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		MemberDTO memberDTO = null;
		mdto.setM_pid(mpid);
		if (mpid != null) {
			memberDTO = sqlSession.selectOne("mapper.carpedm.board.member_pid", mdto);
			model.addAttribute("m_pid", memberDTO);	
		}
		
		model.addAttribute("notice", list);		
		
		return "board/notice_detail.jsp";
	}		
}
