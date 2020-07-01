package br.com.rodrigo.githubfiles.exception;

public class GithubException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GithubException(String message) {
    	super(message);
    }

    public GithubException(String message, Throwable t) {
    	super(message, t);
    }
}