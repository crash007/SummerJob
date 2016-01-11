<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:template match="Document">
		<h1>Sundsvalls kommuns sommarjobbsportal</h1>
		<p class="lead">Här kan du som är skriven i Sundsvalls kommun och går på en gymnasieskola söka sommarjobb.</p>
		
		<h3>Sök sommarjobb inom Sundsvalls Kommun</h3>
		<p>Vi erbjuder sommarjobb inom kommunens olika förvaltningar.</p>
		<a href="{AddMunicipalityApplicationUrl}">Skicka in din ansökan</a>
		
		<h3>Sommarjobb inom näringslivet</h3>
		<p>Sundsvalls kommun förmedlar sommarjobb inom näringslivet.</p>
		<a href="{AddBusinessApplicationUrl}" class="business-jobs-link">Listan över lediga sommarjobb</a>
		
		<h3>Medarbetare inom Sundsvalls kommun</h3>
		<p>Som medarbetare inom Sundsvalls kommun kan man lägga upp nya sommarjobb.</p>
		<a href="{/Document/requestinfo/contextpath}/login">Logga in här för att lägga upp jobb</a>
	</xsl:template>
</xsl:stylesheet>