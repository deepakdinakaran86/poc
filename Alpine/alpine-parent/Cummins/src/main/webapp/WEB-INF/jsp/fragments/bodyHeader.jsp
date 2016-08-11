<div class="app-bar" data-role="appbar" style="width: 100%; background-color: rgb(51, 51, 51)">
<div style="visibility: hidden;overflow: hidden;height: 1px"><form id="home" role="form" action="home" name="home" method="get"></form></div>
	<a class="app-bar-element branding" onclick="$('#home').submit()">Cummins</a>
	<a class="app-bar-element place-right branding" onclick="document.forms[0].method='POST';document.forms[0].action='<%=request.getContextPath()%>/logout';$('#home').submit()">Sign Out</a>
</div>