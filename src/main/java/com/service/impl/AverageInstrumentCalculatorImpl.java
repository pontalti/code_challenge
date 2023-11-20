package com.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.component.InstrumentAverageStrategy;
import com.service.AverageInstrumentCalculator;

@Service
@Scope("prototype")
public class AverageInstrumentCalculatorImpl implements AverageInstrumentCalculator{

	private InstrumentAverageStrategy 	strategy;
	
	public AverageInstrumentCalculatorImpl() {
		super();
	}
	
	@Override
	public String call() throws Exception {
		Double value = this.strategy.process();
		return this.strategy.getInstrument().concat(" is ").concat(value.toString()).concat(" - Executed");
	}

	@Override
	public void setStrategy(InstrumentAverageStrategy strategy) {
		this.strategy = strategy;
	}
	
}
