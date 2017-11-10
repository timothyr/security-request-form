package ca.sfu.delta.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GuardTest {

	private String name = "Bob";
	private int regularHours = 6;
	private int overtimeHours = 3;
	private double regularRate = 17.5;
	private double overtimeRate = 22.75;

	private Guard guard = new Guard(name, regularHours, regularRate, overtimeHours, overtimeRate);

	@Test(expected = IllegalArgumentException.class)
	public void constructorException() throws Exception {
		Guard newGuard = new Guard(null, 0, 0.0, 0, 0.0);
	}

	@Test
	public void calculateTotalPay() throws Exception {
		assertEquals(BigDecimal.valueOf((regularHours * regularRate) + (overtimeHours * overtimeRate)),
				guard.calculateTotalPay());
	}

	@Test
	public void calculateRegularPay() throws Exception {
		assertEquals(BigDecimal.valueOf(regularHours * regularRate), guard.calculateRegularPay());
	}

	@Test
	public void calculateOvertimePay() throws Exception {
		assertEquals(BigDecimal.valueOf(overtimeHours * overtimeRate), guard.calculateOvertimePay());
	}

	@Test
	public void getName() throws Exception {
		assertEquals(name, guard.getName());
	}

	@Test
	public void setName() throws Exception {
		String newName = "Andy";
		guard.setName(newName);
		assertEquals(newName, guard.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNameEmptyException() throws Exception {
		guard.setName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNameNullException() throws Exception {
		guard.setName(null);
	}

//	@Test
//	public void getId() throws Exception {
//		assertEquals(id, guard.getId());
//	}
//
//	@Test
//	public void setId() throws Exception {
//		String newId = "789xyz";
//		guard.setId(newId);
//		assertEquals(newId, guard.getId());
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void setIdEmptyException() throws Exception {
//		guard.setId("");
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void setIdNullException() throws Exception {
//		guard.setId(null);
//	}

	@Test
	public void getRegularHours() throws Exception {
		assertEquals(regularHours, guard.getRegularHours());
	}

	@Test
	public void setRegularHours() throws Exception {
		int newRegularHours = 10;
		guard.setRegularHours(newRegularHours);
		assertEquals(newRegularHours, guard.getRegularHours());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setRegularHoursException() throws Exception {
		int newRegularHours = -10;
		guard.setRegularHours(newRegularHours);
	}

	@Test
	public void getOvertimeHours() throws Exception {
		assertEquals(overtimeHours, guard.getOvertimeHours());
	}

	@Test
	public void setOvertimeHours() throws Exception {
		int newOvertimeHours = 10;
		guard.setOvertimeHours(newOvertimeHours);
		assertEquals(newOvertimeHours, guard.getOvertimeHours());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOvertimeHoursException() throws Exception {
		int newOvertimeHours = -10;
		guard.setRegularHours(newOvertimeHours);
	}

	@Test
	public void getRegularRate() throws Exception {
		assertEquals(BigDecimal.valueOf(regularRate), guard.getRegularRate());
	}

	@Test
	public void setRegularRate() throws Exception {
		double newRegularRate = 25.25;
		guard.setRegularRate(newRegularRate);
		assertEquals(BigDecimal.valueOf(newRegularRate), guard.getRegularRate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setRegularRateException() throws Exception {
		double newRegularRate = -25.25;
		guard.setRegularRate(newRegularRate);
	}

	@Test
	public void getOvertimeRate() throws Exception {
		assertEquals(BigDecimal.valueOf(overtimeRate), guard.getOvertimeRate());
	}

	@Test
	public void setOvertimeRate() throws Exception {
		double newOvertimeRate = 25.66;
		guard.setOvertimeRate(newOvertimeRate);
		assertEquals(BigDecimal.valueOf(newOvertimeRate), guard.getOvertimeRate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOvertimeRateException() throws IllegalArgumentException {
		double newOvertimeRate = -25.66;
		guard.setOvertimeRate(newOvertimeRate);
	}

	@Test
	public void testZeroValues() throws Exception {
		guard.setRegularRate(0);
		assertEquals(BigDecimal.valueOf(0.0), guard.calculateRegularPay());

		guard.setOvertimeRate(0);
		assertEquals(BigDecimal.valueOf(0.0), guard.calculateOvertimePay());

		assertEquals(BigDecimal.valueOf(0.0), guard.calculateTotalPay());
	}

}