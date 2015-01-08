package at.fhhagenberg.sqe.project.connection;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by rknoll on 07/01/15.
 */
public class ElevatorConnectionLostExceptionTest {
    // TODO: finish ElevatorConnectionLostException tests
	
	//private ElevatorConnectionLostException mExeption;
	private Exception mExeption;
	
	@Test
	public void testElevatorConnectionLostException()
	{
		mExeption = new ElevatorConnectionLostException();
		
		assertEquals(null, mExeption.getMessage());
		assertEquals(null, mExeption.getCause());
	}
	
	@Test
	public void testElevatorConnectionLostExceptionMessage()
	{
		mExeption = new ElevatorConnectionLostException("My Error Message");
		
		assertEquals("My Error Message", mExeption.getMessage());
		assertEquals(null, mExeption.getCause());
	}
	
	@Test
	public void testElevatorConnectionLostExceptionMessageCause()
	{
		Throwable cause = new Throwable("throwed for test reason");
		mExeption = new ElevatorConnectionLostException("My Error Message", cause);
		
		assertEquals("My Error Message", mExeption.getMessage());
		assertEquals(cause, mExeption.getCause());
		assertEquals("throwed for test reason", mExeption.getCause().getMessage());
	}
}
