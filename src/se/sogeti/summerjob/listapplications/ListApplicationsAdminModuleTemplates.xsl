<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<h1>Lista ansökningar</h1>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Sök</h3>
			</div>
			<div class="panel-body">
				<form method="POST" role="form" id="search-applications-form" data-toggle="validator">
					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<label>Personnummer</label>
								<input type="text" name="socialSecurityNumber" value="{SocialSecurityNumber}" placeholder="" class="form-control numberValidation" data-error="ÅÅÅÅMMDDxxxx" maxlength="12" />
								<p class="help-block with-errors">ÅÅÅÅMMDDxxxx</p>
							</div>
							<div class="col-md-3">
								<label>Förnamn</label>
								<input type="text" name="firstname" value="{Firstname}" placeholder="" class="form-control" data-error="Förnamn" maxlength="12" />									
							</div>
							<div class="col-md-3">
								<label>Efternamn</label>
								<input type="text" name="lastname" value="{Lastname}" placeholder="" class="form-control" data-error="Efternamn" maxlength="12" />
							</div>
							<div class="col-md-3">
								<label>Fritext i personligt brev</label>
								<input type="text" name="personalLetter" value="{PersonalLetter}" placeholder="" class="form-control" data-error="Personligt brev" />
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-6 col-sm-6">
								<button class="btn btn-primary" type="submit">Skicka</button>
								<button class="btn btn-warning" id="clear-search-button" type="submit">Nollställ</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<h2>Godkända ansökningar</h2>
		
		<div class="row">
			<div class="col-md-6 col-xs-12">
				<xsl:apply-templates select="ApprovedMunicipality"></xsl:apply-templates>
			</div>
			<div class="col-md-6 col-xs-12">
				<xsl:apply-templates select="ApprovedBusiness"></xsl:apply-templates>
				
			</div>
		</div>
		
		<h2>Nekade ansökningar</h2>
		
		<div class="row">
			<div class="col-md-6 col-xs-12">
				<xsl:apply-templates select="DisapprovedMunicipality"></xsl:apply-templates>
			</div>
			<div class="col-md-6 col-xs-12">
				<xsl:apply-templates select="DisapprovedBusiness"></xsl:apply-templates>
			</div>
		</div>
		
		<h2>Tackat nej</h2>
		
		<div class="row">
			<div class="col-md-6 col-xs-12">
				<xsl:apply-templates select="DeclinedMunicipality"></xsl:apply-templates>
			</div>
			<div class="col-md-6 col-xs-12">
				<xsl:apply-templates select="DeclinedBusiness"></xsl:apply-templates>
			</div>
		</div>
		
	</xsl:template>
	
	<xsl:template match="ApprovedMunicipality">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Kommunala jobbansökningar</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Rank</div>
					<div class="col-xs-3 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="MunicipalityJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="DisapprovedMunicipality">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Kommunala jobbansökningar</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Rank</div>
					<div class="col-xs-3 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="MunicipalityJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="ApprovedBusiness">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Näringslivsjobbansökningar</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Rank</div>
					<div class="col-xs-3 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="BusinessSectorJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="DisapprovedBusiness">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Näringslivsjobbansökningar</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Rank</div>
					<div class="col-xs-3 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="BusinessSectorJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="DeclinedMunicipality">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Kommunala jobbansökningar</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Rank</div>
					<div class="col-xs-3 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="MunicipalityJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="DeclinedBusiness">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Näringslivsjobbansökningar</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-1 bold">Rank</div>
					<div class="col-xs-3 bold">Personnummer</div>
					<div class="col-xs-3 bold">Namn</div>
				</div>
				<xsl:apply-templates select="BusinessSectorJobApplication"></xsl:apply-templates>
			</div>
		</div>		
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplication">
		<div class="application-list-row">
			<div class='row application-info-row'>
				<input class="applicationURL" type="hidden" value="{url}" ></input>
				<div class="col-xs-1"><xsl:value-of select="ranking" /></div>
				<div class="col-xs-3"><xsl:value-of select="socialSecurityNumber" /></div>
				<div class="col-xs-6"><xsl:value-of select="name" /></div>
				<div style="padding-left: 0px; padding-right: 0px;" class="col-xs-1">
					<xsl:if test="applicationType = 'REGULAR_ADMIN'">
