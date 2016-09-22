/**
 * 
 */
package service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;

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
	
	public String intentKeyBuilder(String intentName, HashMap<String, JsonElement> params) {
		String ret = "";		
		CustomData customData = customDataRepository.findByKey("keylist");
		String keys = customData.getValue();
		System.out.println("Key List - " + keys);
		
		String[] keysArray = keys.split(":");
		StringBuffer sb = new StringBuffer();
		sb.append(intentName);		
		for (String key : keysArray) {
			String val = params.get(key) != null ? params.get(key).getAsString() : "";
			if(StringUtils.isNotEmpty(val)) {
				sb.append("-").append(val);	
			}			
		}
		ret = sb.toString().toLowerCase();
		System.out.println("Intent Key - " + ret);
		return ret;
	}
}
