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

@WebServlet("/admin_reservation_list")
public class admin_reservation_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Map<String, String>> list = getReserv();

		System.out.println(list);
		request.setAttribute("reser_list", list);
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
		
	
	private static ArrayList<Map<String,String>> getReserv() {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " r_id, b_id, r_resdate, r_resstate, m_pid";
			query += " from reservation";

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				/* 폐기처분행(자바스크립트에서 사용할때 필요할 수 있음)
				map.put("lb_name", StringEscapeUtils.escapeJson(rs.getString("lb_name")));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("lb_address", StringEscapeUtils.escapeJson(rs.getString("lb_address")));
				map.put("lb_tel", StringEscapeUtils.escapeJson(rs.getString("lb_tel")));
				map.put("lb_openTime", StringEscapeUtils.escapeJson(rs.getString("lb_openTime")));
				map.put("lb_content", StringEscapeUtils.escapeJson(rs.getString("lb_content")));
				map.put("lb_imgUrl", StringEscapeUtils.escapeJson(rs.getString("lb_imgUrl")));
				map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
				*/				
				map.put("r_id", rs.getString("r_id"));
				map.put("b_id", rs.getString("b_id"));
				map.put("r_resdate", rs.getString("r_resdate"));
				map.put("r_resstate", rs.getString("r_resstate"));
				map.put("m_pid", rs.getString("m_pid"));

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
