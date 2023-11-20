package com.service;

import java.util.concurrent.Callable;

import com.component.InstrumentAverageStrategy;

public interface AverageInstrumentCalculator extends Callable<String> {
	public void setStrategy(InstrumentAverageStrategy strategy);
}