package carpedm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardDBConn {
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
		
//	공지사항 데이터 가져오기
	// notice 테이블의 데이터를 가져오는 메소드
    public static ArrayList<Map<String, String>> getNoticeData() {
//    	배열을 String타입으로 가져오기
    	ArrayList<Map<String, String>> title_list = new ArrayList<Map<String, String>>();
        Connection conn = null;
//      PreparedStatement : Statement를 상속하고 있는 Interface
        PreparedStatement ps = null;
//      쿼리 결과를 가져오기 위해 사용
        ResultSet rs = null;

//      실행하려고 하는 notice 쿼리
//    	공지사항은 n_otp = 0 
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
            	Map map= new HashMap();
            	map.put("title", rs.getString("n_title"));
            	map.put("write", rs.getString("m_pid"));
            	
//            	System.out.println("\"" + rs.getString("n_title") + "\"");
//            	title_list.add("\"" + rs.getString("n_title") + "\"");
            	title_list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return title_list;
    }

	
	
}
