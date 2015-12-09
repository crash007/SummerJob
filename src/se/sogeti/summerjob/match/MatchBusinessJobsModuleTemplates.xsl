<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
			var isOpen = '<xsl:value-of select="MatchBusinessJob/BusinessSectorJob/isOpen"/>';
		</script>

		<xsl:apply-templates select="MatchBusinessJob"/>

		
	</xsl:template>
	
	<xsl:template match="MatchBusinessJob">
		<div class="well">
			<xsl:apply-templates select="BusinessSectorJob"/>		
			<xsl:apply-templates select="GoodCandidates"/>
			<xsl:apply-templates select="BadCandidates"/>
		</div>
	</xsl:template>
	
	 <xsl:template name="candidatesTableTemplate">
	 	<xsl:param name="header" />
	 	<div class="row" style="margin-top:2em;">
		 	<div class="col-xs-18 col-md-12">
		 		<div class="panel panel-default">
		 			<div class="panel-heading">
			  	 		<h3 class="panel-title"><xsl:value-of select="$header"></xsl:value-of></h3>			
		 			</div>
		 			<div class="panel-body">
	 				  	<xsl:apply-templates select="BusinessSectorJobApplication"/>				  
		 			</div>
		 		</div>
			</div>
		</div>



	 </xsl:template>
	
	 <xsl:template match="GoodCandidates">
		<xsl:call-template name="candidatesTableTemplate" >
			<xsl:with-param name="header" select="'Ansökningar som uppfyller krav på ålder och körkortskrav'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="BadCandidates">
		<xsl:call-template name="candidatesTableTemplate" >
			<xsl:with-param name="header" select="'Ansökningar som ej uppfyller krav på ålder och körkort'" />
		</xsl:call-template>
	</xsl:template>
	
	
	<xsl:template match="BusinessSectorJobApplication">
		<div style="margin-bottom: 8px" class="candidate">
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
				<div class="col-xs-4 col-md-2 bold">Körkort</div>
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
	   			
	   			<div class="col-xs-4 col-md-2 bold">Cv</div>
				<div class="col-md-4 cv">
				<xsl:choose>
				    <xsl:when test="cvFilename !=''">				       
				       <a target="_blank" href="{/Document/requestinfo/contextpath}/{/Document/CvBusinessApplicationUrl}?id={id}">Ladda ner</a>
				    </xsl:when>
				    <xsl:otherwise>
				        CV saknas
				    </xsl:otherwise>
				</xsl:choose>
				
				</div>
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
						<button type="submit" class="btn btn-primary set-matched-btn common-button">Matcha</button>
					</form>
				</div>
				
			</div>
			
		</div>
   			
	</xsl:template>
	
	<xsl:template match="BusinessSectorJob">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Annonsen</h3>
			</div>
			<div class="panel-body">
				
				<div class="row">
					<input type="hidden" id="job-id" value="{id}"/>			
					<div class="col-md-12">	
						<input type="hidden" id="jobIsOpenStatus" value="{isOpen}"></input>
						<xsl:if test="isOpen = 'false'">
							<div class="row">
								<div class="col-xs-8 col-md-4">						
									<h2 style="margin-bottom: 20px; padding-bottom: 15px;" class="prio">Annonsen är stängd</h2>
								</div>
							</div>
						</xsl:if>
					
						<div class="row">
					
							<div class="col-xs-4 col-md-3 bold">Rubrik</div>
							<div class="col-md-3"><xsl:value-of select="workTitle"/></div>
							<div class="col-xs-4 col-md-3 bold">Geografisk plats</div>
							<div class="col-md-3"><xsl:value-of select="GeoArea/name"/></div>
							
						</div>	  	
			 			
			 			<div class="row">
					
							<div class="col-xs-4 col-md-3 bold">Verksamhetsområde</div>
							<div class="col-md-3"><xsl:value-of select="BusinessJobArea/name"/></div>
							<div class="col-xs-4 col-md-3 bold">Antal platser</div>
							<div class="col-md-3"><xsl:value-of select="numberOfWorkersNeeded"/></div>
							
						</div>
						
						<div class="row">
					
							<div class="col-xs-4 col-md-3 bold">Körkort</div>
							<div class="col-md-3"><xsl:value-of select="DriversLicenseType/name"/></div>
							<div class="col-xs-4 col-md-3 bold">Över 18</div>
							<div class="col-md-3">
								<xsl:choose>
									<xsl:when test="mustBeOverEighteen = 'true'">Ja</xsl:when>
									<xsl:otherwise>Nej</xsl:otherwise>
								</xsl:choose>
							</div>
							
						</div>
						<div class="row">
					
							<div class="col-xs-4 col-md-3 bold">Tillsatta platser</div>
							<div class="col-md-3"><xsl:value-of select="appointedApplications"/></div>
							<div class="col-xs-4 col-md-3 bold">Lediga platser</div>
							<div class="col-md-3" id="availableSlotsToMatch"><xsl:value-of select="openApplications"/></div>
							
						</div>
						<div class="row">
					
							<div class="col-xs-4 col-md-3 bold">Address</div>
							<div class="col-md-3"><xsl:value-of select="streetAddress"/></div>
							<div class="col-xs-4 col-md-3 bold">Period</div>
							<div class="col-md-3"><xsl:value-of select="startDate"/><xsl:text> - </xsl:text><xsl:value-of select="endDate"/></div>
							
						</div>	
					
						<div class="row">
							<div class="col-xs-4 col-md-3 bold">Beskrivning</div>
							<div class="col-md-9 col-xs-12"><xsl:value-of select="workDescription"/></div>
						</div>
						
						<div class="mgn-top16px row">
							<div class="col-md-6 col-xs-12">
								<h3>Administrera annonsen</h3>
