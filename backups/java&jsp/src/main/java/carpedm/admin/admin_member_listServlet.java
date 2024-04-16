package carpedm.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin_member_list")
public class admin_member_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Admin_member_listDAO dao = new Admin_member_listDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8;");
		
		System.out.println("서블릿 들어옴");
		
		MemberDTO dto = new MemberDTO();
		
		// input 검색창
		String keyword = request.getParameter("keyword");
		System.out.println("keyword : " + keyword);
		
		// select 박스 필터
		String type = request.getParameter("type");
		System.out.println("type : " + type);
		
		// 정렬
		String orderColumn = request.getParameter("orderColumn");
		System.out.println("orderColumn : " + orderColumn);
		String orderType = request.getParameter("orderType");
		System.out.println("orderType : " + orderType);
				
		dto.setType(type);
		dto.setKeyword(keyword);
		
		dto.setOrderColumn(orderColumn);
		dto.setOrderType(orderType);
		
		List<MemberDTO> list = dao.getMemberList(dto);
//		List list = dao.getMemberList(dto);
		
//		System.out.println("list.size() : "+ list.size());

		System.out.println(list);
		// 연체상태
		// 리스트가 비어 있는지 확인
		if (list.isEmpty()) {
		    System.out.println("리스트가 비어 있습니다.");
		} else {
		    // 리스트가 비어 있지 않으면 각 요소를 출력
		    for (int i = 0; i < list.size(); i++) {
		        MemberDTO member = list.get(i);
		        System.out.println("Index " + i + ": " + member);
		        
			    String m_limitdate_text = "정상";
			    Date m_limitdate = list.get(i).getM_limitdate();
			    
			    if (m_limitdate != null) {
			    	// db에서 가져온 날짜와 오늘날짜 차이 계산
			        long millisecondsDiff = m_limitdate.getTime() - new Date().getTime();
			        // 두 날짜 간의 시간 차이를 일 단위로 변환
			        long daysDiff  = millisecondsDiff / (1000 * 60 * 60 * 24);
			    	
			        if (daysDiff > 0) {
			            m_limitdate_text = "<font color='red'>" + daysDiff  + "일"; // 날짜 차이가 양수일 때
			        } else {
			            m_limitdate_text = "정상"; // 오늘 날짜까지의 차이
			        }
			    }
			    list.get(i).setM_limitdate_text(m_limitdate_text);
		    }	    
		    
		}

		request.setAttribute("list", list);
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("type", type);
		
		request.setAttribute("orderColumn", orderColumn);
		request.setAttribute("orderType", orderType);

        // 페이징!!
		String page = request.getParameter("page");
		if (page == null || "".equals(page)) { // 만약 페이지 번호가 없다면
			page = "1"; // 기본값으로 1페이지를 설정
		}
		int currentPage = Integer.parseInt(page); // 현재 페이지 번호를 정수형으로 변환하여 저장

		// perPage(표시 개수) 처리 부분
		String perPage = request.getParameter("perPage");
		if (perPage == null || "".equals(perPage)) { // 만약 페이지당 표시할 아이템 개수가 없다면
			perPage = "10"; // 기본값으로 10개를 설정
		}
		int itemsPerPage = Integer.parseInt(perPage); // 페이지당 표시할 아이템 개수를 정수형으로 변환하여 저장
		
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1; // 현재 페이지의 시작 행 번호 계산
		int endRow = currentPage * itemsPerPage; // 현재 페이지의 마지막 행 번호 계산
		request.setAttribute("page", page);
		request.setAttribute("perPage", perPage);
		ArrayList<MemberDTO> pageList = new ArrayList<>();
		// 인덱스를 1부터 시작하기 위해 startRow와 endRow를 1씩 감소
		startRow--;
		endRow--;

		// 현재 페이지에 표시할 회원 목록 가져오기
		for (int i = startRow; i <= endRow; i++) { // 시작 행부터 마지막 행까지 반복
			if (i < list.size()) { // 리스트의 크기 범위 내에서
				pageList.add(list.get(i)); // 현재 페이지에 표시할 회원을 pageList에 추가
			} else {
				break;
			}
		}
		
		System.out.println(pageList);
		
		// 전체 회원 수
		int allcount = list.size();
		request.setAttribute("allcount", allcount);
        request.setAttribute("member_list", pageList);

        // 6. JSP 페이지로 포워딩
        request.getRequestDispatcher("/admin/admin_member_list.jsp").forward(request, response);
	}
	

	// 맴버가져오기
//	private static ArrayList<Map<String,String>> getmember(String search) {
//		ArrayList<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
//		try {
//			Connection conn = getConnection();
//	        
//	        // SQL 실행준비
//	        String query = "SELECT m.m_pid, m.m_name, m.m_id, m.m_birthday, m.m_tel, m.m_address, m.lb_id, m.m_limitdate FROM member m";
//
//	        // 검색 조건이 존재할 때만 WHERE 절 추가
//	        if (!search.trim().isEmpty()) {
//	            query += " WHERE m.m_pid LIKE ? OR m.m_name LIKE ? OR m.m_id LIKE ? OR m.m_birthday LIKE ? OR m.m_tel LIKE ? OR m.m_address LIKE ? OR m.lb_id LIKE ?";
//	        }
//
//	        // SQL 실행준비
//	        PreparedStatement ps = conn.prepareStatement(query);
//
//	        // 검색 입력에 따라 다양한 열을 추가
//	        if (!search.trim().isEmpty()) {
//	            String[] columns = {"m.m_pid", "m.m_name", "m.m_id", "m.m_birthday", "m.m_tel", "m.m_address", "m.lb_id","m.m_limitdate"};
//	            for (int i = 0; i < columns.length; i++) {
//	                ps.setString(i + 1, "%" + search + "%");
//	            }
//	        }
//	        
//	        ResultSet rs = ps.executeQuery();
//			
//			while (rs.next()) {
//				Map<String,String> map = new HashMap<String, String>();
//				/* 폐기처분행(자바스크립트에서 사용할때 필요할 수 있음)
//				map.put("lb_name", StringEscapeUtils.escapeJson(rs.getString("lb_name")));//이걸 쓸 줄 알아야 덜 지저분해질 것 같다...
//				map.put("lb_address", StringEscapeUtils.escapeJson(rs.getString("lb_address")));
//				map.put("lb_tel", StringEscapeUtils.escapeJson(rs.getString("lb_tel")));
//				map.put("lb_openTime", StringEscapeUtils.escapeJson(rs.getString("lb_openTime")));
//				map.put("lb_content", StringEscapeUtils.escapeJson(rs.getString("lb_content")));
//				map.put("lb_imgUrl", StringEscapeUtils.escapeJson(rs.getString("lb_imgUrl")));
//				map.put("lb_content", rs.getString("lb_content").replace("\n", "<br>").replace("\"", "\\\"").replace("\r", "\\r"));
//				*/				
//				map.put("m_pid", rs.getString("m_pid"));
//			    map.put("m_name", rs.getString("m_name"));
//			    map.put("m_id", rs.getString("m_id"));
//			    map.put("m_birthday", rs.getString("m_birthday"));
//			    map.put("m_tel", rs.getString("m_tel"));
//			    map.put("m_address", rs.getString("m_address"));
//			    map.put("lb_id", rs.getString("lb_id"));
//
//			    result_list.add(map);
//			}
//
//			rs.close();
//			ps.close();
//			conn.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result_list;
//	}
}
