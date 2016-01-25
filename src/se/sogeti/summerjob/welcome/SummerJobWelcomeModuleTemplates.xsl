<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:template match="Document">
		<h1>Sundsvalls kommuns sommarjobbsportal</h1>
		<p class="lead">Här kan du som är skriven i Sundsvalls kommun och går på en gymnasieskola söka sommarjobb.</p>
		
		<div class="row">
			<div class="col-md-6">
				<h3>Sök sommarjobb inom kommunens olika verksamheter</h3>
				<a href="{AddMunicipalityApplicationUrl}">Skicka in din ansökan</a>
			</div>
			
			<div class="col-md-6">
				<h3>Sundsvalls kommun förmedlar även sommarjobb inom näringslivet. </h3>	
				<a href="{AddBusinessApplicationUrl}" class="business-jobs-link">Sök jobb i näringslivet</a> 
			</div>
		</div>
		
		<div class="row divider">
			<div class="col-md-6">
				<h3>Du som arbetsgivare kan själv lägga in sökbara sommarjobb</h3>
				<a href="{/Document/requestinfo/contextpath}/add-municipality-job">Lägg till ett jobb</a>
			</div>
			<div class="col-md-6">
				<h3>Här kan du som extern arbetsgivare lägga in ett jobb(privat, landsting, myndighet)</h3>
				<a href="{/Document/requestinfo/contextpath}/add-business-sector-job">Lägg till ett jobb</a>
			</div>		
		</div>
		
		
		<div class="row mgn-top-2em">
			<div class="col-md-6">
				<p>För mer information till dig som sökande eller arbetsgivare/arbetsplats.</p>
				<a target="_blank" href="http://www.sundsvall.se/sommarjobb">www.sundsvall.se/sommarjobb</a>
			</div>
		</div>
		

	</xsl:template>
</xsl:stylesheet>