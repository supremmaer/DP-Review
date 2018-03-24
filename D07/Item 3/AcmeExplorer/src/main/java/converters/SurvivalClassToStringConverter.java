
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SurvivalClass;

@Component
@Transactional
public class SurvivalClassToStringConverter implements Converter<SurvivalClass, String> {

	@Override
	public String convert(final SurvivalClass survivalclass) {
		String result;

		if (survivalclass == null)
			result = null;
		else
			result = String.valueOf(survivalclass.getId());

		return result;
	}

}
