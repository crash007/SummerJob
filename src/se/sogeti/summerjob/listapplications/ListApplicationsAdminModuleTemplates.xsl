<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		
		<div class="well">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Ansökningar efter personnummer</h3>
				</div>
				<div class="panel-body">
					<form method="POST" role="form" id="search-applications-form" data-toggle="validator">
						<div class="row">
							<div class="form-group">
								<div class="col-md-3">
									<input type="text" name="socialSecurityNumber" value="" placeholder="" class="numberValidation form-control" data-error="ÅÅÅÅMMDDxxxx" data-minlength="12" maxlength="12" />
									<p class="help-block with-errors">ÅÅÅÅMMDDxxxx</p>
								</div>
								<div style="padding-left: 0px" class="col-md-2">
									<button type="submit" class="btn btn-primary">Skicka</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-6 col-xs-12">
					<xsl:apply-templates select="ApprovedMunicipality"></xsl:apply-templates>
				</div>
				<div class="col-md-6 col-xs-12">
					<xsl:apply-templates select="DisapprovedMunicipality"></xsl:apply-templates>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 col-xs-12">
					<xsl:apply-templates select="ApprovedBusiness"></xsl:apply-templates>
				</div>
				<div class="col-md-6 col-xs-12">
					<xsl:apply-templates select="DisapprovedBusiness"></xsl:apply-templates>
				</div>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="ApprovedMunicipality">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Godkända ansökningar till kommunala jobb</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Prio</div>
					<div class="col-xs-4 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="MunicipalityJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="DisapprovedMunicipality">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Nekade ansökningar till kommunala jobb</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Prio</div>
					<div class="col-xs-4 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="MunicipalityJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="ApprovedBusiness">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Godkända ansökningar till näringslivsjobb</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Prio</div>
					<div class="col-xs-4 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="BusinessSectorJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="DisapprovedBusiness">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Nekade ansökningar till näringslivsjobb</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Prio</div>
					<div class="col-xs-4 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="BusinessSectorJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplication">
		<div>
			<div class='row application-row municipality-application-row'>
				<input class="applicationURL" type="hidden" value="{url}" ></input>
				<div class="col-xs-1"><xsl:value-of select="ranking" /></div>
				<div class="col-xs-4"><xsl:value-of select="socialSecurityNumber" /></div>
				<div class="col-xs-6"><xsl:value-of select="name" /></div>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobApplication">
		<div>
			<div class='row application-row business-application-row'>
				<div class="col-xs-1"><xsl:value-of select="ranking" /></div>
				<div class="col-xs-4"><xsl:value-of select="socialSecurityNumber" /></div>
				<div class="col-xs-6"><xsl:value-of select="name" /></div>
				<div class="col-xs-1 more-information-arrow"><span class="arrow-down glyphicon glyphicon-chevron-down"></span><span class="arrow-up glyphicon glyphicon-chevron-up hidden"></span></div>
			</div>
			<div class="row hidden more-information">
				<div style="margin: 8px; padding: 8px" class="well">
					<div class="row">
						<div class="col-xs-2 bold">Företag</div>
						<div class="col-xs-3"><xsl:value-of select="BusinessSectorJob/company"></xsl:value-of></div>
					</div>
					<div class="row">
						<div class="col-xs-2 bold">Yrkestitel</div>
						<div class="col-xs-6"><xsl:value-of select="BusinessSectorJob/workTitle"></xsl:value-of></div>
					</div>
					<div class="row">
						<div class="col-xs-5">
							<a href="#" class="bold">Hantera ansökan</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</xsl:template>
	
</xsl:stylesheet>					