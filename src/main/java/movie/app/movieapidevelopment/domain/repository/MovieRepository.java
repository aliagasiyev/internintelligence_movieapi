package movie.app.movieapidevelopment.domain.repository;

import movie.app.movieapidevelopment.domain.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    boolean existsByTitleAndDirector(String title, String director);
}
