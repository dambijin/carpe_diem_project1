package carpedm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin_book_overdue")
public class admin_book_overdueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Map<String, String>> list = getoverdue();
		
		System.out.println(list);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/admin/admin_book_overdue.jsp").forward(request, response);
	}

	// 기본적인 접속메소드
	private static Connection getConnection() {
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
	
	// 맴버가져오기
	private static ArrayList<Map<String,String>> getoverdue() {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " b_id, b_title, b_author, b_publisher, b_isbn, b_pubyear, lb_id, b_pubyear, b_resstate, b_loanstate";
			query += " from";
			query += " book";

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
					
				map.put("b_id", rs.getString("b_id"));
				map.put("b_title", rs.getString("b_title"));
				map.put("b_author", rs.getString("b_author"));
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("b_isbn", rs.getString("b_isbn"));
				map.put("b_pubyear", rs.getString("b_pubyear"));
				map.put("lb_id", rs.getString("lb_id"));
				map.put("b_pubyear", rs.getString("b_pubyear"));
				map.put("b_resstate", rs.getString("b_resstate"));
				map.put("b_loanstate", rs.getString("b_loanstate"));

				result_list.add(map);
//		    	System.out.println(rs.getString("lb_name"));
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
