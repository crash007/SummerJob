<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">

		<xsl:apply-templates select="MatchMunicipalityJob"/>
		<xsl:apply-templates select="MatchMunicipalityApplication"/>
		
	</xsl:template>
	
	<xsl:template match="MatchMunicipalityJob">
		
		<xsl:apply-templates select="MunicipalityJob"/>
		<xsl:apply-templates select="AppointedApplications"/>
		<xsl:apply-templates select="MunicipalityApplicationFirstPickCandidates"/>		
		<xsl:apply-templates select="MunicipalityApplicationSecondPickCandidates"/>		
		<xsl:apply-templates select="MunicipalityApplicationThirdPickCandidates"/> 	
		
	</xsl:template>
	
	 <xsl:template name="candidatesTableTemplate">
	 	<xsl:param name="header" />
	 	<div class="col-xs-18 col-md-12">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title"><xsl:value-of select="$header"></xsl:value-of></h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Förnamn</th>
	     				<th>Efternamn</th>	     				
	     			
				  		<th>Körtkortstyp</th>
				  		<th>Skola</th>
				  		<th>Skoltyp</th>
				  		<th>Postort skv</th>
				  		<th>Datum</th>
				  	</tr>
				  </thead>

				  <tbody>
 				  	<xsl:apply-templates select="MunicipalityJobApplication"/>				  
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
	 </xsl:template>
	
	<xsl:template match="MunicipalityApplicationFirstPickCandidates">
		<xsl:call-template name="candidatesTableTemplate" >
			<xsl:with-param name="header" select="'Matchande förstahandsval på verksamhetsområde och geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MunicipalityApplicationSecondPickCandidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Matchande förstahandsval på verksamhetsområde och andrahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MunicipalityApplicationThirdPickCandidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Matchande andrahandsval på verksamhetsområde och förstahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplication">
		<tr>
			<td>
	   			<xsl:value-of select="firstname"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="lastname"></xsl:value-of>
	   		</td>
	 	
	   		<td>
	   			<xsl:value-of select="DriversLicenseType/name"></xsl:value-of>
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
	
	<xsl:template match="MunicipalityJob">
		
		<div class="well">
			<div class="row">
			<form class="form-horizontal">
				<div class="form-group">
				    <label class="col-sm-2 control-label">Rubrik</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="workTitle"/></p>
				    </div>
			  	</div>
			  	<div class="form-group">
				    <label class="col-sm-2 control-label">Geografisk plats</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="GeoArea/name"/></p>
				    </div>
			  	</div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Verksamhetsområde</label>
			    <div class="col-sm-10">
			      <p class="form-control-static"><xsl:value-of select="MunicipalityJobArea/name"/></p>
			    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Beskrivning</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="workDescription"/></p>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Antal platser</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="numberOfWorkersNeeded"/></p>
				    </div>
			  </div>
			   <div class="form-group">
				    <label class="col-sm-2 control-label">Tillsatta platser</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="appointedApplications"/></p>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Lediga platser</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="openApplications"/></p>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Address</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="streetAddress"/></p>
				    </div>
			  </div>
			</form>
			</div>
	  	</div>
	</xsl:template>		

</xsl:stylesheet>					