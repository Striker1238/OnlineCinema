package Model;

import Model.WatchStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchStatusEntry {
    @JsonProperty("login")
    public String login;
    @JsonProperty("mediaId")
    public long mediaId;
    @JsonProperty("status")
    public WatchStatus status;
    @JsonProperty("rating")
    public Integer rating;
    public WatchStatusEntry(){}
    public WatchStatusEntry(String login, long mediaId, WatchStatus status, Integer rating) {
        this.login = login;
        this.mediaId = mediaId;
        this.status = status;
        this.rating = rating;
    }
    public String getLogin() {
        return login;
    }
    public long getMediaId() {
        return mediaId;
    }
    public WatchStatus getStatus() {
        return status;
    }
    public Integer getRating() {
        return rating;
    }
}
