package br.com.walmart.service;

import br.com.walmart.to.ShortestPath;

public interface LocationService {
	boolean addRoute(String origin, String destination, double distance);
	
	ShortestPath getShortestPath(String origin, String destination, Double autonomy, Double valueLiter);
}
