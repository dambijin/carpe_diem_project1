package carpedm.admin;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.BookDTO;
import carpedm.dto.BookgenreDTO;

@Repository
public class A_Book_listDAO {

	@Autowired	
	private SqlSession sqlSession;
	
	// 데이터베이스에서 목록을 가져오는 기능
	public List getBookList(BookDTO dto) {
		List list = null;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
//				list = sqlSession.selectList("mapper.emp.dynamic.selectEmp", dto);
				list = sqlSession.selectList("mapper.carpedm.admin.selectBook", dto);
				System.out.println("list" +list);
			} else {
				System.out.println("DB 접속 실패");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 데이터베이스에서 목록을 가져오는 기능
	public int getBookCount(BookDTO dto) {
		int count = 0;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
//				list = sqlSession.selectList("mapper.emp.dynamic.selectEmp", dto);
				count = sqlSession.selectOne("mapper.carpedm.admin.selectBookCount", dto);

			} else {
				System.out.println("DB 접속 실패");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 데이터베이스에서 목록을 삭제하는 기능
	public int deleteBooksByIds(String b_id) {
		int result = -9999;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
//				list = sqlSession.selectList("mapper.emp.dynamic.selectEmp", dto);
				
				result = sqlSession.delete("mapper.carpedm.admin.deleteBook", b_id);
				
				System.out.println("result : " + result);
				
			} else {
				System.out.println("DB 접속 실패");
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	
	// Bookgenre 테이블 가져오기
	public List getBookGenre(BookgenreDTO dto) {
		List list = null;
		try {
			if(sqlSession != null) {
				list = sqlSession.selectList("mapper.carpedm.admin.selectBooKGenre", dto);
				System.out.println("list" +list);
			} else {
				System.out.println("DB 접속 실패");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
