package carpedm.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import carpedm.dto.WishlistDTO;

@Controller
public class A_WishbookController{

	private static final Logger logger = LoggerFactory.getLogger(A_WishbookController.class);
	
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
	
	
	@RequestMapping(value = "/admin_wishbook_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMemberInfo(
			Locale locale, Model model, 
			@ModelAttribute WishlistDTO dto,
			@RequestParam int w_id ) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    try {
	    	System.out.println("포스트 접근 성공");
	        logger.debug("포스트 접근 성공");
	        dto.setW_id(w_id);
	        
	        // 완료!!
	        int success = wishService.listWishBooksUpdate(dto);
	        System.out.println("success : " + success);
	        
	        // 반려!!
	        int companion = wishService.listCompanionUpdate(dto);
	        System.out.println("companion : " + companion);
	        
	        // 성공적으로 업데이트되었음을 클라이언트에게 알리는 JSON 응답 생성
	        map.put("success", true);
	        map.put("message", "회원 정보가 성공적으로 업데이트되었습니다.");
	    } catch (Exception e) {
	        // 업데이트 중에 오류가 발생한 경우 클라이언트에게 알리는 JSON 응답 생성
	    	map.put("success", false);
	    	map.put("message", "회원 정보를 업데이트하는 동안 오류가 발생했습니다.");
	        logger.error("회원 정보 업데이트 중 오류 발생", e);
	    }
	    
	    return map;
	}
	
}
