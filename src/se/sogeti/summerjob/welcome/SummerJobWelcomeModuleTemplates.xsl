<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:template match="Document">
		<h1>Sundsvalls kommuns sommarjobbsportal</h1>
		<p class="lead mgn-btm0px">Här kan du som är skriven i Sundsvalls kommun och går på en gymnasieskola söka sommarjobb.</p>
		<p class="lead">Sommarjobb på privata företag kan du söka kontinuerligt fram till juni. Nya jobb kommer fortlöpande så besök sidan frekvent.</p>
		
		<div class="row">
			<div class="col-md-6">
				<h3>Sök sommarjobb inom kommunens olika verksamheter</h3>
				<a href="{AddMunicipalityApplicationUrl}">
					<xsl:if test="municipalityAppExceeded = 'true'">
						<xsl:attribute name="href"></xsl:attribute>						
						<xsl:attribute name="class">disabled</xsl:attribute>
					</xsl:if>
				Skicka in din ansökan</a>
				<xsl:if test="municipalityAppExceeded = 'true'">
					<p><i>Sista ansökningsdagen för sommarjobb inom kommunal verksamhet har passerat.</i></p>
				</xsl:if>
			</div>
			
			<div class="col-md-6">
				<h3>Sundsvalls kommun förmedlar även sommarjobb hos privata företag</h3>	
				<a href="{AddBusinessApplicationUrl}" class="business-jobs-link">
					<xsl:if test="businessAppExceeded = 'true'">
						<xsl:attribute name="href"></xsl:attribute>						
						<xsl:attribute name="class">disabled</xsl:attribute>
					</xsl:if>
				Sök jobb hos privata företag</a> 
				<xsl:if test="businessAppExceeded = 'true'">
					<p><i>Sista ansökningsdagen för sommarjobb hos privata företag har passerat.</i></p>
				</xsl:if>				
			</div>
		</div>
		
		<div class="row divider">
			<div class="col-md-6">
				<h3>Du som arbetsgivare kan själv lägga in sökbara sommarjobb</h3>
				<a href="{/Document/requestinfo/contextpath}/add-municipality-job">
					<xsl:if test="municipalityJobExceeded = 'true'">
						<xsl:attribute name="href"></xsl:attribute>						
						<xsl:attribute name="class">disabled</xsl:attribute>
					</xsl:if>
				Lägg till ett jobb</a>
				<xsl:if test="municipalityJobExceeded = 'true'">
					<p><i>Sista dagen för att lägga in sommarjobb inom kommunal verksamhet har passerat.</i></p>
				</xsl:if>
			</div>
			<div class="col-md-6">
				<h3>Här kan du som extern arbetsgivare lägga in ett jobb (privat, landsting, myndighet)</h3>
				<a href="{/Document/requestinfo/contextpath}/add-business-sector-job">
					<xsl:if test="businessJobExceeded = 'true'">
						<xsl:attribute name="href"></xsl:attribute>						
						<xsl:attribute name="class">disabled</xsl:attribute>
					</xsl:if>
				Lägg till ett jobb</a>
				<xsl:if test="businessJobExceeded = 'true'">
					<p><i>Sista dagen för att lägga in sommarjobb hos privata företag har passerat.</i></p>
				</xsl:if>
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