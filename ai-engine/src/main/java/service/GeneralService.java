/**
 * 
 */
package service;

import java.util.HashMap;
import java.util.List;

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
		String city = params.get("city").getAsString();
		String geocity = params.get("geo-city").getAsString();
		String airport = params.get("airport_code").getAsString();
		String merchant = params.get("merchant").getAsString();
		StringBuffer sb = new StringBuffer();
		sb.append(intentName+"-").append(city).append(geocity).append(airport).append(merchant);			
		ret = sb.toString().toLowerCase();
		return ret;
	}
}
