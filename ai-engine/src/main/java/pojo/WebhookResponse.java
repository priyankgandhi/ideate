package pojo;

import java.util.List;
import java.util.Map;

public class WebhookResponse {
	
	String speech;
	String displayText;
	Object data;
	List<Map<String,Object>> contextOut;
	String source;
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public List<Map<String, Object>> getContextOut() {
		return contextOut;
	}
	public void setContextOut(List<Map<String, Object>> contextOut) {
		this.contextOut = contextOut;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
