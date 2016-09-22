package repositories;

import java.util.List;

import pojo.AuthUser;
import pojo.CustomData;

public interface CustomDataRepository extends BaseRepository<CustomData, Long> {
	
	public CustomData findByKey(String key);


}
