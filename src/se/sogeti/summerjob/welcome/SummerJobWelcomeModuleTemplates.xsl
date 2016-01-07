<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:template match="Document">
		<h1>Sundsvalls kommuns sommarjobbsportal</h1>
		<p class="lead">Här kan du som är skriven i Sundsvalls kommun och går på en gymnasieskola söka sommarjobb.</p>
		
		<h2>Sök sommarjobb inom Sundsvalls Kommun</h2>
		<p class="lead">Vi erbjuder sommarjobb inom kommunens olika förvaltningar.</p>
		<a href="{AddMunicipalityApplicationUrl}">Skicka in din ansökan</a>
		
		<h2>Sommarjobb inom näringslivet</h2>
		<p class="lead">Sundsvalls kommun förmedlar sommarjobb inom näringslivet.</p>
		<a href="{AddBusinessApplicationUrl}" class="business-jobs-link">Listan över lediga sommarjobb</a>
		
	</xsl:template>
</xsl:stylesheet>