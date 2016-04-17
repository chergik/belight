package belight;

import com.amazon.speech.slu.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import java.util.Map;

/**
 * Created 4/16/16. Description...
 *
 * @author Andrey Chergik <achergik@gmail.com>
 *         Neo Li. <neo.siqi.li@hotmail.com>
 */
public class BeLightSpeachlet implements Speechlet  {
    private static final Logger log = LoggerFactory.getLogger(BeLightSpeachlet.class);

    public void onSessionStarted(final SessionStartedRequest request, final Session session)
    throws SpeechletException
    {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                 session.getSessionId());
        // any initialization logic goes here
    }

    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
    throws SpeechletException
    {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                 session.getSessionId());
        return getWelcomeResponse();
    }

    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
    throws SpeechletException {
        log.warn("onIntent requestId={}, sessionId={}", request.getRequestId(),
                 session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("HelloWorldIntent".equals(intentName)) {
            return getHelloResponse();
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else if ("WhatIEat".equals(intentName)) {
            return setWhatIEatResponse(intent, session);
        } else if ("WhatElseCanIEat".equals(intentName)) {
            return getWhatElseCanIEat(session);
        } else if ("InitialPrompt".equals(intentName)) {
            return getInitialPrompt(session);
        } else if ("YesPutOrder".equals(intentName)) {
            return setYestPutOrder(intent, session);
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    private SpeechletResponse getInitialPrompt(final Session session) {
        String speechText = ResponseGenerator.getInitialPrompt(session);

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("getInitialPrompt");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SpeechletResponse getWhatElseCanIEat(final Session session) {
        String speechText = ResponseGenerator.getWhatElseCanIEat(session);

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("WhatElseCanIEat");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SpeechletResponse setWhatIEatResponse(final Intent intent, final Session session) {
        // Get the slots from the intent.
        Map<String, Slot> slots = intent.getSlots();

        Slot foodSlot = slots.get("Food");
        String speechText, repromptText;

        if (foodSlot != null) {
            String food = foodSlot.getValue();
            FoodItem foodItem = FoodDAO.findByName(food);
            speechText = ResponseGenerator.getResponse(session, foodItem);
            repromptText ="You can tell me what you eat.";
            SessionHelper.addCurrentInTake(session, foodItem);
        } else {
            // Render an error since we don't know what the users favorite color is.
            speechText = "I'm not sure what food did you eat, please try again";
            repromptText = "You can tell me I ate pizza";
        }

        return getSpeechletResponse(speechText, repromptText, true);
    }

    private SpeechletResponse setYestPutOrder(final Intent intent, final Session session) {
        // Get the slots from the intent.
        Map<String, Slot> slots = intent.getSlots();

        Slot foodSlot = slots.get("Food");
        String speechText, repromptText;

        if (foodSlot != null) {
            String food = foodSlot.getValue();
            FoodItem foodItem = FoodDAO.findByName(food);
            speechText = ResponseGenerator.getResponse(session, foodItem);
            repromptText ="Good choice. Order is placed.";
        } else {
            // Render an error since we don't know what the users favorite color is.
            speechText = "I'm not sure what did you order, please try again";
            repromptText = "You can tell me order apple now.";
        }

        return getSpeechletResponse(speechText, repromptText, true);
    }

    private SpeechletResponse getSpeechletResponse(String speechText, String repromptText,
                                                   boolean isAskResponse) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Session");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        if (isAskResponse) {
            // Create reprompt
            PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
            repromptSpeech.setText(repromptText);
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromptSpeech);

            return SpeechletReponseHack.newAskResponse(speech, reprompt, card);


        } else {
            return SpeechletResponse.newTellResponse(speech, card);
        }
    }

    public void onSessionEnded(final SessionEndedRequest request, final Session session)
    throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                 session.getSessionId());
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "";

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

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelloResponse() {
        String speechText = "Hello Andrey, Veronika and Neo";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "You can say hello to me!";

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
