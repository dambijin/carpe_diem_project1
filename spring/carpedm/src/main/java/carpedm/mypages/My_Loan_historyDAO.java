package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.MemberDTO;

@Repository
public class My_Loan_historyDAO {

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String, String>> getLoanHistory(Map<String, String> map) {
        return sqlSession.selectList("mapper.carpedm.mypage.loanhistory", map);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return sqlSession.selectOne("mapper.carpedm.mypage.myInfo", m_pid);
    }

}
