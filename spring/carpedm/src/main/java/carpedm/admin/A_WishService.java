package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.WishlistDTO;

@Service
public class A_WishService {

	@Autowired
	A_WishbookDAO dao;
	
	public List listBooks(WishlistDTO dto) {
		
		List list = dao.getWishList(dto);
		
		return list;
	}
	
		
}
