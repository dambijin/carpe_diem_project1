package carpedm.mypages;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class My_Wishbook_listService {

    @Autowired
    private My_Wishbook_listDAO wishbookDAO;

    public List getWishbookList(Map<String, String> map) {
        return wishbookDAO.selectWishbookList(map);
    }

    public int deleteWishbook(Map<String, String> map) {
        return wishbookDAO.deleteWishbook(map);
    }

    public int getWishbookCount(Map<String, String> map) {
        return wishbookDAO.selectWishbookCount(map);
    }
}
