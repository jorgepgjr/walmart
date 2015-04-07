package br.com.walmart.to;

import java.util.List;

public class ShortestPath {
	private List<String[]> path;	
	private Double cost;
	private Double distance;
	
	public List<String[]> getPath() {
		return path;
	}
	
	public void setPath(List<String[]> path) {
		this.path = path;
	}
	
	public Double getCost() {
		return cost;
	}
	
	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
}
