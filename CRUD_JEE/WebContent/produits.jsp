<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<%@include file="header.jsp" %>
		<div class="container">
			<div class="card">
				<div class="card-header">
					Recherche des Produits
				</div>
				<div class="card-body">
					<form action="chercher.do" method="get">
						<label>Mot Clé</label>
						<input type="text" name="motCle" value="${model.motCle}" />
						<button type="submit" class="btn btn-primary">Chercher</button>
					</form>
					<table class="table table-striped">
						<tr>
							<th>ID</th>
							<th>Nom Produit</th>
							<th>Prix</th>
							<th>Supprimer</th>
							<th>Modifier</th>
						</tr>
						<c:forEach items="${model.produits}" var="p">
							<tr>
								<td>${p.idProduit}</td>
								<td>${p.nomProduit}</td>
								<td>${p.prix}</td>
								<td><a onclick="return confirm('Etes-vous sûr ?')" href="supprimer.do?id=${p.idProduit}">Supprimer</a></td>
 								<td><a href="editer.do?id=${p.idProduit}">Edit</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>