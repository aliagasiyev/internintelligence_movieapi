package movie.app.movieapidevelopment.service;

import movie.app.movieapidevelopment.dto.request.MovieRequest;
import movie.app.movieapidevelopment.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id);

    MovieResponse addMovie(MovieRequest dto);

    MovieResponse updateMovie(Long id, MovieRequest dto);

    void deleteMovie(Long id);
}
