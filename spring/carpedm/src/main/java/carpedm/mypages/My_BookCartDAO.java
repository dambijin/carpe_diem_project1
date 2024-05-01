package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.MemberDTO;

@Repository
public class My_BookCartDAO {

    @Autowired
    private SqlSession sqlSession;

    public List getBookCartList(Map<String, String> map) {
        return sqlSession.selectList("mapper.carpedm.mypage.bookcart", map);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return sqlSession.selectOne("mapper.carpedm.mypage.myInfo", m_pid);
    }
    public int bookCartDelete(Map<String, String> map) {
        return sqlSession.delete("mapper.carpedm.mypage.bookCartDelete", map);
    }

}
