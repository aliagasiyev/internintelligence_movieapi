package movie.app.movieapidevelopment.exceptions;

public class MovieAlreadyDeletedException extends RuntimeException {
    public MovieAlreadyDeletedException(String message) {
        super(message);
    }
}
