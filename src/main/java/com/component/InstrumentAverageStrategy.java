package com.component;

import java.util.List;

import com.dto.InstrumentDTO;

public interface InstrumentAverageStrategy{
	public void setRecords(final List<InstrumentDTO> records);
	public void setInstrument(String instrument);
	public String getInstrument();
	public Double process();
}