
package keepmeout.travel.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestionModel {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("ct")
    @Expose
    private String ct;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("cn")
    @Expose
    private Object cn;
    @SerializedName("en")
    @Expose
    private Boolean en;
    @SerializedName("rt")
    @Expose
    private String rt;
    @SerializedName("st")
    @Expose
    private String st;
    @SerializedName("co")
    @Expose
    private String co;
    @SerializedName("_oid")
    @Expose
    private Integer oid;
    @SerializedName("eid")
    @Expose
    private String eid;
    @SerializedName("cid")
    @Expose
    private Object cid;
    @SerializedName("useNLP")
    @Expose
    private Boolean useNLP;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("xid")
    @Expose
    private Integer xid;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCn() {
        return cn;
    }

    public void setCn(Object cn) {
        this.cn = cn;
    }

    public Boolean getEn() {
        return en;
    }

    public void setEn(Boolean en) {
        this.en = en;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public Object getCid() {
        return cid;
    }

    public void setCid(Object cid) {
        this.cid = cid;
    }

    public Boolean getUseNLP() {
        return useNLP;
    }

    public void setUseNLP(Boolean useNLP) {
        this.useNLP = useNLP;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }

}
