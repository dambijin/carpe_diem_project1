package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import carpedm.dto.BookCartDTO;
import carpedm.dto.ChaseBookDTO;

public interface Main_Book_DetailDAO {
	List<Map<String, String>> selectBookDetail(String bId);

	List<Map<String, String>> selectSameISBNBookDetail(String ISBN);
	
	int insertChaseBook(ChaseBookDTO updateParams);
	
	int insertBookCart(BookCartDTO bcdto);
	
	List<BookCartDTO> SearchBookCart(BookCartDTO bcdto);
}
