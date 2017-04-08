package keepmeout.travel.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 08-04-2017.
 */

public class Data {

    @SerializedName("originName")
    @Expose
    private String originName;
    @SerializedName("destinationName")
    @Expose
    private String destinationName;
    @SerializedName("origin")
    @Expose
    private Origin origin;
    @SerializedName("destination")
    @Expose
    private Destination destination;
    @SerializedName("noModesPossible")
    @Expose
    private Boolean noModesPossible;
    @SerializedName("routes")
    @Expose
    private List<Object> routes = null;
    @SerializedName("modes")
    @Expose
    private String modes;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("directFlight")
    @Expose
    private Boolean directFlight;
    @SerializedName("directTrain")
    @Expose
    private Boolean directTrain;
    @SerializedName("directBus")
    @Expose
    private Boolean directBus;
    @SerializedName("directCar")
    @Expose
    private Boolean directCar;
    @SerializedName("directIndirectSentence")
    @Expose
    private String directIndirectSentence;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("multiModes")
    @Expose
    private Boolean multiModes;

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Boolean getNoModesPossible() {
        return noModesPossible;
    }

    public void setNoModesPossible(Boolean noModesPossible) {
        this.noModesPossible = noModesPossible;
    }

    public List<Object> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Object> routes) {
        this.routes = routes;
    }

    public String getModes() {
        return modes;
    }

    public void setModes(String modes) {
        this.modes = modes;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getDirectFlight() {
        return directFlight;
    }

    public void setDirectFlight(Boolean directFlight) {
        this.directFlight = directFlight;
    }

    public Boolean getDirectTrain() {
        return directTrain;
    }

    public void setDirectTrain(Boolean directTrain) {
        this.directTrain = directTrain;
    }

    public Boolean getDirectBus() {
        return directBus;
    }

    public void setDirectBus(Boolean directBus) {
        this.directBus = directBus;
    }

    public Boolean getDirectCar() {
        return directCar;
    }

    public void setDirectCar(Boolean directCar) {
        this.directCar = directCar;
    }

    public String getDirectIndirectSentence() {
        return directIndirectSentence;
    }

    public void setDirectIndirectSentence(String directIndirectSentence) {
        this.directIndirectSentence = directIndirectSentence;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Boolean getMultiModes() {
        return multiModes;
    }

    public void setMultiModes(Boolean multiModes) {
        this.multiModes = multiModes;
    }
}