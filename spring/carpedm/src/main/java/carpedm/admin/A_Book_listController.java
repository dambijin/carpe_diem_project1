package carpedm.admin;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
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
import carpedm.mypages.My_Wishbook_detailController;

@Controller
public class A_Book_listController{

	A_Book_listController(){
		System.out.println("A_Book_listController 생성자");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(A_Book_listController.class);
	
	@Autowired	
	A_BookService bookService;
	
	@RequestMapping("/admin_book_list")
	public String listBooks(Model model, @ModelAttribute BookDTO dto) {
		System.out.println("BookController > listBooks 실행");
		
		List list = bookService.listBooks(dto);

		System.out.println("aaaa:"+list);
		model.addAttribute("book_list",list); // 모델에 회원 목록을 속성으로 추가합니다.
		
		return "admin/admin_book_list.jsp.admin"; // "admin/admin_member_list" 뷰를 반환합니다.
		
	}
	
	
	@RequestMapping(value = "/admin_book_list", method = RequestMethod.POST)
	@ResponseBody
	public int bookdelete(
			@ModelAttribute BookDTO dto,
			@RequestParam("b_id") String b_id
			) {
		System.out.println("BookController > bookdelete 실행");
		
		System.out.println(b_id);
		
		int success = bookService.deleteBooks(dto);
		
		int id = Integer.parseInt(b_id);
		
//		// 폼 데이터 처리 로직 작성
//		Map<String, String> delete = new HashedMap();
//				
////		deleteBooks.put("b_id", b_id);
//		delete.put("b_id", b_id);
//		
//		System.out.println(delete);
		
		return success;
	}
	
	/* 
	 * data {
	 * userid : 인풋 아이디 지정한거 겟엘리멘탈아이디 가져온거
	 * }
	 * */
	
}
