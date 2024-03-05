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
		String page = request.getParameter("page");
		if (page == null || "".equals(page)) {
			page = "1";
		}
		int currentPage = Integer.parseInt(page);

		// perPage(표시 개수) 처리 부분
		String perPage = request.getParameter("perPage");
		if (perPage == null || "".equals(perPage)) {
			perPage = "10";
		}
		int itemsPerPage = Integer.parseInt(perPage);
		
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		request.setAttribute("page", page);
		request.setAttribute("perPage", perPage);
		ArrayList<Map<String, String>> pageList = new ArrayList<>();
		// 인덱스를 1부터 시작하기 위해 startRow와 endRow를 1씩 감소
		startRow--;
		endRow--;


		for (int i = startRow; i <= endRow; i++) {
			if (i < list.size()) {
				pageList.add(list.get(i));
			} else {
				break;
			}
		}
		
		System.out.println(pageList);
		
		request.setAttribute("allcount", list.size());
        request.setAttribute("member_list", pageList);
        request.setAttribute("search", search);
        
        // 6. JSP 페이지로 포워딩
        request.getRequestDispatcher("/admin/admin_member_list.jsp").forward(request, response);
	}
	
	
	// 기본적인 접속메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		    System.out.println("db접속성공");
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
	        
	        // SQL 실행준비
	        String query = "SELECT m.m_pid, m.m_name, m.m_id, m.m_birthday, m.m_tel, m.m_address, m.lb_id, m.m_loanstate FROM member m";

	        // 검색 조건이 존재할 때만 WHERE 절 추가
	        if (!search.trim().isEmpty()) {
	            query += " WHERE m.m_pid LIKE ? OR m.m_name LIKE ? OR m.m_id LIKE ? OR m.m_birthday LIKE ? OR m.m_tel LIKE ? OR m.m_address LIKE ? OR m.lb_id LIKE ?";
	        }

	        // SQL 실행준비
	        PreparedStatement ps = conn.prepareStatement(query);

	        // 검색 입력에 따라 다양한 열을 추가
	        if (!search.trim().isEmpty()) {
	            String[] columns = {"m.m_pid", "m.m_name", "m.m_id", "m.m_birthday", "m.m_tel", "m.m_address", "m.lb_id","m.m_loanstate"};
	            for (int i = 0; i < columns.length; i++) {
	                ps.setString(i + 1, "%" + search + "%");
	            }
	        }
	        
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
			    
			    // m_loanstate 컬럼 값 확인
			    String m_loanstate_text;
			    if (rs.getString("m_loanstate") != null) {
			    	// m_loanstate 값이 null이 아니면 "일"을 추가하여 m_loanstate_text에 할당
			    	m_loanstate_text = rs.getString("m_loanstate") + "일";
			    } else {
			    	// m_loanstate 값이 null이면 "정상"을 m_loanstate_text에 할당
			        m_loanstate_text = "정상";
			    }
			    map.put("m_loanstate", m_loanstate_text);

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
