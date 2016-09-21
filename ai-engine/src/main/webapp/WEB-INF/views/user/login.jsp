<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<c:if test="${param.error == 'true'}">
	<div class="alert alert-danger"><strong>Sorry!</strong> Incorrect Username or Password</div>					
</c:if> 
    <div class="container">    	   
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Sign In</div>
                        <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
                    </div>     

                    <div style="padding-top:30px" class="panel-body" >

                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                            
                        <form id="loginform" class="form-horizontal" role="form" action="/j_spring_security_check" method='POST'>
                                    
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                        <input id="login-username" type="text" class="form-control" name="j_username" value="" placeholder="username or email">                                        
                                    </div>
                                
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                        <input id="login-password" type="password" class="form-control" name="j_password" placeholder="password">
                                    </div>
                                    


                                <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->

                                    <div class="col-sm-12 controls">
                                      <button id="btn-login" type="submit" class="btn btn-success">Login  </button>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                            Don't have an account! 
                                        <a href="/user/register">
                                            Sign Up Here
                                        </a> |
                                        <a href="/user/auth/forgotpassword">
                                            Forgot password?
                                        </a> 
                                        </div>
                                    </div>
                                </div>    
                            </form>    

                        </div>                     
                    </div>
                    
                    <div class="row">
                    	<div class="col-md-6">
        <form action="/signin/facebook" method="POST">
			<input type="hidden" name="scope" value="email,public_profile,user_friends" />
			<div class="formInfo">
				<p>Click the button to connect this application with your Facebook account.</p>
			</div>
			<p><input type="image" src="http://i.stack.imgur.com/ZW4QC.png" type="submit"></input></p>
		</form>
		</div>
		<div class="col-md-6">
		<form action="/signin/linkedin" method="POST">
			<!-- <input type="hidden" name="scope" value="email,public_profile,user_friends" /> -->
			<div class="formInfo">
				<p>Click the button to connect this application with your Linkedin account.</p>
			</div>
			<p><input type="image" src="https://snap.licdn.com/microsites/content/dam/developer/global/en_US/site/img/signin-button.png" type="submit"></input></p>
		</form>		
		</div>
        </div>  
        </div>
        
        
    </div>
    
 
 