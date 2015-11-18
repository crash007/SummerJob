<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>

		<xsl:apply-templates select="MatchMunicipalityJob"/>
<!-- 		<xsl:apply-templates select="MatchMunicipalityApplication"/> -->
		
	</xsl:template>
	
	<xsl:template match="MatchMunicipalityJob">		
		<xsl:apply-templates select="MunicipalityJob"/>		
		<xsl:apply-templates select="Area1AndGeoArea1Candidates"/>		
		<xsl:apply-templates select="Area1AndGeoArea2Candidates"/>		
		<xsl:apply-templates select="Area1AndGeoArea3Candidates"/>
		<xsl:apply-templates select="AnyAreaAndGeoArea1Candidates"/>
		<xsl:apply-templates select="AnyAreaAndGeoArea2Candidates"/>
		<xsl:apply-templates select="AnyAreaAndGeoArea3Candidates"/>
		<xsl:apply-templates select="Area2AndGeoArea1Candidates"/>
		<xsl:apply-templates select="Area3AndGeoArea1Candidates"/>
				
	</xsl:template>
	
	 <xsl:template name="candidatesTableTemplate">
	 	<xsl:param name="header" />
	 	<div class="row" style="margin-top:2em;">
		 	<div class="col-xs-18 col-md-12">
			  	 <h3><xsl:value-of select="$header"></xsl:value-of></h3>			
 				  	<xsl:apply-templates select="MunicipalityJobApplication"/>				  
			  </div>
		  </div>
	 </xsl:template>
	
	<xsl:template match="Area1AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate" >
			<xsl:with-param name="header" select="'Matchande f�rstahandsval p� verksamhetsomr�de och geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Area1AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Matchande f�rstahandsval p� verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Area1AndGeoArea3Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Matchande f�rstahandsval p� verksamhetsomr�de och tredjehandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="AnyAreaAndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Godtyckligt verksamhetsomr�de och f�rstahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="AnyAreaAndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Godtyckligt verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="AnyAreaAndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Godtyckligt verksamhetsomr�de och tredjehandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Area2AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Andrahansval p� verksamhetsomr�de och f�rstahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area2AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Andrahansval p� verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplication">
		<div class="candidate">
			<div class="row">
				<div class="col-xs-4 col-md-2 bold">Namn</div>
				<div class="col-md-4 name">
					<xsl:value-of select="firstname"/>
					<xsl:text> </xsl:text>
		   			<xsl:value-of select="lastname"/>
	   			</div>
				
				<div class="col-xs-4 col-md-2 bold">Personnummer</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="socialSecurityNumber"/>	   		
	   			</div>
	   		</div>
	   		
	   		<div class="row">
				<div class="col-xs-4 col-md-2 bold">K�rkort</div>
				<div class="col-md-4">
					<xsl:value-of select="DriversLicenseType/name"/>
		   			
	   			</div>
				
				<div class="col-xs-4 col-md-2 bold">Skola</div>
				<div class="col-md-4">
					<xsl:value-of select="schoolName"/>	   		
	   			</div>
	   		</div>
	   		
	   		<div class="row">
				<div class="col-xs-4 col-md-2 bold">Skoltyp</div>
				<div class="col-md-4">
					<xsl:value-of select="schoolType"/>
	   			</div>
	   		</div>
	   		<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Omr�de 1</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedArea1/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Omr�de 2</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedArea2/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Omr�de 3</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedArea3/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Plats 1</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedGeoArea1/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Plats 2</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedGeoArea2/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Plats 3</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedGeoArea3/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Personligt brev</div>
				<div class="col-md-4 social-number"><xsl:value-of select="personalLetter"/></div>
			</div>
	   		
		   		<div class="row">
			   		<div class="col-md-8 bold">
			   			<a href="#" name="show-more">Visa mer</a> 
			   			<a href="#" name="show-less" style="display:none">Minska</a>
			   		</div>
				<div class="col-md-4 bold">
					<form name="match-worker">
						<input type="hidden" name="application-id" value="{id}"/>
						<button type="submit" class="btn btn-default set-matched-btn">s�tt matchad</button>
					</form>
				</div>
				
			</div>
			
		</div>
   			
	</xsl:template>
	
	<xsl:template match="MunicipalityJob">
		<div class="row">
			<input type="hidden" id="job-id" value="{id}"/>			
			<div class="col-md-12">	
				<div class="row">
			
					<div class="col-xs-4 col-md-3 bold">Rubrik</div>
					<div class="col-md-3"><xsl:value-of select="workTitle"/></div>
					<div class="col-xs-4 col-md-3 bold">Geografisk plats</div>
					<div class="col-md-3"><xsl:value-of select="GeoArea/name"/></div>
					
				</div>	  	
	 			
	 			<div class="row">
			
					<div class="col-xs-4 col-md-3 bold">Verksamhetsomr�de</div>
					<div class="col-md-3"><xsl:value-of select="MunicipalityJobArea/name"/></div>
					<div class="col-xs-4 col-md-3 bold">Antal platser</div>
					<div class="col-md-3"><xsl:value-of select="numberOfWorkersNeeded"/></div>
					
				</div>
				
				<div class="row">
			
					<div class="col-xs-4 col-md-3 bold">K�rkort</div>
					<div class="col-md-3"><xsl:value-of select="DriversLicenseType/name"/></div>
					<div class="col-xs-4 col-md-3 bold">�ver 18</div>
					<div class="col-md-3"><xsl:value-of select="mustBeOverEighteen"/></div>
					
				</div>
				<div class="row">
			
					<div class="col-xs-4 col-md-3 bold">Tillsatta platser</div>
					<div class="col-md-3"><xsl:value-of select="appointedApplications"/></div>
					<div class="col-xs-4 col-md-3 bold">Lediga platser</div>
					<div class="col-md-3"><xsl:value-of select="openApplications"/></div>
					
				</div>
				<div class="row">
			
					<div class="col-xs-4 col-md-3 bold">Address</div>
					<div class="col-md-3"><xsl:value-of select="streetAddress"/></div>
					<div class="col-xs-4 col-md-3 bold">Period</div>
					<div class="col-md-3"><xsl:value-of select="Period/namn"/></div>
					
				</div>	
			
				<div class="row">
					<div class="col-xs-4 col-md-3 bold">Beskrivning</div>
					<div class="col-md-9 col-xs-12"><xsl:value-of select="workDescription"/></div>
				</div>
				
				<div class="row">
					<div class="col-md-6 col-xs-12">
						<h3>Matchade sommarjobbare</h3>
						
						<form id="matched-workers-form">
							<div id="matched-applications-container">
								<xsl:for-each select="applications/MunicipalityJobApplication">
									<xsl:if test="status ='MATCHED'">
										<div class="matched-application">
											<div class="row">
												<div class="col-xs-4 col-md-3 bold">Namn</div>						
												<div class="col-md-9 name"><xsl:value-of select="firstname"/><xsl:text> </xsl:text><xsl:value-of select="lastname"/></div>						
											</div>
											<div class="row">
												<div class="col-xs-4 col-md-3 bold">Personnummer</div>
												<div class="col-md-9 social-number"><xsl:value-of select="socialSecurityNumber"/></div>
											</div>										
											<div class="row">		
												<div class="col-md-3">Markera</div>										
												<div class="col-md-9">
										          <input type="checkbox" name="application-id" value="{id}"></input>								        
												</div>
											</div>
										</div>
									</xsl:if>
								</xsl:for-each>
								
							</div>
							<div class="row">
								<div class="col-md-3"></div>												
								<div class="col-md-2">
									<button type="submit" class="btn btn-default remove-workers-btn">Ta bort</button>
								</div>
								<div class="col-md-2">
									<button type="submit" class="btn btn-default deny-btn">Neka</button>
								</div>
							</div>
						</form>
					</div>
					<div class="col-md-6 col-xs-12">
						<h3>Tackat nej till detta jobb</h3>
						
						<form id="denied-workers-form">
							<div id="denied-applications-container">
								<xsl:for-each select="applications/MunicipalityJobApplication">
									<xsl:if test="status ='DENIED'">
										<div class="denied-application">
											<div class="row">
												<div class="col-xs-4 col-md-3 bold">Namn</div>						
												<div class="col-md-9 name"><xsl:value-of select="firstname"/><xsl:text> </xsl:text><xsl:value-of select="lastname"/></div>						
											</div>
											<div class="row">
												<div class="col-xs-4 col-md-3 bold">Personnummer</div>
												<div class="col-md-9 social-number"><xsl:value-of select="socialSecurityNumber"/></div>
											</div>
											
											<div class="row">		
												<div class="col-md-3">Markera</div>										
												<div class="col-md-9">
										          <input type="checkbox" name="application-id" value="{id}"></input>								        
												</div>
											</div>
										</div>
									</xsl:if>
								</xsl:for-each>
								
							</div>
							
							<div class="row">
								<div class="col-md-3"></div>												
								<div class="col-md-9">
									<button type="submit" class="btn btn-default from-denied-to-matched-btn">�ndra till matchad</button>
								</div>
							</div>
						</form>
					</div>
				 </div>
				
				<div id="matched-application-template" class="collapse">
					<div class="matched-application">
						<div class="row">
							<div class="col-xs-4 col-md-3 bold">Namn</div>						
							<div class="col-md-9 name"></div>						
						</div>
						<div class="row">
							<div class="col-xs-4 col-md-3 bold">Personnummer</div>
							<div class="col-md-9 social-number"></div>
						</div>
						<div class="row">		
							<div class="col-md-3">Markera</div>										
							<div class="col-md-9">
					          <input type="checkbox" name="application-id" value=""></input>								        
							</div>
						</div>
					</div>
				</div>
			</div>
	  	</div>
	</xsl:template>		

</xsl:stylesheet>					