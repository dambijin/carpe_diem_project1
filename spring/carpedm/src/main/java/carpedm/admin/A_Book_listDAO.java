package carpedm.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.BookDTO;

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
	public int getBookDelete(BookDTO dto) {
		int result = -9999;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
//				list = sqlSession.selectList("mapper.emp.dynamic.selectEmp", dto);
				
				result = sqlSession.insert("mapper.carpedm.admin.deleteBook", dto);
				
				System.out.println("result : " + result);
				
			} else {
				System.out.println("DB 접속 실패");
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	
}
