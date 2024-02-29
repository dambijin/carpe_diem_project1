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

@WebServlet("/admin_member_list")
public class admin_member_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		// 1. 검색어를 파라미터에서 가져오기
        String search = request.getParameter("search");
        
        // 2. 검색어가 null이면 빈 문자열로 처리
        if (search == null) {
            search = "";
        }
        
        // 3. 검색 결과를 가져오기
        ArrayList<Map<String, String>> list = getmember(search);
              
        // 4. 검색 결과를 속성으로 설정하여 JSP 페이지로 전달
        request.setAttribute("admin_member_list", list);

        // 5. 검색어도 속성으로 설정하여 JSP 페이지로 전달
        request.setAttribute("search", search);

        request.setAttribute("member_list", list);
        
        // 6. JSP 페이지로 포워딩
        request.getRequestDispatcher("/admin/admin_member_list.jsp").forward(request, response);
	}
	
	
	// 기본적인 접속메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//		    System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 맴버가져오기
	private static ArrayList<Map<String,String>> getmember(String search) {
		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
		try {
			Connection conn = getConnection();
			
			// SQL준비
	        String query = "SELECT m.m_pid, m.m_name, m.m_id, m.m_birthday, m.m_tel, m.m_address, m.lb_id";
	        query += " FROM member m";
	        
	        // Add conditions for various columns based on the search input
	        query += " WHERE m.m_name LIKE '%" + search + "%'";  // 검색어가 이름에 포함되어 있는 경우 검색
	        query += " OR m.m_id LIKE '%" + search + "%'";      // 검색어가 회원ID에 포함되어 있는 경우 검색
	        query += " OR m.m_birthday LIKE '%" + search + "%'"; // 검색어가 생년월일에 포함되어 있는 경우 검색
	        query += " OR m.m_tel LIKE '%" + search + "%'";       // 검색어가 전화번호에 포함되어 있는 경우 검색
	        query += " OR m.m_address LIKE '%" + search + "%'";   // 검색어가 주소에 포함되어 있는 경우 검색
	        query += " OR m.lb_id LIKE '%" + search + "%'";       // 검색어가 도서관ID에 포함되어 있는 경우 검색

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
				map.put("m_pid", rs.getString("m_pid"));
				map.put("m_name", rs.getString("m_name"));
				map.put("m_id", rs.getString("m_id"));
				map.put("m_birthday", rs.getString("m_birthday"));
				map.put("m_tel", rs.getString("m_tel"));
				map.put("m_address", rs.getString("m_address"));
				map.put("lb_id", rs.getString("lb_id"));

				result_list.add(map);
//			   	System.out.println(rs.getString("lb_name"));
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
