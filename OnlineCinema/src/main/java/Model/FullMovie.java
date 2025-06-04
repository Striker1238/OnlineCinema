package Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FullMovie extends LightweightMovie {
    @JsonProperty("altTitle")
    public String altTitle;
    @JsonProperty("genres")
    public String genres;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("status")
    public MovieStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("type")
    public MovieType type;
    @JsonProperty("avgDuration")
    public float avg_duration;
    @JsonProperty("country")
    public String country;
    @JsonProperty("author")
    public String author;
    @JsonProperty("studio")
    public String studio;
    @JsonProperty("director")
    public String director;
    @JsonProperty("userRatingCount")
    public int userRatingCount;
    @JsonProperty("criticRating")
    public float criticRating;
    @JsonProperty("comments")
    public String comments;
    @JsonProperty("otherUrl")
    public String otherUrl;

    @JsonCreator
    public FullMovie(
            @JsonProperty("id") long id,
            @JsonProperty("title") String title,
            @JsonProperty("altTitle") String altTitle,
            @JsonProperty("imageUrl") String imageUrl,
            @JsonProperty("description") String description,
            @JsonProperty("genres") String genres,
            @JsonProperty("status") MovieStatus status,
            @JsonProperty("type") MovieType type,
            @JsonProperty("episodes") int episodes,
            @JsonProperty("avgDuration") float avg_duration,
            @JsonProperty("country") String country,
            @JsonProperty("author") String author,
            @JsonProperty("studio") String studio,
            @JsonProperty("director") String director,
            @JsonProperty("userRatingCount") int userRatingCount,
            @JsonProperty("userRatingAvg") float userRatingAvg,
            @JsonProperty("criticRating") float criticRating,
            @JsonProperty("comments") String comments,
            @JsonProperty("otherUrl") String otherUrl) {
        super(id, title, imageUrl, description, episodes, userRatingAvg);
        this.altTitle = altTitle;
        this.genres = genres;
        this.status = status;
        this.type = type;
        this.avg_duration = avg_duration;
        this.country = country;
        this.author = author;
        this.studio = studio;
        this.director = director;
        this.userRatingCount = userRatingCount;
        this.criticRating = criticRating;
        this.comments = comments;
        this.otherUrl = otherUrl;
    }

    @Override
    public String toString() {
        return "FullMovie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", altTitle='" + altTitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", episodes=" + episodes +
                ", userRatingAvg=" + userRatingAvg +
                ", genres='" + genres + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", avg_duration=" + avg_duration +
                ", country='" + country + '\'' +
                ", author='" + author + '\'' +
                ", studio='" + studio + '\'' +
                ", director='" + director + '\'' +
                ", userRatingCount=" + userRatingCount +
                ", criticRating=" + criticRating +
                ", comments='" + comments + '\'' +
                ", otherUrl='" + otherUrl + '\'' +
                '}';
    }
}