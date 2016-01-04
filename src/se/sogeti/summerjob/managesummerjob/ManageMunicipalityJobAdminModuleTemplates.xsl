<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="MunicipalityJobInfo"/>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobInfo">
	
		<input type="hidden" id="job-id" name="job-id" value="{MunicipalityJob/id}" />
		
	  	<div class="panel panel-default">
		  	<div class="panel-heading">
		  		<h3 class="panel-title">Plats</h3>
		  	</div>
		  	<div class="panel-body">
			  	<div class="row">
			  		<div class="col-md-3">
						<label>Organisation</label>
						<div><xsl:value-of select="MunicipalityJob/organization"></xsl:value-of></div>
					</div>
					<div class="col-md-3">
						<label>Förvaltning</label>
						<div><xsl:value-of select="MunicipalityJob/administration"></xsl:value-of></div>
					</div>
					<div class="col-md-3">
						<label>Plats</label>
						<div><xsl:value-of select="MunicipalityJob/location"></xsl:value-of></div>
					</div>
			  	</div>
			  	<div class="mgn-top8px row">
					<div class="col-md-3">
						<label>Verksamhetsområde</label>
						<div><xsl:value-of select="MunicipalityJob/MunicipalityJobArea/name"></xsl:value-of></div>
					</div>
					<div class="col-md-9">
						<label>Beskrivning</label>
						<div><xsl:value-of select="MunicipalityJob/MunicipalityJobArea/description"></xsl:value-of></div>
					</div>
			  	</div>
		  	</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Adress till arbetsplatsen</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-3">
						<label for="company">Gatuadress</label>
						<div id="company">
							<xsl:value-of select="MunicipalityJob/streetAddress"></xsl:value-of>
						</div>
					</div>
					<div class="col-md-2">
						<label for="zipcode">Postnummer</label>
						<div id="zipcode">
							<xsl:value-of select="MunicipalityJob/zipCode"></xsl:value-of>
						</div>
					</div>
					<div class="col-md-2">
						<label for="city">Postort</label>
						<div id="city">
							<xsl:value-of select="MunicipalityJob/city"></xsl:value-of>
						</div>
					</div>
				</div>
				<div class="mgn-top8px row">
					<div class="col-md-3">
						<label>Avdelning</label>
						<div>
							<xsl:value-of select="MunicipalityJob/department"></xsl:value-of>
						</div>								
					</div>
					<div class="col-md-3">
						<label>Geografiskt område</label>
						<div>
							<xsl:value-of select="MunicipalityJob/GeoArea/name"></xsl:value-of>
						</div>								
					</div>
				</div>
			</div>
		</div>
			
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Arbete</h3>
			</div>
			<div class="panel-body">
				<div class="mgn-top8px row">
					<div class="col-md-3 ">
						<label>Rubrik</label>
						<div><xsl:value-of select="MunicipalityJob/workTitle"></xsl:value-of></div>
					</div>
					<div class="col-md-9">
						<label>Arbetsbeskrivning</label>
						<div><xsl:value-of select="MunicipalityJob/workDescription"></xsl:value-of></div>
					</div>
				</div>
				<div class="row mgn-top8px">
					<div class="col-md-3">
						<label>Antal lediga platser</label>
						<div><xsl:value-of select="MunicipalityJob/numberOfWorkersNeeded"></xsl:value-of></div>
					</div>
					<div class="col-md-5">
						<label>Period</label>
						<div><xsl:value-of select="MunicipalityJob/Period/name"></xsl:value-of><i> (<xsl:value-of select="substring(MunicipalityJob/Period/startDate, 1, 10)"></xsl:value-of> - <xsl:value-of select="substring(MunicipalityJob/Period/endDate, 1, 10)"></xsl:value-of>)</i></div>
					</div>
				</div>
				<div class="row mgn-top8px">
					<div class="col-md-3">
						<label>Ansvarig på arbetsplatsen</label>
					</div>
					<div class="col-md-12 row">
						<div class="col-md-2">
							<label>Förnamn</label>
							<div><xsl:value-of select="MunicipalityJob/MunicipalityManager/firstname"></xsl:value-of></div>
						</div>
						<div class="col-md-3">
							<label>Efternamn</label>
							<div><xsl:value-of select="MunicipalityJob/MunicipalityManager/lastname"></xsl:value-of></div>
						</div>
						<div class="col-md-2">
							<label>Telefonnummer</label>
							<div><xsl:value-of select="MunicipalityJob/MunicipalityManager/mobilePhone"></xsl:value-of></div>
						</div>
						<div class="col-md-5">
							<label>E-postadress</label>
							<div><xsl:value-of select="MunicipalityJob/MunicipalityManager/email"></xsl:value-of></div>
						</div>
					</div>
				</div>
					<div class="mgn-top8px row">
					  	<div class="col-md-12">
						  	<label>Handledare</label>
						  	<div class="row">
					  			<div class="col-md-2 bold col-xs-2" style="font-size: 90%; padding-right: 0px;">Förnamn</div>
					  			<div class="col-md-2 bold col-xs-2" style="font-size: 90%; padding-right: 0px;">Efternamn</div>
					  			<div class="col-md-2 bold col-xs-3" style="font-size: 90%; padding-right: 0px;">Telefonnummer</div>
					  			<div class="col-md-2 bold col-xs-3" style="font-size: 90%; padding-right: 0px;">E-postadress</div>
					  		</div>
					  		<xsl:for-each select="MunicipalityJob/mentors/MunicipalityMentor">
					  			<div class="row">
					  				<div class="col-md-2 col-xs-2" style="padding-right: 0px;"><xsl:value-of select="firstname" /></div>
					  				<div class="col-md-2 col-xs-2" style="padding-right: 0px;"><xsl:value-of select="lastname" /></div>
					  				<div class="col-md-2 col-xs-3" style="padding-right: 0px;"><xsl:value-of select="mobilePhone" /></div>
					  				<div class="col-md-2 col-xs-3" style="padding-right: 0px;"><xsl:value-of select="email" /></div>
					  			</div>
					  		</xsl:for-each>
						</div>
					</div>
				</div>
			</div>
					
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Krav</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
							<label>Körkort</label>
							<div>
			  					Tjänsten kräver <xsl:value-of select="MunicipalityJob/DriversLicenseType/name"></xsl:value-of>.
							</div>
						</div>
						<div class="col-md-4">
							<label>Ålder</label>
							<div>
								<xsl:choose>
									<xsl:when test="MunicipalityJob/mustBeOverEighteen = 'true'">Tjänsten kräver att sökande är över 18 år.</xsl:when>
									<xsl:otherwise>Tjänsten kräver <i>ej</i> att sökande är över 18 år.</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
					</div>
					<div class="mgn-top8px row">
						<div class="col-md-12">
							<label>Övriga krav och önskemål</label>
							<div>
								<xsl:value-of select="MunicipalityJob/freeTextRequirements"></xsl:value-of>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Övrigt</h3>
				</div>
				<div class="panel-body">					
					<div class="mgn-top8px row">
						<div class="col-md-12">
							<label>Övrigt</label>
							<div>
								<xsl:value-of select="MunicipalityJob/freeText"></xsl:value-of>
							</div>
						</div>
					</div>				
				</div>
			</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Hantera annonsen</h3>
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
				
				<div class="form-group">
					<label for="description-call-papers">Text på kallelse</label>
					<textarea class="form-control" maxlength="255" rows="3" id="description-call-papers" name="description-call-papers"><xsl:value-of select="MunicipalityJob/descriptionForCallPapers"></xsl:value-of></textarea>
					<div class="help-block with-errors">Skriv här information som ska hamna på kallelsebrevet. Max 255 tecken.</div>
				</div>
				
				<div class="form-group">
					<label for="description-employment-papers">Text på anställningsbevis</label>
					<textarea class="form-control" maxlength="255" rows="3" id="description-employment-papers" name="description-employment-papers"><xsl:value-of select="MunicipalityJob/descriptionForEmploymentPapers"></xsl:value-of></textarea>
					<div class="help-block with-errors">Skriv här information om arbetet som ska hamna på anställningsbeviset. Om det här fältet är tomt används arbetsbeskrivningen. Max 255 tecken.</div>
				</div>
				
				<div class="form-group">
					<label for="admin-notes">Handläggarkommentar</label>
					<textarea class="form-control" rows="5" id="admin-notes" name="admin-notes"><xsl:value-of select="MunicipalityJob/adminNotes"></xsl:value-of></textarea>
					<div class="help-block with-errors">Valfri. Skriv här anledningen till godkännande eller nekande av en annons.</div>
				</div>
				
				<div class="row mgn-btm8px">
					<div class="col-md-2 col-xs-6">
						<label>Status på annons</label>
						<select class="form-control" name="job-status-select">
							<option value="true">
								<xsl:if test="MunicipalityJob/approved = 'true'">
							   		<xsl:attribute name="selected">selected</xsl:attribute>	   	
							   	</xsl:if>
								Godkänd
							</option>
							<option value="false">
								<xsl:if test="MunicipalityJob/approved = 'false'">
								   		<xsl:attribute name="selected">selected</xsl:attribute>	   	
								</xsl:if>
								Nekad
							</option>
						</select>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<button id="save-job-options" type="button" class="btn btn-success mgn-lft8px pull-right">Spara</button>				
						<button id="mark-job-as-initiated-button" type="button" class="pull-right mgn-lft8px btn btn-primary">Markera som påbörjad</button>
						<a href="{editJobURL}?jobId={MunicipalityJob/id}" id="edit-job-button" type="button" class="pull-right mgn-lft8px btn btn-warning">Redigera</a>
						
					</div>
				</div>
				
				<div class="row mgn-top8px">
					<div class="col-md-12">
						<a href="{listJobsURL}?municipality=true" type="button" class="pull-right">Gå till alla kommunala jobb</a>
						
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						
						<a href="{BackURL}" type="button" class="pull-right">Gå tillbaka</a>
					</div>
				</div>
				
			</div>
		</div>
	</xsl:template>
	
</xsl:stylesheet>					