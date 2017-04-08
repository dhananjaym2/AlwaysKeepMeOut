package keepmeout.travel.pojo;

/**
 * Created by vikesh on 08-04-2017.
 */

public class SuggestionResponse {
    public SuggestionResponse(String nameSuggested, String id, long Xid) {
        this.nameSuggested = nameSuggested;
        this.id = id;
        this.xid = Xid;
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

    public long getXid() {
        return xid;
    }

    long xid;
}
