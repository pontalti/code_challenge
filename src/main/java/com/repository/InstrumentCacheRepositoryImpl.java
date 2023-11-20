package com.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.dto.InstrumentPriceModifierDTO;
import com.entity.InstrumentPriceModifier;
import com.record.InstrumentRecord;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class InstrumentCacheRepositoryImpl implements InstrumentCacheRepository{

	@PersistenceContext
    private EntityManager em;
	
	private InstrumentRepo repository;
	
	public InstrumentCacheRepositoryImpl(InstrumentRepo repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Cacheable(cacheNames = "instrumentList")
	@Scheduled(fixedRateString = "${caching.spring.instrumentTTL}", timeUnit = TimeUnit.SECONDS)
	public Optional<InstrumentRecord> getInstrument(String instrument){
		Optional<InstrumentRecord> optional;
		Query q = this.em.createNamedQuery("InstrumentPriceModifier.findAll");
		q.setParameter(1, instrument);
		try {
			optional = Optional.of((InstrumentRecord) q.getSingleResult());			
		} catch (NoResultException | NonUniqueResultException e) {
			optional = Optional.empty();
		}
		return optional;
	}

	@Override
	@Cacheable(cacheNames = "instrumentList")
	public InstrumentPriceModifierDTO saveAndupdate(InstrumentPriceModifier instrument) {
		InstrumentPriceModifier entity = this.repository.save(instrument);
		return InstrumentPriceModifierDTO.builder()
											.id(entity.getId())
											.name(entity.getName())
											.multiplier(entity.getMultiplier())
											.build();
	}
	
	@Override
	@Cacheable(cacheNames = "instrumentList")
	public boolean saveAndUpdateAll(List<InstrumentPriceModifier> instrument) {
		this.repository.saveAll(instrument);
		return true;
	}
	
	@Override
	@CacheEvict(cacheNames = "instrumentList")
	public boolean delete(InstrumentPriceModifier instrument) {
		this.repository.delete(instrument);
		return true;
	}
	
}
