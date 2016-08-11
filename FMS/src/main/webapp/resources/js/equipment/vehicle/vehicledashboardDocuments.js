function FnducmentGrid() {
	var ssData = new kendo.data.DataSource({
		data : [],
		pageSize : 10,
		page : 1,
		serverPaging : false,
		serverFiltering : false,
		serverSorting : false,
		sort : {
			field : "docName",
			dir : "desc"
		}
	});

	var grid = $("#documents-list").kendoGrid({
		autoBind : true,
		dataSource : ssData,
		pageable : {
			refresh : false,
			pageSizes : true,
			previousNext : true
		},
		height : 300,
		columns : [ {
			field : "docName",
			title : "Document Name"
		}, {
			field : "docHolderName",
			title : "Document holder name"
		}, {
			field : "docIssueDate",
			title : "Document Issued date"
		},
		{
			field : "docExpiryDate",
			title : "Date of expiry"
		},
		{
			field : "attachments",
			title : "Attachments"
		}]
	});
}
