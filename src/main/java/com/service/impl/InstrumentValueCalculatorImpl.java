package com.service.impl;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dto.InstrumentDTO;
import com.record.InstrumentRecord;
import com.repository.InstrumentCacheRepository;
import com.service.InstrumentValueCalculator;

@Service
@Scope("prototype")
public class InstrumentValueCalculatorImpl implements InstrumentValueCalculator {

	private InstrumentCacheRepository repository;
	
	public InstrumentValueCalculatorImpl(InstrumentCacheRepository repository) {
		this.repository = repository;
	}

	@Override
	public Double calc(final InstrumentDTO dto) {
		Optional<InstrumentRecord> inOptional = this.repository.getInstrument(dto.getName());
		Double value = dto.getValue();
		if (inOptional.isPresent()) {
			value *= inOptional.get().multiplier();
		}else {
			value *= value;
		}
		return value;
	}

}
