package pojo;

import java.util.Map;

public class WebhookResponse {
	
	String speech;
	String displayText;
	Map<String,Object> data;
	Map<String,Object> contextOut;
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
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Map<String, Object> getContextOut() {
		return contextOut;
	}
	public void setContextOut(Map<String, Object> contextOut) {
		this.contextOut = contextOut;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
