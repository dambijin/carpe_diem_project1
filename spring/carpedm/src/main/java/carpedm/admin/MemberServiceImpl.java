package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	Admin_member_listDAO dao;
	
	public List listMembers(MemberDTO dto) {
		
		return dao.getMemberList(dto);
	}
	
}
