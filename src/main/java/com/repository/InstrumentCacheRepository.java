package com.repository;

import java.util.List;
import java.util.Optional;

import com.dto.InstrumentPriceModifierDTO;
import com.entity.InstrumentPriceModifier;
import com.record.InstrumentRecord;

public interface InstrumentCacheRepository {
	public Optional<InstrumentRecord> getInstrument(String instrument);
	public InstrumentPriceModifierDTO saveAndupdate(InstrumentPriceModifier instrument);
	public boolean delete(InstrumentPriceModifier instrument);
	public boolean saveAndUpdateAll(List<InstrumentPriceModifier> instrument);
}
