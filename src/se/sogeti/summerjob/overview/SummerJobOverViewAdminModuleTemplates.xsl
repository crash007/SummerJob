<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
	<xsl:apply-templates select="OverView"/>
	</xsl:template>
	
	<xsl:template match="OverView">
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
	     				<th>Skapad</th>
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
	     				<th>Chef</th>
	     				<th>Antal platser</th>
	     				<th>Tillsatta platser</th>
	     				<th>Tilldela sommarjobbare</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
				  <xsl:for-each select="ApprovedJobs/Job">
				  <tr>
			   			<xsl:value-of select="location"></xsl:value-of>
			   		</tr>
			   		<tr>
			   			<xsl:value-of select="manager"></xsl:value-of>
			   		</tr>
			   		<tr>
			   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
			   		</tr>
			   		<tr>
			   			<xsl:value-of select="numberOfOccupiedWorkers"></xsl:value-of>
			   		</tr>
			   		
				  </xsl:for-each>
				   				   	
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  
		  <div class="col-xs-18 col-md-12">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Nya ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Verksamhetsområde 1</th>
				  		<th>Verksamhetsområde 2</th>
				  		<th>Verksamhetsområde 3</th>
				  		<th>Område 1</th>
				  		<th>Område 2</th>
				  		<th>Område 3</th>
				  		<th>Körtkortstyp</th>
				  		
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:apply-templates select="NewMunicipalityApplications/MunicipalityApplication"/>
				  <tbody>
				  
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
	     				<th>Område 1</th>
				  		<th>Område 2</th>
				  		<th>Område 3</th>
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:apply-templates select="ApprovedApplications/Application"/>
				  <tbody>
				  
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  
		</div>
		
		
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
				  		<th>Plats</th>
	     				<th>Chef</th>
	     				<th>Antal platser</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
				   	<xsl:apply-templates select="NewBusinessSectorJobs/Job"/>				   	
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Nya ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Sökt arbete</th>				  		
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:for-each select="NewBusinessSectorJobs/Application">
				  		<tr>
				   			<xsl:value-of select="firstname"></xsl:value-of>
				   		</tr>
				   		<tr>
				   			<xsl:value-of select="lastname"></xsl:value-of>
				   		</tr>				   		
				   		<tr>
				   			<xsl:value-of select="appliedJobName"></xsl:value-of>
				   		</tr>
				   		<tr>
				   			<xsl:value-of select="created"></xsl:value-of>
				   		</tr>
				  	</xsl:for-each>
				  	
				  <tbody>
				  
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
				  		<th>Plats</th>
	     				<th>Chef</th>
	     				<th>Antal platser</th>
	     				<th>Tillsatta platser</th>
				  	</tr>
				  </thead>
				  
				  <tbody>
				  <xsl:for-each select="ApprovedBusinessSectorJobs/Job">
				  <tr>
			   			<xsl:value-of select="location"></xsl:value-of>
			   		</tr>
			   		<tr>
			   			<xsl:value-of select="manager"></xsl:value-of>
			   		</tr>
			   		<tr>
			   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
			   		</tr>
			   		<tr>
			   			<xsl:value-of select="numberOfOccupiedWorkers"></xsl:value-of>
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
			    <h3 class="panel-title">Godkända ansökningar</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>
	     				<th>Sökt arbete</th>				  		
				  		<th>Datum</th>
				  		
				  	</tr>
				  </thead>
				  	<xsl:for-each select="ApprovedBusinessSectorJobs/Application">
				  		<tr>
				   			<xsl:value-of select="firstname"></xsl:value-of>
				   		</tr>
				   		<tr>
				   			<xsl:value-of select="lastname"></xsl:value-of>
				   		</tr>				   		
				   		<tr>
				   			<xsl:value-of select="appliedJobName"></xsl:value-of>
				   		</tr>
				   		<tr>
				   			<xsl:value-of select="created"></xsl:value-of>
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
	   			<xsl:value-of select="created"></xsl:value-of>
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
	   			<xsl:value-of select="preferedArea2"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="preferedArea3"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="preferedGeoArea1"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="preferedGeoArea2"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="preferedGeoArea3"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="driversLicenseType"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="created"></xsl:value-of>
	   		</td>
   		</tr>
	</xsl:template>
	
</xsl:stylesheet>					