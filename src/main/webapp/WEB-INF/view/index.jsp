<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Desafio Walmart</title>
</head>
<body>
	<form action="/location/get" method="post">
		Origem: <input type="text" name="origin" value="<c:out value="${param.origin}" />" /><br />
		Destino: <input type="text" name="destination" value="${param.destination}" /><br />
		Autonomia: <input type="text" name="autonomy" value="${param.autonomy}" /><br />
		Valor por litro: R$ <input type="text" name="valueLiter" value="${param.valueLiter}" />
		<hr >
		<input type="submit" value="Pesquisar" />
	</form>
	<br />
	<c:if test="${shortestPath ne null}">
		<c:forEach items="${shortestPath.path}" var="path">
			<strong><c:out value="${path[0]}" /></strong> para <strong><c:out value="${path[1]}" /></strong> - <c:out value="${path[2]}" /> KM <br />
		</c:forEach>
		<c:if test="${empty shortestPath.path}">
			Nenhuma rota foi encontrada.
		</c:if>
		<c:if test="${not empty shortestPath.path}">
			<br />Distancia total <c:out value="${shortestPath.distance}" />km<br />
			Custo: R$ <c:out value="${shortestPath.cost}" />
		</c:if>
		
	</c:if>
</body>
</html>