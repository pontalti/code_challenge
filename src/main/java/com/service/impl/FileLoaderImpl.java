package com.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dto.InstrumentDTO;
import com.service.FileLoader;
import com.service.InstrumentValueCalculator;
import com.util.DateConverter;

@Service
@Scope("prototype")
public class FileLoaderImpl implements FileLoader {
	
	private static final String DEFAULF_INPUT_TXT = "input.txt";

	private static Logger LOG = LoggerFactory.getLogger(FileLoaderImpl.class);
	
	private List<InstrumentDTO> 		instrumentRecords;
	private Set<String> 				instruments;
	private InstrumentValueCalculator 	calculator;

	public FileLoaderImpl(InstrumentValueCalculator calculator) {
		super();
		this.calculator = calculator;
		this.instrumentRecords = Collections.synchronizedList(new ArrayList<InstrumentDTO>());
		this.instruments = Collections.synchronizedSet(new HashSet<>());
	}
	
	@Override
	public void load(String filePath) {
		File file = null;
		if(filePath != null && !filePath.isEmpty() ) {
			file = new File(filePath);
			LOG.info("Reading file from '{}'", filePath);
		}else {
			var url = Thread.currentThread().getContextClassLoader().getResource(DEFAULF_INPUT_TXT);
			file = new File(url.getFile());
			LOG.info("Reading default file '{}'", DEFAULF_INPUT_TXT);
		}
		try(var lines = Files.lines(Path.of(file.getPath()))){
			var comparator = Comparator.comparing(InstrumentDTO::getName, Comparator.naturalOrder())
					.thenComparing(InstrumentDTO::getDate);
			this.instrumentRecords = 
					Collections.synchronizedList(
						lines.map(l -> mapStrLineToInstrumentRecord(l))
								.collect(Collectors.toList())
									.parallelStream()
										.filter(i-> !i.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
													!i.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) )
										.collect(Collectors.toList())
											.parallelStream()
												.map( i-> valueCalcCall(i))
												.sorted(comparator)
												.collect(Collectors.toList()));
			findInstrumentName();
		}catch(NoSuchFileException e){
			LOG.warn("File '{}' not found", filePath);
		} catch (IOException e) {
			LOG.error("Error executing {}", e);
		}
	}

	private InstrumentDTO valueCalcCall(final InstrumentDTO i) {
		i.setValue(this.calculator.calc(i));
		return i;
	}

	private InstrumentDTO mapStrLineToInstrumentRecord(final String line) {
		String[] parts = line.split(",");
		String name = parts[0];
		LocalDate date = DateConverter.strDateConverter(parts[1]);
		Double value = Double.valueOf(parts[2]);
		return InstrumentDTO.builder()
							.name(name)
							.date(date)
							.value(value)
							.build();
	}
	
	private void findInstrumentName() {
		this.instruments = Collections.synchronizedSet(
									this.instrumentRecords
										.parallelStream()
										.map(i->i.getName())
										.distinct()
										.collect(Collectors.toSet()));
	}
	
	@Override
	public List<InstrumentDTO> getInstrumentDTOs() {
		return instrumentRecords;
	}

	@Override
	public Set<String> getRecords() {
		return this.instruments;
	}
}