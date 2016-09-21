<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<div class="container">
	<div class="row">
		<div class="form-main" class="col-md-8">
			<form class="form-signin" role="form" action="/user/register"
				method='POST'>
				<h2>Signup</h2>
				<div class="form-group">
					<input name="fullName" type="text" class="form-control"
						placeholder="Full name" required />
				</div>
				<div class="form-group">
					<input name="email" type="email" class="form-control"
						placeholder="Email" required />
				</div>
				<div class="form-group">
					<input name="password" type="password" class="form-control"
						placeholder="Password" required />
				</div>
				<button class="btn btn-info" type="submit">Register</button>
			</form>
		</div>
	</div>
</div>
