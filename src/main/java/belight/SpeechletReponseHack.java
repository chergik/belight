package belight;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created 4/16/16. Description...
 *
 * @author Neo Li. <neo.siqi.li@hotmail.com>
 *     Andrey Chergik <achergik@gmail.com>
 */
public class SpeechletReponseHack extends SpeechletResponse {
    @Override
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public boolean getShouldEndSession() {
        return super.getShouldEndSession();
    }

    public static SpeechletResponse newAskResponse(OutputSpeech outputSpeech, Reprompt reprompt) {
        if(outputSpeech == null) {
            throw new IllegalArgumentException("OutputSpeech cannot be null");
        } else if(reprompt == null) {
            throw new IllegalArgumentException("Reprompt cannot be null");
        } else {
            SpeechletReponseHack response = new SpeechletReponseHack();
            response.setShouldEndSession(false);
            response.setOutputSpeech(outputSpeech);
            response.setReprompt(reprompt);
            return response;
        }
    }

    public static SpeechletResponse newAskResponse(OutputSpeech outputSpeech, Reprompt reprompt, Card card) {
        if(card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        } else {
            SpeechletResponse response = SpeechletReponseHack.newAskResponse(outputSpeech,
                                                                             reprompt);
            response.setCard(card);
            return response;
        }
    }
}



