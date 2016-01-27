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
	
		<div class="success-message-wrapper">
			<div style="padding: 16px;" class="alert alert-success" role="alert">
				<h2 style="margin-left: 16px;" class="header"><xsl:value-of select="header" /></h2>
				<p style="margin-left: 16px; margin-bottom: 16px;"><xsl:value-of select="message" /></p>
				<xsl:if test="message1 and message2 and message3">
					<p style="margin-left: 16px; margin-bottom: 8px;"><xsl:value-of select="message1" /></p>
					<p style="margin-left: 16px; margin-bottom: 8px;"><xsl:value-of select="message2" /></p>
					<p style="margin-left: 16px; margin-bottom: 16px;"><xsl:value-of select="message3" /> <xsl:text> </xsl:text><a target="_blank" href="http://www.sundsvall.se/sommarjobb">www.sundsvall.se/sommarjobb</a></p>
				</xsl:if>
				<xsl:if test="newUrl and newText">
					<div class="row">
						<a class="mgn-top16px mgn-btm8px mgn-rgt32px pull-right btn btn-primary" href="{newUrl}"><strong><xsl:value-of select="newText" /></strong></a>
					</div>
				</xsl:if>
			</div>
		</div>
		
	</xsl:template>
</xsl:stylesheet>					