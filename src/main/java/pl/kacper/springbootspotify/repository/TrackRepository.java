package pl.kacper.springbootspotify.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.kacper.springbootspotify.entity.Track;

@Repository
public interface TrackRepository extends MongoRepository<Track, String> {
}
