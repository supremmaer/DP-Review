
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

	@Autowired
	private CreditCardRepository	creditcardRepository;


	@Override
	public CreditCard convert(final String text) {
		CreditCard result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.creditcardRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
