package carpedm.mainpages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.sql.DataSource;

/**
 * Servlet implementation class book_detailServlet
 */
@WebServlet("/book_detail")
public class book_detailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String b_id = request.getParameter("id");
		if(b_id == null || "".equals(b_id))
		{
			b_id="";
		}

		String query = "";
		query += "SELECT b.*, bg.bg_name, l.lb_name";
		query += " FROM book b";
		query += " JOIN bookgenre bg ON b.bg_id = bg.bg_id";
		query += " JOIN library l ON b.lb_id = l.lb_id";
		query += " WHERE b.b_id = '" + b_id + "'";
		
		long unixTime = System.currentTimeMillis();
		ArrayList<Map<String,String>> bookdetail_list = getDBList(query);
		System.out.println("책 상세(DB) 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		Book_Recommend brc = new Book_Recommend();
		
		unixTime = System.currentTimeMillis();
		ArrayList<Map<String,String>> bookrecommend_list = brc.selenium_getyes24();
		System.out.println("추천 목록 총 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		request.setAttribute("bookdetail_list", bookdetail_list);
		request.setAttribute("bookrecommend_list", bookrecommend_list);
		request.getRequestDispatcher("/mainpages/book_detail.jsp").forward(request, response);
	}
	// 기본적인 접속메소드
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
}
