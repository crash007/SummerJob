<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
			var freeSlots = '<xsl:value-of select="FreeSlots"/>';
		</script>
		
		<xsl:apply-templates select="MatchMunicipalityJob"/>
<!-- 		<xsl:apply-templates select="MatchMunicipalityApplication"/> -->
		
	</xsl:template>
	
	<xsl:template match="MatchMunicipalityJob">
		<div class="well">
			<xsl:apply-templates select="MunicipalityJob"/>		
			<xsl:apply-templates select="Area1AndGeoArea1Candidates"/>		
			<xsl:apply-templates select="Area1AndGeoArea2Candidates"/>		
			<xsl:apply-templates select="Area1AndGeoArea3Candidates"/>
			<xsl:apply-templates select="AnyAreaAndGeoArea1Candidates"/>
			<xsl:apply-templates select="AnyAreaAndGeoArea2Candidates"/>
			<xsl:apply-templates select="AnyAreaAndGeoArea3Candidates"/>
			<xsl:apply-templates select="Area2AndGeoArea1Candidates"/>
			<xsl:apply-templates select="Area3AndGeoArea1Candidates"/>
		</div>
				
	</xsl:template>
	
	 <xsl:template name="candidatesTableTemplate">
	 	<xsl:param name="header" />
		 	<div style="padding-top: 16px" class="row">
			 	<div class="col-xs-18 col-md-12">
			 		<div class="panel panel-default">
			 			<div class="panel-heading">
						  	 <h3 class="panel-title" style="margin-top: 0px"><xsl:value-of select="$header"></xsl:value-of></h3>			
			 			</div>
			 			<div class="panel-body">
		 				  <xsl:apply-templates select="MunicipalityJobApplication"/>				  
			 			</div>
			 		</div>
				  </div>
			  </div>
	 </xsl:template>
	
	<xsl:template match="Area1AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate" >
			<xsl:with-param name="header" select="'Matchande förstahandsval på verksamhetsområde och geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Area1AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Matchande förstahandsval på verksamhetsområde och andrahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Area1AndGeoArea3Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Matchande förstahandsval på verksamhetsområde och tredjehandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="AnyAreaAndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Godtyckligt verksamhetsområde och förstahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="AnyAreaAndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Godtyckligt verksamhetsområde och andrahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="AnyAreaAndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Godtyckligt verksamhetsområde och tredjehandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Area2AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Andrahansval på verksamhetsområde och förstahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area2AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header" select="'Andrahansval på verksamhetsområde och andrahandsval på geografiskt område'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplication">
		<div style="margin-bottom: 8px" class="candidate">
			<div class="row">
				<div class="col-xs-4 col-md-2 bold">Namn</div>
				<div class="col-md-4 name">
					<xsl:value-of select="firstname"/>
					<xsl:text> </xsl:text>
		   			<xsl:value-of select="lastname"/>
	   			</div>
				
				<div class="col-xs-4 col-md-2 bold">Personnummer</div>
				<div class="col-md-3 social-number">
					<xsl:value-of select="socialSecurityNumber"/>	   		
	   			</div>
	   			
	   			<xsl:if test="applicationType = 'REGULAR_ADMIN'">
		   			<div style="text-align: right; font-size: larger" class="col-xs-1 bold">*</div>
	   			</xsl:if>
	   			<xsl:if test="applicationType = 'PRIO'">
		   			<div style="text-align: right; font-size: larger" class="col-xs-1 bold prio">*</div>
	   			</xsl:if>
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
				       <a target="_blank" href="{/Document/requestinfo/contextpath}/{/Document/CvMunicipalityApplicationUrl}?id={id}">Ladda ner</a>
				    </xsl:when>
				    <xsl:otherwise>
				        CV saknas
				    </xsl:otherwise>
				</xsl:choose>
				
				</div>
	   			
	   		</div>
	   		
	   		<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Område 1</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedArea1/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Område 2</div>
				<div class="col-md-4 social-number"><xsl:value-of select="preferedArea2/name"/></div>
			</div>
			
			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Område 3</div>
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
							<button type="submit" class="btn btn-primary set-matched-btn">Matcha</button>
					</form>
				</div>
			</div>
		</div>
   			
	</xsl:template>
	
	<xsl:template match="MunicipalityJob">
		<div class="panel panel-default">
			<div class="panel-heading"><h3 class="panel-title">Annonsen</h3></div>
			<div class="panel-body">
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
					
							<div class="col-xs-4 col-md-3 bold">Verksamhetsområde</div>
							<div class="col-md-3"><xsl:value-of select="MunicipalityJobArea/name"/></div>
							<div class="col-xs-4 col-md-3 bold">Antal platser</div>
							<div class="col-md-3"><xsl:value-of select="numberOfWorkersNeeded"/></div>
							
						</div>
						
						<div class="row">
					
							<div class="col-xs-4 col-md-3 bold">Körkort</div>
							<div class="col-md-3">
								<xsl:choose>
									<xsl:when test="DriversLicenseType">Ja, <xsl:value-of select="DriversLicenseType/name"/></xsl:when>
									<xsl:otherwise>Nej</xsl:otherwise>
								</xsl:choose>
							</div>
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
							<div class="col-md-3"><xsl:value-of select="Period/name"/></div>
							
						</div>	
					
						<div class="row">
							<div class="col-xs-4 col-md-3 bold">Beskrivning</div>
							<div class="col-md-9 col-xs-12"><xsl:value-of select="workDescription"/></div>
						</div>
						
						<div class="mgn-top16px row">
							<div class="col-md-6 col-xs-12">
								<h3>Administrera annonsen</h3>
								<button class="col-md-6 col-xs-12 generate-workplace-document-button btn btn-primary" id="generate-workplace-document_{id}">Generera dokument till arbetsplatsen</button>
								<div class="col-md-1"></div>
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
										<xsl:variable name="mentorslist" select="mentors"></xsl:variable>
										<xsl:for-each select="applications/MunicipalityJobApplication">
											<xsl:if test="status ='MATCHED'">
												<div style="margin-top: 8px" class="matched-application">
													<xsl:if test="applicationType = 'REGULAR_ADMIN'">
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">Typ</div> 
															<div class="col-xs-6 bold">Inlagd av admin</div>
														</div>
													</xsl:if>
													<xsl:if test="applicationType = 'PRIO'">
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">Typ</div> 
															<div class="col-xs-4 bold prio">Prioriterad</div>
														</div>
													</xsl:if>
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
														<div class="col-md-9 name"><xsl:value-of select="phoneNumber"/></div>						
													</div>
													<div class="row">
														<div class="col-xs-4 col-md-3 bold">E-post</div>						
													<div class="col-md-9 name"><xsl:value-of select="email"/></div>						
													</div>
													<div class="mgn-top8px row">
														<div class="col-xs-4 col-md-2 bold">Handledare</div>
														<div class="col-md-5 col-xs-8 mentor-select-div">
															<select id="personal-mentor_{id}" class="form-control personal-mentor">
																<xsl:variable name="personalMentorId" select="personalMentorId"></xsl:variable>
																<option value=""></option>
																<xsl:for-each select="$mentorslist/MunicipalityMentor">
																		<xsl:choose>
																			<xsl:when test="$personalMentorId = id">
																				<option selected="selected" value="{id}"><xsl:value-of select="firstname"/><xsl:text> </xsl:text><xsl:value-of select="lastname"/></option>
																			</xsl:when>
																			<xsl:otherwise>
																				<option value="{id}"><xsl:value-of select="firstname"/><xsl:text> </xsl:text><xsl:value-of select="lastname"/></option>
																			</xsl:otherwise>
																		</xsl:choose>
																</xsl:for-each>
															</select>
														</div>
													</div>
													<div class="mgn-top8px row">
														<div class="col-md-3 col-xs-5 bold">Tid för samtal</div>
														<div class="col-md-4 col-xs-8">
															<input type="text" id="timeForInfo_{id}" class="form-control timeForInfo" value="{timeForInformation}"></input>
														</div>
													</div>
													<div class="mgn-top8px row">
														<div class="col-md-3 col-xs-5 bold">Status</div>
														<div class="col-md-4 col-xs-8 status-select-div">
															<select id="call-status_{id}" class="form-control call-status">
																<option value="NONE"></option>
																<xsl:choose>
																	<xsl:when test="callStatus = 'READY_TO_BE_CALLED'">
																		<option selected="selected" value="READY_TO_BE_CALLED">Redo att kallas</option>
																	</xsl:when>
																	<xsl:otherwise>
																		<option value="READY_TO_BE_CALLED">Redo att kallas</option>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="callStatus = 'HAS_BEEN_CALLED'">
																		<option selected="selected" value="HAS_BEEN_CALLED">Kallad</option>
																	</xsl:when>
																	<xsl:otherwise>
																		<option value="HAS_BEEN_CALLED">Kallad</option>
																	</xsl:otherwise>
																</xsl:choose>
															</select>
														</div>
													</div>
													<div class="mgn-top8px row">
														<div class="col-md-2 col-xs-4"><button class="save-application-options btn btn-primary" id="{id}">Spara</button></div>
													</div>
													<div class="mgn-top16px row">
