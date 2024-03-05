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
		String m_pid = request.getParameter("m_pid");
		ArrayList<Map<String, String>> list = getLoan(m_pid);
		
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
	private static ArrayList<Map<String,String>> getLoan(String m_pid) {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
//			String query = "";
//			query += " select";
//			query += " m.m_pid, m.m_name, b.b_title, b.b_author, l.lb_name, o.l_loanDate, o.l_returnDate, b.b_resstate, b.b_loanstate";
//			query += " from member m";
//			query += " join library l on (m.lb_id = l.lb_id)";
//			query += " join loan o on(m.m_pid = o.m_pid)";
//			query += " join book b on(o.b_id = b.b_id)";
//			query += " where m.m_pid ="+m_pid;
			
			String query = "";
			query += "select";
			query += " l_id, b_title, b_author, b_publisher, l_loandate, l_returnrealdate, l_extendcount, library.lb_name,m_pid,book.b_resstate,book.b_loanstate";
			query += " from";
			query += " loan";
			query += " INNER JOIN book ON loan.b_id = book.b_id INNER JOIN library ON book.lb_id = library.lb_id";	
			query += " WHERE m_pid = " + m_pid;
			query += " order by l_returnrealdate desc";
			

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
					
				map.put("m_pid", rs.getString("m_pid"));
//				map.put("m_name", rs.getString("m_name"));
				map.put("b_title", rs.getString("b_title"));
				map.put("b_author", rs.getString("b_author"));
				map.put("lb_name", rs.getString("lb_name"));
				map.put("l_loanDate", rs.getString("l_loanDate"));
				map.put("l_returnrealdate", rs.getString("l_returnrealdate"));
				map.put("b_resstate", rs.getString("b_resstate"));
				map.put("b_loanstate", rs.getString("b_loanstate"));

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
