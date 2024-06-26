package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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
public class QnA_updateController extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(QnA_updateController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/QnA_update", method = RequestMethod.GET)
	protected String QnA_update(Locale locale, Model model, @RequestParam int N_ID)
			throws ServletException, IOException {
		NoticeBoardDTO noticeDTO = new NoticeBoardDTO();
		System.out.println("엔아이디 "+N_ID);
		noticeDTO.setN_id(N_ID);
		
		List qna_notice = sqlSession.selectList("mapper.carpedm.board.notice_nid", noticeDTO);
		List library = sqlSession.selectList("mapper.carpedm.board.library_nid", noticeDTO);
		
		if (qna_notice != null) {
			System.out.println("list.isze : " + qna_notice.size());
			logger.error("list.size : " + qna_notice.size());
		}

		model.addAttribute("qna_notice", qna_notice);		
		model.addAttribute("library", library);		
		
		return "board/QnA_update.jsp";
	}		
	
	
	
	// 업데이트 메소드
	@RequestMapping(value = "/QnA_update", method = RequestMethod.POST)
	protected String noticeUp(Locale locale, Model model, 
			@ModelAttribute NoticeBoardDTO dto,
			HttpServletRequest request) throws ServletException, IOException {
		sqlSession.update("mapper.carpedm.board.QnA_update", dto);

		return "redirect:/QnA_board";
	}
}
