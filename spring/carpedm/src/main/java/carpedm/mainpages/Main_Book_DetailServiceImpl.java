package carpedm.mainpages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import carpedm.dto.BookCartDTO;
import carpedm.dto.ChaseBookDTO;

@Service
@Primary
public class Main_Book_DetailServiceImpl implements Main_Book_DetailService {

	@Autowired
	private Main_Book_DetailDAO main_Book_DetailDAO;

	@Override
	public List<Object> getBookDetail(ChaseBookDTO updateParams) {
        List<Object> result = new ArrayList<Object>();
        
        // 검색 키워드 업데이트
//        if (!"".equals(updateParams.getSi_id())) {
            int success = main_Book_DetailDAO.insertChaseBook(updateParams);
            System.out.println("업데이트성공 : " + success);
            // 업데이트 성공 여부에 대한 로깅 등 추가 작업
//        }

        // 책 상세 정보 조회
        List<Map<String, String>> bookDetailMapList = main_Book_DetailDAO.selectBookDetail(updateParams.getB_id());
        Map<String, String> bookDetailMap = bookDetailMapList.get(0);
        bookDetailMap.put("B_ISBN", String.valueOf(bookDetailMap.get("B_ISBN")));
        result.add(bookDetailMap);
        
        //도서관별 소장상황가져오기
        List<Map<String, String>> SameISBNbookDetailMapList = main_Book_DetailDAO.selectSameISBNBookDetail(String.valueOf(bookDetailMap.get("B_ISBN")));
        for(int i = 0; i < SameISBNbookDetailMapList.size(); i++){
            Map<String, String> SameISBNbookDetailMap = SameISBNbookDetailMapList.get(i);
            SameISBNbookDetailMapList.get(i).put("B_ISBN", String.valueOf(SameISBNbookDetailMap.get("B_ISBN")));
        }
 
        result.add(SameISBNbookDetailMapList);

        // 책 추천 목록 조회
        long unixTime = System.currentTimeMillis();
        Main_Book_Recommend brc = new Main_Book_Recommend();
        String yes24BookNo = brc.yes24SearchISBN(bookDetailMap.get("B_ISBN"));
        ArrayList<Map<String, String>> bookrecommend_list = new ArrayList<Map<String, String>>();
		if (yes24BookNo != null && !"".equals(yes24BookNo)) {	
//			bookrecommend_list = brc.selenium_getyes24("https://www.yes24.com/Product/Goods/" + yes24url); 
			bookrecommend_list = brc.Httpgetyes24("https://www.yes24.com/Product/addModules/GoodsRecommend/"
					+ yes24BookNo + "?type=AREA_ASSOC_RECOMMEND&pageNo=0&pageSize=40&divId=buyNCateGoodsWrap&bookYn=Y");
		}
		System.out.println("추천 목록 총 걸린시간 : " + (System.currentTimeMillis() - unixTime));

        result.add(bookrecommend_list);

        //장바구니검사
        BookCartDTO bcdto = new BookCartDTO();
        bcdto.setB_id(updateParams.getB_id());
        bcdto.setM_pid(updateParams.getM_pid());
        List<BookCartDTO> bookcart_count = main_Book_DetailDAO.SearchBookCart(bcdto);
        result.add(bookcart_count);

        return result;
    }
	
	public int insertBookCart(BookCartDTO bcdto)
	{
		int result = 0;
		result = main_Book_DetailDAO.insertBookCart(bcdto);
		
		return result;
	}
}
