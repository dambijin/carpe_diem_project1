package carpedm.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.NoticeBoardDTO;

@Service
public class notice_boardService{

	@Autowired
	notice_boardDAO nbDAO;

	public List listNotice(NoticeBoardDTO dto,String search, String type) {
		List list = nbDAO.getNotice(dto, search, type);
		return list;
	}

}
