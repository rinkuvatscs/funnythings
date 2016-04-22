package com.interview.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysqlDb.CountryService;
import com.interview.validateException.ValidationException;
import com.interview.validator.Validation;

@RestController
public class Controller {

	@Autowired
	private CountryService countryService;

	@RequestMapping("/")
	public String test() {
		return "hello world";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getCountry(@PathVariable("id") int contryCode) throws ValidationException {
		String countryName = countryService.getCountry(contryCode);
		if (countryName == null) {
			String message = "country name does't exists" + " " + countryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(countryName, HttpStatus.OK);

	}

}
