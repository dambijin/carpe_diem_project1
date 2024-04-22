package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.mypages.My_chginfoDAO;

@Service
public class My_chginfoService {

    @Autowired
    private My_chginfoDAO chginfoDAO;

    public List<Map<String, Object>> getMyInfo(String userId) {
        return chginfoDAO.getMyInfo(userId);
    }

    public int updateInfo(Map<String, String> chgInfo) {
        return chginfoDAO.updateInfo(chgInfo);
    }
}