<!-- 														<form> -->
														<div class="col-md-2 col-xs-4 bold">Dokument</div>
														<div class="col-md-6 col-xs-12">
															<xsl:choose>
																<xsl:when test="timeForInformation != '' and callStatus != 'NONE'">
																	<select id="generate-document-select_{id}" class="form-control generate-document-select">
																		<option value=""></option>
																		<option value="ALL">Generera aktuella dokument</option>
																		<option value="kallelse">Kallelse</option>
																		<option value="bekraftelse">Bekräftelse för anställning</option>
																		<option value="anstallningsbevis">Anställningsbevis</option>
																		<option value="tjanstgoringsrapport">Tjänstgöringsrapport</option>
																		<option value="bank">Löntagaruppgifter</option>
																		<option value="skattebefrielse">Lön utan skatteavdrag</option>
																		<option value="belastningsregister">Utdrag ur belastningsregistret</option>
																	</select>
																</xsl:when>
																<xsl:otherwise>
																	<select disabled="disabled" id="generate-document-select_{id}" class="form-control"></select>
																</xsl:otherwise>
															</xsl:choose>
														</div>
														<div class="col-md-2 col-xs-4"><button disabled="disabled" class="generate-document-button btn btn-primary" id="generate-document_{id}">Generera</button></div>
<!-- 														</form> -->
													</div>
													<div class="mgn-top8px row">		
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
										<div class="col-md-2">
											<button type="submit" class="btn btn-danger remove-workers-btn">Ta bort</button>
										</div>
										<div class="col-md-2">
											<button type="submit" class="btn btn-warning deny-btn">Tackat nej</button>
										</div>
									</div>
								</form>
							</div>
							<div class="col-md-6 col-xs-12">
								<h3>Personer som tackat nej</h3>
								
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
<!-- 										<div class="col-md-3"></div>												 -->
										<div class="col-md-9">
											<button type="submit" class="btn btn-primary from-denied-to-matched-btn">Ändra till matchad</button>
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