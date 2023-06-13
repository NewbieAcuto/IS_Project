package control;

public class MyException extends Exception {
	private String messaggio;
	
	public MyException() {
		super();
	}

	public MyException(String m) {
		messaggio=m;
	}
	
	public String toString() {return messaggio;}
}
