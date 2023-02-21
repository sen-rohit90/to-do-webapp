<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<title>List ToDos Page</title>
<head>
<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3>Your Todos</h3>

		<table class="table">
			<thead></thead>
			<tbody>
				<tr>
					<th>Description</th>
					<th>Target Date</th>
					<th>is Done?</th>
				</tr>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.description}</td>
						<td>${todo.targetDate}</td>
						<td>${todo.done}</td>
						<td> <a href="delete-todo?id=${todo.id}" class="btn btn-warning">DELETE</a> </td>
						<td> <a href="update-todo?id=${todo.id}" class="btn btn-success">EDIT</a> </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a href="add-todo" class="btn btn-success">Add Todo</a>
	</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>
