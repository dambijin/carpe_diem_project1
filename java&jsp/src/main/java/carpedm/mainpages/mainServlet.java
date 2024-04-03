package carpedm.mainpages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

@WebServlet("/main")
public class mainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		Date date = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//코드압축
		String now_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ArrayList<Map<String, String>> notice_list = getDBList("select n_id,n_title,n_date from notice where n_opt = 0 order by n_id desc");
		ArrayList<Map<String, String>> book_list = getDBList("select b_id,b_title,b_author,b_imgurl from book order by b_id desc");
		ArrayList<Map<String, String>> library_list = getDBList("select lb_name,lb_opentime,lb_address,lb_tel,lb_content from library");
		ArrayList<Map<String, String>> banner_list = getDBList("SELECT * FROM banner WHERE TO_DATE('"+now_date+"','YYYY-MM-DD HH24:MI:SS') BETWEEN ban_startdate AND ban_enddate");
		
//		System.out.println(banner_list);
//		System.out.println(notice_list);
		request.setAttribute("notice_list", notice_list);
		request.setAttribute("book_list", book_list);
		request.setAttribute("library_list", library_list);
		request.setAttribute("banner_list", banner_list);

		request.getRequestDispatcher("/mainpages/main.jsp").forward(request, response);
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
