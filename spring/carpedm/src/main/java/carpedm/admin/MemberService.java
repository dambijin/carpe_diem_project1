package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;

@Service
public class MemberService {

	@Autowired
	Member_listDAO dao;
	
	public List listMembers(MemberDTO dto) {
		
		List list = dao.getMemberList(dto);
		
		return list;
	}
	
		
}
