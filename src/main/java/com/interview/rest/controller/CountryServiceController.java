package com.interview.rest.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

import com.interview.mysqlDb.CountryServiceDAO;
import com.interview.pojo.Country;
import com.interview.validateException.ValidationException;

@RestController
public class CountryServiceController {

	@Autowired
	private CountryServiceDAO countryService;

	@RequestMapping("/")
	public String test() {
		return "hello world";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getCountryName(
			@PathVariable("id") int contryCode) throws ValidationException {
		String countryName = countryService.getCountryName(contryCode);
		if (StringUtils.isEmpty(countryName)) {
			String message = "country name does't exists" + " " + countryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(countryName, HttpStatus.OK);

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> addCountry(@PathVariable String contryName)
			throws ValidationException {
		String status = countryService.addCountry(contryName);
		if (StringUtils.isEmpty(status)) {
			String message = "country is not added" + " " + contryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(status, HttpStatus.OK);

	}

	@RequestMapping(value = "/getContrycode", method = RequestMethod.GET)
	public ResponseEntity<Integer> getContrycodeByName(
			@PathVariable String contryName) throws ValidationException {
		int countryName = countryService.getContrycodeByName(contryName);
		if (countryName == 0) {
			String message = "country name does't exists" + " " + countryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Integer>(countryName, HttpStatus.OK);

	}

	@RequestMapping(value = "/modifyCountry", method = RequestMethod.POST)
	public ResponseEntity<Country> modifyCountry(
			@PathVariable String oldContryName,
			@PathVariable String newContryName) throws ValidationException {
		Country country = countryService.modifyCountry(oldContryName,
				newContryName);
		if (StringUtils.isEmpty(country.getCountryName())) {
			String message = "country name does't exists" + " " + oldContryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Country>(country, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteCountry", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCountryByName(
			@PathVariable String contryName) throws ValidationException {
		String status = countryService.deleteCountryByName(contryName);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCountry", method = RequestMethod.GET)
	public ResponseEntity<HashMap<Integer, String>> getCountry() {
		HashMap<Integer, String> countryMap = countryService.getCountry();
		return new ResponseEntity<HashMap<Integer, String>>(countryMap,
				HttpStatus.OK);
	}

}
