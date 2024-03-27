package carpedm.admin;

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

@WebServlet("/admin_reservation_list")
public class admin_reservation_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String m_pid = request.getParameter("m_pid");
		ArrayList<Map<String, String>> list = getReserv(m_pid);

		System.out.println(list);
		request.setAttribute("reserv_list", list);
		request.getRequestDispatcher("/admin/admin_reservation_list.jsp").forward(request, response);
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
		
	
	private static ArrayList<Map<String,String>> getReserv(String m_pid) {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " b_title, b_author,b_publisher, r_resdate, library.lb_name, r_resstate ";
			query += " from";
			query += " reservation";
			query += " inner join book";
			query += " on reservation.b_id = book.b_id";
			query += " inner join library";
			query += " on book.lb_id = library.lb_id";
			query += " where m_pid = " + m_pid;

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();

				map.put("b_title", rs.getString("b_title"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_author", rs.getString("b_author"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("b_publisher", rs.getString("b_publisher"));
				map.put("r_resdate", rs.getString("r_resdate"));
				map.put("lb_name", rs.getString("lb_name"));
				map.put("r_resstate", rs.getString("r_resstate"));
				
//				
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
