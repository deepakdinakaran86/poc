<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function login() {
		$("#loginForm").submit();

	}
</script>

<div>

	<!-- Panel for Login Form -->
	<div class="panel panel-default">
		<div class="panel-body" style="width:350px;margin-left: auto;margin-right: auto; height:500px;margin-top:250px">

			<form id="loginForm" role="form" action="authenticate" name="form"
				method="post">

				<div class="input-group input-group-md"  style="margin-top:10px;">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-user"></span></span> <input
						class="form-control" type="text" name="userName" value="adminuser@cummins.alpine.com"
						placeholder="Username" required />
				</div>

				<div class="input-group input-group-md"  style="margin-top:10px;">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-lock"></span></span> <input
						class="form-control" type="password" name="password" value="password"
						id="inputPassword" placeholder="Password" required />
				</div>
				<c:if test="${not empty error}">
					<div style="color:red;">Error: ${error.getErrorMessage()}</div>
				</c:if>
				
				<div align="right" style="margin-top:10px;">
					<button class="btn  btn-primary " onclick="login()">
						Login <span class="glyphicon glyphicon-log-in"></span>
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
