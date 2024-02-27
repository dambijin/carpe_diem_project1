package carpedm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class MypageDBConn {

	// 쿼리를 직접 작성하여 가져오기(Select만 가능)
		public static ArrayList<String> getLoanAll(String query) {
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
//		    		    System.out.println(columnCount);
					for (int i = 1; i <= columnCount; i++) {
						// 열의 인덱스는 1부터 시작
						String value = rs.getString(i).replace("\n", "<br>");
						//문자열로 집어넣기 위해서 앞뒤로 큰따옴표 추가
						result_list.add("\"" + value + "\"");
					}
				}
//		    		result_list.get(0).toString();

				rs.close();
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result_list;
		}
	
	

	
}
