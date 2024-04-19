package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
			@RequestParam(value = "n_search", defaultValue = "제목") String type) throws ServletException, IOException {
		
//		NoticeBoardDTO dto = new NoticeBoardDTO();
//		System.out.println("타입" + type);
//		System.out.println("검색내"+search);
		dto.setSearch(search); // search 값을 설정
		dto.setType(type); // type 값을 설정
		System.out.println("DTO내용 : "+dto);
		
		List list = sqlSession.selectList("mapper.carpedm.board.Q_board", dto);
		System.out.println("list : " + list);

		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}

		model.addAttribute("list", list);

		return "board/QnA_board.jsp";
	}
}
