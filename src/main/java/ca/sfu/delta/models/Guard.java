package ca.sfu.delta.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Embeddable
public class Guard{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	private String guardID;
	private String location;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String name;
	private String telephone;
	private int regularHours;
	private int overtimeHours;
	private BigDecimal regularRate;
	private BigDecimal overtimeRate;

	public Guard() {

	}

	// Constructor and setters accept doubles instead of BigDecimals for ease of use
	// doubles shouldn't be used for currency due to inaccuracy
	public Guard(
			String name,
			int regularHours,
			double regularRate,
			int overtimeHours,
			double overtimeRate
	) throws IllegalArgumentException {
		setName(name);
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

	public String getGuardID() {
		return guardID;
	}

	public void setGuardID(String guardID) throws IllegalArgumentException {
		if (guardID != null) {
			this.guardID = guardID;
		} else {
			throw new IllegalArgumentException("ID must be non-null");
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

	@Override
	public String toString() {
		return "Guard{" +
				"guardID='" + guardID + "\'" +
				", location='" + location + "\'" +
				", startDate='" + startDate + "\'" +
				", endDate='" + endDate + "\'" +
				", startTime='" + startTime + "\'" +
				", endTime='" + endTime + "\'" +
				", name='" + name + "\'" +
				", telephone='" + telephone + "\'" +
				", regularHours='" + regularHours + "\'" +
				", overtimeHours='" + overtimeHours + "\'" +
				", regularRate='" + regularRate + "\'" +
				", overtimeRate='" + overtimeRate + "\'}";
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}