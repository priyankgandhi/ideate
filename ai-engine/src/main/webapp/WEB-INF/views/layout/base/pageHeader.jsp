<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize var="loggedIn" access="isAuthenticated()" />

<!-- HEADER BAR -->
<div class="navbar-header">
<a href="/" class="navbar-brand logo">barebones</a>
   <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
     <span class="icon-bar"></span>
     <span class="icon-bar"></span>
     <span class="icon-bar"></span>
   </button>
</div>
<div class="navbar-collapse collapse" id="navbar-main">

</div>

