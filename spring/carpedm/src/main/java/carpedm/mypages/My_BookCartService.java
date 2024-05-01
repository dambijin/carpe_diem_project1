package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.MemberDTO;

@Service
public class My_BookCartService {

    @Autowired
    private My_BookCartDAO myBookCartDAO;

    public List getBookCartList(Map<String, String> map) {
        return myBookCartDAO.getBookCartList(map);
    }

    public MemberDTO getMemberInfo(String m_pid) {
        return myBookCartDAO.getMemberInfo(m_pid);
    }
    public int bookCartDelete(Map<String, String> map) {
        return myBookCartDAO.bookCartDelete(map);
    }

}
