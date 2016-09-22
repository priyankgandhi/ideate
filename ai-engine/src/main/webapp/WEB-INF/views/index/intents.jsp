<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<div class="container">
<form action="/intents" method="POST">
  <div class="form-group">
    <label for="exampleInputEmail1">Intent</label>
    <input type="text" class="form-control" id="key" name="key" placeholder="Intent Name">
  </div>
  <div class="form-group">
	<textarea rows="5" cols="100%" name="value" id="value"></textarea>  
  </div>
  <button type="submit" class="btn btn-default">Submit</button>
</form>
<table class="table table-striped">
<c:forEach var="intent" items="${intents}">
<tr>
<td>
${intent.key}
</td>
<td>
${intent.value}
</td>
<td>
<form action="/delete" method="POST">
 <input type="hidden" name="intentId" value="${intent.id}"/>
  <button type="submit" class="btn btn-default">Delete</button>
</form>
</td>
</tr>
</c:forEach>
</table>
</div>
