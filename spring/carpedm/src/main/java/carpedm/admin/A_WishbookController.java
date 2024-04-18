package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import carpedm.dto.WishlistDTO;

@Controller
public class A_WishbookController{

	A_WishbookController(){
		System.out.println("Book_listController 생성자");
	}
	
	@Autowired	
	A_WishService wishService;
	
	@RequestMapping("/admin_wishbook_list")
	public String wishlist(Model model, @ModelAttribute WishlistDTO dto) {
		System.out.println("BookController > wishlist 실행");
		
		List list = wishService.listBooks(dto);

		System.out.println("aaaa:"+list);
		model.addAttribute("wishbook_list",list);
		
		return "admin/admin_wishbook_list.jsp.admin";
		
	}
	
}
