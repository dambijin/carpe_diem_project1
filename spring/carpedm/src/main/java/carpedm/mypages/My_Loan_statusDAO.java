package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.MemberDTO;

@Repository
public class My_Loan_statusDAO {

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String, String>> getLoanStatus(String m_pid) {
        return sqlSession.selectList("mapper.carpedm.mypage.loanstatus", m_pid);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return sqlSession.selectOne("mapper.carpedm.mypage.myInfo", m_pid);
    }

    public boolean insertWeapon(Map<String, String> weapon) {
        int result = sqlSession.insert("mapper.carpedm.mypage.weapon", weapon);
        return result > 0;
    }
}
