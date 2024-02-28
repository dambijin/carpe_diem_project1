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

import org.apache.commons.text.StringEscapeUtils;

import com.google.gson.Gson;


@WebServlet("/mypage_wishbook_list")
public class mypage_wishbook_listServlet extends HttpServlet {
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String id = request.getParameter("id");
//		request.setAttribute("id2", id);
		ArrayList<Map<String,String>> list = getLoan();
		ArrayList<Map<String, String>> myInfo = getDBList("select * from member");
		request.setAttribute("myInfo", myInfo);
//		Gson gson = new Gson();
//		String json = gson.toJson(list);
//		request.setAttribute("list", json);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/mypage/mypage_wishbook_list.jsp").forward(request, response);
	}

	// 기본적인 접속메소드
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//		            System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static ArrayList<Map<String,String>> getLoan() {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = DBConn.getConnection();
			// SQL준비
			String query = "";
			query += "select";
			query += " lb_name, w_title, w_author, w_pubyear, w_isbn, w_content, w_publisher, w_tel, w_date";
			query += " from";
			query += " wishlist";
			query += " inner join library";
			query += " on library.lb_id = wishlist.lb_id";
			

			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();

				map.put("lb_name", rs.getString("lb_name"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("w_title", rs.getString("w_title"));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
				map.put("w_author", rs.getString("w_author"));
				map.put("w_pubyear", rs.getString("w_pubyear"));
				map.put("w_isbn", rs.getString("w_isbn"));
				map.put("w_content", rs.getString("w_content"));
				map.put("w_publisher", rs.getString("w_publisher"));
				map.put("w_tel", rs.getString("w_tel"));
				map.put("w_date", rs.getString("w_date"));
//				map.put("lb_name", rs.getString("lb_name"));
//				map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
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
	public static ArrayList<Map<String, String>> getDBList(String query) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = DBConn.getConnection();
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
