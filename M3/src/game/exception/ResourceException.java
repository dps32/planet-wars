package game.exception;

public class ResourceException extends Exception {
	public ResourceException() {}
	public ResourceException(String msg) {
		super(msg);
		System.out.println("---> Resource exception");
	}
}