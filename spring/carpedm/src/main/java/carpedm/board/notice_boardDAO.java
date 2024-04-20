package carpedm.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.MemberDTO;
import carpedm.dto.NoticeBoardDTO;

@Repository
public class notice_boardDAO {

	@Autowired
	private SqlSession sqlSession;

	public List getNotice(NoticeBoardDTO dto, String search, String type) {
		List list = null;
		try {
			if (sqlSession != null) {
				dto.setSearch(search); // search 값을 설정
				dto.setType(type); // type 값을 설정
				System.out.println("dto : " + dto);

				list = sqlSession.selectList("mapper.carpedm.board.n_board", dto);
				System.out.println("list" + list);
			} else {
				System.out.println("DB 접속 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public MemberDTO getSessionLogin(MemberDTO mdto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		System.out.println("로그인 된 아이디 : " + mpid);
		MemberDTO memberDTO = null;
		try {
			if (sqlSession != null) {
				mdto.setM_pid(mpid);
				// selectOne : member테이블에서 m_pid값 where문으로 찾아서 1명만 가져옴
				System.out.println("mdto dd: " + mdto);
				if (mpid != null) {
					memberDTO = sqlSession.selectOne("mapper.carpedm.board.member_pid", mdto);
				}
				System.out.println("memberDTO : " + memberDTO);
			} else {
				System.out.println("DB 접속 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberDTO;
	}
}
