package carpedm.admin;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import carpedm.dto.BookDTO;

@Controller
public class A_Book_listController{

	A_Book_listController(){
		System.out.println("A_Book_listController 생성자");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(A_Book_listController.class);
	
	
	@Autowired	
	A_BookService bookService;
	
	@RequestMapping("/admin_book_list")
	public String listBooks(Model model,
			@ModelAttribute BookDTO dto,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "perPage", defaultValue = "10") String perPage
			) {
		System.out.println("BookController > listBooks 실행");
		int book_count = bookService.listBookscount(dto);
		
		int currentPage = Integer.parseInt(page);
		int itemsPerPage = Integer.parseInt(perPage);
		
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		List list = bookService.listBooks(dto);
		
		int startPage = Math.max(currentPage - 2, 1);	
		int totalPages = book_count > 0 ? (int) Math.ceil(book_count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages);
//        끝 페이지를 기준으로 조정
		startPage = Math.max(1, endPage - 4);

		
		// 날짜 한글로 변경
		BookDTO dto1 = (BookDTO)list.get(0);
		Date date = dto1.getB_date();
			
		System.out.println("date : " + date);
		
		if (date != null) {
		    SimpleDateFormat sdf = new SimpleDateFormat("YYYY년 MM월 dd일", Locale.KOREAN);
		    // 형식에 맞춰 날짜를 문자열로 변환
		    String formattedDate = sdf.format(date);
		    model.addAttribute("formattedDate", formattedDate);
		} else {
		    model.addAttribute("formattedDate", "날짜 없음");
		}
		
		// model에 book_list 속성 추가
		model.addAttribute("book_list",list);
		model.addAttribute("allcount", book_count);
		model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
		model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);
		
		return "admin/admin_book_list.jsp.admin"; // "admin/admin_member_list" 뷰를 반환합니다.
		
	}
	
	
	@RequestMapping(value = "/admin_book_list", method = RequestMethod.POST)
	@ResponseBody
	public String bookdelete(@RequestParam("b_id") String b_id) {
		System.out.println("bookdelete 실행됨????");
		
		logger.info("bookdelete 실행됨");
//		logger.info("삭제할 책 ID들: " + Arrays.toString(b_id));
		    
		System.out.println(b_id);
//		System.out.println(Arrays.toString(b_id));
		
		int success = bookService.deleteBooks(b_id);
		System.out.println("완료 개수 : "+ success);
		String result = "{\"message\": \"fail\"}";
		if(success > 0) {
			result = "{\"message\": \"success\"}";
		}
		return result;
	}
	
	
	
	
}
