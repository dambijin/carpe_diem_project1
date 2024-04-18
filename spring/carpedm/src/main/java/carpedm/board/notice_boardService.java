package carpedm.board;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import carpedm.dto.NoticeBoardDTO;

@Controller
public class notice_boardService{

	@Autowired
	notice_boardDAO nbDAO;

	public List listNotice(NoticeBoardDTO dto) {
		List list = nbDAO.getNotice(dto);
		return list;
	}

}
