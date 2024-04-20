package carpedm.mainpages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

@Controller
public class Main_Book_SearchController extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(Main_Book_SearchController.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/book_search", method = RequestMethod.GET)
	protected String book_search(Locale locale, Model model, HttpServletRequest request,
			@RequestParam(value = "search", defaultValue = "") String searchWord,
			@RequestParam(value = "item", defaultValue = "전체") String item,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "perPage", defaultValue = "10") String perPage,
			@RequestParam(value = "libraryIds", required = false) String[] libraryIds,
			@RequestParam(value = "okywd", defaultValue = "제목 오름차순") String okywd)
			throws ServletException, IOException {

		String item_query = "b.b_title";
		if (item.equals("제목")) {
			item_query = "b.b_title";
		} else if (item.equals("저자")) {
			item_query = "b.b_author";
		} else if (item.equals("출판사")) {
			item_query = "b.b_publisher";
		} else if (item.equals("키워드")) {
			item_query = "b.b_kywd";
		} else if (item.equals("ISBN")) {
			item_query = "b.b_isbn";
		}

		String okywd_kywd = "b.b_title";
		String okywd_order = "ASC";
		try {
			String temp_kywd = okywd.split(" ")[0];
			String temp_order = okywd.split(" ")[1];
			if (temp_kywd.equals("제목")) {
				okywd_kywd = "b.b_title";
			} else if (temp_kywd.equals("키워드")) {
				okywd_kywd = "b.b_kywd";
			} else if (temp_kywd.equals("저자")) {
				okywd_kywd = "b.b_author";
			} else if (temp_kywd.equals("발행년도")) {
				okywd_kywd = "b.b_pubyear";
			} else if (temp_kywd.equals("소장기관")) {
				okywd_kywd = "l.lb_name";
			}
			if (temp_order.equals("내림차순"))
				okywd_order = "DESC";
		} catch (Exception e) {
			okywd_kywd = "b.b_title";
			okywd_order = "ASC";
		}

		int currentPage = Integer.parseInt(page);
		int itemsPerPage = Integer.parseInt(perPage);
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;

		// 얘는 앞에서 쓰기때문에 미리 해줘야함
		List<LibraryDTO> library_list = sqlSession.selectList("mapper.carpedm.mainpages.selectLibList_book_search");
		if (libraryIds == null || libraryIds.length == 0) {
			libraryIds = new String[library_list.size()];
			for (int i = 0; i < library_list.size(); i++) {
				libraryIds[i] = library_list.get(i).getLb_id() + "";
			}
		}
		// SQL 쿼리(쿼리가 너무 복잡해서 2개로 나눠서 보겠음..)
		// 원하는 조건에 맞는 책들을 가져옴(페이지계산X)
		String baseQuery = " SELECT b.*, l.lb_name" + " FROM book b" + " JOIN library l ON b.lb_id = l.lb_id";

		if (item.equals("전체")) {
			baseQuery += " WHERE (b.b_title LIKE '%" + searchWord + "%'" + " OR b.b_author LIKE '%" + searchWord + "%'"
					+ " OR b.b_publisher LIKE '%" + searchWord + "%'" + " OR b.b_kywd LIKE '%" + searchWord + "%')";
		} else {
			baseQuery += " WHERE " + item_query + " LIKE '%" + searchWord + "%'";
		}
		baseQuery += " AND l.lb_id IN (";
		for (int i = 0; i < libraryIds.length; i++) {
			if (i > 0) {
				baseQuery += ", ";
			}
			baseQuery += "'" + libraryIds[i] + "'";
		}
		baseQuery += ")";

		baseQuery += " ORDER BY " + okywd_kywd + " " + okywd_order;
//				System.out.println("베이스쿼리:"+ baseQuery);

		// 위 쿼리에 페이지별 계산까지 들어가서 최종완성시킴
		String query = "SELECT *" + " FROM (SELECT rownum rnum, a.* FROM (" + baseQuery + ") a" + " WHERE rownum <= "
				+ endRow + ")" + " WHERE rnum >= " + startRow;

//		System.out.println(query);
		
		List<Map> book_list = sqlSession.selectList("mapper.carpedm.mainpages.selectBookList_book_search", query);
		List<SearchinfoDTO> pop_search_list = sqlSession
				.selectList("mapper.carpedm.mainpages.selectPopSearchList_book_search");

//		System.out.println(book_list);

		Gson gson = new Gson();
		String libraryIdsJson = gson.toJson(libraryIds);
//		System.out.println(libraryIdsJson);

		String clientIP = request.getHeader("X-Forwarded-For");
		if (clientIP == null || clientIP.isEmpty()) {
			clientIP = request.getRemoteAddr();
		}
		System.out.println(clientIP);
		
		String si_id = "";
		if (!"".equals(searchWord) && searchWord != null) {
			String now_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			  	Map<String, String> insert_params = new HashMap();
			  	insert_params.put("searchWord", searchWord);
			  	insert_params.put("item", item);
			  	insert_params.put("now_date", now_date);
			  	insert_params.put("clientIP", clientIP);
		        
		        int succhk = sqlSession.insert("mapper.carpedm.mainpages.insertSearchInfo_book_search", insert_params);
		        System.out.println("검색성공:"+succhk);
		        // 조회id값 가져오기
		        si_id = (String) sqlSession.selectOne("mapper.carpedm.mainpages.selectSiId_book_search");
		}

		Map<String, String> temp_book_count = new HashMap();
		temp_book_count = (Map<String,String>)sqlSession.selectOne("mapper.carpedm.mainpages.selectBookList_book_search", baseQuery.replace("SELECT b.*, l.lb_name ", "SELECT count(*) "));
		int book_count = Integer.parseInt(String.valueOf(temp_book_count.get("COUNT(*)")));

        int startPage = Math.max(currentPage - 2, 1);
        int totalPages = book_count > 0 ? (int) Math.ceil(book_count / Double.parseDouble(perPage)) : 1;
        int endPage = Math.min(startPage + 4, totalPages);     
//        끝 페이지를 기준으로 조정
        startPage = Math.max(1, endPage - 4);
		HttpSession session = request.getSession();
		String m_pid = (String)session.getAttribute("m_pid")+"";
		logger.info("로그인 id : "+m_pid);
//		String m_pid = "15";

		model.addAttribute("library_list", library_list);
		model.addAttribute("pop_search_list", pop_search_list);
		model.addAttribute("book_list", book_list);

		model.addAttribute("searchWord", searchWord);
		model.addAttribute("item", item);
		model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
		model.addAttribute("okywd", okywd);
		model.addAttribute("book_count", book_count);
		model.addAttribute("libraryIds", libraryIdsJson);
		model.addAttribute("si_id", si_id);
		model.addAttribute("m_pid", m_pid);

        model.addAttribute("start_page", startPage);
        model.addAttribute("end_page", endPage);
        model.addAttribute("total_pages", totalPages);
		return "mainpages/book_search.jsp";
	}
	
	@RequestMapping(value = "/book_search", method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	protected String book_search_post(@RequestParam("b_id") String b_id, @RequestParam("m_pid") String m_pid) throws IOException {
        String result = "fail";

        Map<String, String> params = new HashMap<String,String>();
        params.put("b_id", b_id);
        params.put("m_pid", m_pid);
        
		if (m_pid != null && !m_pid.isEmpty() && !"null".equals(m_pid)) {
			int succhk = sqlSession.update("mapper.carpedm.mainpages.updateBookResState_book_search", params);
			logger.info("업데이트 : " + succhk);
			if(succhk > 0)
			{
				succhk = sqlSession.insert("mapper.carpedm.mainpages.insertBookRes_book_search", params);
				logger.info("인서트 : " + succhk);
			}
			
			result = "{\"message\": \"success\"}";
        } else {
        	result = "{\"message\": \"fail\"}";
        }
		
		return result;
	}
}
