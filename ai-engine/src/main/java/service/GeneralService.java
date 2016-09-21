/**
 * 
 */
package service;

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
	
}
