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
	
		<div style="margin-top: 32px; padding-left: 48px; padding-right: 48px;">
			<div style="padding: 16px;" class="alert alert-success" role="alert">
				<h3 style="margin-left: 16px;" class="header"><xsl:value-of select="header" /></h3>
				<p style="margin-left: 16px; margin-bottom: 16px;"><xsl:value-of select="message" /></p>
				<xsl:if test="newUrl and newText">
					<div class="row">
						<a style="margin-right: 16px;" class="float-rgt btn btn-primary" href="{newUrl}"><strong><xsl:value-of select="newText" /></strong></a>
					</div>
				</xsl:if>
			</div>
		</div>
		
	</xsl:template>
</xsl:stylesheet>					