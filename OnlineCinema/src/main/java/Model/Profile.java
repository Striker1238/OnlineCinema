package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("avatarUrl")
    String avatarUrl;// ? нужно ли...
    @JsonProperty("bio")
    String bio;
    @JsonProperty("visibility")
    boolean isVisibility;
    @JsonProperty("comments")
    int comments;
    @JsonProperty("collections")
    int collections;

    public Profile() {}
    public Profile(String nickname, String avatarUrl, String bio, boolean isVisibility){
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.isVisibility = isVisibility;
    }
    public String getNickname(){
        return nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getBio() {
        return bio;
    }
    public boolean getVisibility() {
        return isVisibility;
    }
    public int getComments() {
        return comments;
    }
    public int getCollections() {
        return collections;
    }
    @Override
    public String toString(){
        return "Nickname: " + nickname+
                "\nAvatarURL: " + avatarUrl+
                "\nBio: " + bio +
                "\nVisibility: " + isVisibility +
                "\nComments: " + comments +
                "\nCollections: " + collections;
    }
}
