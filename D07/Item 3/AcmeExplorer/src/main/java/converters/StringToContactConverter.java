
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ContactRepository;
import domain.Contact;

@Component
@Transactional
public class StringToContactConverter implements Converter<String, Contact> {

	@Autowired
	private ContactRepository	contactRepository;


	@Override
	public Contact convert(final String text) {
		Contact result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.contactRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
