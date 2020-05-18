<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Jaakaapin tietojen muutos</title>
</head>
<body onkeydown="tutkiKey(event)">
<form id="tiedot">
	<table class="table">
		<thead>
			<tr>
				<th colspan="5" class="oikealle"><a href="listaaKaikki.jsp" id="takaisin">Takaisin listaukseen</a></th>
			</tr>
			<tr>
				<th>Nimi</th>
				<th>Avauspaiva</th>
				<th>Valmistuspaiva</th>
				<th>Parasta ennen</th>
				<th>Kategoria</th>			
				<th>Hallinta</th>
			</tr>
		</thead>
			<tr>
				<td><input type="text" name="nimi" id="nimi"></td>
				<td><input type="text" name="avauspaiva" id="avauspaiva"></td>
				<td><input type="text" name="valmistuspaiva" id="valmistuspaiva"></td>
				<td><input type="text" name="parastaennen" id="parastaennen"></td>
				<td><input type="text" name="kategoria" id="kategoria"></td>					
				<td><input type="button" name="nappi" value="Tallenna" id="tallenna" onclick="vieTiedot()"></td>
			</tr>
		<tbody>
		</tbody>
	</table>
	<input type="hidden" name="elintarvike_id" id="elintarvike_id">
</form>
<span id="ilmo"></span>
<script>

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		vieTiedot();
	}		
}

document.getElementById("nimi").focus();//vied‰‰n kursori etunimi-kentt‰‰n sivun latauksen yhteydess‰

//Haetaan muutettavan asiakkaan tiedot. Kutsutaan backin GET-metodia ja v‰litet‰‰n kutsun mukana muutettavan tiedon id
//GET /asiakkaat/haeyksi/id
var asiakas_id = requestURLParam("elintarvike_id"); //Funktio lˆytyy scripts/main.js 
fetch("jaakaapit/haeyksi/" + elintarvike_id,{
      method: 'GET'	      
    })
.then( function (response) {
	return response.json()
})
.then( function (responseJson) {	
	document.getElementById("nimi").value = responseJson.nimi;	
	document.getElementById("avauspaiva").value = responseJson.avauspaiva;	
	document.getElementById("valmistuspaiva").value = responseJson.valmistuspaiva;	
	document.getElementById("parastaennen").value = responseJson.parastaennen;	
	document.getElementById("kategoria").value = responseJson.kategoria
	document.getElementById("elintarvike_id").value = responseJson.elintarvike_id;	
});	

//Funktio tietojen muuttamista varten. Kutsutaan backin PUT-metodia ja v‰litet‰‰n kutsun mukana muutetut tiedot json-stringin‰.
//PUT /asiakkaat/
function vieTiedot(){
	var nimi = document.getElementById("etunimi").value;
	var avauspaiva = document.getElementById("sukunimi").value;
	var valmistuspaiva = document.getElementById("puhelin").value;
	var parastaennen = document.getElementById("sposti").value;	
	var kategoria = document.getElementById("kategoria").value;
	
	var formJsonStr=formDataToJSON(document.getElementById("tiedot")); //muutetaan lomakkeen tiedot json-stringiksi
	//L‰het‰‰n muutetut tiedot backendiin
	fetch("jaakaapit",{
	      method: 'PUT',
	      body:formJsonStr
	    })
	.then( function (response) {
		return response.json();
	})
	.then( function (responseJson) {
		var vastaus = responseJson.response;		
		if(vastaus==0){
			document.getElementById("ilmo").innerHTML= "Tietojen p‰ivitys ep‰onnistui";
        }else if(vastaus==1){	        	
        	document.getElementById("ilmo").innerHTML= "Tietojen p‰ivitys onnistui";			      	
		}	
	});	
}
</script>
</body>
</html>