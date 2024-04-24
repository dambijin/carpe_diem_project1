package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.WishlistDTO;

@Service
public class A_WishService {

	@Autowired
	A_WishbookDAO dao;
	
	public List listWishBooks(WishlistDTO dto) {
		
		List list = dao.getWishList(dto);
		
		return list;
	}
	
	public int listWishBooksCount(WishlistDTO dto) {
		
		int count = dao.getWishListCount(dto);
		
		return count;
	}
	
//	public int listWishBooksUpdate(WishlistDTO dto) {
//		
//		int result = Integer.parseInt(dao.getWishListUpdate(dto));  
//		
//		return result;
//	}
		
}
