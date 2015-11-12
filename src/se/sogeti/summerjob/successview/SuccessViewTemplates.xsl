<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="SuccessMessage"/>
	</xsl:template>
	
	<xsl:template match="SuccessMessage">
		<div style="margin-top: 32px; padding-left: 48px; padding-right: 48px; padding-bottom: 16px">
			<div style="padding: 32px" class="alert alert-success" role="alert">
				<h3 style="margin-top: 0px" class="header"><xsl:value-of select="header"></xsl:value-of></h3>
				<p><xsl:value-of select="message"></xsl:value-of></p>
				<xsl:if test="newUrl and newText">
					<div style="margin-bottom: 8px;" class="float-rgt">
						<a class="btn btn-primary" href="{newUrl}"><strong><xsl:value-of select="newText"></xsl:value-of></strong></a>
					</div>
				</xsl:if>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>					