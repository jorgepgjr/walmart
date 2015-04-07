package br.com.walmart.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Location {

	@GraphId
	private Long id;
	
	@Indexed(indexName = "place",indexType = IndexType.FULLTEXT)
	private String name;
	
	@Fetch
	@RelatedToVia(type = "ROUTE", direction = Direction.BOTH)
	private Set<Route> routes = new HashSet<Route>();
	
	public Route routeTo(Location destination, Double distance) {
		Route route = new Route(this, destination, distance);
		this.routes.add(route);
		
		return route;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}
	
}
