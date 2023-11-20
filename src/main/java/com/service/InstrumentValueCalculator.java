package com.service;

import com.dto.InstrumentDTO;

public interface InstrumentValueCalculator{
	public Double calc(final InstrumentDTO dto);
}