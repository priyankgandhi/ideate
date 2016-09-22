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

/**
 * @author priyank
 *
 */
@Service
public class AlexaService  {

	 public SpeechletResponse getWelcomeResponse(IntentRequest request) {
	        String speechText = "Welcome to the Alexa Skills Kit, you can say hello";

	        // Create the plain text output.
	        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	        speech.setText(speechText);
	        

	        return SpeechletResponse.newTellResponse(speech);
	    }

}
