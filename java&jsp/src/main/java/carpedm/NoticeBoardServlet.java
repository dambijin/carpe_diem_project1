package carpedm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/notice_board")
public class NoticeBoardServlet extends HttpServlet {
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
       
		
//		---------------------------------------------------------------------
//		제목을 눌렀을때 QnA_detail.jsp로 넘어가기 만들기
		
//		notice_board.jsp의 
//		<a href="notice_detail?N_ID=<%=list.get(i).get("N_ID")%>" 
//		class="table_a"><%=list.get(i).get("N_TITLE")%></a>
//		이 a태그에서 notice_detail?N_ID  << N_ID를 넣기위해 만듬
//		? 다음 들어올 이름이 "N_ID"인것
		String nid_list = request.getParameter("N_ID");
//		만약 N_ID가 null이거나 비어있다면
		if(nid_list == null || "".equals(nid_list))
		{
			nid_list="";
		}
		request.setAttribute("N_ID", nid_list);
		
		
//		가져올 데이터 값의 쿼리문
		String notice = "";
		notice += "SELECT * FROM notice where n_opt=0 order by n_id desc";
		ArrayList<Map<String,String>> list = getDBList(notice);
		request.setAttribute("list", list);

		
		
//		관리자 Y인지 N인지 설정하기
		String M_PID = "10"; // 8 : 관리자아님, 10 : 관리자
		String query = "";
		query += "SELECT * FROM member where ";
		query += "M_PID = ";
		query += M_PID;
		
		System.out.println("MEMBER테이블 쿼리: " + query);
		ArrayList<Map<String, String>> mem = getDBList(query);

		request.setAttribute("mem", mem);
		
		
//		멤버 가져오기
		String manager = "";
		if (mem != null && !mem.isEmpty()) {
			for (int i = 0; i < mem.size(); i++) {
				Map<String, String> row = mem.get(i);
				manager = row.get("M_MANAGERCHK");
				System.out.println("manager 값: " + manager);
			}
		}
		
		request.setAttribute("manager", manager);
		
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        
        
        
//      board/notice_board.jsp 파일을 이어줌
		request.getRequestDispatcher("board/notice_board.jsp").forward(request, response);
	
	}
	

	public static ArrayList<Map<String, String>> getDBList(String notice) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비

			System.out.println("nidquery:" + notice);
			// SQL 실행준비
			PreparedStatement ps = conn.prepareStatement(notice);
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
			    System.out.println(map.get("N_ID"));
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
	
	
	
