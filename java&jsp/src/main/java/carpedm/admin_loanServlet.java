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

@WebServlet("/admin_loan")
public class admin_loanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Map<String, String>> list = getLoan();
		
		request.setAttribute("loan", list);
		request.getRequestDispatcher("/admin/admin_loan.jsp").forward(request, response);
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
	private static ArrayList<Map<String,String>> getLoan() {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
			String query = "";
			query += " select";
			query += " l_id, b_id, l_loanDate, l_returnDate, l_returnRealDate, l_extendCount, m_pid";
			query += " from loan";

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
					
				map.put("l_id", rs.getString("l_id"));
				map.put("b_id", rs.getString("b_id"));
				map.put("l_loanDate", rs.getString("l_loanDate"));
				map.put("l_returnDate", rs.getString("l_returnDate"));
				map.put("l_returnRealDate", rs.getString("l_returnRealDate"));
				map.put("l_extendCount", rs.getString("l_extendCount"));
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
