package carpedm.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carpedm.dto.BookDTO;
import carpedm.dto.BookgenreDTO;

@Service
public class A_BookService {

	@Autowired
	A_Book_listDAO dao;

	public List listBooks(BookDTO dto) {

		List list = dao.getBookList(dto);

		return list;
	}

	public int listBookscount(BookDTO dto) {

		int count = dao.getBookCount(dto);

		return count;
	}

	public int deleteBooks(BookDTO dto) {

		int result = dao.getBookDelete(dto);

		return result;
	}

	// book_genre 셀렉트
	public List listGenre(BookgenreDTO dto) {
		List list = dao.getBookGenre(dto);
		return list;
	}

}
