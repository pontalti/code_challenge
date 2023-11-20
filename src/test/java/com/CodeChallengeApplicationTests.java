package com;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.component.InstrumentAverageStrategy;
import com.dto.InstrumentDTO;
import com.service.FileLoader;
import com.util.DateConverter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CodeChallengeApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
class CodeChallengeApplicationTests {

	@Autowired
	private ConfigurableApplicationContext context;

	@Test
	@DisplayName("Testing fileLoader with a null path file")
	void fileLoaderWithNullPathFile() {
		FileLoader fileLoader = this.context.getBean(FileLoader.class);
		fileLoader.load(null);
		assertThat(fileLoader.getInstrumentDTOs().size(), greaterThan(0));
		assertThat(fileLoader.getRecords().size(),  greaterThan(0));
	}

	@Test
	@DisplayName("Testing fileLoader with an invalid path file")
	void fileLoaderWithInvalidPathFile() {
		FileLoader fileLoader = this.context.getBean(FileLoader.class);
		fileLoader.load("/home/gustavo/sw/input.txt");
		assertThat(fileLoader.getInstrumentDTOs(), hasSize(0));
		assertThat(fileLoader.getRecords(),  hasSize(0));
	}

	@DisplayName("Testing instrument strategy")
	@ParameterizedTest(name = "Testing ''{0}'' for strategy ''{4}'' ")
	@CsvSource({"INSTRUMENT4,01-Jan-1996,1.00,10.00,instrument", 
				"INSTRUMENT1,12-Nov-2014,1.00,1.00,instrument_1",
				"INSTRUMENT2,12-Nov-2014,1.00,1.00,instrument_2",
				"INSTRUMENT3,12-Nov-2014,1.00,1.00,instrument_3" })
	@Tag("Important")
	void strategyTest(String instrument, String date, Double value, Double expected, String qualifier) {
		List<InstrumentDTO> dtos = new ArrayList<>();

		InstrumentAverageStrategy i = BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.context.getBeanFactory(),
																						InstrumentAverageStrategy.class, qualifier);
		IntStream.range(0, 50).forEach(r -> {
			InstrumentDTO dto = InstrumentDTO.builder()
												.name(instrument)
												.date(DateConverter.strDateConverter(date))
												.value(value).build();
			dtos.add(dto);
		});
		i.setInstrument(instrument);
		i.setRecords(dtos);
		Double result = i.process();
		assertEquals(expected, result);
	}

}
