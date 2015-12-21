<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		<h1>Överblick</h1>
		
		<ul class="nav nav-tabs">
			<li class="active">
				<a data-toggle="tab" href="#municipality-page">Kommunalt</a>
			</li>
			<li>
				<a data-toggle="tab" href="#business-page">Näringsliv</a>
			</li>
		</ul>
	
		<div class="tab-content">
			<div style="padding: 16px 16px 16px 16px" id="municipality-page" class="tab-pane fade in active">
				<xsl:apply-templates select="Municipality"/>
			</div>
			<div style="padding: 16px 16px 16px 16px" id="business-page" class="tab-pane fade">
				<xsl:apply-templates select="Business"/>
			</div>
		</div>
	
	</xsl:template>
	
	<xsl:template match="Municipality">
		<h2>Kommunala sommarjobb</h2>
			
		<ul class="nav nav-tabs">
			<li class="active">
				<a data-toggle="tab" href="#municipality-jobs">Annonser</a>
			</li>
			<li>
				<a data-toggle="tab" href="#municipality-applications">Ansökningar</a>
			</li>
		</ul>
			
		<div class="tab-content mgn-top8px">
			<div id="municipality-jobs" class="tab-pane fade in active">
				<div class="panel panel-default">
					<div class="panel-heading">
				    	<h3 class="panel-title">Nya arbetsplatser</h3>
				  	</div>
				  	<div class="panel-body">
				  		<div class="row">
				  			<div class="col-md-2 bold">Arbetsplats</div>
					  		<div class="col-md-2 bold">Avdelning</div>
					  		<div class="col-md-3 bold">Verksamhetsområde</div>
					  		<div class="col-md-2 bold">Geografiskt område</div>
					  		<div class="col-md-1 bold">Platser</div>
					  		<div class="col-md-1 bold">Period</div>
					  	</div>
					  	
					  	<xsl:for-each select="NewMunicipalityJobs/MunicipalityJob">
					  		<div class="row overview-row">
						  		<div class="col-md-2"><xsl:value-of select="location" /></div>
						  		<div class="col-md-2"><xsl:value-of select="department" /></div>
						  		<div class="col-md-3"><xsl:value-of select="MunicipalityJobArea/name" /></div>
						  		<div class="col-md-2"><xsl:value-of select="GeoArea/name" /></div>
						  		<div class="col-md-1"><xsl:value-of select="numberOfWorkersNeeded" /></div>
						  		<div class="col-md-1"><xsl:value-of select="Period/name" /></div>
						  		<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageMunicipalityJobsUrl}?jobId={id}">Hantera</a></div>
					  		</div>
					  	</xsl:for-each>
					</div>
				</div>
	  
		  		<div class="panel panel-default">
			  		<div class="panel-heading">
			    		<h3 class="panel-title">Senast godkända arbetsplatser</h3>
			  		</div>
			  		<div class="panel-body">
			  			<div class="row">
				  			<div class="col-md-2 bold">Arbetsplats</div>
						  	<div class="col-md-2 bold">Avdelning</div>
						  	<div class="col-md-2 bold">Verksamhetsområde</div>
						  	<div class="col-md-2 bold">Geografiskt område</div>
						  	<div class="col-md-1 bold">Platser</div>
						  	<div class="col-md-1 bold">Period</div>
					  	</div>
					  	<xsl:for-each select="approvedMunicipalityJobs/MunicipalityJob">
					  		<div class="row overview-row">
						  		<div class="col-md-2"><xsl:value-of select="location" /></div>
						  		<div class="col-md-2"><xsl:value-of select="department" /></div>
						  		<div class="col-md-2"><xsl:value-of select="MunicipalityJobArea/name" /></div>
						  		<div class="col-md-2"><xsl:value-of select="GeoArea/name" /></div>
						  		<div class="col-md-1"><xsl:value-of select="numberOfWorkersNeeded" /></div>
						  		<div class="col-md-1"><xsl:value-of select="Period/name" /></div>
						  		<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageMunicipalityJobsUrl}?jobId={id}">Hantera</a></div>
						  		<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/MatchMunicipalityJobsUrl}?jobId={id}">Matcha</a></div>
					  		</div>
					  	</xsl:for-each>
			  		</div>
				</div>
	  		</div>
	  		
	  		<div id="municipality-applications" class="tab-pane fade in">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Senast godkända ansökningar</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2 bold">Personnummer</div>
							<div class="col-md-2 bold">Förnamn</div>
							<div class="col-md-2 bold">Efternamn</div>
							<div class="col-md-2 bold">Verksamhetsområde 1</div>
							<div class="col-md-2 bold">Område 1</div>
							<div class="col-md-1 bold" style="padding-left: 0px; padding-right: 0px;">Datum</div>
							<div class="col-md-1"></div>
						</div>
						<xsl:for-each select="approvedMunicipalityApplications/MunicipalityApplication">
							<div class="row application-row">
								<div class="col-md-2"><xsl:value-of select="socialSecurityNumber" /></div>
								<div class="col-md-2"><xsl:value-of select="firstname" /></div>
								<div class="col-md-2"><xsl:value-of select="lastname" /></div>
								<div class="col-md-2"><xsl:value-of select="preferedArea1" /></div>
								<div class="col-md-2"><xsl:value-of select="preferedGeoArea1" /></div>
								<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="created" /></div>
								<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageMunicipalityApplicationUrl}?appId={id}">Hantera</a></div>
							</div>
						</xsl:for-each>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Senast nekade ansökningarna</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2 bold">Personnummer</div>
							<div class="col-md-2 bold">Förnamn</div>
							<div class="col-md-2 bold">Efternamn</div>
							<div class="col-md-2 bold">Skola</div>
							<div class="col-md-1 bold">Skoltyp</div>
							<div class="col-md-1 bold" style="padding-left: 0px;">Postort SKV</div>
							<div class="col-md-1 bold" style="padding-left: 0px; padding-right: 0px;">Datum</div>
							<div class="col-md-1"></div>
						</div>
						<xsl:for-each select="unapprovedMunicipalityApplications/MunicipalityApplication">
							<div class="row application-row">
								<div class="col-md-2"><xsl:value-of select="socialSecurityNumber" /></div>
								<div class="col-md-2"><xsl:value-of select="firstname" /></div>
								<div class="col-md-2"><xsl:value-of select="lastname" /></div>
								<div class="col-md-2"><xsl:value-of select="schoolName" /></div>
								<div class="col-md-1"><xsl:value-of select="schoolType" /></div>
								<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="skvCity" /></div>
								<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="created" /></div>
								<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageMunicipalityApplicationUrl}?appId={id}">Hantera</a></div>
							</div>
						</xsl:for-each>
					</div>
				</div>
			</div>
		</div>
		  
	</xsl:template>
		
	<xsl:template match="Business">
		<h2>Näringslivssommarjobb</h2> 
		
		<ul class="nav nav-tabs">
			<li class="active">
				<a data-toggle="tab" href="#business-jobs">Annonser</a>
			</li>
			<li>
				<a data-toggle="tab" href="#business-applications">Ansökningar</a>
			</li>
		</ul>
		
		<div class="tab-content mgn-top8px">
			<div id="business-jobs" class="tab-pane fade in active">
		  		<div class="panel panel-default">
					<div class="panel-heading">
			    		<h3 class="panel-title">Nya arbetsplatser</h3>
			  		</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2 bold">Yrkestitel</div>
							<div class="col-md-2 bold">Företag</div>
							<div class="col-md-1 bold">Platser</div>
							<div class="col-md-1 bold" style="padding-left: 0px;">Startdatum</div>
							<div class="col-md-1 bold" style="padding-left: 0px;">Slutdatum</div>
							<div class="col-md-2 bold" style="padding-left: 0px;">Sista ansökningsdag</div>
						</div>
						<xsl:for-each select="NewBusinessJobs/BusinessSectorJob">
							<div class="row job-row">
								<div class="col-md-2"><xsl:value-of select="workTitle" /></div>
								<div class="col-md-2"><xsl:value-of select="company" /></div>
								<div class="col-md-1"><xsl:value-of select="numberOfWorkersNeeded" /></div>
								<div class="col-md-1" style="padding-left: 0px;"><xsl:value-of select="substring(startDate, 1, 10)" /></div>
								<div class="col-md-1" style="padding-left: 0px;"><xsl:value-of select="substring(endDate, 1, 10)" /></div>
								<div class="col-md-2" style="padding-left: 0px;"><xsl:value-of select="substring(lastApplicationDay, 1, 10)" /></div>
								<div class="col-md-2"></div>
								<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageBusinessJobsUrl}?jobId={id}">Hantera</a></div>
							</div>
						</xsl:for-each>
					</div>
				</div>
				<div class="panel panel-default">
			  		<div class="panel-heading">
			    		<h3 class="panel-title">Senast godkända arbetsplatser</h3>
			  		</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2 bold">Yrkestitel</div>
							<div class="col-md-2 bold">Företag</div>
							<div class="col-md-1 bold">Platser</div>
							<div class="col-md-1 bold" style="padding-left: 0px; padding-right: 0px;">Startdatum</div>
							<div class="col-md-1 bold" style="padding-left: 0px; padding-right: 0px;">Slutdatum</div>
							<div class="col-md-2 bold" style="padding-left: 0px;">Sista ansökningsdag</div>
						</div>
						<xsl:for-each select="ApprovedBusinessJobs/BusinessSectorJob">
							<div class="row job-row">
								<div class="col-md-2"><xsl:value-of select="workTitle" /></div>
								<div class="col-md-2"><xsl:value-of select="company" /></div>
								<div class="col-md-1"><xsl:value-of select="numberOfWorkersNeeded" /></div>
								<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="substring(startDate, 1, 10)" /></div>
								<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="substring(endDate, 1, 10)" /></div>
								<div class="col-md-2" style="padding-left: 0px;"><xsl:value-of select="substring(lastApplicationDay, 1, 10)" /></div>
								<div class="col-md-1"></div>
								<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageBusinessJobsUrl}?jobId={id}">Hantera</a></div>
								<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/MatchBusinessJobsUrl}?jobId={id}">Matcha</a></div>
							</div>
						</xsl:for-each>
					 </div>
				</div>
			</div>
			
			<div id="business-applications" class="tab-pane fade">
				<div class="panel panel-default">
			  		<div class="panel-heading">
			    		<h3 class="panel-title">Godkända ansökningar</h3>
			  		</div>
				  	<div class="panel-body">
				  		<div class="row">
				  			<div class="col-md-2 bold">Personnummer</div>
				  			<div class="col-md-2 bold">Förnamn</div>
				  			<div class="col-md-2 bold">Efternamn</div>
				  			<div class="col-md-2 bold">Företag</div>
				  			<div class="col-md-2 bold">Yrkestitel</div>
				  			<div class="col-md-1 bold" style="padding-left: 0px;">Datum</div>
				  			<div class="col-md-1 bold"></div>
				  		</div>
				  		<xsl:for-each select="ApprovedBusinessApplications/BusinessSectorJobApplication">
				  			<div class="row application-row">
				  				<div class="col-md-2"><xsl:value-of select="socialSecurityNumber" /></div>
					  			<div class="col-md-2"><xsl:value-of select="firstname" /></div>
					  			<div class="col-md-2"><xsl:value-of select="lastname" /></div>
					  			<div class="col-md-2"><xsl:value-of select="BusinessSectorJob/company" /></div>
					  			<div class="col-md-2"><xsl:value-of select="BusinessSectorJob/workTitle" /></div>
								<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="substring(created, 1, 10)" /></div>
					  			<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageBusinessApplicationUrl}?appId={id}">Hantera</a></div>
				  			</div>
				  		</xsl:for-each>
				  	</div>
				</div>
				<div class="panel panel-default">
			  		<div class="panel-heading">
			    		<h3 class="panel-title">Senast nekade ansökningarna</h3>
			  		</div>
			  		<div class="panel-body">
			  			<div class="row">
			  				<div class="col-md-2 bold">Personnummer</div>
				  			<div class="col-md-2 bold">Förnamn</div>
				  			<div class="col-md-2 bold">Efternamn</div>
				  			<div class="col-md-2 bold">Skola</div>
				  			<div class="col-md-1 bold">Skoltyp</div>
				  			<div class="col-md-1 bold" style="padding-left: 0px;">Postort SKV</div>
				  			<div class="col-md-1 bold" style="padding-left: 0px;">Datum</div>
				  			<div class="col-md-1 bold"></div>
			  			</div>
				  		<xsl:for-each select="UnapprovedBusinessApplications/BusinessSectorJobApplication">
				  			<div class="row application-row">
				  				<div class="col-md-2"><xsl:value-of select="socialSecurityNumber" /></div>
					  			<div class="col-md-2"><xsl:value-of select="firstname" /></div>
					  			<div class="col-md-2"><xsl:value-of select="lastname" /></div>
					  			<div class="col-md-2"><xsl:value-of select="schoolName" /></div>
					  			<div class="col-md-1"><xsl:value-of select="schoolType" /></div>
					  			<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="skvCity" /></div>
					  			<div class="col-md-1" style="padding-left: 0px; padding-right: 0px;"><xsl:value-of select="substring(created, 1, 10)" /></div>
					  			<div class="col-md-1 bold"><a href="{/Document/requestinfo/contextpath}/{/Document/Municipality/ManageBusinessApplicationUrl}?appId={id}">Hantera</a></div>
				  			</div>
				  		</xsl:for-each>
			  		</div>
				</div>
			</div>
		</div>
	</xsl:template>
	
</xsl:stylesheet>					