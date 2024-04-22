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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.NoticeBoardDTO;

@Controller // MVC컨트롤러로 선언
public class QnA_reply_writeController extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(QnA_reply_writeController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/QnA_reply_write", method = RequestMethod.GET)
	protected String QnA_reply_write(Locale locale, Model model, 
			@RequestParam("N_ID") int N_ID,
			HttpServletRequest request)
			throws ServletException, IOException {
		NoticeBoardDTO noticeDTO = new NoticeBoardDTO();
		System.out.println("엔아이디 "+N_ID);
		noticeDTO.setN_id(N_ID);
		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		List qna_notice = sqlSession.selectList("mapper.carpedm.board.notice_nid", noticeDTO);
		List library = sqlSession.selectList("mapper.carpedm.board.library_nid", noticeDTO);
		List loginpid = sqlSession.selectList("mapper.carpedm.board.login_mpid",mpid);
		
		if (qna_notice != null) {
			System.out.println("list.isze : " + qna_notice.size());
			logger.error("list.size : " + qna_notice.size());
		}

		model.addAttribute("qna_notice", qna_notice);		
		model.addAttribute("library", library);		
		model.addAttribute("loginpid", loginpid);		
		
		return "board/QnA_reply_write.jsp";
	}		
	
	
	// 인서트 메소드
	@RequestMapping(value = "/QnA_reply_write", method = RequestMethod.POST)
	protected String QnAReplyInsert(Locale locale, Model model, @ModelAttribute NoticeBoardDTO dto,
			HttpServletRequest request) throws ServletException, IOException {
		sqlSession.insert("mapper.carpedm.board.QnA_reinsert", dto);

		return "redirect:/QnA_board";
	}
}
