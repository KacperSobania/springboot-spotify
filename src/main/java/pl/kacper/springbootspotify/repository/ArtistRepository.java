package pl.kacper.springbootspotify.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.kacper.springbootspotify.entity.Artist;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
}
