package com.component.strategy;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.component.InstrumentAverageStrategy;
import com.dto.InstrumentDTO;

@Component
@Scope("prototype")
@Qualifier("instrument")
public class Instrument implements InstrumentAverageStrategy {
	
	private static Logger LOG = LoggerFactory.getLogger(Instrument.class);
	
	private List<InstrumentDTO> records;
	private String instrument;
	
	public Instrument() {
		super();
	}
	
	@Override
	public Double process() {
		var comparator = Comparator.comparing(InstrumentDTO::getDate, Comparator.reverseOrder());
		var average = this.records.parallelStream()
										.filter(i->i.getName().equals(this.instrument))
										.sorted(comparator)
										.mapToDouble(InstrumentDTO::getValue)
										.limit(10)
										.sum();
		return average;
	}
	
	@Override
	public void setRecords(List<InstrumentDTO> records) {
		this.records = records;
	}

	@Override
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	@Override
	public String getInstrument() {
		return this.instrument;
	}

}
