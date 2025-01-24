package movie.app.movieapidevelopment.exceptions;

public class EmptyDatabaseException extends RuntimeException
{
    public EmptyDatabaseException(String message) {
        super(message);
    }
}
