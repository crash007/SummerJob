<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1" />

	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri" />';
			var isOpen = '<xsl:value-of select="MatchBusinessJob/BusinessSectorJob/isOpen" />';
		</script>
		<h1 class="header-match">Matcha jobb med ansökningar</h1>
		<xsl:apply-templates select="MatchBusinessJob" />


	</xsl:template>

	<xsl:template match="MatchBusinessJob">
		<xsl:apply-templates select="BusinessSectorJob" />
		

		<div class="candidates-wrapper">
			<xsl:apply-templates select="GoodCandidates" />
			<xsl:apply-templates select="BadCandidates" />
		</div>
		
		<footer class="footer-offset">


			<div class="navbar navbar-fixed-bottom navbar-bottom">
				<div class="container">
					<div class="navbar-collapse" id="footer-body">
						<div class="row">

							<div class="col-xs-6 col-md-3 mgn-top1px">
								<span class="navbar-info">
									Företag:
									<xsl:value-of select="BusinessSectorJob/company" />
								</span>
							</div>


							<div class="col-xs-6 col-md-5 mgn-top1px">
								<span class="navbar-info">
									Rubrik:
									<xsl:value-of select="BusinessSectorJob/workTitle" />
								</span>
							</div>							
							<div class="col-xs-6 col-md-2 mgn-top1px">
								<div class="navbar-info">
									Kvar att matcha:
									<span id="availableToMatch">
										<xsl:value-of select="BusinessSectorJob/openApplications" />
									</span>
								</div>
							</div>

							<div class="col-xs-6 col-md-2 col-md-offset-0">
								<button class="btn btn-primary match-btn pull-right btn-mgn-top">
								<xsl:if test="BusinessSectorJob/isOpen = 'false'">
									<xsl:attribute name="class">btn btn-primary match-btn pull-right btn-mgn-top disabled</xsl:attribute>

									<xsl:attribute name="disabled">disabled</xsl:attribute>
								</xsl:if>
								Matcha</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</footer>
	</xsl:template>

	<xsl:template name="candidatesTableTemplate">
		<xsl:param name="header" />
		<div class="row candidates-container" >
			<div class="col-xs-18 col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<xsl:value-of select="$header"></xsl:value-of>
						</h3>
					</div>
					<div class="panel-body">
						<xsl:apply-templates select="BusinessSectorJobApplication" />
					</div>
				</div>
			</div>
		</div>
	</xsl:template>

	<xsl:template match="GoodCandidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Ansökningar som uppfyller krav på ålder och körkortskrav'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="BadCandidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Ansökningar som ej uppfyller krav på ålder och/eller körkort'" />
		</xsl:call-template>
	</xsl:template>


	<xsl:template match="BusinessSectorJobApplication">
		<div class="mgn-btm8px candidate">
			<div class="row">
				<div class="col-xs-4 col-md-2 bold">Rank</div>
				<div class="col-md-4 rank"><xsl:value-of select="ranking"></xsl:value-of></div>
			</div>
		
			<div class="row">
				<div class="col-xs-4 col-md-2 bold">Namn</div>
				<div class="col-md-4 name">
					<xsl:value-of select="firstname" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="lastname" />
				</div>

				<div class="col-xs-4 col-md-2 bold">Personnummer</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="socialSecurityNumber" />
				</div>
			</div>

			<div class="row">
				

				<div class="col-xs-4 col-md-2 bold">Skola</div>
				<div class="col-md-4">
					<xsl:value-of select="schoolName" />
				</div>
				
				<div class="col-xs-4 col-md-2 bold">Skoltyp</div>
				<div class="col-md-4">
					<xsl:value-of select="schoolType" />
				</div>
			</div>

			<div class="row">
				
				<div class="col-xs-4 col-md-2 bold">Körkort</div>
				<div class="col-md-4">
					<xsl:value-of select="DriversLicenseType/name" />

				</div>
				
				<div class="col-xs-4 col-md-2 bold">Cv</div>
				<div class="col-md-4 cv">
					<xsl:choose>
						<xsl:when test="cvFilename !=''">
							<a target="_blank"
								href="{/Document/requestinfo/contextpath}{/Document/CvBusinessApplicationUrl}?id={id}">Ladda ner</a>
						</xsl:when>
						<xsl:otherwise>
							CV saknas
						</xsl:otherwise>
					</xsl:choose>

				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-4 col-md-2 bold">Personligt brev</div>
				<div class="col-md-8 col-xs-8 social-number">
					<xsl:value-of select="personalLetter" />
				</div>
			</div>

			<div class="row">
				<div class="col-md-6 bold">
					<a href="#" name="show-more">Visa mer</a>
					<a href="#" name="show-less" style="display:none">Minska</a>
				</div>
				<div class="col-xs-5 col-md-2 bold">
					Välj:
				</div>
				<div class="col-xs-5 col-md-4">
					<input class="notMatched" type="checkbox" name="application-id" value="{id}" />
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
					<input type="hidden" id="job-id" name="job-id" value="{id}" />
		
					<div class="col-md-12">
						<input type="hidden" id="jobIsOpenStatus" value="{isOpen}"></input>
						<xsl:if test="isOpen = 'false'">
							<div class="row">
								<div class="col-xs-8 col-md-4">
									<h2 style="margin-bottom: 20px; padding-bottom: 15px;"
										class="prio">Annonsen är stängd</h2>
								</div>
							</div>
						</xsl:if>

						<div class="row">

							<div class="col-xs-4 col-md-3 bold">Företag</div>
							<div class="col-md-3">
								<xsl:value-of select="company" />
							</div>
							<div class="col-xs-4 col-md-3 bold">Organisationsnummer</div>
							<div class="col-md-3">
								<xsl:value-of select="corporateNumber" />
							</div>

						</div>

						<div class="row">

							<div class="col-xs-4 col-md-3 bold">Rubrik</div>
							<div class="col-md-3">
								<xsl:value-of select="workTitle" />
							</div>
							<div class="col-xs-4 col-md-3 bold">Antal platser</div>
							<div class="col-md-3">
								<xsl:value-of select="numberOfWorkersNeeded" />
							</div>
						</div>

						<div class="row">

							<div class="col-xs-4 col-md-3 bold">Körkort</div>
							<div class="col-md-3">
								<xsl:value-of select="DriversLicenseType/name" />
							</div>
							<div class="col-xs-4 col-md-3 bold">Över 18</div>
							<div class="col-md-3">
								<xsl:choose>
									<xsl:when test="mustBeOverEighteen = 'true'">
										Ja
									</xsl:when>
									<xsl:otherwise>
										Nej
									</xsl:otherwise>
								</xsl:choose>
							</div>

						</div>
						<div class="row">

							<div class="col-xs-4 col-md-3 bold">Tillsatta platser</div>
							<div class="col-md-3">
								<xsl:value-of select="matchedApplications" />
							</div>
							<div class="col-xs-4 col-md-3 bold">Lediga platser</div>
							<div class="col-md-3" id="availableSlotsToMatch">
								<xsl:value-of select="openApplications" />
							</div>

						</div>
						<div class="row">

							<div class="col-xs-4 col-md-3 bold">Adress</div>
							<div class="col-md-3">
								<xsl:value-of select="streetAddress" />
							</div>
							<div class="col-xs-4 col-md-3 bold">Period</div>
							<div class="col-md-3">
								<xsl:value-of select="substring(startDate, 1, 10)" />
								<xsl:text> - </xsl:text>
								<xsl:value-of select="substring(endDate, 1, 10)" />
							</div>

						</div>

						<div class="row">
							<div class="col-xs-4 col-md-3 bold">Sista ansökningsdag</div>
							<xsl:choose>
								<xsl:when test="/Document/hasPastLastApplicationDay = 'false'">
									<div class="col-md-3 prio">
										<xsl:value-of select="substring(lastApplicationDay, 1, 10)" />
									</div>
								</xsl:when>
								<xsl:otherwise>
									<div class="col-md-3">
										<xsl:value-of select="substring(lastApplicationDay, 1, 10)" />
									</div>
								</xsl:otherwise>
							</xsl:choose>
						</div>

						<div class="row mgn-top8px">
							<div class="col-xs-4 col-md-3 bold">Beskrivning</div>
							<div class="col-md-9 col-xs-12">
								<xsl:value-of select="workDescription" />
							</div>
						</div>

						<div class="row mgn-top8px">
							<div class="col-xs-4 col-md-3 bold">Intervjuer</div>
							<div class="col-md-5">
								<xsl:choose>
									<xsl:when test="inChargeOfInterviews = 'true'">
										<span class="prio">Arbetsgivaren vill att kommunen tar hand om
											intervjuer</span>
									</xsl:when>
									<xsl:otherwise>
										Arbetsgivaren vill ta hand om intervjuer själv
									</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>

						<div class="row mgn-top8px">
							<div class="col-xs-4 col-md-3 bold">Övriga krav och önskemål</div>
							<div class="col-md-9 col-xs-12">
								<xsl:value-of select="freeTextRequirements" />
							</div>
						</div>
						<xsl:if test="adminNotes != ''">
							<div class="row mgn-top8px">
								<div class="col-xs-4 col-md-3 bold">Handläggarkommentar</div>
								<div class="col-md-9 col-xs-12 prio">
									<xsl:value-of select="adminNotes" />
								</div>
							</div>
						</xsl:if>

						<div id="jobinfo-contact">
							<label>Kontaktuppgifter</label>
							<div class="row mgn-top8px">
								<div class="col-xs-4 col-md-3 bold">Förnamn</div>
								<div class="col-md-3">
									<xsl:value-of select="BusinessSectorManager/firstname" />
								</div>
								<div class="col-xs-4 col-md-3 bold">Efternamn</div>
								<div class="col-md-3">
									<xsl:value-of select="BusinessSectorManager/lastname" />
								</div>
							</div>

							<div class="row">
								<div class="col-xs-4 col-md-3 bold">Telefonnummer</div>
								<div class="col-md-3">
									<xsl:value-of select="BusinessSectorManager/mobilePhone" />
								</div>
								<div class="col-xs-4 col-md-3 bold">E-postadress</div>
								<div class="col-md-3">
									<xsl:value-of select="BusinessSectorManager/email" />
								</div>
							</div>
						</div>

						<div class="mgn-top12px row">
							<div class="col-md-3 col-xs-10">

								<button class="generate-workplace-document-button btn btn-primary"
									id="generate-workplace-document_{id}">Generera dokument till arbetsplatsen</button>
							</div>
							<div class="col-md-3 col-xs-10 col-xs-top-margin">				
								<xsl:choose>
									<xsl:when test="isOpen = 'true'">

										<button class="close-job-button btn btn-danger" id="close-job_{id}">Stäng annons</button>
									</xsl:when>
									<xsl:otherwise>
										<button class="open-job-button btn btn-success" id="open-job_{id}">Öppna annons</button>
									</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>

						<div class="row">
							<xsl:if test="applications/BusinessSectorJobApplication[status ='MATCHED']">
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
															<div class="col-md-9 name">
																<xsl:value-of select="firstname" />
																<xsl:text> </xsl:text>
																<xsl:value-of select="lastname" />
															</div>
														</div>
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">Personnummer</div>
															<div class="col-md-9 social-number">
																<xsl:value-of select="socialSecurityNumber" />
															</div>
														</div>
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">Telefonnummer</div>
															<div class="col-md-9">
																<xsl:value-of select="phoneNumber" />
															</div>
														</div>
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">E-post</div>
															<div class="col-md-9">
																<xsl:value-of select="email" />
															</div>
														</div>
														<div class="mgn-top8px row">
															<div class="col-md-3 col-xs-5 bold">Ranking</div>
															<div class="col-md-2 col-xs-4">
																<input class="form-control ranking-input" type="number"
																	min="1" max="10" value="{ranking}" />
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
											<!-- <div class="col-md-3"></div> -->
											<div class="col-md-2">
												<button type="submit"
													class="btn btn-danger remove-workers-btn common-button">Ta bort</button>
											</div>
											<div class="col-md-2">
												<button type="submit" class="btn btn-warning deny-btn common-button">Neka</button>
											</div>
										</div>
									</form>
								</div>
							</xsl:if>
							
							<xsl:if test="applications/BusinessSectorJobApplication[status='DECLINED']">
								<div class="col-md-6 col-xs-12">
									<h3>Tackat nej till detta jobb</h3>
	
									<form id="denied-workers-form">
										<div id="denied-applications-container">
											<xsl:for-each select="applications/BusinessSectorJobApplication">
												<xsl:if test="status ='DECLINED'">
													<div class="denied-application">
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">Namn</div>
															<div class="col-md-9 name">
																<xsl:value-of select="firstname" />
																<xsl:text> </xsl:text>
																<xsl:value-of select="lastname" />
															</div>
														</div>
														<div class="row">
															<div class="col-xs-4 col-md-3 bold">Personnummer</div>
															<div class="col-md-9 social-number">
																<xsl:value-of select="socialSecurityNumber" />
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
	
										<div class="row mgn-top8px">
											<div class="col-md-9">
												<button type="submit"
													class="btn btn-primary from-denied-to-matched-btn common-button">Ändra till matchad</button>
											</div>
										</div>
									</form>
								</div>
							</xsl:if>
							
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
