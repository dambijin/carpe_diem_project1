package carpedm.mypage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class wishbook_detailServlet
 */
@WebServlet("/wishbook_detail")
public class wishbook_detailServlet extends HttpServlet {

	private static final int COM_CODE = 0;
	private static final int ERROR_CODE = 1;
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/carpedm");
			conn = dataFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String w_id = request.getParameter("w_id");
		if (w_id == null || "".equals(w_id)) {
			w_id = "";
		}
		ArrayList<Map<String, String>> wish = getDBList(
				"select * from wishlist join library on wishlist.lb_id = library.lb_id where w_id =" + w_id);
		request.setAttribute("wish", wish);
		System.out.println("w_id : " + w_id);
		request.setAttribute("w_id", Integer.parseInt(w_id));
		System.out.println(wish);

		request.getRequestDispatcher("/mypage/wishbook_detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String w_id = request.getParameter("w_id");
		System.out.println(w_id);
		String m_pid = request.getParameter("m_pid");
		System.out.println(m_pid);
		String query = "delete wishlist where w_id = " + w_id + " and m_pid = " + m_pid;
		
		setDBList(query);
		
		response.getWriter().print(COM_CODE);
		
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
