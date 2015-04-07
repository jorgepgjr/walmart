package br.com.walmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.walmart.service.LocationService;

@RestController
public class LocationRestController {
	
	@Autowired
	private LocationService locationService;
	
	@RequestMapping(value = "/location/add", method = RequestMethod.POST, produces = {"application/json"})
	public void addRoute(@RequestParam String origin, @RequestParam String destination, @RequestParam double distance) {
		locationService.addRoute(origin, destination, distance);
	}
	
}
