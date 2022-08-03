package pl.kacper.springbootspotify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kacper.springbootspotify.dto.SpotifyAlbumDTO;
import pl.kacper.springbootspotify.dto.SpotifyArtistDTO;
import pl.kacper.springbootspotify.dto.SpotifyTrackDTO;
import pl.kacper.springbootspotify.entity.Artist;
import pl.kacper.springbootspotify.entity.Track;
import pl.kacper.springbootspotify.service.Service;

import java.security.Principal;
import java.util.List;

@RestController
public class Controller {

    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Hello world";
    }

    @GetMapping("/user")
    public Principal getPrincipal(Principal principal){
        return principal;
    }

    @GetMapping("/artists/{artistName}")
    public List<SpotifyArtistDTO> getArtistByArtistName(Authentication authentication, @PathVariable String artistName){

        return service.getArtistByArtistName(authentication, artistName);
    }

    @GetMapping("/artists/{artistName}/albums")
    public List<SpotifyAlbumDTO> getAlbumsByArtistName(Authentication authentication, @PathVariable String artistName){

        return service.getAlbumsByArtistName(authentication, artistName);
    }

    @GetMapping("/artists/{artistName}/top-tracks")
    public List<SpotifyTrackDTO> getTopTracksOfArtist(Authentication authentication, @PathVariable String artistName){

        return service.getTopTracksOfArtist(authentication, artistName);
    }

    @GetMapping("/tracks/{trackTitle}")
    public List<SpotifyTrackDTO> getTracksByTitle(Authentication authentication, @PathVariable String trackTitle){

        return service.getTracksByTitle(authentication, trackTitle);
    }

    @PostMapping("/add-track")
    public void addTrack(@RequestBody Track track){
        service.addTrack(track);
    }

    @PostMapping("/add-artist")
    public void addTrack(@RequestBody Artist artist){
        service.addArtist(artist);
    }

}
