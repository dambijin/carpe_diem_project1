package carpedm.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.NoticeBoardDTO;
import carpedm.test222.HomeController;

@Controller // MVC컨트롤러로 선언
public class notice_updateController extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(notice_updateController.class);
	
	@Autowired	
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/notice_update", method = RequestMethod.GET)
	protected String notice_update(Locale locale, Model model, @RequestParam("N_ID") int N_ID)
			throws ServletException, IOException {
		NoticeBoardDTO noticeDTO = new NoticeBoardDTO();
		noticeDTO.setN_id(N_ID);
		System.out.println("엔아이디 "+N_ID);
		
		List qna_notice = sqlSession.selectList("mapper.carpedm.board.n_update", noticeDTO);
		List library = sqlSession.selectList("mapper.carpedm.board.n_update_lb", noticeDTO);
		
		if (qna_notice != null) {
			System.out.println("qna_notice.isze : " + qna_notice.size());
			logger.error("qna_notice.size : " + qna_notice.size());
			System.out.println("list.isze : " + library.size());
			logger.error("list.size : " + library.size());			
		}

		model.addAttribute("qna_notice", qna_notice);	
		model.addAttribute("library", library);	
		
		return "board/notice_update.jsp";
	}		
}
