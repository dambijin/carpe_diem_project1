package carpedm.board;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.BookDTO;
import carpedm.dto.MemberDTO;
import carpedm.dto.NoticeBoardDTO;

@Service
public class notice_boardService {

	@Autowired
	notice_boardDAO nbDAO;

	public HashMap listNotice(NoticeBoardDTO dto, String search, String type, HttpServletRequest request) {
		// map에 list, memberDTO값을 넣고 리턴한다
		// controller에서는 map에서 list, memberDTO를 동시에 꺼내서
		// jsp로 전송한다.
		HashMap map = new HashMap();

		List list = nbDAO.getNotice(dto, search, type);
		MemberDTO memberDTO = nbDAO.getSessionLogin(request);

		map.put("list", list);
		map.put("memberDTO", memberDTO);
		return map;
	}

	public int noticeCount(NoticeBoardDTO dto) {
		int count = nbDAO.getNoticeCount(dto);
		return count;
	}
	
	public MemberDTO loginID(HttpServletRequest request) {
		MemberDTO memberDTO = nbDAO.getSessionLogin(request);
		return memberDTO;
	}
	
	
	public List noticeDetail(@RequestParam("N_ID") int N_ID) {
		List list = nbDAO.getNoticeDetail(N_ID);
		return list;
	}
	
	public int noticeDeleteCount(@RequestParam("N_ID") int N_ID) {
		int count = nbDAO.getNoticeDelete(N_ID);
		return count;
	}
	

}
