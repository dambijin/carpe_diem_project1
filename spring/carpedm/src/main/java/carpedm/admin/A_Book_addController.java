package carpedm.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import carpedm.dto.BookDTO;
import carpedm.dto.BookgenreDTO;

@Controller
public class A_Book_addController {

	A_Book_addController() {
		System.out.println("A_Book_addController 생성자");
	}

	@Autowired
	A_BookService abookService;

	private static final Logger logger = LoggerFactory.getLogger(A_Book_addController.class);

	@Autowired
	private SqlSession sqlSession;

	// 희망 도서 신청 목록 페이지
	@RequestMapping(value = "/admin_book_add", method = RequestMethod.GET)
	protected String bookAdd(Locale locale, Model model, @ModelAttribute BookgenreDTO gdto)
			throws ServletException, IOException {

		List list = sqlSession.selectList("mapper.carpedm.admin.selectBooKAdd");

		List genre = abookService.listGenre(gdto);

		model.addAttribute("list", list);
		model.addAttribute("genre", genre);

		return "admin/admin_book_add.jsp.noTiles";

	}

	// 인서트 메소드
	@RequestMapping(value = "/admin_book_add", method = RequestMethod.POST)
	protected String BookInsert(Locale locale, Model model, @ModelAttribute BookDTO dto)
			throws ServletException, IOException {
		System.out.println("책인서트 DTO" + dto);
		int booksql = sqlSession.insert("mapper.carpedm.admin.insertBook", dto);
		System.out.println("책인서트" + booksql);
		return "redirect:/admin_book_add";
	}

	// 인서트 메소드
	@RequestMapping(value = "/isbn_import", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, String> Book_isbn_import(Locale locale, Model model, HttpServletRequest request,
			@RequestParam("isbn") String isbn) throws ServletException, IOException {
		isbn = isbn.replace("-", "").trim();

//		String isbn = "9788901271729";
		System.out.println("isbn : " + isbn);
		Map<String, String> result = nlISBNGetInfo(isbn);
		System.out.println(result);

		// JSON으로 변환하여 응답하기
//		String json = new Gson().toJson(result);
		return result;
	}

	// ISBN값을 기준으로 값을 가져오는 웹크롤링 함수
	private Map<String, String> nlISBNGetInfo(String isbn) {
		// 9788901271729 콜드리딩
		// 9791168473690 세이노
		Map<String, String> result = new HashMap<String, String>();
		try {
			String urlStr = "https://www.nl.go.kr/seoji/contents/S80100000000.do?schType=simple&schFld=ea_isbn&schStr="
					+ isbn;
//		           System.out.println(urlStr);
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// HTTP GET 요청
			connection.setRequestMethod("GET");

			// 응답 코드 확인
//				int responseCode = connection.getResponseCode();
//		            System.out.println("Response Code: " + responseCode);

			// 웹 페이지 내용 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer rs = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				rs.append(inputLine);
			}
			in.close();
			String selText = getDataOne(rs.toString(), "<div id=\"resultList_div\"", "</ul>");
//				System.out.println(selText);
			String title = getDataOne(selText, "<label for=\"chkItem_", "</label");
			title = getDataOne(title, ">", "").trim();
//				System.out.println(title);

			String author = getDataOne(selText, "<li>저자 : ", ";").trim();
			if (author.equals("")) {
				author = getDataOne(selText, "<ul class=\"dot-list\">									<li>", "</li>")
						.trim();
			}
			if (author.contains("</li>")) {
				author = getDataOne(author, "", "</li>").trim();
			}
//				System.out.println(author);

			String publisher = getDataOne(selText, "<li>발행처: ", "</li>").trim();
//				System.out.println(publisher);

			String b_date = getDataOne(selText, "<li>발행(예정)일: ", ".");
//				System.out.println(b_date);

			result.put("title", title);
			result.put("author", author);
			result.put("publisher", publisher);
			result.put("b_date", b_date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 단어 잘라내는 함수
	public String getDataOne(String source, String start, String endString) {
		int startIndex = source.indexOf(start);
		int endIndex;

		if (endString == null || endString.isEmpty()) {
			endIndex = source.length();
		} else {
			endIndex = source.indexOf(endString, startIndex + start.length());
			if (endIndex == -1) { // endString이 소스 내에 없는 경우, 소스 문자열의 끝까지를 지정
				endIndex = source.length();
			}
		}

		if (startIndex != -1) {
			// startIndex + start.length()를 해줌으로써, 시작 태그 바로 뒤의 위치를 얻습니다.
			// endIndex는 종료 태그의 시작 위치 또는 문자열의 끝이므로, 이 사이의 문자열을 추출합니다.
			return source.substring(startIndex + start.length(), endIndex);
		} else {
			return "";
		}
	}
}
