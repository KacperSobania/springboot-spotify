package pl.kacper.springbootspotify.dto;

import java.util.ArrayList;
import java.util.List;

public class SpotifyArtistDTO {

    private long followers;
    private final List<String> genres = new ArrayList<>();
    private String imageURL;

    public SpotifyArtistDTO(long followers, String imageURL, List<String> genres) {
        this.followers = followers;
        this.imageURL = imageURL;
        this.genres.addAll(genres);
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
