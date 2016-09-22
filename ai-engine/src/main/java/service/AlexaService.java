/**
 * 
 */
package service;

import org.springframework.stereotype.Service;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import pojo.CustomData;

/**
 * @author priyank
 *
 */
@Service
public class AlexaService  {

	 public SpeechletResponse getWelcomeResponse(IntentRequest request, CustomData customData) {
		 	String speechText = "Welcome to the Genie";
		 	if (customData != null && customData.getValue()!= null) {
		 		speechText = customData.getValue();
		 	}
	        // Create the plain text output.
	        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	        speech.setText(speechText);
	        
	        return SpeechletResponse.newTellResponse(speech);
	    }

}