<!-- 								<button class="col-md-6 col-xs-12 generate-workplace-document-button btn btn-primary" id="generate-workplace-document_{id}">Generera dokument till arbetsplatsen</button> -->
<!-- 								<div class="col-md-1"></div> -->
								<xsl:choose>
									<xsl:when test="isOpen = 'true'">
										<button class="col-md-3 col-xs-6 close-job-button btn btn-danger" id="close-job_{id}">Stäng annons</button>
									</xsl:when>
									<xsl:otherwise>
										<button class="col-md-3 col-xs-6 open-job-button btn btn-success" id="open-job_{id}">Öppna annons</button>
									</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<h3>Matchade sommarjobbare</h3>
								
								<form id="matched-workers-form">
									<div id="matched-applications-container">
										<xsl:for-each select="applications/BusinessSectorJobApplication">
											<xsl:if test="status ='MATCHED'">
												<div class="matched-application">
													<input type="hidden" name="applicationId" value="{id}" />
													<div class="row">
														<div class="col-xs-4 col-md-3 bold">Namn</div>						
														<div class="col-md-9 name"><xsl:value-of select="firstname"/><xsl:text> </xsl:text><xsl:value-of select="lastname"/></div>						
													</div>
													<div class="row">
														<div class="col-xs-4 col-md-3 bold">Personnummer</div>
														<div class="col-md-9 social-number"><xsl:value-of select="socialSecurityNumber"/></div>
													</div>	
													<div class="row">
														<div class="col-xs-4 col-md-3 bold">Telefonnummer</div>
														<div class="col-md-9"><xsl:value-of select="phoneNumber"/></div>
													</div>	
													<div class="row">
														<div class="col-xs-4 col-md-3 bold">E-post</div>
														<div class="col-md-9"><xsl:value-of select="email"/></div>
													</div>
													<div class="mgn-top8px row">
														<div class="col-md-3 col-xs-5 bold">Ranking</div>
														<div class="col-md-2 col-xs-4">
															<input class="form-control ranking-input" type="number" min="1" max="10" value="{ranking}" />
														</div>
														<div class="col-md-2 col-xs-4">
															<button type="submit" class="btn btn-primary save-ranking-btn">Spara</button>
														</div>
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
									<div class="mgn-top8px row">
<!-- 										<div class="col-md-3"></div>											 -->
										<div class="col-md-2">
											<button type="submit" class="btn btn-danger remove-workers-btn common-button">Ta bort</button>
										</div>
										<div class="col-md-2">
											<button type="submit" class="btn btn-warning deny-btn common-button">Neka</button>
										</div>
									</div>
								</form>
							</div>
							<div class="col-md-6 col-xs-12">
								<h3>Tackat nej till detta jobb</h3>
								
								<form id="denied-workers-form">
									<div id="denied-applications-container">
										<xsl:for-each select="applications/BusinessSectorJobApplication">
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
									
									<div class="row mgn-top8px">
										<div class="col-md-9">
											<button type="submit" class="btn btn-primary from-denied-to-matched-btn common-button">Ändra till matchad</button>
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
			
			</div>
		</div>
		
	</xsl:template>		

</xsl:stylesheet>					