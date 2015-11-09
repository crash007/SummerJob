<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="MunicipalityJobInfo/MunicipalityJob"/>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobInfo/MunicipalityJob">
	
		<div style="display: none" id="jobIdDiv"><xsl:value-of select="id"></xsl:value-of></div>

		<div class="well">
		  	<div class="panel panel-default">
					  	<div class="panel-heading">
					  		<h3 class="panel-title">Plats</h3>
					  	</div>
					  	<div class="panel-body">
						  	<div class="row">
						  		<div class="col-md-3">
									<label>Organisation</label>
									<div><xsl:value-of select="organization"></xsl:value-of></div>
								</div>
								<div class="col-md-3">
									<label>Förvaltning</label>
									<div><xsl:value-of select="administration"></xsl:value-of></div>
								</div>
								<div class="col-md-3">
									<label>Plats</label>
									<div><xsl:value-of select="location"></xsl:value-of></div>
								</div>
						  	</div>
						  	<div class="mgn-top8px row">
								<div class="col-md-3">
									<label>Verksamhetsområde</label>
									<div><xsl:value-of select="MunicipalityJobArea/name"></xsl:value-of></div>
								</div>
								<div class="col-md-9">
									<label>Beskrivning</label>
									<div><xsl:value-of select="MunicipalityJobArea/description"></xsl:value-of></div>
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
										<xsl:value-of select="streetAddress"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-2">
									<label for="zipcode">Postnummer</label>
									<div id="zipcode">
										<xsl:value-of select="zipCode"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-2">
									<label for="city">Postort</label>
									<div id="city">
										<xsl:value-of select="city"></xsl:value-of>
									</div>
								</div>
							</div>
							<div class="mgn-top8px row">
								<div class="col-md-3">
									<label>Avdelning</label>
									<div>
										<xsl:value-of select="department"></xsl:value-of>
									</div>								
								</div>
								<div class="col-md-3">
									<label>Geografiskt område</label>
									<div>
										<xsl:value-of select="GeoArea/name"></xsl:value-of>
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
								<div><xsl:value-of select="workTitle"></xsl:value-of></div>
							</div>
							<div class="col-md-9">
								<label>Arbetsbeskrivning</label>
								<div><xsl:value-of select="workDescription"></xsl:value-of></div>
							</div>
						</div>
						<div class="row mgn-top8px">
							<div class="col-md-3">
								<label>Antal lediga platser</label>
								<div><xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of></div>
							</div>
							<div class="col-md-5">
								<label>Period</label>
								<div><xsl:value-of select="Period/name"></xsl:value-of><i> (<xsl:value-of select="Period/startDate"></xsl:value-of> - <xsl:value-of select="Period/endDate"></xsl:value-of>)</i></div>
							</div>
						</div>
						<div class="row mgn-top8px">
							<div class="col-md-3">
								<label>Ansvarig på arbetsplatsen</label>
							</div>
							<div class="col-md-12 row">
								<div class="col-md-2">
									<label>Förnamn</label>
									<div><xsl:value-of select="MunicipalityManager/firstname"></xsl:value-of></div>
								</div>
								<div class="col-md-3">
									<label>Efternamn</label>
									<div><xsl:value-of select="MunicipalityManager/lastname"></xsl:value-of></div>
								</div>
								<div class="col-md-2">
									<label>Telefonnummer</label>
									<div><xsl:value-of select="MunicipalityManager/mobilePhone"></xsl:value-of></div>
								</div>
								<div class="col-md-5">
									<label>E-postadress</label>
									<div><xsl:value-of select="MunicipalityManager/email"></xsl:value-of></div>
								</div>
							</div>
						</div>
						
						<div class="row mgn-top8px">
							<div class="col-md-3 mgn-top8px">
								<label>Handledare</label>
							</div>
							<div class="row">
								<div class="col-md-12">
									<table class="table">
										<thead>
											<tr>
												<th>Förnamn</th>
												<th>Efternamn</th>							
												<th>Telefonnummer</th>
												<th>E-postadress</th>
											</tr>
										</thead>
										<tbody>
											<xsl:apply-templates select="mentors/MunicipalityMentor" />
										</tbody>
									</table>
								</div>
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
								<xsl:choose>
									<xsl:when test="hasDriversLicense = 'true'">Tjänsten kräver att sökande har körkort av typ <xsl:value-of select="DriversLicenseType/name"></xsl:value-of>.</xsl:when>
									<xsl:otherwise>Tjänsten kräver <i>ej</i> körkort</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
						<div class="col-md-4">
							<label>Ålder</label>
							<div>
								<xsl:choose>
									<xsl:when test="isOverEighteen = 'true'">Tjänsten kräver att sökande är över 18 år.</xsl:when>
									<xsl:otherwise>Tjänsten kräver <i>ej</i> att sökande är över 18 år.</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
					</div>
					<div class="mgn-top8px row">
						<div class="col-md-12">
							<label>Övriga krav och önskemål</label>
							<div>
								<xsl:value-of select="freeTextRequirements"></xsl:value-of>
							</div>
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
				<textarea class="form-control" rows="5" id="admin-notes" name="admin-notes"><xsl:value-of select="adminNotes"></xsl:value-of></textarea>
				<div class="help-block with-errors">Valfri. Skriv här anledningen till godkännande eller nekande av en annons.</div>
			</div>
			
			<xsl:choose>
				<xsl:when test="approvedByUser != '' and approved = 'true'">
					<button id="approve-businessjob-button" type="button" class="btn btn-success" disabled="disabled">Godkänn</button>
				</xsl:when>
				<xsl:otherwise>
					<button id="approve-businessjob-button" type="button" class="btn btn-success">Godkänn</button>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="approvedByUser != '' and approved = 'false'">
					<button id="disapprove-businessjob-button" type="button" class="mgn-lft8px btn btn-danger" disabled="disabled">Neka</button>
				</xsl:when>
				<xsl:otherwise>
					<button id="disapprove-businessjob-button" type="button" class="mgn-lft8px btn btn-danger">Neka</button>
				</xsl:otherwise>
			</xsl:choose>
			
			<a href="list-summerjobs?showMunicipalityJobs=true" type="button" class="float-rgt mgn-lft8px btn btn-info">Gå tillbaka</a>
			<button id="mark-businessjob-as-initiated-button" type="button" class="float-rgt mgn-lft8px btn btn-primary">Markera som påbörjad</button>
			<a href="#" id="edit-job-button" type="button" class="float-rgt mgn-lft8px btn btn-warning">Redigera</a>
		</div>
	</div>
	</xsl:template>
	
	<xsl:template match="MunicipalityMentor">
		<tr>
			<td>
				<xsl:value-of select="firstname"></xsl:value-of>
			</td>
			<td>
				<xsl:value-of select="lastname"></xsl:value-of>
			</td>
			<td>
				<xsl:value-of select="mobilePhone"></xsl:value-of>
			</td>
			<td>
				<xsl:value-of select="email"></xsl:value-of>
			</td>
		</tr>
	</xsl:template>
	
</xsl:stylesheet>					