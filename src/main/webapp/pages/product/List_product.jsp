<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Product</title>
</head>

<body>
<table>
<thead>
<th>id</th>
<th>name</th>
<th>price</th>
<th>amount</th>
<th>sum</th>
</tr>
</thead>
<tbody>
<form action ="/readAllProduct" method= "GET">
<c:forEach var = "product" items = "${products}">
<tr>
<td>
${product.id}
</td>

<td>
${product.product.name}
</td>

<td>
${product.price}
</td>

<td>
${product.amount}
</td>

<td>
${product.sum}
</td>

</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>