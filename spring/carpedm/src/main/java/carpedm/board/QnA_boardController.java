package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
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
public class QnA_boardController extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(QnA_boardController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/QnA_board", method = RequestMethod.GET)
	protected String QnA_board(Locale locale, Model model, @ModelAttribute NoticeBoardDTO dto,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "n_search", defaultValue = "제목") String type, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "perPage", defaultValue = "10") String perPage) throws ServletException, IOException {

//		NoticeBoardDTO dto = new NoticeBoardDTO();
//		System.out.println("타입" + type);
//		System.out.println("검색내"+search);
		dto.setSearch(search); // search 값을 설정
		dto.setType(type); // type 값을 설정
//		System.out.println("DTO내용 : " + dto);

		// 페이징 시작
		int currentPage = Integer.parseInt(page);
		int itemsPerPage = Integer.parseInt(perPage);
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		dto.setStartrow(startRow);
		dto.setEndrow(endRow);
		
		// 총 게시물 개수
		int count = sqlSession.selectOne("mapper.carpedm.board.QresultCount", dto);
		
		int startPage = Math.max(currentPage - 2, 1);	
		int totalPages = count > 0 ? (int) Math.ceil(count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages);
//        끝 페이지를 기준으로 조정
		startPage = Math.max(1, endPage - 4);

		
		System.out.println("총개수개수 : "+count);
		model.addAttribute("allcount", count);
		model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
		model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);
		
		
		List list = sqlSession.selectList("mapper.carpedm.board.Q_board", dto);

		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}

		model.addAttribute("list", list);

		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		if (mpid != null) {
			List login_id = sqlSession.selectList("mapper.carpedm.board.member_pid", mpid);
			model.addAttribute("login_id", login_id);

		}
		return "board/QnA_board.jsp";
	}
}
