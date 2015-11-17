<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="ApplicationInfo"/>
	</xsl:template>
	
	<xsl:template match="ApplicationInfo">
	
		<div style="display: none" id="appIdDiv"><xsl:value-of select="MunicipalityJobApplication/id"></xsl:value-of></div>

		<div class="well">
		  	<div class="panel panel-default">
			  	<div class="panel-heading">
			  		<h3 class="panel-title">Personuppgifter</h3>
			  	</div>
			  	<div class="panel-body">
			  		<div class="row">
			  			<div class="col-md-3">
			  				<label>Personnummer</label>
			  				<div><xsl:value-of select="MunicipalityJobApplication/socialSecurityNumber"></xsl:value-of></div>
			  			</div>
			  		</div>
				  	<div class="mgn-top8px row">
		  				<div class="col-md-3">
						    <label>Förnamn</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/firstname"></xsl:value-of></div>
				    	</div>
				    	<div class="col-md-3">
						    <label>Efternamn</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/lastname"></xsl:value-of></div>
				    	</div>
		  				<div class="col-md-3">
						    <label>Telefonnummer</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/phoneNumber"></xsl:value-of></div>
				    	</div>
		  				<div class="col-md-3">
						    <label>E-post</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/email"></xsl:value-of></div>
				    	</div>
	    			</div>
	    			<div class="mgn-top8px row">
		  				<div class="col-md-3">
						    <label>Gatuadress</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/streetAddress"></xsl:value-of></div>
				    	</div>
				    	<div class="col-md-3">
						    <label>Postnummer</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/zipCode"></xsl:value-of></div>
				    	</div>
		  				<div class="col-md-3">
						    <label>Postort</label>				    
						    <div><xsl:value-of select="MunicipalityJobApplication/city"></xsl:value-of></div>
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
				  		<div><xsl:value-of select="MunicipalityJobApplication/personalLetter"></xsl:value-of></div>
				  	</div>
			  	</div>
		  		<div class="mgn-top8px row">
				    <div class="col-md-4">
				    	<label>CV</label>
				    	<xsl:choose>
				    		<xsl:when test="MunicipalityJobApplication/cvLocation != ''">
						    	<div><a href="{MunicipalityJobApplication/cvLocation}"></a></div>
				    		</xsl:when>
				    		<xsl:otherwise>
				    			<div><i>Inget CV är bifogat</i></div>
				    		</xsl:otherwise>
				    	</xsl:choose>
				    </div>
			 	</div>
		  		<div class="mgn-top8px row">
			  		<div class="mgn-top8px col-md-12"><label>Önskat arbetsområde</label></div>
		  			<xsl:choose>
		  				<xsl:when test="MunicipalityJobApplication/noPreferedArea = 'true'">
		  					<div class="col-md-4">
		  						<div>Jag kan tänka mig jobba med vad som helst</div>
		  					</div>
		  				</xsl:when>
		  				<xsl:otherwise>
		  					<div>
			  					<div class="col-md-4">
								    <label style="font-size: 80%">Prio 1</label>				    
								    <div><xsl:value-of select="MunicipalityJobApplication/preferedArea1/name"></xsl:value-of></div>
								</div>
								<div class="col-md-4">
								    <label style="font-size: 80%">Prio 2</label>				    
								    <div><xsl:value-of select="MunicipalityJobApplication/preferedArea2/name"></xsl:value-of></div>
								</div>
								<div class="col-md-4">
								    <label style="font-size: 80%">Prio 3</label>				    
								    <div><xsl:value-of select="MunicipalityJobApplication/preferedArea3/name"></xsl:value-of></div>
								</div>
							</div>
		  				</xsl:otherwise>
		  			</xsl:choose>
				</div>
		  		<div class="mgn-top8px row">
		  			<div class="mgn-top8px col-md-12"><label>Önskat geografiskt område</label></div>
		  			<div class="col-md-4">
					    <label style="font-size: 80%">Prio 1</label>
					    <div><xsl:value-of select="MunicipalityJobApplication/preferedGeoArea1/name"></xsl:value-of></div>    
					</div>
					<div class="col-md-4">
					    <label style="font-size: 80%">Prio 2</label>
					    <div><xsl:value-of select="MunicipalityJobApplication/preferedGeoArea2/name"></xsl:value-of></div>    
					</div>
					<div class="col-md-4">
					    <label style="font-size: 80%">Prio 3</label>
					    <div><xsl:value-of select="MunicipalityJobApplication/preferedGeoArea3/name"></xsl:value-of></div>    
					</div>
				</div>
			  	<div class="mgn-top8px row">
			  		<div class="col-md-3">
			  			<label>Körkort</label>
			  			<xsl:choose>
			  				<xsl:when test="MunicipalityJobApplication/hasDriversLicense = 'true'">
			  					<div>Ja, jag har körkort av typ <xsl:value-of select="MunicipalityJobApplication/DriversLicenseType/name"></xsl:value-of></div>
			  				</xsl:when>
			  				<xsl:otherwise>
			  					<div>Nej, jag har inget körkort</div>
			  				</xsl:otherwise>
			  			</xsl:choose>
			  		</div>						
				</div>
	  		</div>
	  	</div>
			
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Hantera ansökan</h3>
			</div>
			<div class="panel-body">
				<div id="save-failed" class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
					<span class="sr-only">Error:</span>
					<span class="message"></span>
				</div>
				<div id="save-succeeded" class="alert alert-success" role="alert">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					<span class="sr-only">Success:</span>
					<span class="message"></span>
				</div>
				
				<div class="form-group">
					<label for="admin-notes">Handläggarkommentar</label>
					<textarea class="form-control" rows="5" id="admin-notes" name="admin-notes"><xsl:value-of select="MunicipalityJobApplication/adminNotes"></xsl:value-of></textarea>
					<div class="help-block with-errors">Valfri. Skriv här anledningen till godkännande eller nekande av en ansökan.</div>
				</div>
				
				<xsl:choose>
					<xsl:when test="MunicipalityJobApplication/controlledByUser != '' and MunicipalityJobApplication/approved = 'true'">
						<button id="approve-application-button" type="button" class="btn btn-success" disabled="disabled">Godkänn</button>
					</xsl:when>
					<xsl:otherwise>
						<button id="approve-application-button" type="button" class="btn btn-success">Godkänn</button>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="MunicipalityJobApplication/controlledByUser != '' and MunicipalityJobApplication/approved = 'false'">
						<button id="disapprove-application-button" type="button" class="mgn-lft8px btn btn-danger" disabled="disabled">Neka</button>
					</xsl:when>
					<xsl:otherwise>
						<button id="disapprove-application-button" type="button" class="mgn-lft8px btn btn-danger">Neka</button>
					</xsl:otherwise>
				</xsl:choose>
				
				<a href="{listJobApplicationsURL}" type="button" class="float-rgt mgn-lft8px btn btn-info">Gå tillbaka</a>
				<a href="{editAppURL}?appId={MunicipalityJobApplication/id}" id="edit-job-button" type="button" class="float-rgt mgn-lft8px btn btn-warning">Redigera</a>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>					