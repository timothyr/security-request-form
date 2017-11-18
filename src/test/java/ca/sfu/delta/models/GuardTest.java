package ca.sfu.delta.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GuardTest {

	private String name = "Bob";
	private BigDecimal regularHours = BigDecimal.valueOf(6.0);
	private BigDecimal overtimeHours = BigDecimal.valueOf(3.5);
	private BigDecimal regularRate = BigDecimal.valueOf(17.50);
	private BigDecimal overtimeRate = BigDecimal.valueOf(22.75);

	private Guard guard = new Guard(name, regularHours.doubleValue(), regularRate.doubleValue(),
			overtimeHours.doubleValue(), overtimeRate.doubleValue());

	private String correctCSVOutput = "Guard ID, Name, Location, Start Date, End Date, Start Time, End Time, Phone Number, Type, Regular Hours Worked, Overtime Hours Worked, Regular Pay Rate, Overtime Pay Rate, Total Amount Due\n" +
                                      "Not specified, Bob, Not specified, Not specified, Not specified, Not specified, Not specified, Not specified, Not specified, 6.0, 3.5, $17.5/hr, $22.75/hr, $184.625\n";

	@Test(expected = IllegalArgumentException.class)
	public void constructorException() throws Exception {
		Guard newGuard = new Guard(null, 0, 0.0, 0, 0.0);
	}

	@Test
	public void calculateTotalPay() throws Exception {
		assertEquals((regularHours.multiply(regularRate).add((overtimeHours.multiply(overtimeRate)))),
				guard.calculateTotalPay());
	}

	@Test
	public void calculateRegularPay() throws Exception {
		assertEquals(regularHours.multiply(regularRate), guard.calculateRegularPay());
	}

	@Test
	public void calculateOvertimePay() throws Exception {
		assertEquals(overtimeHours.multiply(overtimeRate), guard.calculateOvertimePay());
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

	@Test
	public void getRegularHours() throws Exception {
		assertEquals(regularHours, guard.getRegularHours());
	}

	@Test
	public void setRegularHours() throws Exception {
		double newRegularHours = 10;
		guard.setRegularHours(newRegularHours);
		assertEquals(BigDecimal.valueOf(newRegularHours), guard.getRegularHours());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setRegularHoursException() throws Exception {
		double newRegularHours = -10;
		guard.setRegularHours(newRegularHours);
	}

	@Test
	public void getOvertimeHours() throws Exception {
		assertEquals(overtimeHours, guard.getOvertimeHours());
	}

	@Test
	public void setOvertimeHours() throws Exception {
		double newOvertimeHours = 10;
		guard.setOvertimeHours(newOvertimeHours);
		assertEquals(BigDecimal.valueOf(newOvertimeHours), guard.getOvertimeHours());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOvertimeHoursException() throws Exception {
		double newOvertimeHours = -10;
		guard.setRegularHours(newOvertimeHours);
	}

	@Test
	public void getRegularRate() throws Exception {
		assertEquals(regularRate, guard.getRegularRate());
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
		assertEquals(overtimeRate, guard.getOvertimeRate());
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
	public void toStringTest() throws Exception {
		System.out.println(guard.toString());
		assert true;
	}

	@Test
    public void saveAsCSV() throws Exception {
        String testMe = guard.getAsCSV(true);
        assertEquals(correctCSVOutput, testMe);
    }

}