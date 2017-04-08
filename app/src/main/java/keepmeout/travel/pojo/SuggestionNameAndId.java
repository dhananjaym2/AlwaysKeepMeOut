package keepmeout.travel.pojo;

/**
 * Created by vikesh on 08-04-2017.
 */

public class SuggestionNameAndId {
    public SuggestionNameAndId(String nameSuggested, String id) {
        this.nameSuggested = nameSuggested;
        this.id = id;
    }

    public String getNameSuggested() {
        return nameSuggested;
    }

   /* public void setNameSuggested(String nameSuggested) {
        this.nameSuggested = nameSuggested;
    }*/

    public String getId() {
        return id;
    }

    /*public void setId(int id) {
        this.id = id;
    }*/

    String nameSuggested;
    String id;
}
