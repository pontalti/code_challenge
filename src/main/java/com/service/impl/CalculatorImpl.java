package com.service.impl;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.component.InstrumentAverageStrategy;
import com.dto.InstrumentDTO;
import com.service.AverageInstrumentCalculator;
import com.service.Calculator;
import com.service.FileLoader;

@Service
public class CalculatorImpl implements Calculator{
	
	private static Logger LOG = LoggerFactory.getLogger(CalculatorImpl.class);
	
	private ConfigurableApplicationContext 		context;
	private FileLoader 							fileLoader;
	private ExecutorService 					executor;
	private List<AverageInstrumentCalculator> 	instrumentCalculators;
	private final CompletionService<String> 	completionService;
	
	public CalculatorImpl(ConfigurableApplicationContext context, FileLoader fileLoader, ExecutorService executor) {
		super();
		this.context 			= context;
		this.fileLoader 		= fileLoader;
		this.executor 			= executor;
		this.completionService 	= new ExecutorCompletionService<String>(this.executor);
		this.instrumentCalculators 	= Collections.synchronizedList(new ArrayList<>());
	}
	
	@Override
	public void fileLoader(String filePath) {
		LocalTime start = LocalTime.now();
		LOG.info("EXECUTING : CalculatorImpl.fileLoader() - Started at -> {}", start);
		this.fileLoader.load(filePath);
		LocalTime end = LocalTime.now();
		LOG.info("EXECUTED : CalculatorImpl.fileLoader() - Ended at -> {}, time of execution at -> {} @Millis \n", end, Duration.between(start, end).toMillis());
	}
	
	@Override
	public void instrumentCalculators() {
		LocalTime start = LocalTime.now();
		LOG.info("EXECUTING : CalculatorImpl.instrumentCalculators() - Started at -> {}", start);
		for (String instrument : this.fileLoader.getRecords()) {
			switch (instrument) {
				case "INSTRUMENT1" -> 
					buildInstrumentExecutor(instrument, "instrument_1", this.fileLoader.getInstrumentDTOs());
				case "INSTRUMENT2" -> 
					buildInstrumentExecutor(instrument, "instrument_2", this.fileLoader.getInstrumentDTOs());
				case "INSTRUMENT3" -> 
					buildInstrumentExecutor(instrument, "instrument_3", this.fileLoader.getInstrumentDTOs());
				default -> 
					buildInstrumentExecutor(instrument, "instrument", this.fileLoader.getInstrumentDTOs());
			}
		}
		instrumentCalculatorsExecutor();
		LocalTime end = LocalTime.now();
		LOG.info("EXECUTED : CalculatorImpl.instrumentCalculators() - Ended at -> {}, time of execution at -> {} @Millis \n", end, Duration.between(start, end).toMillis());
	}

	private void instrumentCalculatorsExecutor() {
		int received = 0;
		int numOfThreads = this.instrumentCalculators.size();
		try {
			this.instrumentCalculators.parallelStream().forEach(c->this.completionService.submit(c));
			while(received < numOfThreads) {
				Future<String> future = this.completionService.take();
				LOG.info("future calc for {}", future.get());
				received ++;
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		LOG.info("Nr of threads executeds  {}", received);
	}
	
	private void buildInstrumentExecutor(final String instrument, final String qualifiedInstrument, 
																			final List<InstrumentDTO> instrumentDTOs ) {
		AverageInstrumentCalculator calc = this.context.getBean(AverageInstrumentCalculator.class);
		InstrumentAverageStrategy i = BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.context.getBeanFactory(), 
																						InstrumentAverageStrategy.class, 
																						qualifiedInstrument);
		i.setRecords(instrumentDTOs);
		i.setInstrument(instrument);
		calc.setStrategy(i);
		this.instrumentCalculators.add(calc);
	}

}