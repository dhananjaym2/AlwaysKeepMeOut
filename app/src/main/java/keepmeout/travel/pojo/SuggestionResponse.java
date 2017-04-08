package keepmeout.travel.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikesh on 08-04-2017.
 */

public class SuggestionResponse {
    public ArrayList<SuggestionModel> getSuggestionModels() {
        return suggestionModels;
    }

    public void setSuggestionModels(ArrayList<SuggestionModel> suggestionModels) {
        this.suggestionModels = suggestionModels;
    }

    private ArrayList<SuggestionModel> suggestionModels;

}
