package carpedm.mainpages;

import java.util.List;

public interface Main_PageService {
    List getLibraryList();
    List getNoticeList();
    List getBookList();
    List getBannerList(String now_date);
}
