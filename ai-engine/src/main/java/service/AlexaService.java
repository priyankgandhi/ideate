/**
 * 
 */
package service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

/**
 * @author priyank
 *
 */
@Service
public class AlexaService  {

	 public SpeechletResponse getWelcomeResponse() {
	        String speechText = "Welcome to the Alexa Skills Kit, you can say hello";

	        // Create the Simple card content.
	        SimpleCard card = new SimpleCard();
	        card.setTitle("HelloWorld");
	        card.setContent(speechText);

	        // Create the plain text output.
	        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	        speech.setText(speechText);

	        // Create reprompt
	        Reprompt reprompt = new Reprompt();
	        reprompt.setOutputSpeech(speech);

	        return SpeechletResponse.newAskResponse(speech, reprompt, card);
	    }

}
