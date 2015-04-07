package br.com.walmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.walmart.service.LocationService;
import br.com.walmart.to.ShortestPath;

@Controller
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@RequestMapping("/location")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/location/get")
	public String getRoute(Model model, @RequestParam String origin, @RequestParam String destination, 
							@RequestParam Double autonomy, @RequestParam Double valueLiter) {
		
		ShortestPath shortestPath = locationService.getShortestPath(origin, destination, autonomy, valueLiter);
		model.addAttribute("shortestPath", shortestPath);		
		
		return "index";
	}
}
