
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;
import domain.Trip;
import forms.SystemConfigurationForm;

@Service
@Transactional
public class SystemConfigurationService {

	//Managed Repository
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	@Autowired
	private TripService						tripService;


	//Constructor
	public SystemConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SystemConfiguration findSystemConfiguration() {
		SystemConfiguration result = null;
		result = this.systemConfigurationRepository.findAll().get(0);
		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.isTrue(systemConfiguration.getId() != 0);
		SystemConfiguration result;

		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.isTrue(systemConfiguration.getId() != 0);
		Assert.isTrue(this.systemConfigurationRepository.exists(systemConfiguration.getId()));

		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();

		return result;
	}

	public SystemConfigurationForm toForm(final SystemConfiguration systemConfiguration) {
		SystemConfigurationForm result;

		result = new SystemConfigurationForm();
		result.setBanner(systemConfiguration.getBanner());
		result.setCountryCode(systemConfiguration.getCountryCode());
		result.setFinderCacheTime(systemConfiguration.getFinderCacheTime());
		result.setFinderResultsNumber(systemConfiguration.getFinderResultsNumber());
		result.setVATTax(systemConfiguration.getVATTax());
		result.setWelcomeMessageEnglish(systemConfiguration.getWelcomeMessageEnglish());
		result.setWelcomeMessageSpanish(systemConfiguration.getWelcomeMessageSpanish());
		String spamWords = "";
		for (final String s : systemConfiguration.getSpamWords()) {
			spamWords += s;
			spamWords += ",";
		}
		result.setSpamWords(spamWords);
		String tags = "";
		for (final String s : systemConfiguration.getTags()) {
			tags += s;
			tags += ",";
		}
		result.setTags(tags);

		return result;
	}

	public void fromForm(final SystemConfigurationForm systemConfigurationForm) {
		SystemConfiguration systemConfiguration;
		systemConfiguration = this.findSystemConfiguration();

		systemConfiguration.setBanner(systemConfigurationForm.getBanner());
		systemConfiguration.setCountryCode(systemConfigurationForm.getCountryCode());
		systemConfiguration.setFinderCacheTime(systemConfigurationForm.getFinderCacheTime());
		systemConfiguration.setFinderResultsNumber(systemConfigurationForm.getFinderResultsNumber());
		systemConfiguration.setVATTax(systemConfigurationForm.getVATTax());
		systemConfiguration.setWelcomeMessageEnglish(systemConfigurationForm.getWelcomeMessageEnglish());
		systemConfiguration.setWelcomeMessageSpanish(systemConfigurationForm.getWelcomeMessageSpanish());

		Collection<String> spamWords;
		Collection<String> tags;
		Collection<String> deletedTags;
		Collection<String> tripTags;

		spamWords = new HashSet<String>();
		tags = new HashSet<String>();
		spamWords.addAll(Arrays.asList(systemConfigurationForm.getSpamWords().split("\\s*,\\s*")));
		tags.addAll(Arrays.asList(systemConfigurationForm.getTags().split("\\s*,\\s*")));
		systemConfiguration.setSpamWords(spamWords);

		deletedTags = new HashSet<String>(systemConfiguration.getTags());
		deletedTags.removeAll(tags);
		Collection<Trip> trips;
		trips = this.tripService.findAll();
		for (final Trip t : trips) {
			tripTags = t.getTag().keySet();
			for (final String tag : tripTags)
				if (deletedTags.contains(tag)) {
					tripTags.remove(tag);
					this.tripService.saveStage(t);
				}
		}
		systemConfiguration.setTags(tags);

		this.save(systemConfiguration);
	}

	@SuppressWarnings("deprecation")
	public String generateTicker() {
		String result, month, year, day, letters;
		SystemConfiguration systemConfiguration;
		Date today;
		LocalDate confDate, todayLocalDate;
		Integer tickerNumber;

		systemConfiguration = this.findSystemConfiguration();
		today = new Date();

		year = String.valueOf(today.getYear() - 100);
		if (today.getMonth() < 10)
			month = "0" + String.valueOf(today.getMonth() + 1);
		else
			month = String.valueOf(today.getMonth() + 1);
		if (today.getDate() < 10)
			day = "0" + String.valueOf(today.getDate());
		else
			day = String.valueOf(today.getDate());

		//Generate letters
		confDate = new LocalDate(systemConfiguration.getCurrentDay());
		todayLocalDate = new LocalDate();

		if (confDate.equals(todayLocalDate)) {
			tickerNumber = systemConfiguration.getNextTicker();
			letters = this.toAlphabeticRadix(tickerNumber);
			systemConfiguration.setNextTicker(tickerNumber + 1);
		} else {
			systemConfiguration.setCurrentDay(today);
			systemConfiguration.setNextTicker(1);
			letters = this.toAlphabeticRadix(0);
		}

		result = day + month + year + "-" + letters;
		this.save(systemConfiguration);
		return result;
	}

	private String toAlphabeticRadix(final int num) {
		String s;

		final char[] str = Integer.toString(num, 26).toCharArray();
		for (int i = 0; i < str.length; i++)
			str[i] += str[i] > '9' ? 10 : 49;

		s = String.valueOf(str);
		while (s.length() < 4)
			s = 'A' + s;
		s = s.toUpperCase();
		return s;
	}
}
