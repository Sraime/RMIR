package project.server;

public class IncorrectDeclarationException extends Exception {
    String message;
    public IncorrectDeclarationException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
