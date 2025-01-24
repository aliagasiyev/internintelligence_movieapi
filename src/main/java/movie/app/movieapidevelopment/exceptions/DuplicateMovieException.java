package movie.app.movieapidevelopment.exceptions;

public class DuplicateMovieException extends RuntimeException
{
    public DuplicateMovieException(String message) {
        super(message);
    }
}
