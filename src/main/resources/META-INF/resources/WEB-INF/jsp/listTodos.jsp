<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<title>List ToDos Page</title>
<head>
</head>
<body>
	<div>Welcome ${name} .</div>
	<hr>
	<h3>Your Todos </h3>

	<table>
		<thead></thead>
		<tbody>
			<tr>
				<th>id</th>
				<th>Description</th>
				<th>Target Date</th>
				<th>is Done?</th>
			</tr>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.id}</td>
					<td>${todo.description}</td>
					<td>${todo.targetDate}</td>
					<td>${todo.done}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</form>
</html>
