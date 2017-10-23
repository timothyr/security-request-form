package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestIDTest {
	// Create two RequestIDs to test the auto generating index
	private RequestID requestID;

	private Long entityIndexOrig = 0L;
	private Long entityIndexNew = 1L;

	private Integer yearOrig = 15;
	private Integer yearNew = 17;

	private Integer digitsOrig = 1;
	private Integer digitsNew = 2;

	@Before
	public void setUp() {
		requestID = new RequestID();
		requestID.setEntityIndex(entityIndexOrig);
		requestID.setYear(yearOrig);
		requestID.setDigits(digitsOrig);
	}

	@Test
	public void testEntityIndexField() {
		assertEquals(entityIndexOrig, requestID.getEntityIndex());
		requestID.setEntityIndex(entityIndexNew);
		assertEquals(entityIndexNew, requestID.getEntityIndex());
	}

	@Test
	public void testYearField() {
		assertEquals(yearOrig, requestID.getYear());
		requestID.setYear(yearNew);
		assertEquals(yearNew, requestID.getYear());
	}

	@Test
	public void testDigitsField() {
		assertEquals(digitsOrig, requestID.getDigits());
		requestID.setDigits(digitsNew);
		assertEquals(digitsNew, requestID.getDigits());
	}
}
