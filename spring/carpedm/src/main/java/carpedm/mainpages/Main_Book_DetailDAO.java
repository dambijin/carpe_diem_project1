package carpedm.mainpages;

import java.util.List;
import java.util.Map;

import carpedm.dto.ChaseBookDTO;

public interface Main_Book_DetailDAO {
	List<Map<String, String>> selectBookDetail(String bId);

	int insertChaseBook(ChaseBookDTO updateParams);
}
