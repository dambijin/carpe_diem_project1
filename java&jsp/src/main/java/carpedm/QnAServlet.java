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

@WebServlet("/QnA_board")
public class QnAServlet extends HttpServlet {
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
        String query = "SELECT * FROM notice where n_opt=1 or n_opt=2 order by n_id desc "; 
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
           
            // 결과 처리
            while (rs.next()) {
            	Map map= new HashMap();
            	map.put("num", rs.getString("n_id")); // 순번
            	map.put("opt", rs.getString("n_opt")); // 구분
            	map.put("title", rs.getString("n_title")); // 제목
            	map.put("write", rs.getString("m_pid")); // 작성자
            	map.put("date", rs.getString("n_date")); // 등록일
            	map.put("view", rs.getString("n_viewcount")); //조회
            
//            	map 객체를 list에 추가하는 작업을 수행
            	list.add(map);   
            	
//              list.get(0) : 리스트 첫번째에 있는 맵을 꺼내옴
//            	list.get(0).get("num") : 뒤에 붙는 .get("num") : 그 맵에서 num이라는 키값을 갖는 밸류값을 가져온다
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("list", list);
   
		request.getRequestDispatcher("board/QnA_board.jsp").forward(request, response);
	
	}

}
