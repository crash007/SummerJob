<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		
		<xsl:apply-templates select="ApplicationInfo/BusinessSectorJobApplication"/>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobApplication">		
	  	<input type="hidden" id="app-id" name="job-id" value="{id}" />
	  	<div class="panel panel-default">
		  	<div class="panel-heading">
		  		<h3 class="panel-title">Personuppgifter</h3>
		  	</div>
		  	<div class="panel-body">
		  		<div class="row">
		  			<div class="col-md-3">
		  				<label>Personnummer</label>
		  				<div><xsl:value-of select="socialSecurityNumber"></xsl:value-of></div>
		  			</div>
		  		</div>
			  	<div class="mgn-top8px row">
	  				<div class="col-md-3">
					    <label>Förnamn</label>				    
					    <div><xsl:value-of select="firstname"></xsl:value-of></div>
			    	</div>
			    	<div class="col-md-3">
					    <label>Efternamn</label>				    
					    <div><xsl:value-of select="lastname"></xsl:value-of></div>
			    	</div>
	  				<div class="col-md-3">
					    <label>Telefonnummer</label>				    
					    <div><xsl:value-of select="phoneNumber"></xsl:value-of></div>
			    	</div>
	  				<div class="col-md-3">
					    <label>E-post</label>				    
					    <div><xsl:value-of select="email"></xsl:value-of></div>
			    	</div>
    			</div>
    			<div class="mgn-top8px row">
	  				<div class="col-md-3">
					    <label>Gatuadress</label>				    
					    <div><xsl:value-of select="streetAddress"></xsl:value-of></div>
			    	</div>
			    	<div class="col-md-3">
					    <label>Postnummer</label>				    
					    <div><xsl:value-of select="zipCode"></xsl:value-of></div>
			    	</div>
	  				<div class="col-md-3">
					    <label>Postort</label>				    
					    <div><xsl:value-of select="city"></xsl:value-of></div>
			    	</div>
    			</div>
    			<div class="mgn-top8px row">
	  				<div class="col-md-3">
					    <label>Skola</label>				    
					    <div><xsl:value-of select="schoolName"></xsl:value-of></div>
			    	</div>
			    	<div class="col-md-3">
					    <label>Skoltyp</label>				    
					    <div><xsl:value-of select="schoolType"></xsl:value-of></div>
			    	</div>
	  				<div class="col-md-3">
					    <label>Ort från skatteverket</label>				    
					    <div><xsl:value-of select="skvCity"></xsl:value-of></div>
			    	</div>
    			</div>
		  	</div>
		</div>
		
		<div class="panel panel-default">
		 	<div class="panel-heading">
		   		<h3 class="panel-title">Ansökan</h3>
		 	</div>
		  <div class="panel-body">
		  	<div class="row">
		  		<div class="col-md-12">
					<label>Personligt brev</label>				    
			  		<div><xsl:value-of select="personalLetter"></xsl:value-of></div>
			  	</div>
		  	</div>
	  		<div class="mgn-top8px row">
			    <div class="col-md-4">
			    	<label>CV</label>
			    	<xsl:choose>
			    		<xsl:when test="cvFilename != ''">
					    	<div>
					    		<a target="_blank" href="{/Document/requestinfo/contextpath}{/Document/CvBusinessApplicationUrl}?id={id}">Ladda ner</a>
					    	</div>
			    		</xsl:when>
			    		<xsl:otherwise>
			    			<div><i>Inget CV är bifogat</i></div>
			    		</xsl:otherwise>
			    	</xsl:choose>
			    </div>
		 	</div>
		  	<div class="mgn-top8px row">
		  		<div class="col-md-3">
		  			<label>Körkort</label>			  			
  					<div>Jag har <xsl:value-of select="DriversLicenseType/name"></xsl:value-of></div>			  				
		  		</div>						
			</div>
  		</div>
  	</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Hantera ansökan</h3>
			</div>
			<div class="panel-body">
				<div class="save-failed alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
					<span class="sr-only">Error:</span>
					<span class="message"></span>
				</div>
				<div class="save-succeeded alert alert-success" role="alert">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					<span class="sr-only">Success:</span>
					<span class="message"></span>
				</div>
				
				<div class="row form-group">
					<div class="col-md-2">
						<label>Ranking</label><br/>
						<select class="form-control" name="ranking">
							<xsl:for-each select="/Document/ApplicationInfo/Rankings/Ranking">
								<xsl:choose>
									<xsl:when test="selected = 'true'">
										<option selected="selected" value="{value}"><xsl:value-of select="value"></xsl:value-of></option>
									</xsl:when>
									<xsl:otherwise>
										<option value="{value}"><xsl:value-of select="value"></xsl:value-of></option>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label for="admin-notes">Handläggarkommentar</label>
					<textarea class="form-control" rows="5" id="admin-notes" name="admin-notes"><xsl:value-of select="adminNotes"></xsl:value-of></textarea>
					<div class="help-block with-errors">Valfri. Skriv här anledningen till godkännande eller nekande av en ansökan.</div>
				</div>
				
				<div class="row mgn-btm8px">
					<div class="col-md-2 col-xs-6">
						<label>Status på ansökan</label>
						<select class="form-control" name="application-status-select">
							<option value="true">
								<xsl:if test="approved = 'true'">
							   		<xsl:attribute name="selected">selected</xsl:attribute>	   	
							   	</xsl:if>
								Godkänd
							</option>
							<option value="false">
								<xsl:if test="approved = 'false'">
								   		<xsl:attribute name="selected">selected</xsl:attribute>	   	
								</xsl:if>
								Nekad
							</option>
						</select>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<button id="save-application-options" type="button" class="btn btn-success mgn-lft8px pull-right">Spara</button>
						<a href="{/Document/ApplicationInfo/editAppURL}" id="edit-job-button" type="button" class="pull-right mgn-lft8px btn btn-warning">Redigera</a>				
						
						
						
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mgn-top8px">						
						<a href="{/Document/ApplicationInfo/BackURL}" type="button" class="pull-right">Tillbaka</a>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<a href="{/Document/ApplicationInfo/listJobApplicationsURL}" type="button" class="pull-right">Alla ansökningar</a>						
					</div>
				</div>
								
				<div class="row">
					<div class="col-md-12">
						<a href="{/Document/ApplicationInfo/matchURL}" type="button" class="pull-right">Matcha</a>						
					</div>
				</div>
				
			</div>
		</div>
	</xsl:template>

</xsl:stylesheet>					