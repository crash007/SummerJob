<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="BusinessSectorJobInfo"/>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobInfo">
	
		<div style="display: none" id="jobIdDiv"><xsl:value-of select="BusinessSectorJob/id"></xsl:value-of></div>

		<div class="well">
		  	<div class="panel panel-default">
					  	<div class="panel-heading">
					  		<h3 class="panel-title">Arbete</h3>
					  	</div>
					  	<div class="panel-body">
						  	<div class="row">
						  		<div class="col-md-5">
									<label>Yrkestitel</label>
									<div id="work"><xsl:value-of select="BusinessSectorJob/workTitle"></xsl:value-of></div>
								</div>
						  	</div>
						  	<div class="mgn-top8px row">
						  		<div class="col-md-12">
									<label>Arbetsbeskrivning</label>
									<div id="description"><xsl:value-of select="BusinessSectorJob/workDescription"></xsl:value-of></div>
								</div>
						  	</div>
						  	
						  	<div class="mgn-top8px row">
						  		<div class="col-md-3">
									<label>Antal platser</label>
									<span class="mgn-lft8px" id="numberNeeded"><xsl:value-of select="BusinessSectorJob/numberOfWorkersNeeded"></xsl:value-of></span>
								</div>
							</div>
						  	
						  	<div class="mgn-top8px row">
								<div class="col-md-3">
									<label for="startDate">Startdatum</label>
									<span class="mgn-lft8px" id="startDate"><xsl:value-of select="BusinessSectorJob/startDate"></xsl:value-of></span>
								</div>
								<div class="col-md-3">
									<label for="endDate">Slutdatum</label>
									<span class="mgn-lft8px" id="endDate"><xsl:value-of select="BusinessSectorJob/endDate"></xsl:value-of></span>
								</div>
								<div class="col-md-4">
									<label for="lastApplicationDay">Sista ans�kningsdag</label>
									<span class="mgn-lft8px" id="lastApplicationDay"><xsl:value-of select="BusinessSectorJob/lastApplicationDay"></xsl:value-of></span>
								</div>
						  	</div>
						  	<div class="mgn-top8px row">
						  		<div class="col-md-12">
							  		<label>Handledare</label>
							  		<table class="table">
										<thead>
											<tr>
												<th class="overview">F�rnamn</th>
												<th class="overview">Efternamn</th>							
												<th class="overview">Telefonnummer</th>
												<th class="overview">E-postadress</th>
											</tr>
										</thead>
						
										<tbody>
											<xsl:for-each select="BusinessSectorJob/mentors/BusinessSectorMentor">
											<tr>
									  			<td><xsl:value-of select="firstname"></xsl:value-of></td>
									  			<td><xsl:value-of select="lastname"></xsl:value-of></td>
									  			<td><xsl:value-of select="mobilePhone"></xsl:value-of></td>
									  			<td><xsl:value-of select="email"></xsl:value-of></td>
									  		</tr>
								  			</xsl:for-each>
										</tbody>
									</table>
								</div>
						  	</div>
					  	</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Uppgifter om arbetsplatsen</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-3">
									<label for="corporate-number">Organisationsnummer</label>
									<div id="corporate-number">
										<xsl:value-of select="BusinessSectorJob/corporateNumber"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-5">
									<label for="company">F�retag</label>
									<div id="company">
										<xsl:value-of select="BusinessSectorJob/company"></xsl:value-of>
									</div>
								</div>
							</div>
							<div class="mgn-top8px row">
								<div class="col-md-3">
									<label for="streetAddress">Gatuadress</label>
									<div id="streetAddress">
										<xsl:value-of select="BusinessSectorJob/streetAddress"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-2">
									<label for="zipcode">Postnummer</label>
									<div id="zipcode">
										<xsl:value-of select="BusinessSectorJob/zipCode"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-2">
									<label for="city">Postort</label>
									<div id="city">
										<xsl:value-of select="BusinessSectorJob/city"></xsl:value-of>
									</div>
								</div>
							</div>
							<div class="mgn-top8px">
								<label class="mgn-top8px">Ansvarig p� arbetsplatsen</label>
								<div class="row">
									<div class="col-md-2">
										<label>F�rnamn</label>
										<div>
											<xsl:value-of select="BusinessSectorJob/BusinessSectorManager/firstname"></xsl:value-of>
										</div>
									</div>
									<div class="col-md-2">
										<label>Efternamn</label>
										<div>
											<xsl:value-of select="BusinessSectorJob/BusinessSectorManager/lastname"></xsl:value-of>
										</div>
									</div>
									<div class="col-md-2">
										<label>Telefonnummer</label>
										<div>
											<xsl:value-of select="BusinessSectorJob/BusinessSectorManager/mobilePhone"></xsl:value-of>
										</div>
									</div>
									<div class="col-md-3">
										<label>E-postadress</label>
										<div>
											<xsl:value-of select="BusinessSectorJob/BusinessSectorManager/email"></xsl:value-of>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Krav och �nskem�l</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
							<label>K�rkort</label>
							<div>
			  					Tj�nsten kr�ver <xsl:value-of select="BusinessSectorJob/DriversLicenseType/name"></xsl:value-of>.					  												
							</div>
						</div>
					</div>
					<div class="mgn-top8px row">
						<div class="col-md-4">
							<label>�lder</label>
							<div>
								<xsl:choose>
									<xsl:when test="BusinessSectorJob/mustBeOverEighteen = 'true'">Tj�nsten kr�ver att s�kande �r �ver 18 �r.</xsl:when>
									<xsl:otherwise>Tj�nsten kr�ver <i>ej</i> att s�kande �r �ver 18 �r.</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
					</div>
					
					<div class="mgn-top8px row">
						<div class="col-md-6">
							<label>Intervjuer</label>
							<div>
								<xsl:choose>
									<xsl:when test="BusinessSectorJob/inChargeOfInterviews = 'true'"><span class="prio">Jag vill att kommunen tar hand om intervjuer.</span></xsl:when>
									<xsl:otherwise>Jag vill sj�lv ta hand om intervjuer.</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
					</div>
					
					<div class="mgn-top8px row">
						<div class="col-md-12">
							<label>�vriga krav och �nskem�l</label>
							<div>
								<xsl:value-of select="BusinessSectorJob/freeTextRequirements"></xsl:value-of>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">�vrigt</h3>
				</div>
				<div class="panel-body">					
					<div class="mgn-top8px row">
						<div class="col-md-12">
							<label>�vrigt</label>
							<div>
								<xsl:value-of select="BusinessSectorJob/freeText"></xsl:value-of>
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
				<label for="admin-notes">Handl�ggarkommentar</label>
				<textarea class="form-control" rows="5" id="admin-notes" name="admin-notes"><xsl:value-of select="BusinessSectorJob/adminNotes"></xsl:value-of></textarea>
				<div class="help-block with-errors">Valfri. Skriv h�r anledningen till godk�nnande eller nekande av en annons.</div>
			</div>
			
			<xsl:choose>
				<xsl:when test="BusinessSectorJob/approvedByUser != '' and BusinessSectorJob/approved = 'true'">
					<button id="approve-job-button" type="button" class="btn btn-success" disabled="disabled">Godk�nn</button>
				</xsl:when>
				<xsl:otherwise>
					<button id="approve-job-button" type="button" class="btn btn-success">Godk�nn</button>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="BusinessSectorJob/approvedByUser != '' and BusinessSectorJob/approved = 'false'">
					<button id="disapprove-job-button" type="button" class="mgn-lft8px btn btn-danger" disabled="disabled">Neka</button>
				</xsl:when>
				<xsl:otherwise>
					<button id="disapprove-job-button" type="button" class="mgn-lft8px btn btn-danger">Neka</button>
				</xsl:otherwise>
			</xsl:choose>
			
			<a href="{listJobsURL}?showMunicipalityJobs=false" type="button" class="float-rgt mgn-lft8px btn btn-info">G� tillbaka</a>
			<button id="mark-job-as-initiated-button" type="button" class="float-rgt mgn-lft8px btn btn-primary">Markera som p�b�rjad</button>
			<a href="{editURL}?jobId={BusinessSectorJob/id}" id="edit-job-button" type="button" class="float-rgt mgn-lft8px btn btn-warning">Redigera</a>
		</div>
	</div>
	</xsl:template>
	
</xsl:stylesheet>					