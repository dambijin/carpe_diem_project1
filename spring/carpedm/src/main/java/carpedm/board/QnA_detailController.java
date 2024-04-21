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
import javax.servlet.http.HttpSession;
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
public class QnA_detailController extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(QnA_detailController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/QnA_detail", method = RequestMethod.GET)
	protected String QnA_detail(Locale locale, Model model, @RequestParam("N_ID") int N_ID, HttpServletRequest request)
			throws ServletException, IOException {
		NoticeBoardDTO noticeDTO = new NoticeBoardDTO();
//		System.out.println("엔아이디 "+N_ID);
		noticeDTO.setN_id(N_ID);
		sqlSession.update("mapper.carpedm.board.updateNotice", N_ID);
		List list = sqlSession.selectList("mapper.carpedm.board.notice_nid", noticeDTO);
		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}
		model.addAttribute("qnanotice", list);

		noticeDTO =(NoticeBoardDTO)list.get(0);
//		String.valueOf(noticeDTO.getM_pid()) === " "+noticeDTO.getM_pid();
		String m_pid = String.valueOf(noticeDTO.getM_pid());

		HttpSession session = request.getSession();
		String login_mpid = (String) session.getAttribute("m_pid");
		String managerchk = (String) session.getAttribute("m_managerchk");
		System.out.println("관리자값 : "+managerchk);
		if (login_mpid != null) {
			List login = sqlSession.selectList("mapper.carpedm.board.member_pid", login_mpid);
			model.addAttribute("login", login);
		}
		
		
		// 관리자가 쓴 답글 > 질문글 올린 글쓴이가 볼수있게 하기
		if (m_pid == null && managerchk == null && !m_pid.equals(login_mpid) && managerchk.equals("N")) {
		    return "redirect:/QnA_board";
		}

		return "board/QnA_detail.jsp";
	}
	
	
	// 딜리트 메소드
	@RequestMapping(value = "/QnA_delete", method = RequestMethod.GET)
	protected String noticeDelete(Locale locale, Model model, 
			@RequestParam("N_ID") int N_ID,
			@RequestParam("n_mpid") String mpid,
			HttpServletRequest request) throws ServletException, IOException {
		// location.href='notice_delete?N_ID=${notice[0].n_id}&n_mpid=${notice[0].m_pid}
		// m_pid값을 알면 게시물을 지울 수 있는데 보안상 괜찮은가?
		HttpSession session = request.getSession();
		String login_mpid = (String) session.getAttribute("m_pid");
		String managerchk = (String) session.getAttribute("m_managerchk");
		System.out.println("매니저값은?"+managerchk);
		System.out.println("mpid: "+mpid+", login_mpid : "+login_mpid +","+(mpid == login_mpid));
		if (mpid.equals(login_mpid) || managerchk.equals("Y")) {
			sqlSession.delete("mapper.carpedm.board.QnA_board", N_ID);
		}
		return "redirect:/QnA_board";

	}
}
