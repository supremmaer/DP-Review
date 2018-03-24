
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AuditRecord;

@Component
@Transactional
public class AuditRecordToStringConverter implements Converter<AuditRecord, String> {

	@Override
	public String convert(final AuditRecord auditrecord) {
		String result;

		if (auditrecord == null)
			result = null;
		else
			result = String.valueOf(auditrecord.getId());

		return result;
	}

}
