package Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LightweightMovie extends Movie {
    @JsonProperty("title")
    public String title;
    @JsonProperty("imageUrl")
    public String imageUrl;
    @JsonProperty("description")
    public String description;
    @JsonProperty("episodes")
    public int episodes;
    @JsonProperty("userRatingAvg")
    public float userRatingAvg;

    @JsonCreator
    public LightweightMovie(
            @JsonProperty("id") long id,
            @JsonProperty("title") String title,
            @JsonProperty("imageUrl") String imageUrl,
            @JsonProperty("description") String description,
            @JsonProperty("episodes") int episodes,
            @JsonProperty("userRatingAvg") float userRatingAvg) {
        super(id);
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.episodes = episodes;
        this.userRatingAvg = userRatingAvg;
    }
}