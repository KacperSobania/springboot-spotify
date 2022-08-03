
package pl.kacper.springbootspotify.model.artist;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "artists"
})
@Generated("jsonschema2pojo")
public class SpotifyArtist {

    @JsonProperty("artists")
    private Artists artists;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("artists")
    public Artists getArtists() {
        return artists;
    }

    @JsonProperty("artists")
    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
