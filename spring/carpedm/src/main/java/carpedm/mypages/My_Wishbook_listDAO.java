package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class My_Wishbook_listDAO {

    @Autowired
    private SqlSession sqlSession;

    public List selectWishbookList(Map<String, String> map) {
        return sqlSession.selectList("mapper.carpedm.mypage.wishbooklist", map);
    }

    public int deleteWishbook(Map<String, String> map) {
        return sqlSession.delete("mapper.carpedm.mypage.wishDelete", map);
    }

    public int selectWishbookCount(Map<String, String> map) {
        Map<String, Object> result = sqlSession.selectOne("mapper.carpedm.mypage.wishCount", map);
        return Integer.parseInt(String.valueOf(result.get("count")));
    }
}
