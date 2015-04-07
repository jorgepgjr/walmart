package br.com.walmart.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.walmart.domain.Location;
import br.com.walmart.repository.LocationRepository;
import br.com.walmart.service.LocationService;
import br.com.walmart.to.ShortestPath;

@Service
public class LocationServiceImpl implements LocationService {
	
	private static enum Types implements RelationshipType {
		ROUTE
	}
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private Neo4jTemplate template;
	
	@Autowired
	private GraphDatabaseService graphDb;
	
	@Transactional
	@SuppressWarnings("all")
	public boolean addRoute(String origin, String destination, double distance) {
		
		Location destinationLocation = locationRepository.findByPropertyValue("name", destination); 
		if (destinationLocation == null) {
			destinationLocation = new Location();
			destinationLocation.setName(destination);
			locationRepository.save(destinationLocation);
		}
		
		Location originLocation = locationRepository.findByPropertyValue("name", origin);
		if (originLocation == null) {
			originLocation = new Location();
			originLocation.setName(origin);
		}
		
		originLocation.routeTo(destinationLocation, distance);
		locationRepository.save(originLocation);
		
		return true;
	}

	@Transactional
	@SuppressWarnings("all")
	public ShortestPath getShortestPath(String origin, String destination, Double autonomy, Double valueLiter) {
		ShortestPath shortestPath = new ShortestPath();
		
		// Busca a origem e o destino
		Location originLocation = locationRepository.findByPropertyValue("name", origin);
		Location destinationLocation = locationRepository.findByPropertyValue("name", destination);
		
		// Verifica se existe a origem e o destino
		if (originLocation != null && destinationLocation != null) {
		
			Transaction tx = graphDb.beginTx();
			
			// Converte a entidade para Node
			Node nodeA = template.getNode(originLocation.getId());
			Node nodeB = template.getNode(destinationLocation.getId());
			
			// Busca o menor caminho usando o algoritmo de Dijkstra
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(PathExpanders.forTypeAndDirection(Types.ROUTE, Direction.BOTH), "distance");
			WeightedPath path = finder.findSinglePath(nodeA, nodeB);
			
			tx.success();
			
			// Monta o objeto de retorno
			shortestPath.setDistance(path.weight());
			shortestPath.setCost((path.weight() / autonomy) * valueLiter);
			
			List<String[]> relationshipList = new ArrayList<String[]>();
	
			Node origem = nodeA;
			Node destino = null;
			
			Iterable<Relationship> relationships = path.relationships();
			Iterator<Relationship> iterator = relationships.iterator();
			while(iterator.hasNext()) { 
				Relationship relationship = iterator.next();
				
				if (origem.equals(relationship.getEndNode())) {
					origem = relationship.getEndNode();
					destino = relationship.getStartNode();
				} else {
					origem = relationship.getStartNode();
					destino = relationship.getEndNode();			
				}
				
				relationshipList.add(new String[] {String.valueOf(origem.getProperty("name")), String.valueOf(destino.getProperty("name")), String.valueOf(relationship.getProperty("distance"))});
	
				if (origem.equals(relationship.getEndNode())) {
					origem = relationship.getStartNode();
				} else {
					origem = relationship.getEndNode();
				}
			}
			
			shortestPath.setPath(relationshipList);
		}
		
		return shortestPath;
	}
}
