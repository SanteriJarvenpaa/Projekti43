<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body onkeydown="tutkiKey(event)">
<form id="tiedot">
<table>
	<thead>
		<tr>
			<th colspan="6" class="oikealle"><a href="listaaKaikki.jsp" id="takaisin">Takaisin listaukseen</a></th>
		</tr>
		<tr>
			<th>Nimi</th>
			<th>Avauspaiva</th>
			<th>Valmistuspaiva</th>
			<th>Parasta ennen</th>
			<th>Kategoria</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="text" name="nimi" id="nimi"></td>
			<td><input type="text" name="avauspaiva" id="avauspaiva"></td>
			<td><input type="text" name="valmistuspaiva" id="valmistuspaiva"></td>
			<td><input type="text" name="parasta_ennen" id="parasta_ennen"></td> 
			<td><input type="text" name="kategoria" id="kategoria"></td>
			<td><input type="button" name="nappi" id="tallenna" value="Lis‰‰" onclick="lisaaTiedot()"></td>
		</tr>
	</tbody>
</table>
</form>
<span id="ilmo"></span>
<script>

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		lisaaTiedot();
	}		
}
document.getElementById("nimi").focus();//vied‰‰n kursori etunimi-kentt‰‰n sivun latauksen yhteydess‰

//funktio tietojen lis‰‰mist‰ varten. Kutsutaan backin POST-metodia ja v‰litet‰‰n kutsun mukana uudet tiedot json-stringin‰.
//POST /asiakkaat/
function lisaaTiedot(){	
	var nimi = document.getElementById("nimi").value;
	var avauspaiva = document.getElementById("avauspaiva").value;
	var valmistuspaiva = document.getElementById("valmistuspaiva").value;
	var parasta_ennen = document.getElementById("parasta_ennen").value;	
	var kategoria = document.getElementById("kategoria").value;
	
	var formJsonStr=formDataToJSON(document.getElementById("tiedot")); //muutetaan lomakkeen tiedot json-stringiksi
	//L‰het‰‰n uudet tiedot backendiin
	fetch("jaakaapit",{
	      method: 'POST',
	      body:formJsonStr
	    })
	.then( function (response) {
		return response.json()
	})
	.then( function (responseJson) {
		var vastaus = responseJson.response;		
		if(vastaus==0){
			document.getElementById("ilmo").innerHTML= "Tietojen lis‰‰minen ep‰onnistui";
        }else if(vastaus==1){	        	
        	document.getElementById("ilmo").innerHTML= "Tietojen lis‰‰minen onnistui";			      	
		}	
	});		
}
</script>
</body>
</html>