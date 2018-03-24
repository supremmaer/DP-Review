
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.AuditRecordRepository;
import domain.AuditRecord;

@Component
@Transactional
public class StringToAuditRecordConverter implements Converter<String, AuditRecord> {

	@Autowired
	private AuditRecordRepository	auditrecordRepository;


	@Override
	public AuditRecord convert(final String text) {
		AuditRecord result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.auditrecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
