package carpedm.sign;

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


@WebServlet("/find_id_tel")
public class find_id_telServlet extends HttpServlet {
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

//	DB접속 메소드
	private static Connection getConnection() {
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
        // 쿼리(sql코드) 구문을 사용하기위해 
//      PreparedStatement : Statement를 상속하고 있는 Interface
        PreparedStatement ps = null;
//      쿼리 결과를 가져오기 위해 사용
        ResultSet rs = null;
        
//      실행하려고 하는 쿼리(sql코드)
        String query = "SELECT * FROM member"; 
        try {
            // 데이터베이스 연결
            conn = getConnection();
            // PreparedStatement 생성
            ps = conn.prepareStatement(query);
            // 쿼리 실행 및 결과 가져오기
            rs = ps.executeQuery();
           
            // 결과 처리
            // while 전달인자가 true인 동안 계속 반복
            // re.next() 다음줄이 있으면 true 없으면 false
            while (rs.next()) {
            	// HashMap() 중복된 key값과 1개의 value값을 입력 받을수 있다
            	Map map= new HashMap();
            	map.put("id", rs.getString("M_ID")); // 아이디
            	map.put("name", rs.getString("M_NAME")); // 이름
            	String tel = rs.getString("M_TEL");
            	if(tel!= null)
            	{                	
                	map.put("tel", tel.replace("-", ""));
            	}
            	else
            	{
            		map.put("tel", "132654657984651");
            	}
    
            
//            	map 객체를 list에 추가하는 작업을 수행
            	list.add(map); 
            	System.out.println("tel : "+map.get("tel"));
	
//              list.get(0) : 리스트 첫번째에 있는 맵을 꺼내옴
//            	뒤에 붙는 .get("num") : 그 맵에서 num이라는 키값을 갖는 밸류값을 가져온다
//            	System.out.println(list.get(0).get("num"));
//            	System.out.println(list.get(0).get("title"));
            	
//            	ArrayList al= new ArrayList();
//            	al.add(list.get(0).get("num"));
            }
//            System.out.println(al);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       for(int i=1; i<list.size(); i++) {
    	   System.out.println(list.get(i).get("id"));
       }
       
        request.setAttribute("nametel_list", list);
   
		request.getRequestDispatcher("sign/find_id_tel.jsp").forward(request, response);
	
	}

}