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
public class QnABoardServlet extends HttpServlet {
	private static final String URL = "jdbc:oracle:thin:@112.148.46.134:51521:xe";
	private static final String USER = "carpedm";
	private static final String PASSWORD = "dm1113@";

//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		한글 깨짐 방지
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8;");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		
		
//		---------------------------------------------------------------------
//		제목을 눌렀을때 QnA_detail.jsp로 넘어가기
		String nid_list = request.getParameter("N_ID");
		if(nid_list == null || "".equals(nid_list))
		{
			nid_list="";
		}
		request.setAttribute("N_ID", nid_list);
	
		
		//검색어가져오기
		String searchWord = request.getParameter("search");
		if (searchWord == null || "".equals(searchWord)) {
			searchWord = "";
		}
		
		String select = request.getParameter("n_search");
		String select_sql = "";
		System.out.println(select);
		

		if (select == null || "".equals(select)) {
			select="제목";
		}
		if (select.equals("제목")) {
			select_sql = " AND N_TITLE LIKE '%"+searchWord+"%'";
		} else if (select.equals("제목 내용")) {
			select_sql = " AND N_TITLE LIKE '%"+searchWord+"%' OR N_CONTENT LIKE '%"+searchWord+"%'";
		} else if (select.equals("작성자")) {
			select_sql = " AND M_NAME LIKE '%"+searchWord+"%'";
		} 
		
		
//		실행할 쿼리문
		String notice = "";
		notice += "SELECT notice.*,  member.M_NAME";
		notice += "	FROM notice";
		notice += "	INNER JOIN MEMBER ON notice.M_PID = member.M_PID";
		notice += "	WHERE  N_OPT IN (1, 2)";
		notice += select_sql;
		notice += "	ORDER BY n_id DESC";
		System.out.println("notice쿼리 : "+notice);
		ArrayList<Map<String,String>> list = getDBList(notice);

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

		request.setAttribute("list", pageList);
		request.setAttribute("allcount", list.size());
//		request.setAttribute("list", list);
		

		
//		board/QnA_board.jsp와 이어줌
		request.getRequestDispatcher("board/QnA_board.jsp").forward(request, response);
	}
	
	public static ArrayList<Map<String, String>> getDBList(String notice) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnection();
			// SQL준비
//			System.out.println("notice:" + notice);
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
//			    값 잘 나오는지 확인
//			    System.out.println(map.get("N_ID"));
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
