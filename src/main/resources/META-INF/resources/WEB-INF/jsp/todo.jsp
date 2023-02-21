<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<title>Add ToDos Page</title>
<head>
<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3>Enter Todo details</h3>
		<form method="post">
			Description: <input type="text" name="description"> 
			<input type="Submit" class="btn btn-success">
		</form>
	</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	<script src="webjars/jquery/3.6.0/js/jquery.min.js"></script>
</body>
</html>
