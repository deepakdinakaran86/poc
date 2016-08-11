<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<jsp:include page="fragments/headTag.jsp"/>

<style>

		.error-headline {
    font: 32px/38px "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, sans-serif !important;
    text-align: center;
}

.error-description {
    color: #333333;
    font: 19px/24px "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, sans-serif !important;
    max-width: 600px;
    text-align: center;
    margin: 0 auto;
}
.error-description-small{ font: 15px/18px "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, sans-serif !important;}
</style>
<body>
<div class="container">   
    
<div class="row"><div class="col-md-12 text-center" style="margin:24px 0 0 0 ;">	<a href="${homePage}" class="dc_homeicon"></a> <jsp:include page="fragments/bodyHeader.jsp"/></div></div>


<div class="row">
		<div class="col-md-12">		
			<h3 class="error-headline">Hmm, somethings not right.</h3>		
			<p class="error-description">${exception.message}</p>
			<p class="error-description">404 error: The page you were looking for appears to have been moved, deleted or does not exist.</p>
		<hr/>
		<p class="error-description error-description-small">Please feel free to <a  href="${homePage}"> return to the home page</a> to find the information you were looking for. 
		We are very sorry for any inconvenience.</p>
		
		
		
		</div>




</div>

<div class="row"><div class="col-md-12">	<jsp:include page="fragments/footer.jsp"/></div></div>
    
    

</div>
</body>

</html>
