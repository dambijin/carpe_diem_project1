package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class My_chginfoDAO {

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String, Object>> getMyInfo(String userId) {
        return sqlSession.selectList("mapper.carpedm.mypage.myInfo1", userId);
    }

    public int updateInfo(Map<String, String> chgInfo) {
        return sqlSession.insert("mapper.carpedm.mypage.updateInfo", chgInfo);
    }
}
