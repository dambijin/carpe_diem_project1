package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;

@Service
public class My_Reservation_listService {

    @Autowired
    private My_Reservation_listDAO reservationDAO;

    public List getReservationList(Map<String, String> map) {
        return reservationDAO.selectReservationList(map);
    }

    public int updateReservation(Map<String, String> map) {
        return reservationDAO.updateReservation(map);
    }

    public int cancelReservation(Map<String, String> map) {
        return reservationDAO.cancelReservation(map);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return reservationDAO.selectMemberInfo(m_pid);
    }

    public Map<String, Object> getReservationCount(Map<String, String> map) {
        return reservationDAO.selectReservationCount(map);
    }
}
