package pl.kacper.springbootspotify.dto;

public class SpotifyTrackDTO {

    private String trackName;
    private String performer;
    private String imageURl;
    private int durationInMs;

    public SpotifyTrackDTO(String trackName, String performer, String imageURl, int durationInMs) {
        this.trackName = trackName;
        this.performer = performer;
        this.imageURl = imageURl;
        this.durationInMs = durationInMs;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public int getDurationInMs() {
        return durationInMs;
    }

    public void setDurationInMs(int durationInMs) {
        this.durationInMs = durationInMs;
    }
}
