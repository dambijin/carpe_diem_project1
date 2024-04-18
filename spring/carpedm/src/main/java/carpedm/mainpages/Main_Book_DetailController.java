package carpedm.mainpages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main_Book_DetailController extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(Main_Book_DetailController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/book_detail", method = RequestMethod.GET)
	protected String book_detail(Locale locale, Model model, HttpServletRequest request,
			@RequestParam(value = "id", defaultValue = "") String b_id,
			@RequestParam(value = "si_id", defaultValue = "") String si_id) throws ServletException, IOException {

		if (!"".equals(si_id)) {
			// 검색키워드테이블 업데이트(검색기록과 연계하기 위해서)
			Map<String, String> update_params = new HashMap();
			update_params.put("b_id", b_id);
			update_params.put("si_id", si_id);
			int succhk = sqlSession.update("mapper.carpedm.mainpages.updateSearchInfo_book_detail", update_params);
			logger.info("업데이트 여부 : " + succhk);
		}
		long unixTime = System.currentTimeMillis();
		Map<String, String> bookdetail_map = sqlSession.selectOne("mapper.carpedm.mainpages.selectBookDetail_book_detail", b_id);
		System.out.println("책 상세(DB) 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		
		bookdetail_map.put("B_ISBN", String.valueOf(bookdetail_map.get("B_ISBN")));
		
		// 책 추천관련 작업들
		unixTime = System.currentTimeMillis();
		
		Main_Book_Recommend brc = new Main_Book_Recommend();
		String yes24bookno = brc.yes24SearchISBN(bookdetail_map.get("B_ISBN"));
		ArrayList<Map<String, String>> bookrecommend_list = new ArrayList<Map<String, String>>();
		logger.info("yes24bookno : " + yes24bookno);
		if (yes24bookno != null && !"".equals(yes24bookno)) {	
//			bookrecommend_list = brc.selenium_getyes24("https://www.yes24.com/Product/Goods/" + yes24url); 
			bookrecommend_list = brc.Httpgetyes24("https://www.yes24.com/Product/addModules/GoodsRecommend/"
					+ yes24bookno + "?type=AREA_ASSOC_RECOMMEND&pageNo=0&pageSize=40&divId=buyNCateGoodsWrap&bookYn=Y");
		}
		System.out.println("추천 목록 총 걸린시간 : " + (System.currentTimeMillis() - unixTime));

		model.addAttribute("bookdetail_map", bookdetail_map);
		model.addAttribute("bookrecommend_list", bookrecommend_list);

		return "mainpages/book_detail.jsp.noTiles";
	}
}
