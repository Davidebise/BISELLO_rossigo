package eccezioni;

import java.sql.SQLException;

public class ErroreDatabaseException extends RossiGoException {
    public ErroreDatabaseException(String message) {
        super(message);
    }
}
