package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;

@Service
public class My_Loan_historyService {

    @Autowired
    private My_Loan_historyDAO myLoanHistoryDAO;

    public List<Map<String, String>> getLoanHistory(Map<String, String> map) {
        return myLoanHistoryDAO.getLoanHistory(map);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return myLoanHistoryDAO.getMemberInfo(m_pid);
    }
    
}
