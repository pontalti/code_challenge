package com.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class InstrumentPriceModifierDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String 	name;
	private Double 	multiplier;
}