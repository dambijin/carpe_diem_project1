package carpedm.board;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
public class notice_boardController extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(notice_boardController.class);
	
	@Autowired	
	notice_boardService NBdao;
	
	@RequestMapping(value = "/notice_board", method = RequestMethod.GET)
	protected String notice_board(Locale locale, Model model, @ModelAttribute NoticeBoardDTO dto
			, @RequestParam(value="search", defaultValue="") String search
			, @RequestParam(value="type", defaultValue="제목") String type)
			throws ServletException, IOException {
		List list = NBdao.listNotice(dto);
		
		
		
		if (list != null) {
			System.out.println("list.isze : " + list.size());
			logger.error("list.size : " + list.size());
		}

		model.addAttribute("list", list);		
		
		return "board/notice_board.jsp";
	}	
	
}
