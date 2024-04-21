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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import carpedm.dto.NoticeBoardDTO;
import carpedm.test222.HomeController;

@Controller // MVC컨트롤러로 선언
public class notice_writeController extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(notice_writeController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/notice_write", method = RequestMethod.GET)
	protected String notice_write(Locale locale, Model model,
			HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		List list = sqlSession.selectList("mapper.carpedm.board.library_list");
		List login_mpid = sqlSession.selectList("mapper.carpedm.board.login_mpid", mpid);

		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}
		model.addAttribute("library_list", list);
		model.addAttribute("member", login_mpid);

		return "board/notice_write.jsp";
	}

	// 인서트 메소드
	@RequestMapping(value = "/notice_write", method = RequestMethod.POST)
	protected String noticeInsert(Locale locale, Model model, @ModelAttribute NoticeBoardDTO dto,
			HttpServletRequest request) throws ServletException, IOException {
		sqlSession.insert("mapper.carpedm.board.no_insert", dto);

		return "redirect:/notice_board";
	}
}
