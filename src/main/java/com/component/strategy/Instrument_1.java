package com.component.strategy;

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
@Qualifier("instrument_1")
public class Instrument_1 implements InstrumentAverageStrategy {
	
	private static Logger LOG = LoggerFactory.getLogger(Instrument_1.class);
	
	private List<InstrumentDTO> records;
	private String instrument;
	
	public Instrument_1() {
		super();
	}

	@Override
	public Double process() {
		Double average = this.records.parallelStream()
										.filter(i->i.getName().equals(this.instrument))
										.mapToDouble(InstrumentDTO::getValue)
										.average()
										.orElse(Double.NaN);
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
