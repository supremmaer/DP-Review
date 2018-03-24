
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ManagerRepository;
import domain.Manager;

@Component
@Transactional
public class StringToManagerConverter implements Converter<String, Manager> {

	@Autowired
	private ManagerRepository	managerRepository;


	@Override
	public Manager convert(final String text) {
		Manager result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.managerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
