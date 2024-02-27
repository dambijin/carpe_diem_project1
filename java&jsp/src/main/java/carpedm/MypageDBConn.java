package carpedm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class MypageDBConn {

	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

	// 기본적인 접속메소드
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//	            System.out.println("db접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 쿼리를 직접 작성하여 가져오기(Select만 가능)
	public static ArrayList<String> getSelectQueryAll(String query) {
		ArrayList<String> result_list = new ArrayList<String>();
		try {

			Connection conn = DBConn.getConnection();
			System.out.println("query:" + query);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				int columnCount = rsmd.getColumnCount(); // 열의 수를 가져옵니다.
//	    		    System.out.println(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					// 열의 인덱스는 1부터 시작
					String value = rs.getString(i);
					//문자열로 집어넣기 위해서 앞뒤로 큰따옴표 추가
					result_list.add("\"" + value + "\"");
				}
			}
//	    		result_list.get(0).toString();

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}

	
}
