package carpedm.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/admin_book_list")
public class admin_book_listServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Admin_book_listDAO dao = new Admin_book_listDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8;");
		
		System.out.println("서블릿 들어옴");
		
		BookDTO dto = new BookDTO();
		
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
				
		List<BookDTO> list = dao.getBookList(dto);

		System.out.println("list : " + list);
		
		if (list.isEmpty()) {
		    System.out.println("리스트가 비어 있습니다.");
		} else {
		    // 리스트가 비어 있지 않으면 각 요소를 출력
		    for (int i = 0; i < list.size(); i++) {
		        BookDTO book = list.get(i);
		        System.out.println("Index " + i + ": " + book);
		    }
		}
		
		request.setAttribute("list", list);
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("type", type);
		
		request.setAttribute("orderColumn", orderColumn);
		request.setAttribute("orderType", orderType);
		
		
		// 페이징!!
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
		ArrayList<BookDTO> pageList = new ArrayList<>();

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

		// 전체 회원 수
		int allcount = list.size();
		request.setAttribute("allcount", allcount);
		request.setAttribute("book_list", pageList);
		
		System.out.println(list);
		// 6. JSP 페이지로 포워딩
		request.getRequestDispatcher("/admin/admin_book_list.jsp").forward(request, response);
	}
	

}
