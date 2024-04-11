package carpedm.board;

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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/QnA_board")
public class QnABoardServlet extends HttpServlet {

//	DB접속 메소드
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/carpedm");
			conn = dataFactory.getConnection();
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
		if (nid_list == null || "".equals(nid_list)) {
			nid_list = "";
		}
		request.setAttribute("N_ID", nid_list);

		// 검색어가져오기
		String searchWord = request.getParameter("search");
		if (searchWord == null || "".equals(searchWord)) {
			searchWord = "";
		}

		String select = request.getParameter("n_search");
		String select_sql = "";
		System.out.println(select);

		if (select == null || "".equals(select)) {
			select = "제목";
		}
		if (select.equals("제목")) {
			select_sql = " AND N_TITLE LIKE '%" + searchWord + "%'";
		} else if (select.equals("제목 내용")) {
			select_sql = " AND N_TITLE LIKE '%" + searchWord + "%' OR N_CONTENT LIKE '%" + searchWord + "%'";
		}

//		실행할 쿼리문
		String query = "";
		query += " select * from (";
		query += " select rownum rnum, n1.* from (";
		// 답글 정렬 시작
		query += " with notice_recu (lv, N_ID, N_OPT, N_TITLE, M_PID, N_DATE, N_PARENT_ID, M_NAME, N_VIEWCOUNT) as (";
		query += " select";
		query += " 1 as lv,";
		query += " N.N_ID, N.N_OPT, N.N_TITLE, N.M_PID, N.N_DATE, N.N_PARENT_ID, M.M_NAME, N.N_VIEWCOUNT";
		query += " from NOTICE N";
		query += " left outer join MEMBER M on N.M_PID = M.M_PID";
		query += " where N.N_PARENT_ID is null";
		query += " union all";
		query += " select";
		query += " nr.lv + 1 as lv,";
		query += " n.N_ID, n.N_OPT, n.N_TITLE, n.M_PID, n.N_DATE, n.N_PARENT_ID, m.M_NAME, n.N_VIEWCOUNT";
		query += " from notice_recu nr";
		query += " left outer join NOTICE n on n.N_PARENT_ID = nr.N_ID";
		query += " left outer join MEMBER m on n.M_PID = m.M_PID";
		query += " where n.N_PARENT_ID is not null";
		query += " )";
		query += " search depth first by N_ID desc set sort_N_ID";
		query += " select * from notice_recu";
		query += " order by sort_N_ID";
		// 답글 정렬 끝
		query += " ) n1";
		query += " ) n2";
		query += " where rnum >= 1 and rnum <= 10";
		System.out.println("notice쿼리 : " + query);
		ArrayList<Map<String, String>> list = getDBList(query);

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
