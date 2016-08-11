/**
 * PCSEG339 - Kenny Chavez
 * Global variables
 */
  
 var currentGrid = "";
 var mapParameterEdit=""; 
 
 // test for subid token
 sessionStorage.sub_id = "1";
 
 function alphanumericValidator(e,elementName){
 	var re = /^[\w -][\w\s-]*/; 
     var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
     var compare = re.test(str);

     if (str !== null) {        
 		if (compare==true) {
 			var strVal = $('#'+elementName).val();			
 			if (strVal.charAt(0) == " " || strVal.charAt(0) == "-"){
 				$('#'+elementName).val("");				
 			}			     
 			return true;
         }
         e.preventDefault();
         return false;    	
     }
 } 
 