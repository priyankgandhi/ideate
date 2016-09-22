/**
 * 
 */
package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.CustomData;
import repositories.CustomDataRepository;

/**
 * @author priyank
 *
 */
@Service
public class GeneralService {

	@Autowired
	private CustomDataRepository customDataRepository;
		
	public CustomData getValueForKey(String key) {
		return customDataRepository.findByKey(key);
	}
	
	public void deleteKey(Integer id) {
		customDataRepository.delete(customDataRepository.findById(id));
	}
	
	public List<CustomData> findAll() {
		return customDataRepository.findAll();
	}
	
	public void save(CustomData customData) {
		customDataRepository.save(customData);
	}	
	
}
