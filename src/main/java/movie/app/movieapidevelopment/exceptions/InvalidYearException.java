package movie.app.movieapidevelopment.exceptions;

public class InvalidYearException extends RuntimeException
{
    public InvalidYearException(String message) {
        super(message);
    }
}
