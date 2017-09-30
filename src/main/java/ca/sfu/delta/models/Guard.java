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
	public Guard(String name, String id, int regularHours, double regularRate, int overtimeHours, double overtimeRate) {
		this.name = name;
		this.id = id;
		this.regularHours = regularHours;
		this.regularRate = BigDecimal.valueOf(regularRate);
		this.overtimeHours = overtimeHours;
		this.overtimeRate = BigDecimal.valueOf(overtimeRate);
	}

	public BigDecimal calculateTotalPay() {
		return (calculcateRegularPay().add(calculateOvertimePay()));
	}

	public BigDecimal calculcateRegularPay() {
		return (regularRate.multiply(new BigDecimal(regularHours)));
	}

	public BigDecimal calculateOvertimePay() {
		return (overtimeRate.multiply(new BigDecimal(overtimeHours)));
	}

	// Getters and setters -------------------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(int regularHours) {
		if (regularHours >= 0) {
			this.regularHours = regularHours;
		}
	}

	public int getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(int overtimeHours) {
		if (overtimeHours >= 0) {
			this.overtimeHours = overtimeHours;
		}
	}

	public BigDecimal getRegularRate() {
		return regularRate;
	}

	public void setRegularRate(double regularRate) {
		// allow only non-negative in setter
		BigDecimal newRate = BigDecimal.valueOf(regularRate);
		if (newRate.compareTo(BigDecimal.ZERO) >= 0) {
			this.regularRate = newRate;
		}
	}

	public BigDecimal getOvertimeRate() {
		return overtimeRate;
	}

	public void setOvertimeRate(double overtimeRate) {
		// allow only non-negative in setter
		BigDecimal newRate = BigDecimal.valueOf(overtimeRate);
		if (newRate.compareTo(BigDecimal.ZERO) >= 0) {
			this.overtimeRate = newRate;
		}
	}
}