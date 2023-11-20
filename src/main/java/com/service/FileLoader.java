package com.service;

import java.util.List;
import java.util.Set;

import com.dto.InstrumentDTO;

public interface FileLoader {
	public void load(String filePath);
	public Set<String> getRecords();
	public List<InstrumentDTO> getInstrumentDTOs();
}
