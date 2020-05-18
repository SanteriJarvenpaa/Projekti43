<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Kaiken listaus</title>
</head>
<body onkeydown="tutkiKey(event)">
	<span id="ilmo"></span>
	<table id="listaus">
		<thead>	
			<tr>
				<th colspan="7" class="oikealle"><a id="uusijaakaappi" href="lisaajaakaappi.jsp">Lis‰‰ uusi tuote</a></th>
			</tr>
			<tr>
				<th colspan="5" class="oikealle">Hakusana:</th>
				<th><input type="text" id="hakusana"></th>
				<th><input type="button" id="hae" value="Hae" onclick="haeTiedot()"></th>
			</tr>		
			<tr>
				<th>Elintarvike_id</th>
				<th>Nimi</th>
				<th>Avausp‰iv‰m‰‰r‰</th>
				<th>Valmistusp‰iv‰m‰‰r‰</th>	
				<th>Parasta ennen</th>
				<th>Kategoria</th>
				<th></th>			
			</tr>
		</thead>
		<tbody id="tbody">
		</tbody>
	</table>
<script>
	
haeTiedot();	
document.getElementById("hakusana").focus();//vied‰‰n kursori hakusana-kentt‰‰n sivun latauksen yhteydess‰

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		haeTiedot();
	}		
}
//Funktio tietojen hakemista varten
//GET   /jaakaapit/{hakusana}
function haeTiedot(){	
	document.getElementById("tbody").innerHTML = "";
	fetch("jaakaapit/*" + document.getElementById("hakusana").value,{
	      method: 'GET'	      
	    })
	.then( function (response) {
		return response.json()
	})
	.then( function (responseJson) {
		var jaakaapit = responseJson.jaakaapit;
		var htmlStr="";
		for(var i=0;i<jaakaapit.length;i++){			
        	htmlStr+="<tr>";
        	htmlStr+="<td>"+jaakaapit[i].id+"</td>";
        	htmlStr+="<td>"+jaakaapit[i].nimi+"</td>";
        	htmlStr+="<td>"+jaakaapit[i].avaus+"</td>";
        	htmlStr+="<td>"+jaakaapit[i].valmistus+"</td>"; 
        	htmlStr+="<td>"+jaakaapit[i].parastaennen+"</td>";  
        	htmlStr+="<td>"+jaakaapit[i].kategoria+"</td>";  
        	htmlStr+="<td><a href='muutajaakaappi.jsp?Elintarvike_id="+jaakaapit[i].id+"'>Muuta</a>&nbsp;";
        	htmlStr+="<span class='poista' onclick=poista("+jaakaapit[i].id+",'"+jaakaapit[i].nimi+"','"+jaakaapit[i].kategoria+"')>Poista</span></td>";
        	htmlStr+="</tr>";        	
		}
		document.getElementById("tbody").innerHTML = htmlStr;		
	})	
}

//Funktio tietojen poistamista varten. Kutsutaan backin DELETE-metodia ja v‰litet‰‰n poistettavan tiedon id. 
//DELETE /asiakkaat/id
function poista(id, nimi, kategoria){
	if(confirm("Poista tuote id " + id +", "+ nimi +"?")){	
		fetch("jaakaapit/" + id,{
		      method: 'DELETE'	      
		    })
		.then( function (response) {
			return response.json()
		})
		.then( function (responseJson) {
			var vastaus = responseJson.response;		
			if(vastaus==0){
				document.getElementById("ilmo").innerHTML= "Tuotteen poisto ep‰onnistui.";
	        }else if(vastaus==1){	        	
	        	alert("Tuotteen id " + id +", "+ nimi +" poisto onnistui.");
				haeTiedot();        	
			}	
		})		
	}	
}
</script>
</body>
</html>