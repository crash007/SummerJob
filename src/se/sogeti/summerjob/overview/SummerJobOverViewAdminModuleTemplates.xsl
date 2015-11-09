<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">


	
	<xsl:apply-templates select="Municipality"/>
	<xsl:apply-templates select="Business"/>
	
	</xsl:template>
	
	<xsl:template match="Municipality">
		<h3>Kommunala sommarjobb</h3> 
		
		<div class="row">			
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Nya inkomna arbetsplatser</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Förvaltning</th>
	     				<th>Verksamhetsområde</th>
	     				<th>Geografiskt område</th>
	     				<th>Antal platser</th>
	     				<th>Period</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
				   	<xsl:apply-templates select="NewMunicipalityJobs/MunicipalityJob"/>				   	
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  
		  
  		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Godkända arbetsplatser</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Förvaltning</th>
	     				<th>Verksamhetsområde</th>
	     				<th>Geografiskt område</th>
	     				<th>Antal platser</th>
	     				<th>Period</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
				 	 <xsl:apply-templates select="approvedMunicipalityJobs/MunicipalityJob"/>
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  
		  <div class="col-xs-18 col-md-12">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Godkända ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Verksamhetsområde 1</th>				  	
				  		<th>Område 1</th>
				  		<th>Körtkortstyp</th>
				  		<th>Skola</th>
				  		<th>Skoltyp</th>
				  		<th>Postort skv</th>
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:apply-templates select="approvedMunicipalityApplications/MunicipalityApplication"/>
				  <tbody>
				  
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  

		  <div class="col-xs-18 col-md-12">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Okontrollerade ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Verksamhetsområde 1</th>				  	
				  		<th>Område 1</th>
				  		<th>Körtkortstyp</th>
				  		<th>Skola</th>
				  		<th>Skoltyp</th>
				  		<th>Postort skv</th>
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:apply-templates select="unapprovedMunicipalityApplications/MunicipalityApplication"/>
				  <tbody>
				  
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  
		</div>
		</xsl:template>
		
		<xsl:template match="Business">
		
		<h3>Näringslivssommarjobb</h3> 
		<div class="row">			
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Nya arbetsplatser</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Yrke</th>
	     				<th>Företag</th>
	     				<th>Antal platser</th>
	     				<th>Startdatum</th>
	     				<th>Slutdatum</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
				   	<xsl:apply-templates select="NewBusinessJobs/BusinessSectorJob"/>				   	
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Godkända ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Sökt arbete</th>
	     				<th>Skola</th>
	     				<th>Skoltyp</th>				  		
				  		<th>Datum</th>
				  	</tr>
				  </thead>
				  	
				  <tbody>
				  	<xsl:for-each select="ApprovedBusinessApplications/BusinessSectorJobApplication">
				  		<tr>
					  		<td>
					   			<xsl:value-of select="firstname"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="lastname"></xsl:value-of>
					   		</td>				   		
					   		<td>
					   			<xsl:value-of select="BusinessSectorJob/workTitle"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="schoolName"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="schoolType"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="created"></xsl:value-of>
					   		</td>
			   			</tr>
				  	</xsl:for-each>
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  </div>
		  
		  <div class="row">
		  
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Godkända arbetsplatser</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Yrke</th>
	     				<th>Företag</th>
	     				<th>Antal platser</th>
	     				<th>Startdatum</th>
	     				<th>Slutdatum</th>
	     				<th>Godkänd</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
					  <xsl:for-each select="ApprovedBusinessJobs/BusinessSectorJob">
					  	<tr>
							<td>
					   			<xsl:value-of select="workTitle"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="company"/>
					   		</td>
					   		<td>
					   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="startDate"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="endDate"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="controlledDate"></xsl:value-of>
					   		</td>
				   		</tr>
					  </xsl:for-each>
				   				   	
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">icke godkända ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Sökt arbete</th>
	     				<th>Skolnamn</th>
	     				<th>Skoltyp</th>				  		
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:for-each select="UnapprovedBusinessApplications/BusinessSectorJobApplication">
				  		<tr>
					  		<td>
					   			<xsl:value-of select="firstname"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="lastname"></xsl:value-of>
					   		</td>				   		
					   		<td>
					   			<xsl:value-of select="BusinessSectorJob/workTitle"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="schoolName"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="schoolType"></xsl:value-of>
					   		</td>
					   		
					   		<td>
					   			<xsl:value-of select="created"></xsl:value-of>
					   		</td>
				   		</tr>
				  	</xsl:for-each>
				  	
				  <tbody>
				  
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  
		</div>
		
	
	</xsl:template>
	
	<xsl:template match="MunicipalityJob">
		<tr>
			<td>
	   			<xsl:value-of select="organization"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="MunicipalityJobArea/name"/>
	   		</td>
	   		<td>
	   			<xsl:value-of select="GeoArea/name"/>
	   		</td>
	   		<td>
	   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="Period/name"></xsl:value-of>
	   		</td>
	   		<td>
	   			<a href="{/Document/requestinfo/contextpath}/matchjobs?jobId={id}">Matcha</a>
	   		</td>
   		</tr>
	</xsl:template>
	
	<xsl:template match="MunicipalityApplication">
		<tr>
			<td>
	   			<xsl:value-of select="firstname"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="lastname"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="preferedArea1"></xsl:value-of>
	   		</td>	   		
	   		<td>
	   			<xsl:value-of select="preferedGeoArea1"></xsl:value-of>
	   		</td>	   		
	   		<td>
	   			<xsl:value-of select="driversLicenseType"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="schoolName"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="schoolType"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="skvCity"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="created"></xsl:value-of>
	   		</td>
   		</tr>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJob">
		<tr>
			<td>
	   			<xsl:value-of select="workTitle"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="company"/>
	   		</td>
	   		<td>
	   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="startDate"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="endDate"></xsl:value-of>
	   		</td>
   		</tr>
	</xsl:template>
</xsl:stylesheet>					