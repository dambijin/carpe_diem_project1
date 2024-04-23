package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carpedm.dto.WishlistDTO;

@Controller
public class A_WishbookController{

	A_WishbookController(){
		System.out.println("Book_listController 생성자");
	}
	
	@Autowired	
	A_WishService wishService;
	
	@RequestMapping("/admin_wishbook_list")
	public String wishlist(Model model, @ModelAttribute WishlistDTO dto,
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "perPage", defaultValue = "10") String perPage
			) {
		System.out.println("BookController > wishlist 실행");
		int currentPage = Integer.parseInt(page);
		int itemsPerPage = Integer.parseInt(perPage);
		// 페이지 처리를 위한 계산
		int startRow = (currentPage - 1) * itemsPerPage + 1;
		int endRow = currentPage * itemsPerPage;
		
		dto.setStartrow(startRow);
		dto.setEndrow(endRow);
		List list = wishService.listWishBooks(dto);

		System.out.println(list);
		int wishbook_count = wishService.listWishBooksCount(dto);

		int startPage = Math.max(currentPage - 2, 1);	
		int totalPages = wishbook_count > 0 ? (int) Math.ceil(wishbook_count / Double.parseDouble(perPage)) : 1;
		int endPage = Math.min(startPage + 4, totalPages);
//        끝 페이지를 기준으로 조정
		startPage = Math.max(1, endPage - 4);
//		System.out.println("aaaa:"+list);
		
		model.addAttribute("allcount", wishbook_count);
		model.addAttribute("page", page);
		model.addAttribute("perPage", perPage);
		model.addAttribute("start_page", startPage);
		model.addAttribute("end_page", endPage);
		model.addAttribute("total_pages", totalPages);
		
		model.addAttribute("wishbook_list",list);
		
		return "admin/admin_wishbook_list.jsp.admin";
		
	}
	
}
