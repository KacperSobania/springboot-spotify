package pl.kacper.springbootspotify.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.client.RestTemplate;
import pl.kacper.springbootspotify.dto.SpotifyAlbumDTO;
import pl.kacper.springbootspotify.dto.SpotifyArtistDTO;
import pl.kacper.springbootspotify.dto.SpotifyTrackDTO;
import pl.kacper.springbootspotify.entity.Artist;
import pl.kacper.springbootspotify.entity.Track;
import pl.kacper.springbootspotify.model.album.SpotifyAlbum;
import pl.kacper.springbootspotify.model.artist.SpotifyArtist;
import pl.kacper.springbootspotify.model.track.SpotifyTrack;
import pl.kacper.springbootspotify.repository.ArtistRepository;
import pl.kacper.springbootspotify.repository.TrackRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private TrackRepository trackRepository;
    private ArtistRepository artistRepository;
    private OAuth2AuthorizedClientManager authorizedClientManager;

    public Service(TrackRepository trackRepository, ArtistRepository artistRepository, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
        this.authorizedClientManager = authorizedClientManager;
    }

    public List<SpotifyArtistDTO> getArtistByArtistName(Authentication authentication, String artistName){

        String tokenValue = getTokenValue(authentication);
        HttpEntity httpEntity = getConfiguredHttpEntity(tokenValue);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<SpotifyArtist> artists = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=" + artistName + "&type=artist&market=PL&limit=1",
                HttpMethod.GET,
                httpEntity,
                SpotifyArtist.class);

        List<SpotifyArtistDTO> spotifyArtistDTO = artists.getBody()
                .getArtists().getItems()
                .stream()
                .map(item -> new SpotifyArtistDTO(
                        item.getFollowers().getTotal(),
                        item.getImages().get(0).getUrl(),
                        item.getGenres()))
                .toList();

        return spotifyArtistDTO;
    }

    public List<SpotifyAlbumDTO> getAlbumsByArtistName(Authentication authentication, String artistName){

        String tokenValue = getTokenValue(authentication);
        HttpEntity httpEntity = getConfiguredHttpEntity(tokenValue);
        RestTemplate restTemplate = new RestTemplate();

        String artistId = getArtistId(authentication, artistName);

        ResponseEntity<SpotifyAlbum> albums = restTemplate.exchange(
                "https://api.spotify.com/v1/artists/" + artistId + "/albums?include_groups=album&market=PL&limit=10",
                HttpMethod.GET,
                httpEntity,
                SpotifyAlbum.class);

        List<SpotifyAlbumDTO> spotifyAlbumDTO = albums.getBody()
                .getItems()
                .stream()
                .map(item -> new SpotifyAlbumDTO(
                        item.getName(),
                        item.getReleaseDate(),
                        item.getTotalTracks(),
                        item.getImages().get(0).getUrl()))
                .toList();

        return spotifyAlbumDTO;
    }

    public List<SpotifyTrackDTO> getTopTracksOfArtist(Authentication authentication, String artistName){

        String tokenValue = getTokenValue(authentication);
        HttpEntity httpEntity = getConfiguredHttpEntity(tokenValue);
        RestTemplate restTemplate = new RestTemplate();

        String artistId = getArtistId(authentication, artistName);

        ResponseEntity<SpotifyTrack> topTracks = restTemplate.exchange(
                "https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=PL&limit=1",
                HttpMethod.GET,
                httpEntity,
                SpotifyTrack.class);

        List<SpotifyTrackDTO> spotifyTrackDTO = topTracks.getBody()
                .getTracks()
                .stream()
                .map(track -> new SpotifyTrackDTO(
                        track.getName(),
                        track.getArtists().get(0).getName(),
                        track.getAlbum().getImages().get(0).getUrl(),
                        track.getDurationMs()))
                .toList();

        return spotifyTrackDTO;
    }

    public List<SpotifyTrackDTO> getTracksByTitle(Authentication authentication, String trackTitle){

        String tokenValue = getTokenValue(authentication);
        HttpEntity httpEntity = getConfiguredHttpEntity(tokenValue);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<SpotifyTrack> tracks = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=" + trackTitle + "&type=track&market=PL&limit=5",
                HttpMethod.GET,
                httpEntity,
                SpotifyTrack.class);

        List<SpotifyTrackDTO> spotifyTrackDTO = tracks.getBody()
                .getTracks()
                .stream()
                .map(track -> new SpotifyTrackDTO(
                        track.getName(),
                        track.getArtists().get(0).getName(),
                        track.getAlbum().getImages().get(0).getUrl(),
                        track.getDurationMs()))
                .toList();

        return spotifyTrackDTO;
    }

    public void addArtist(Artist artist){
        artistRepository.save(artist);
    }

    public void addTrack(Track track){
        trackRepository.save(track);
    }

    private String getArtistId(Authentication authentication, String artistName){

        String tokenValue = getTokenValue(authentication);
        HttpEntity httpEntity = getConfiguredHttpEntity(tokenValue);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<SpotifyArtist> artists = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=" + artistName + "&type=artist&market=PL&limit=1",
                HttpMethod.GET,
                httpEntity,
                SpotifyArtist.class);

        return artists.getBody().getArtists().getItems().get(0).getId();
    }

    private HttpEntity getConfiguredHttpEntity(String tokenValue) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenValue);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        return httpEntity;
    }

    private String getTokenValue(Authentication authentication){
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("spotify")
                .principal(authentication)
                .attributes(attrs -> {})
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        return authorizedClient.getAccessToken().getTokenValue();
    }
}
