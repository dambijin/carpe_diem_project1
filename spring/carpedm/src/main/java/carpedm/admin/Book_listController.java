package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import carpedm.dto.BookDTO;

@Controller
public class Book_listController{

	Book_listController(){
		System.out.println("Book_listController 생성자");
	}
	
	@Autowired	
	BookService bookService;
	
	@RequestMapping("/admin_book_list")
	public String listBooks(Model model, @ModelAttribute BookDTO dto) {
		System.out.println("BookController > listBooks 실행");
		
		List list = bookService.listBooks(dto);

		System.out.println("aaaa:"+list);
		model.addAttribute("book_list",list); // 모델에 회원 목록을 속성으로 추가합니다.
		
		return "admin/admin_book_list.jsp"; // "admin/admin_member_list" 뷰를 반환합니다.
		
	}
	
}
