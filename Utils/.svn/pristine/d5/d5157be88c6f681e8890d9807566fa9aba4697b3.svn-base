<div class="app-bar" data-role="appbar" style="width: 100%; background-color: rgb(51, 51, 51)">
<div style="visibility: hidden;overflow: hidden;height: 1px"><form id="home" role="form" action="home" name="home" method="get"></form></div>
	<a class="app-bar-element branding" onclick="$('#home').submit()">Template Builder</a>
	<ul id="profileBar" class="place-right" style="width:180px;background-color: rgb(51, 51, 51)" >
		<li style="position:absolute;z-index:2000;width:180px;"><a style="background-color: rgb(51, 51, 51)">Welcome User</a>
			<ul >
				<li style="background-color: rgb(51, 51, 51)"><a href="">About</a></li>
				<li style="background-color: rgb(51, 51, 51)"><a href="">Contacts</a></li>
				<li style="background-color: rgb(51, 51, 51)"><a
					onclick="document.forms[0].method='POST';document.forms[0].action='<%=request.getContextPath()%>/logout';$('#home').submit()">Sign
						Out</a></li>
			</ul>
		</li>
	</ul>
	<script>
		$("#profileBar").kendoPanelBar({
			animation : {
				// fade-out closing items over 1000 milliseconds
				collapse : {
					duration : 100,
					effects : "fadeOut"
				},
				// fade-in and expand opening items over 500 milliseconds
				expand : {
					duration : 100,
					effects : "expandVertical fadeIn"
				}
			}
		});
	</script>
</div>

