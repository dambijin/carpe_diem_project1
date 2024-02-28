package carpedm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@WebServlet("/wishbook_add")
public class Wishbook_addServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";
	

//	DB접속 메소드
	public static Connection getConnection() {
		Connection conn= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		한글 깨짐 방지
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
//      실행하려고 하는 쿼리
        String query = "SELECT * FROM member "; 
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
           
            // 결과 처리
            while (rs.next()) {
            	Map map= new HashMap();
            	map.put("id", rs.getString("m_id")); // 아이디
            	map.put("name", rs.getString("m_name")); // 이름
            	map.put("tel", rs.getString("m_tel")); // 전화번호
            
            	list.add(map);                        	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//      만약 로그인을 했다면
//      로그인한 아이디가 데이터값의 아이디가 같다면
//      list.get(i).get("id") : 아이디값
//      같은 아이디의 이름(list.get(i).get("name"))과
//      같은 아이디의 전화번호(list.get(i).get("tel"))를 불러오겠어
        
        request.setAttribute("list", list);
   

	
		request.getRequestDispatcher("board/wishbook_add.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
