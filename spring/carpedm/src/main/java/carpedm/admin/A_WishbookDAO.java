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
	
	public int getWishListCount(WishlistDTO dto) {
		int count = 0;
		try {

			if(sqlSession != null) {
				// select 결과가 없으면 null
				count = sqlSession.selectOne("mapper.carpedm.admin.selectWishlistCount", dto);
			} else {
				System.out.println("DB 접속 실패");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 완료 업데이트 기능
	public int getWishListUpdate(WishlistDTO dto) {
		int result = 9999;
		try {

			if (sqlSession != null) {
				// select 결과가 없으면 null
				// 회원 정보 업데이트 쿼리 실행
				result = sqlSession.update("mapper.carpedm.admin.updateWishlist", dto);
				System.out.println("result : " + result);
			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	// 반려 업데이트 기능
	public int getWishListCompanion(WishlistDTO dto) {
		int result = 9999;
		try {

			if (sqlSession != null) {
				// select 결과가 없으면 null
				// 회원 정보 업데이트 쿼리 실행
				result = sqlSession.update("mapper.carpedm.admin.updateWishCompanion", dto);
				System.out.println("result : " + result);
			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
}
