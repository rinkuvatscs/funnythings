package com.interview.rest.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysqlDb.CountryService;
import com.interview.pojo.Country;
import com.interview.validateException.ValidationException;

@RestController
@RequestMapping(value = "/api/interviewservice/countryservice/")
public class CountryServiceController {

	@Autowired
	private CountryService countryService;

	@RequestMapping("/")
	public String test() {
		return "Country-Service-Contorller";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getCountryNameByCountryCode(
			@PathVariable("id") int countryCode) throws ValidationException {
		String countryName = countryService.getCountryNameByCountryCode(countryCode);
		if (StringUtils.isEmpty(countryName)) {
			String message = "country name does't exists" + " " + countryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(countryName, HttpStatus.OK);

	}

	@RequestMapping(value = "/addCountry", method = RequestMethod.POST)
	public ResponseEntity<String> addCountry(@PathVariable String contryName)
			throws ValidationException {
		String status = countryService.addCountry(contryName);
		if (StringUtils.isEmpty(status)) {
			String message = "country is not added" + " " + contryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(status, HttpStatus.OK);

	}

	@RequestMapping(value = "/getCountryCodeByCountryName", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCountryCodeByCountryName(
			@PathVariable String countryName) throws ValidationException {
		int countryCode = countryService.getCountryCodeByCountryName(countryName);
		if (countryCode == 0) {
			String message = "country name does't exists" + " " + countryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Integer>(countryCode, HttpStatus.OK);

	}

	@RequestMapping(value = "/modifyCountry", method = RequestMethod.POST)
	public ResponseEntity<Country> modifyCountry(
			@PathVariable String oldCountryName,
			@PathVariable String newCountryName) throws ValidationException {
		Country country = countryService.modifyCountry(oldCountryName,
				newCountryName);
		if (org.springframework.util.StringUtils.isEmpty(country.getCountryName())) {
			String message = "country name does't exists" + " " + oldCountryName;
			throw new ValidationException(message, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Country>(country, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteCountryByCountryName", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCountryByName(
			@PathVariable String countryName) throws ValidationException {
		String status = countryService.deleteCountryByCountryName(countryName);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCountries", method = RequestMethod.GET)
	public ResponseEntity<HashMap<Integer, Country>> getCountry() {
		HashMap<Integer, Country> countryMap = countryService.getCountry();
		return new ResponseEntity<HashMap<Integer, Country>>(countryMap,
				HttpStatus.OK);
	}

}
