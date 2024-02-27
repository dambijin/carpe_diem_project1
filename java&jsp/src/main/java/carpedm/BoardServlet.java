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


@WebServlet("/svboard")
public class BoardServlet extends HttpServlet {
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
		
////	공지사항 데이터 가져오기
//	// notice 테이블의 데이터를 가져오는 메소드
//    public static ArrayList<Map<String, String>> getNoticeData() {
////    	배열을 String타입으로 가져오기
//    	
//        return list;
//    }
	
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		한글 깨짐 방지
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map map= new HashMap();
        Connection conn = null;
//      PreparedStatement : Statement를 상속하고 있는 Interface
        PreparedStatement ps = null;
//      쿼리 결과를 가져오기 위해 사용
        ResultSet rs = null;

//      실행하려고 하는 notice 쿼리
        String query = "SELECT * FROM notice"; 
        try {
            // 데이터베이스 연결
            conn = getConnection();
            // PreparedStatement 생성
            ps = conn.prepareStatement(query);
            // 쿼리 실행 및 결과 가져오기
            rs = ps.executeQuery();

            // 결과 처리
            while (rs.next()) {
            	map.put("num", rs.getString("n_id")); // 순번
            	map.put("lb", rs.getString("lb_id")); // 도서관 
            	map.put("title", rs.getString("n_title")); // 제목
            	map.put("write", rs.getString("m_pid")); // 작성자
            	map.put("date", rs.getString("n_date")); // 등록일
            	map.put("view", rs.getString("n_viewcount")); //조회
            
//            	map 객체를 list에 추가하는 작업을 수행
            	list.add(map);   
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
  	   
      
       request.setAttribute("list", list);
		
		request.getRequestDispatcher("notice_board.jsp").forward(request, response);
	
	}

}
