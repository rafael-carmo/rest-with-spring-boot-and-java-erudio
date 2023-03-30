package br.com.erudio.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static br.com.erudio.converters.NumberConverter.*;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.math.SimpleMath;

@RestController
public class MathController {
	
	private final AtomicLong counter = new AtomicLong();
	private SimpleMath math = new SimpleMath();
	
	@RequestMapping(value = "/sun/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please a numeric value!");
		}
		return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double subtraction(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please a numeric value!");
		}
		
		return math.subtraction(convertToDouble(numberOne), convertToDouble(numberTwo));
		
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double multiplication(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please a numeric value!");
		}
		
		return math.multiplication(convertToDouble(numberOne), convertToDouble(numberTwo));
		
	}
	
	@RequestMapping(value = "/division/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double division(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please a numeric value!");
		}
		
		if(convertToDouble(numberTwo).equals(0d))
			throw new UnsupportedMathOperationException("Não é possível dividir um numero por zero!");
		
		return math.division(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/mean/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double mean(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please a numeric value!");
		}
		
		return math.mean(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/squareRoot/{number}",
			method = RequestMethod.GET)
	public Double squareRoot(
			@PathVariable(value = "number") String number
			) throws Exception {
		
		if(!isNumeric(number)) {
			throw new UnsupportedMathOperationException("Please a numeric value!");
		}
		
		return math.squareRoot(convertToDouble(number));
		
	}
}
