package service;

import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class JsonDateDeserializer implements JsonDeserializer<Date> {
	
	@Override
	public Date deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
			throws com.google.gson.JsonParseException {
//		String s = json.getAsJsonPrimitive().getAsString();
		Date d = new Date();
		return d;
	}
}