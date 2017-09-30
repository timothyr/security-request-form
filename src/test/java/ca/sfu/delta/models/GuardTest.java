package ca.sfu.delta.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GuardTest {

	private String name = "Bob";
	private String id = "abc123";
	private int regularHours = 6;
	private int overtimeHours = 3;
	private double regularRate = 17.5;
	private double overtimeRate = 22.75;

	private Guard guard = new Guard(name, id, regularHours, regularRate, overtimeHours, overtimeRate);

	@Test
	public void calculateTotalPay() throws Exception {
		assertEquals(BigDecimal.valueOf((regularHours * regularRate) + (overtimeHours * overtimeRate)),
				guard.calculateTotalPay());

		System.out.println(guard.calculcateRegularPay() + "+" +
				guard.calculateOvertimePay() + "=" + guard.calculateTotalPay());
	}

	@Test
	public void calculcateRegularPay() throws Exception {
		assertEquals(BigDecimal.valueOf(regularHours * regularRate), guard.calculcateRegularPay());
		System.out.println(guard.getRegularHours() + "*" + guard.getRegularRate() + "=" + guard.calculcateRegularPay());
	}

	@Test
	public void calculateOvertimePay() throws Exception {
		assertEquals(BigDecimal.valueOf(overtimeHours * overtimeRate), guard.calculateOvertimePay());
		System.out.println(guard.getOvertimeHours() + "*" + guard.getOvertimeRate() + "=" + guard.calculateOvertimePay());
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

	@Test
	public void getId() throws Exception {
		assertEquals(id, guard.getId());
	}

	@Test
	public void setId() throws Exception {
		String newId = "789xyz";
		guard.setId(newId);
		assertEquals(newId, guard.getId());
	}

	@Test
	public void getRegularHours() throws Exception {
		assertEquals(regularHours, guard.getRegularHours());
	}

	@Test
	public void setRegularHours() throws Exception {
		int newRegularHours = 10;
		guard.setRegularHours(newRegularHours);
		assertEquals(newRegularHours, guard.getRegularHours());

		newRegularHours = -10;
		guard.setRegularHours(newRegularHours);
		assertNotEquals(newRegularHours, guard.getRegularHours());
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

		newOvertimeHours = -10;
		guard.setOvertimeHours(newOvertimeHours);
		assertNotEquals(newOvertimeHours, guard.getOvertimeHours());
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

		newRegularRate = -10.5;
		guard.setRegularRate(newRegularRate);
		assertNotEquals(BigDecimal.valueOf(newRegularRate), guard.getRegularRate());
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

		newOvertimeRate = -15;
		guard.setOvertimeRate(newOvertimeRate);
		assertNotEquals(BigDecimal.valueOf(newOvertimeRate), guard.getOvertimeRate());
	}

}