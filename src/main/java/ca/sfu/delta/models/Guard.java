package ca.sfu.delta.models;

import java.math.BigDecimal;

public class Guard{

	private String name;
	private String id;	// placeholder, don't know what this would look like yet
	private int regularHours;
	private int overtimeHours;
	private BigDecimal regularRate;
	private BigDecimal overtimeRate;

	public Guard() {

	}

	// Constructor and setters accept doubles instead of BigDecimals for ease of use
	// doubles shouldn't be used for currency due to inaccuracy
	public Guard(String name, String id, int regularHours, double regularRate, int overtimeHours, double overtimeRate)
			throws IllegalArgumentException {
		setName(name);
		setId(id);
		setRegularHours(regularHours);
		setRegularRate(regularRate);
		setOvertimeHours(overtimeHours);
		setOvertimeRate(overtimeRate);
	}

	public BigDecimal calculateTotalPay() {
		return (calculateRegularPay().add(calculateOvertimePay()));
	}

	public BigDecimal calculateRegularPay() {
		return (regularRate.multiply(new BigDecimal(regularHours)));
	}

	public BigDecimal calculateOvertimePay() {
		return (overtimeRate.multiply(new BigDecimal(overtimeHours)));
	}

	// Getters and setters -------------------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String must be non-null & non-empty");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) throws IllegalArgumentException {
		if (id != null && !id.isEmpty()) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("String must be non-null & non-empty");
		}
	}

	public int getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(int regularHours) throws IllegalArgumentException {
		if (regularHours >= 0) {
			this.regularHours = regularHours;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	public int getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(int overtimeHours) throws IllegalArgumentException {
		if (overtimeHours >= 0) {
			this.overtimeHours = overtimeHours;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	public BigDecimal getRegularRate() {
		return regularRate;
	}

	public void setRegularRate(double regularRate) throws IllegalArgumentException {
		BigDecimal newRate = BigDecimal.valueOf(regularRate);
		if (newRate.compareTo(BigDecimal.ZERO) >= 0) {
			this.regularRate = newRate;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	public BigDecimal getOvertimeRate() {
		return overtimeRate;
	}

	public void setOvertimeRate(double overtimeRate) throws IllegalArgumentException {
		BigDecimal newRate = BigDecimal.valueOf(overtimeRate);
		if (newRate.compareTo(BigDecimal.ZERO) >= 0) {
			this.overtimeRate = newRate;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}
}