<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="MunicipalityJobs" />
		<xsl:apply-templates select="BusinessJobs" />
	</xsl:template>
	
	<xsl:template match="MunicipalityJobs">
		<h1>Kommunala sommarjobb</h1>
		<xsl:apply-templates select="MunicipalityOpen"/>
		<xsl:apply-templates select="MunicipalityUncontrolled"/>
		<xsl:apply-templates select="MunicipalityFinished"/>
		<xsl:apply-templates select="MunicipalityDisapproved"/>
	</xsl:template>
	
	<xsl:template match="BusinessJobs">	
		<h1>Sommarjobb inom näringslivet</h1>
		<xsl:apply-templates select="BusinessOpen"/>
		<xsl:apply-templates select="BusinessUncontrolled"/>
		<xsl:apply-templates select="BusinessFinished"/>
		<xsl:apply-templates select="BusinessDisapproved"/>
	</xsl:template>
	
	<xsl:template match="MunicipalityOpen">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Öppna annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Arbetsplats</div>
					<div class="col-md-2 bold">Avdelning</div>
					<div class="col-md-2 bold">Period</div>
					<div class="col-md-2 bold">Totalt/Tillsatta</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-1 bold"></div>
					<div class="col-md-1 bold"></div>
				</div>
				
				<xsl:for-each select="MunicipalityJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="location" /></div>
							<div class="col-md-2"><xsl:value-of select="department" /></div>
							<div class="col-md-2"><xsl:value-of select="period" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /><xsl:text> </xsl:text><span>(<xsl:value-of select="matchedApplications" />)</span></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
							<div class="col-md-1"><a href="{matchURL}"><strong>Matcha</strong></a></div>
						</div>
						
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-2 bold">Organisation</div>
									<div class="col-xs-3"><xsl:value-of select="organization" /></div>
									<div class="col-xs-2 bold">Förvaltning</div>
									<div class="col-xs-3"><xsl:value-of select="administration" /></div>
								</div>
								<div class="row">
									<div class="col-xs-2 bold">Godkänd av</div>
									<div class="col-xs-3"><xsl:value-of select="approvedByUser" /></div>
									<div class="col-xs-2 bold">Datum för kontroll</div>
									<div class="col-xs-3"><xsl:value-of select="controlledDate" /></div>
								</div>
								<div class="row">
									<div class="col-xs-2 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
									<div class="col-xs-2 bold">Arbetsbeskrivning</div>
									<div class="col-xs-5"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="MunicipalityUncontrolled">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Ej kontrollerade annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Arbetsplats</div>
					<div class="col-md-2 bold">Avdelning</div>
					<div class="col-md-2 bold">Period</div>
					<div class="col-md-2 bold">Antal platser</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-1 bold">Kontrollant</div>
					<div class="col-md-1 bold"></div>
				</div>
				
				<xsl:for-each select="MunicipalityJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="location" /></div>
							<div class="col-md-2"><xsl:value-of select="department" /></div>
							<div class="col-md-2"><xsl:value-of select="period" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-1"><xsl:value-of select="initiatedByUser" /></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
						</div>
						
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-2 bold">Organisation</div>
									<div class="col-xs-3"><xsl:value-of select="organization" /></div>
									<div class="col-xs-2 bold">Förvaltning</div>
									<div class="col-xs-3"><xsl:value-of select="administration" /></div>
								</div>
								<div class="row">
									<div class="col-xs-2 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
									<div class="col-xs-2 bold">Arbetsbeskrivning</div>
									<div class="col-xs-5"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="MunicipalityFinished">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Stängda annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Arbetsplats</div>
					<div class="col-md-2 bold">Avdelning</div>
					<div class="col-md-2 bold">Period</div>
					<div class="col-md-2 bold">Totalt/Tillsatta</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-1 bold"></div> <!-- HANTERA-URL -->
					<div class="col-md-1 bold"></div> <!-- MATCHA-URL -->
				</div>
				
				<xsl:for-each select="MunicipalityJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="location" /></div>
							<div class="col-md-2"><xsl:value-of select="department" /></div>
							<div class="col-md-2"><xsl:value-of select="period" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /><xsl:text> </xsl:text><span>(<xsl:value-of select="matchedApplications" />)</span></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
							<div class="col-md-1"><a href="{matchURL}"><strong>Öppna</strong></a></div>
						</div>
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-2 bold">Organisation</div>
									<div class="col-xs-3"><xsl:value-of select="organization" /></div>
									<div class="col-xs-2 bold">Förvaltning</div>
									<div class="col-xs-3"><xsl:value-of select="administration" /></div>
								</div>
								<div class="row">
									<div class="col-xs-2 bold">Godkänd av</div>
									<div class="col-xs-3"><xsl:value-of select="approvedByUser" /></div>
									<div class="col-xs-2 bold">Datum för kontroll</div>
									<div class="col-xs-3"><xsl:value-of select="controlledDate" /></div>
								</div>
								<div class="row">
									<div class="col-xs-2 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
									<div class="col-xs-2 bold">Arbetsbeskrivning</div>
									<div class="col-xs-5"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="MunicipalityDisapproved">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Nekade annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Arbetsplats</div>
					<div class="col-md-2 bold">Avdelning</div>
					<div class="col-md-2 bold">Period</div>
					<div class="col-md-2 bold">Antal platser</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-1 bold">Kontrollant</div>
					<div class="col-md-1 bold"></div>
				</div>
				
				<xsl:for-each select="MunicipalityJob">
					<div class="row job-row">
						<div class="col-md-2"><xsl:value-of select="location" /></div>
						<div class="col-md-2"><xsl:value-of select="department" /></div>
						<div class="col-md-2"><xsl:value-of select="period" /></div>
						<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /></div>
						<div class="col-md-2"><xsl:value-of select="created" /></div>
						<div class="col-md-1"><xsl:value-of select="approvedByUser" /></div>
						<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="BusinessOpen">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Öppna annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Företag</div>
					<div class="col-md-3 bold">Datum</div>
					<div class="col-md-2 bold">Totalt/Tillsatta</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-1 bold"></div>
					<div class="col-md-1 bold"></div> <!-- HANTERA-URL -->
					<div class="col-md-1 bold"></div> <!-- MATCHA-URL -->
				</div>
				
				<xsl:for-each select="BusinessJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="company" /></div>
							<div class="col-md-3"><xsl:value-of select="dates" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /><xsl:text> </xsl:text><span>(<xsl:value-of select="matchedApplications" />)</span></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-1"></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
							<div class="col-md-1"><a href="{matchURL}"><strong>Matcha</strong></a></div>
						</div>
						
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-3 bold">Sista ansökningsdag</div>
									<div class="col-xs-3"><xsl:value-of select="lastApplicationDay" /></div>
									<div class="col-xs-3 bold">Organisationsnummer</div>
									<div class="col-xs-3"><xsl:value-of select="corporateNumber" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Godkänd av</div>
									<div class="col-xs-3"><xsl:value-of select="approvedByUser" /></div>
									<div class="col-xs-3 bold">Datum för kontroll</div>
									<div class="col-xs-3"><xsl:value-of select="controlledDate" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Arbetsbeskrivning</div>
									<div class="col-xs-6"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="BusinessUncontrolled">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Ej kontrollerade annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Företag</div>
					<div class="col-md-3 bold">Datum</div>
					<div class="col-md-2 bold">Antal platser</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-2 bold">Kontrollant</div>
					<div class="col-md-1 bold"></div> <!-- HANTERA-URL -->
				</div>
				
				<xsl:for-each select="BusinessJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="company" /></div>
							<div class="col-md-3"><xsl:value-of select="dates" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-2"><xsl:value-of select="initiatedByUser" /></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
						</div>
						
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-3 bold">Sista ansökningsdag</div>
									<div class="col-xs-3"><xsl:value-of select="lastApplicationDay" /></div>
									<div class="col-xs-3 bold">Organisationsnummer</div>
									<div class="col-xs-3"><xsl:value-of select="corporateNumber" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Arbetsbeskrivning</div>
									<div class="col-xs-6"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="BusinessFinished">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Stängda annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Företag</div>
					<div class="col-md-3 bold">Datum</div>
					<div class="col-md-2 bold">Totalt/Tillsatta</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-1 bold"></div>
					<div class="col-md-1 bold"></div> <!-- HANTERA-URL -->
					<div class="col-md-1 bold"></div> <!-- MATCHA-URL -->
				</div>
				
				<xsl:for-each select="BusinessJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="company" /></div>
							<div class="col-md-3"><xsl:value-of select="dates" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /><xsl:text> </xsl:text><span>(<xsl:value-of select="matchedApplications" />)</span></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-1"></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
							<div class="col-md-1"><a href="{matchURL}"><strong>Öppna</strong></a></div>
						</div>
						
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-3 bold">Sista ansökningsdag</div>
									<div class="col-xs-3"><xsl:value-of select="lastApplicationDay" /></div>
									<div class="col-xs-3 bold">Organisationsnummer</div>
									<div class="col-xs-3"><xsl:value-of select="corporateNumber" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Godkänd av</div>
									<div class="col-xs-3"><xsl:value-of select="approvedByUser" /></div>
									<div class="col-xs-3 bold">Datum för kontroll</div>
									<div class="col-xs-3"><xsl:value-of select="controlledDate" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Arbetsbeskrivning</div>
									<div class="col-xs-6"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="BusinessDisapproved">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Nekade annonser</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2 bold">Företag</div>
					<div class="col-md-3 bold">Datum</div>
					<div class="col-md-2 bold">Antal platser</div>
					<div class="col-md-2 bold">Skapad</div>
					<div class="col-md-2 bold">Kontrollant</div>
					<div class="col-md-1 bold"></div> <!-- HANTERA-URL -->
				</div>
				
				<xsl:for-each select="BusinessJob">
					<div>
						<div class="row job-row">
							<div class="col-md-2"><xsl:value-of select="company" /></div>
							<div class="col-md-3"><xsl:value-of select="dates" /></div>
							<div class="col-md-2"><xsl:value-of select="numberOfWorkersNeeded" /></div>
							<div class="col-md-2"><xsl:value-of select="created" /></div>
							<div class="col-md-2"><xsl:value-of select="approvedByUser" /></div>
							<div class="col-md-1"><a href='{url}'><strong>Hantera</strong></a></div>
						</div>
						
						<div class="row hidden more-information">
							<div style="margin: 8px; padding: 8px" class="well">
								<div class="row">
									<div class="col-xs-3 bold">Sista ansökningsdag</div>
									<div class="col-xs-3"><xsl:value-of select="lastApplicationDay" /></div>
									<div class="col-xs-3 bold">Organisationsnummer</div>
									<div class="col-xs-3"><xsl:value-of select="corporateNumber" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Rubrik</div>
									<div class="col-xs-3"><xsl:value-of select="workTitle" /></div>
								</div>
								<div class="row">
									<div class="col-xs-3 bold">Arbetsbeskrivning</div>
									<div class="col-xs-6"><xsl:value-of select="workDescription" /></div>
								</div>
							</div>
						</div>
					</div>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
	
</xsl:stylesheet>					