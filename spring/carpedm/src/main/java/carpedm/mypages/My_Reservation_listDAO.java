package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carpedm.dto.MemberDTO;

@Repository
public class My_Reservation_listDAO {

    @Autowired
    private SqlSession sqlSession;

    public List selectReservationList(Map<String, String> map) {
        return sqlSession.selectList("mapper.carpedm.mypage.reservationlist", map);
    }

    public int updateReservation(Map<String, String> map) {
        return sqlSession.update("mapper.carpedm.mypage.reservationUpdate", map);
    }

    public int cancelReservation(Map<String, String> map) {
        return sqlSession.delete("mapper.carpedm.mypage.reservationCancle", map);
    }

    public Map<String, Object> selectReservationCount(Map<String, String> map) {
        return sqlSession.selectOne("mapper.carpedm.mypage.resCount", map);
    }
    public MemberDTO selectMemberInfo(String m_pid) {
        return sqlSession.selectOne("mapper.carpedm.mypage.myInfo", m_pid);
    }
}
