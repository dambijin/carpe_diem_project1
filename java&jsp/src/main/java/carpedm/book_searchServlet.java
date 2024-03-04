package carpedm;

import java.io.IOException;
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

@WebServlet("/book_search")
public class book_searchServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ajax로 할껄 급 후회중...
		// 검색어
		String searchWord = request.getParameter("search");
		if (searchWord == null || "".equals(searchWord)) {
			searchWord = "";
		}
		// 검색필터(제목,저자 등등)
		String item = request.getParameter("item");
		String item_query = "b.b_title";
		if (item == null || "".equals(item)) {
			item = "전체";
		} else {
			if (item.equals("제목")) {
				item_query = "b.b_title";
			} else if (item.equals("저자")) {
				item_query = "b.b_author";
			} else if (item.equals("출판사")) {
				item_query = "b.b_publisher";
			} else if (item.equals("키워드")) {
				item_query = "b.b_kywd";
			}
		}

		// page 처리 부분
		String page = request.getParameter("page");
		if (page == null || "".equals(page)) {
			page = "1";
		}
		int currentPage = Integer.parseInt(page);

		// perPage(표시 개수) 처리 부분
		String perPage = request.getParameter("perPage");
		if (perPage == null || "".equals(perPage)) {
			perPage = "10";
		}
		int itemsPerPage = Integer.parseInt(perPage);

		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		// 오름차순 내림차순 계산
		String okywd = request.getParameter("okywd");

		if (okywd == null || "".equals(okywd)) {
			okywd = "제목 오름차순";
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
		// 도서관필터
		ArrayList<Map<String, String>> library_list = getDBList("select lb_id,lb_name from library");
		String[] libraryIds = request.getParameterValues("libraryIds");
		if (libraryIds == null || libraryIds.length == 0) {
			libraryIds = new String[library_list.size()];
			for (int i = 0; i < library_list.size(); i++) {
				libraryIds[i] = library_list.get(i).get("LB_ID");
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
		// 위 쿼리에 페이지별 계산까지 들어가서 최종완성시킴
		String query = "SELECT *" + " FROM (SELECT rownum rnum, a.* FROM (" + baseQuery + ") a" + " WHERE rownum <= "
				+ endRow + ")" + " WHERE rnum >= " + startRow;
		// 이건 그냥 조건에 맞는 책들의 개수를 알기 위해 따로 만듬(합치려니 너무 복잡해져서 쿼리를 2번날림)
		String countQuery = "";
		countQuery += "SELECT COUNT(*)";
		countQuery += " FROM book b";
		countQuery += " JOIN library l ON b.lb_id = l.lb_id";
		if (item.equals("전체")) {
			countQuery += " WHERE (b.b_title LIKE '%" + searchWord + "%' OR b.b_author LIKE '%" + searchWord
					+ "%' OR b.b_publisher LIKE '%" + searchWord + "%' OR b.b_kywd LIKE '%" + searchWord + "%')";
		} else {
			countQuery += " WHERE " + item_query + " LIKE '%" + searchWord + "%'";
		}
		countQuery += " AND l.lb_id IN (";
		for (int i = 0; i < libraryIds.length; i++) {
			if (i > 0) {
				countQuery += ", ";
			}
			countQuery += "'" + libraryIds[i] + "'";
		}
		countQuery += ")";
		ArrayList<Map<String, String>> book_list = getDBList(query);
		String book_count = getDBList(countQuery).get(0).get("COUNT(*)");
//		System.out.println(book_count);

		request.setAttribute("searchWord", searchWord);
		request.setAttribute("item", item);
		request.setAttribute("page", page);
		request.setAttribute("perPage", perPage);
		request.setAttribute("okywd", okywd);
		request.setAttribute("book_count", book_count);
		request.setAttribute("libraryIds", libraryIds);

		request.setAttribute("book_list", book_list);
		request.setAttribute("library_list", library_list);
		request.getRequestDispatcher("/mainpages/book_search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String b_id = request.getParameter("b_id");
		System.out.println(b_id);
		String query = "UPDATE book SET b_resstate = 'N' WHERE b_id = " + b_id;

		String m_pid = request.getParameter("m_pid");
		System.out.println(m_pid);
		if(m_pid != null && !m_pid.equals("null") && !m_pid.equals(""))
		{
			int successRow = setDBList("INSERT INTO reservation (r_id, b_id, r_resdate, r_resstate, m_pid)"
					+ " VALUES (reservation_seq.NEXTVAL," + b_id + ",SYSDATE, 0," + m_pid + ")");
			System.out.println("추가된 행 수:" + successRow);
			if (successRow > 0) {
				System.out.println("변경된 행 수:" + setDBList(query));
				// 완료하고 결과값을 보내기 위해
				response.getWriter().write("{\"message\": \"success\"}");
			}
		}
		else
		{
			System.out.println("현재 로그인되어있지 않습니다.");
			// 완료하고 결과값을 보내기 위해
			response.getWriter().write("{\"message\": \"fail\"}");
		}
	


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
//			    System.out.println("db접속성공");
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

	private int setDBList(String query) {
		int result = -1;
		try {
			Connection conn = getConnection();
			// SQL준비

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			result = ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
