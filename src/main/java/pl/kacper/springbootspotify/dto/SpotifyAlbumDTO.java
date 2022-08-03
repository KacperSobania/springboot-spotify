package pl.kacper.springbootspotify.dto;

public class SpotifyAlbumDTO {

    private String name;
    private String releaseDate;
    private int totalTracks;
    private String imageURL;

    public SpotifyAlbumDTO(String name, String releaseDate, int totalTracks, String imageURL) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.totalTracks = totalTracks;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
