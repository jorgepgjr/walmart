# Desafio Walmart

Para o desenvolvimento desse projeto eu escolhi usar

1. Jetty - Jetty embeded para que o teste do programa seja mais fácil e rápido
2. Spring - Para injeção de depêndencia 
3. Spring MVC - Para apresentação e serviços
4. Spring Data - Para manipulação dos graphos
5. Neo4J - Para armazenamento dos dados em grapho

Para solucionar o problema do caminho mais curto no grafo, utilizei o algoritmo de Dijkstra junto e Neo4J

# Executado o programa

Clone o projeto no GitHub e execute o Jetty através do maven

mvn jetty:run

# Adicionando Rotas

Através do serviço disponibilizado o usuário poderá adicionar o ponto de origem, destino e a distância entre eles através do endpoint

POST localhost:8080/location/add

Informando os seguintes parametros

1. origin (String)
2. destination (String)
3. distance (Double)

Eu usei o RESTClient para os testes

# Visualizando menor rota
 
 Após a inclusão da malha, o usuário poderá consultar o caminho mais curto através da seguinte URL
 http://localhost:8080/location


