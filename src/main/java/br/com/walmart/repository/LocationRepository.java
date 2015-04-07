package br.com.walmart.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import br.com.walmart.domain.Location;


public interface LocationRepository extends GraphRepository<Location> {
	
}