<!-- 						<span style="font-size: medium">(A) </span> -->
						<span>(A) </span>
					</xsl:if>
					<xsl:if test="applicationType = 'PRIO'">
						<span class="prio">(P) </span>
					</xsl:if>
					<xsl:if test="controlledByUser and approved = 'false'">
						<span>(K)</span>					
					</xsl:if>
				</div>
				<div class="col-xs-1 more-information-arrow pull-right"><span class="arrow-down glyphicon glyphicon-chevron-down"></span><span class="arrow-up glyphicon glyphicon-chevron-up hidden"></span></div>
			</div>
			<div class="hidden application-more-info-row">
				
				<div class="row">
					<div class="col-xs-4 bold">Ansökningstyp</div>
					<div class="col-xs-6">
						<xsl:choose>
							<xsl:when test="applicationType = 'PRIO'">
								<span class="prio bold">Prioriterad</span>
							</xsl:when>
							<xsl:otherwise>
								<xsl:if test="applicationType = 'REGULAR_ADMIN'">
									<span class="bold">Inlagd av admin</span>
								</xsl:if>
								<xsl:if test="applicationType = 'REGULAR'">
									<i>Vanlig</i>
								</xsl:if>						
							</xsl:otherwise>
						</xsl:choose>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-4 bold">Kontrollerad av</div>
					<div class="col-xs-6">
						<xsl:choose>
							<xsl:when test="controlledByUser = 'System'">
								<i><xsl:value-of select="controlledByUser" /></i>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="controlledByUser" />
							</xsl:otherwise>
						</xsl:choose>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-4 bold">Kontrolldatum</div>
					<div class="col-xs-6"><xsl:value-of select="controlledDate" /></div>
				</div>
				<div class="row">
					<div class="col-xs-4 bold">Cv</div>
					 <xsl:if test="hasCv='true'">
						<div class="col-xs-6"><a target="_blank" href="{/Document/requestinfo/contextpath}{/Document/CvMunicipalityApplicationUrl}?id={id}">Ladda ner</a></div>
					</xsl:if>
					 <xsl:if test="hasCv='false'">
					 	<div class="col-xs-6">Cv saknas</div>
					 </xsl:if>
				</div>
				<div class="row">
					<div class="col-xs-5">
						<a href="{url}" class="bold">Hantera ansökan</a>
					</div>
				</div>
				<xsl:if test="matchUrl != ''">
					<div class="row">
						<div class="col-xs-5">
							<a href="{matchUrl}" class="bold">Matcha jobbet</a>
						</div>
					</div>
				</xsl:if>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobApplication">
		<div class="application-list-row">
			<div class="row application-info-row">
				<div class="col-xs-1"><xsl:value-of select="ranking" /></div>
				<div class="col-xs-3"><xsl:value-of select="socialSecurityNumber" /></div>
				<div class="col-xs-6"><xsl:value-of select="name" /></div>
				<div style="padding-left: 0px; padding-right: 0px;" class="col-xs-1">
					<xsl:if test="controlledByUser and approved = 'false'">
						<span>(K)</span>					
					</xsl:if>
				</div>
				<div class="col-xs-1 more-information-arrow pull-right"><span class="arrow-down glyphicon glyphicon-chevron-down"></span><span class="arrow-up glyphicon glyphicon-chevron-up hidden"></span></div>
			</div>
			<div class="hidden application-more-info-row">

					<div class="row">
						<div class="col-xs-4 bold">Kontrollerad av</div>
						<div class="col-xs-6">
							<xsl:choose>
								<xsl:when test="controlledByUser = 'System'">
									<i><xsl:value-of select="controlledByUser" /></i>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="controlledByUser" />
								</xsl:otherwise>
							</xsl:choose>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4 bold">Kontrolldatum</div>
						<div class="col-xs-6"><xsl:value-of select="controlledDate" /></div>
					</div>
					<div class="row">
						<div class="col-xs-4 bold">Företag</div>
						<div class="col-xs-6"><xsl:value-of select="Company"></xsl:value-of></div>
					</div>
					<div class="row">
						<div class="col-xs-4 bold">Yrkestitel</div>
						<div class="col-xs-6"><xsl:value-of select="WorkTitle"></xsl:value-of></div>
					</div>
					<div class="row">
						<div class="col-xs-4 bold">Cv</div>
						 <xsl:if test="hasCv='true'">
							<div class="col-xs-6"><a target="_blank" href="{/Document/requestinfo/contextpath}{/Document/CvBusinessApplicationUrl}?id={id}">Ladda ner</a></div>
						</xsl:if>
						 <xsl:if test="hasCv='false'">
						 	<div class="col-xs-6">Cv saknas</div>
						 </xsl:if>
					</div>
					<div class="row">
						<div class="col-xs-5">
							<a href="{url}" class="bold">Hantera ansökan</a>
						</div>
					</div>
					
					<xsl:if test="matchUrl != ''">
						<div class="row">
							<div class="col-xs-5">
								<a href="{matchUrl}" class="bold">Matcha jobbet</a>
							</div>
						</div>
					</xsl:if>
				
			</div>
		</div>
	</xsl:template>
	
</xsl:stylesheet>					