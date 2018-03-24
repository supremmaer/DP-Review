
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Trip;

public class SurvivalClassForm {

	private int		survivalClassId;
	private String	title;
	private String	description;
	private Date	moment;
	private int		locationId;
	private String	name;
	private double	latitude;
	private double	longitude;
	private Trip	trip;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public int getSurvivalClassId() {
		return this.survivalClassId;
	}

	public void setSurvivalClassId(final int survivalClassId) {
		this.survivalClassId = survivalClassId;
	}

	public int getLocationId() {
		return this.locationId;
	}

	public void setLocationId(final int locationId) {
		this.locationId = locationId;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
