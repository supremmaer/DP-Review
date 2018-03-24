
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Component
@Transactional
public class StringToEducationRecordConverter implements Converter<String, EducationRecord> {

	@Autowired
	private EducationRecordRepository	educationrecordRepository;


	@Override
	public EducationRecord convert(final String text) {
		EducationRecord result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.educationrecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
