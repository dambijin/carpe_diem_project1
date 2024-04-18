package carpedm.admin;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.WishlistDTO;

@Repository
public class A_WishbookDAO {

	@Autowired	
	private SqlSession sqlSession;
	
	// 데이터베이스에서 목록을 가져오는 기능
	public List getWishList(WishlistDTO dto) {
		List list = null;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
				list = sqlSession.selectList("mapper.carpedm.admin.selectWishlist", dto);
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
