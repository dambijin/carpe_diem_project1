package carpedm.mainpages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class Main_PageServiceImpl implements Main_PageService{

    @Autowired
    private Main_PageDAO main_PageDAO;
	
    @Override
    public List getLibraryList() {
        return main_PageDAO.selectLib_libinfo();
    }

    @Override
    public List getNoticeList() {
        return main_PageDAO.selectNotice_main();
    }

    @Override
    public List getBookList() {
        return main_PageDAO.selectBook_main();
    }

    @Override
    public List getBannerList(String now_date) {
        return main_PageDAO.selectBanner_main(now_date);
    }
    
    @Override
    public List getRecommendBookList() {
        return main_PageDAO.selectRecommendBook_main();
    }
}
