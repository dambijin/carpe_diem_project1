package carpedm.mainpages;

import java.util.List;

public interface Main_PageDAO {
    List selectLib_libinfo();
    List selectNotice_main();
    List selectBook_main();
    List selectBanner_main(String now_date);
    List selectRecommendBook_main();
}
