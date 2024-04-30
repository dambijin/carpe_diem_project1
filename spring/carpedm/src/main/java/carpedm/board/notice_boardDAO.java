package carpedm.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.MemberDTO;
import carpedm.dto.NoticeBoardDTO;

@Repository
public class notice_boardDAO {

	@Autowired
	private SqlSession sqlSession;

	
	// notice_board 게시판 불러오기
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

	// 로그인 된 m_pid값
	public MemberDTO getSessionLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mpid = (String) session.getAttribute("m_pid");
		System.out.println("로그인 된 아이디 : " + mpid);
		MemberDTO memberDTO = null;
		try {
			if (sqlSession != null) {
				// selectOne : member테이블에서 m_pid값 where문으로 찾아서 1명만 가져옴
				if (mpid != null) {
					memberDTO = sqlSession.selectOne("mapper.carpedm.board.member_pid", mpid);
//					System.out.println("로그인한 멤버디티오" + memberDTO);
				}
//				System.out.println("memberDTO : " + memberDTO);
			} else {
				System.out.println("DB 접속 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberDTO;
	}

	// 공지사항 게시물 개수
	public int getNoticeCount(NoticeBoardDTO dto) {
		int count = 0;
		try {
			if(sqlSession != null) {
				count = sqlSession.selectOne("mapper.carpedm.board.NresultCount", dto);
			} else {
				System.out.println("DB 접속 실패");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	//  notice_detail 불러오기
		public List getNoticeDetail(@RequestParam("N_ID") int N_ID) {
			List list = null;
			try {
				if (sqlSession != null) {
					NoticeBoardDTO noticeDTO = new NoticeBoardDTO();
//					System.out.println("엔아이디 "+N_ID);
					sqlSession.update("mapper.carpedm.board.updateNotice", N_ID);
					noticeDTO.setN_id(N_ID);
					list = sqlSession.selectList("mapper.carpedm.board.notice_nid", noticeDTO);
					System.out.println("list" + list);
				} else {
					System.out.println("DB 접속 실패");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		// 공지사항 게시물 삭제 메소드
		public int getNoticeDelete(@RequestParam("N_ID") int N_ID) {
			int count = 0;
			try {
				if(sqlSession != null) {
					count = sqlSession.delete("mapper.carpedm.board.notice_delete", N_ID);
				} else {
					System.out.println("DB 접속 실패");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
		
		
}
