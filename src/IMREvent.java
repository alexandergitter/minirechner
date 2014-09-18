public interface IMREvent
{
	public static final int PROGRESET = 1;
	public static final int ONE = 2;
	public static final int ZERO = 3;
	public static final int RESET = 4;
	public static final int TAKT = 5;
	public static final int A8 = 6;
	public static final int LOAD = 7;
	public static final int WRITE = 8;
	
	public void procEvent(int event);
}
