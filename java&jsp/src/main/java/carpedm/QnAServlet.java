package carpedm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	
		
		
		String title = request.getParameter("title");
		if(title == null || "".equals(title))
		{
			title="";
		}
		request.setAttribute("title", title);
		
		String titlequery = "";
		titlequery += "SELECT n.n_id, n.nb_id, n.n_opt, n.n_title, n.n_content, n.n_date, n.n_viewcount,n.n_file, n.n_chgdate, n.m_pid";
		titlequery += " FROM notice n";
		titlequery += " WHERE n.n_title LIKE '%" + title + "%'";
		
		ArrayList<Map<String,String>> title_list = getDBList(titlequery);
		ArrayList<Map<String,String>> notice_list = getDBList("select lb_id,lb_name from library");

		request.setAttribute("title_list", title_list);
		request.setAttribute("notice_list", notice_list);
	}

	private ArrayList<Map<String, String>> getDBList(String titlequery) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = DBConn.getConnection();
			// SQL준비

			System.out.println("titlequery:" + titlequery);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(titlequery);
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


