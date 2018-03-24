
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	private double				VATTax;
	private int					finderCacheTime;
	private int					finderResultsNumber;
	private Collection<String>	spamWords;
	private String				banner;
	private String				welcomeMessageEnglish;
	private String				welcomeMessageSpanish;
	private String				countryCode;
	private Collection<String>	tags;
	private Integer				nextTicker;
	private Date				currentDay;


	@Digits(integer = 3, fraction = 2)
	@Max(100)
	@Min(0)
	public double getVATTax() {
		return this.VATTax;
	}

	public void setVATTax(final double vATTax) {
		this.VATTax = vATTax;
	}

	@Range(min = 1, max = 24)
	public int getFinderCacheTime() {
		return this.finderCacheTime;
	}

	public void setFinderCacheTime(final int finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
	}

	@Range(min = 1, max = 100)
	public int getFinderResultsNumber() {
		return this.finderResultsNumber;
	}

	public void setFinderResultsNumber(final int finderResultsNumber) {
		this.finderResultsNumber = finderResultsNumber;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotNull
	public String getWelcomeMessageEnglish() {
		return this.welcomeMessageEnglish;
	}

	public void setWelcomeMessageEnglish(final String welcomeMessageEnglish) {
		this.welcomeMessageEnglish = welcomeMessageEnglish;
	}

	@NotNull
	public String getWelcomeMessageSpanish() {
		return this.welcomeMessageSpanish;
	}

	public void setWelcomeMessageSpanish(final String welcomeMessageSpanish) {
		this.welcomeMessageSpanish = welcomeMessageSpanish;
	}

	@NotNull
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotEmpty
	@ElementCollection(targetClass = String.class)
	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}

	@NotNull
	public Integer getNextTicker() {
		return this.nextTicker;
	}

	public void setNextTicker(final Integer nextTicker) {
		this.nextTicker = nextTicker;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCurrentDay() {
		return this.currentDay;
	}

	public void setCurrentDay(final Date currentDay) {
		this.currentDay = currentDay;
	}

}
