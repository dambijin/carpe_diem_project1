package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.BookDTO;

@Service
public class A_BookService {

	@Autowired
	A_Book_listDAO dao;
	
	public List listBooks(BookDTO dto) {
		
		List list = dao.getBookList(dto);
		
		return list;
	}
	
		
}
