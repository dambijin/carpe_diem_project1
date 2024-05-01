package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;

@Service
public class My_Loan_statusService {

    @Autowired
    private My_Loan_statusDAO myLoanStatusDAO;

    public List<Map<String, String>> getLoanStatus(String m_pid) {
        return myLoanStatusDAO.getLoanStatus(m_pid);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return myLoanStatusDAO.getMemberInfo(m_pid);
    }

    public boolean insertWeapon(Map<String, String> weapon) {
        return myLoanStatusDAO.insertWeapon(weapon);
    }
}
