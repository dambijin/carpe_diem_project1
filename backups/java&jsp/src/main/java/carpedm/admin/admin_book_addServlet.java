package carpedm.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/admin_book_add")
public class admin_book_addServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Map<String, String>> library_list = getDBList("select lb_id,lb_name from library");
		
		request.setAttribute("library_list", library_list);
		request.getRequestDispatcher("/admin/admin_book_add.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn").replace("-", "").trim();

//		String isbn = "9788901271729";
		System.out.println("isbn : " + isbn);
		Map<String, String> result = nlISBNGetInfo(isbn);
		System.out.println(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		// JSON으로 변환하여 응답하기
		String json = new Gson().toJson(result);
		response.getWriter().write(json);
	}

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	// 기본적인 접속메소드
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//				    System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	private ArrayList<Map<String, String>> getDBList(String query) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsmd.getColumnName(i);
					map.put(columnName, rs.getString(columnName));
				}

				result_list.add(map);
			}

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}

	// ISBN값을 기준으로 값을 가져오는 웹크롤링 함수
	private Map<String, String> nlISBNGetInfo(String isbn) {
		// 9788901271729 콜드리딩
		// 9791168473690 세이노
		Map<String, String> result = new HashMap<String, String>();
		try {
			String urlStr = "https://www.nl.go.kr/seoji/contents/S80100000000.do?schType=simple&schFld=ea_isbn&schStr="
					+ isbn;
//	           System.out.println(urlStr);
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// HTTP GET 요청
			connection.setRequestMethod("GET");

			// 응답 코드 확인
//			int responseCode = connection.getResponseCode();
//	            System.out.println("Response Code: " + responseCode);

			// 웹 페이지 내용 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer rs = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				rs.append(inputLine);
			}
			in.close();
			String selText = getDataOne(rs.toString(), "<div id=\"resultList_div\"", "</ul>");
			System.out.println(selText);
			String title = getDataOne(selText, "<label for=\"chkItem_", "</label");
			title = getDataOne(title, ">", "").trim();
			System.out.println(title);

			String author = getDataOne(selText, "<li>저자 : ", ";").trim();
			if (author.equals("")) {
				author = getDataOne(selText, "<ul class=\"dot-list\">									<li>", "</li>")
						.trim();
			}
			System.out.println(author);

			String publisher = getDataOne(selText, "<li>발행처: ", "</li>").trim();
			System.out.println(publisher);

			String b_date = getDataOne(selText, "<li>발행(예정)일: ", "</li>");
			System.out.println(b_date);

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
