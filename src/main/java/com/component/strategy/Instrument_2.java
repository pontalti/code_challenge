package com.component.strategy;

import java.time.Month;
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
@Qualifier("instrument_2")
public class Instrument_2 implements InstrumentAverageStrategy {

	private static Logger LOG = LoggerFactory.getLogger(Instrument_2.class);
			
	private List<InstrumentDTO> records;
	private String instrument;
	
	public Instrument_2() {
		super();
	}

	@Override
	public Double process() {
		Double average = this.records.parallelStream()
										.filter(i->i.getName().equals(this.instrument) && 
												i.getDate().getMonth().equals(Month.NOVEMBER) && 
												i.getDate().getYear() == 2014 )
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
