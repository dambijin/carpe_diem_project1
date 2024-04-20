package carpedm.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;
import carpedm.dto.NoticeBoardDTO;

@Service
public class notice_boardService{

	@Autowired
	notice_boardDAO nbDAO;	
	
	
	public HashMap listNotice(NoticeBoardDTO dto,String search, String type, MemberDTO mdto,HttpServletRequest request) {
		// map에 list, memberDTO값을 넣고 리턴한다
		// controller에서는 map에서 list, memberDTO를 동시에 꺼내서
		// jsp로 전송한다.
		HashMap map = new HashMap();
		
		List list = nbDAO.getNotice(dto, search, type);
		MemberDTO memberDTO = nbDAO.getSessionLogin(mdto, request);
		
		map.put("list", list);
		map.put("memberDTO", memberDTO);
		return map;
	}
	

}
