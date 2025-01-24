package movie.app.movieapidevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String director;
    private Integer releaseYear;
    private String genre;
    private Float imdbRating;

    public MovieResponse() {
    }
}
