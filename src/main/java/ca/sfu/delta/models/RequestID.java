package ca.sfu.delta.models;

import javax.persistence.*;


@Entity
@IdClass(RequestIDPrimaryKey.class)
@NamedQuery(name = "RequestID.getNextID",
		query = "select COALESCE(max(R.digits) + 1, 0001) from RequestID R where R.year = ?1")
public class RequestID {
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long entityIndex;

	@Id
	@Column(nullable = false)
	private Integer year;

	@Id
	@Column(nullable = false)
	private Integer digits;

	public RequestID() {

	}

	public Long getEntityIndex() {
		return entityIndex;
	}

	public void setEntityIndex(Long newEntityIndex) {
		entityIndex = newEntityIndex;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer newYear) {
		year = newYear;
	}

	public Integer getDigits() {
		return digits;
	}

	public void setDigits(Integer newDigits) {
		digits = newDigits;
	}
}
