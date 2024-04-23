package carpedm.mainpages;

import java.util.List;

import carpedm.dto.BookCartDTO;
import carpedm.dto.ChaseBookDTO;

public interface Main_Book_DetailService {
	List<Object> getBookDetail(ChaseBookDTO updateParams);
	
	int insertBookCart(BookCartDTO bcdto);
}
