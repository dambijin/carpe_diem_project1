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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchWord = request.getParameter("search");
		if(searchWord == null || "".equals(searchWord))
		{
			searchWord="";
		}
		request.setAttribute("search", searchWord);
		
		String item = request.getParameter("item");
		if(item == null || "".equals(item))
		{
			item="";
		}
		request.setAttribute("search", searchWord);
		String query = "";
		query += "SELECT b.b_id,b.lb_id,b.b_title,b.b_author,b.b_pubyear,b.b_isbn,b.b_publisher,b.b_kywd,b.b_imgurl,b.b_loanstate,b.b_resstate,l.lb_name";
		query += " FROM book b";
		query += " JOIN library l ON b.lb_id = l.lb_id";
		if (item.equals("전체")) {
			query += " WHERE b.b_title LIKE '%" + searchWord + "%' OR b.b_author LIKE '%" + searchWord + "%' OR b.b_publisher LIKE '%" + searchWord + "%' OR b.b_kywd LIKE '%" + searchWord + "%'";
		} else if (item.equals("제목")) {
			query += " WHERE b.b_title LIKE '%" + searchWord + "%'";
		} else if (item.equals("저자")) {
			query += " WHERE b.b_author LIKE '%" + searchWord + "%'";
		} else if (item.equals("출판사")) {
			query += " WHERE b.b_publisher LIKE '%" + searchWord + "%'";
		} else if (item.equals("키워드")) {
			query += " WHERE b.b_kywd LIKE '%" + searchWord + "%'";
		}
		ArrayList<Map<String,String>> book_list = getDBList(query);
		ArrayList<Map<String,String>> library_list = getDBList("select lb_id,lb_name from library");

		request.setAttribute("book_list", book_list);
		request.setAttribute("library_list", library_list);
		request.getRequestDispatcher("/mainpages/book_search.jsp").forward(request, response);
	}
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	// 기본적인 접속메소드
	public static Connection getConnection() {
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

	public static ArrayList<Map<String, String>> getDBList(String query) {
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

}
