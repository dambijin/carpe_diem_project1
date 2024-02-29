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
		String searchWord = request.getParameter("search");
		if (searchWord == null || "".equals(searchWord)) {
			searchWord = "";
		}

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

		// perPage 처리 부분
		String perPage = request.getParameter("perPage");
		if (perPage == null || "".equals(perPage)) {
			perPage = "10";
		}
		int itemsPerPage = Integer.parseInt(perPage);

		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;

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

		// SQL 쿼리
		String query = "";
		query += "SELECT * FROM (";
		query += "  SELECT rownum rnum, a.* FROM (";
		query += "    SELECT b.b_id, b.lb_id, b.b_title, b.b_author, b.b_pubyear, b.b_isbn, b.b_publisher, b.b_kywd, b.b_imgurl, b.b_loanstate, b.b_resstate, l.lb_name";
		query += "    FROM book b";
		query += "    JOIN library l ON b.lb_id = l.lb_id";
		if (item.equals("전체")) {
			query += "    WHERE b.b_title LIKE '%" + searchWord + "%' OR b.b_author LIKE '%" + searchWord
					+ "%' OR b.b_publisher LIKE '%" + searchWord + "%' OR b.b_kywd LIKE '%" + searchWord + "%'";
		} else {
			query += "    WHERE " + item_query + " LIKE '%" + searchWord + "%'";
		}
		query += "    ORDER BY " + okywd_kywd + " " + okywd_order;
		query += "  ) a";
		query += "  WHERE rownum <= " + endRow;
		query += ")";
		query += "WHERE rnum >= " + startRow;

		ArrayList<Map<String, String>> book_list = getDBList(query);
		ArrayList<Map<String, String>> library_list = getDBList("select lb_id,lb_name from library");

		request.setAttribute("searchWord", searchWord);
		request.setAttribute("item", item);
		request.setAttribute("page", page);
		request.setAttribute("perPage", perPage);
		request.setAttribute("okywd", okywd);

		request.setAttribute("book_list", book_list);
		request.setAttribute("library_list", library_list);
		request.getRequestDispatcher("/mainpages/book_search.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		  String b_id = request.getParameter("b_id");
		  System.out.println(b_id);		
		  String query = "UPDATE book SET b_resstate = 'N' WHERE b_id = "+b_id;
		  System.out.println(setDBList(query));
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
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
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
