package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import carpedm.dto.LibraryDTO;
import carpedm.dto.SearchinfoDTO;

public interface Main_Book_DetailService {
	List<Object> getBookDetail(String bId, String siId);
}
