<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1" />

	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri" />';
			
			var isOpen = '<xsl:value-of select="MatchMunicipalityJob/MunicipalityJob/isOpen" />';
		</script>
		<h1 class="header-match">Matcha jobb med ans�kningar</h1>
		<div class="match-fail alert alert-danger collapse" role="alert">
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<span class="sr-only">Error:</span>
			<span class="message">Du m�ste v�lja minst en kandidat f�re du kan utf�ra �tg�rden.</span>
		</div>
		<xsl:apply-templates select="MatchMunicipalityJob" />
		
	</xsl:template>

	<xsl:template match="MatchMunicipalityJob">
		<xsl:apply-templates select="MunicipalityJob" />

		<div class="candidates-wrapper">
			<xsl:apply-templates select="Area1AndGeoArea1Candidates" />
			<xsl:apply-templates select="Area1AndGeoArea2Candidates" />
			<xsl:apply-templates select="Area1AndGeoArea3Candidates" />
			<xsl:apply-templates select="AnyAreaAndGeoArea1Candidates" />
			<xsl:apply-templates select="AnyAreaAndGeoArea2Candidates" />
			<xsl:apply-templates select="AnyAreaAndGeoArea3Candidates" />
			<xsl:apply-templates select="Area2AndGeoArea1Candidates" />
			<xsl:apply-templates select="Area2AndGeoArea2Candidates" />
			<xsl:apply-templates select="Area2AndGeoArea3Candidates" />
			<xsl:apply-templates select="Area3AndGeoArea1Candidates" />
			<xsl:apply-templates select="Area3AndGeoArea2Candidates" />
			<xsl:apply-templates select="Area3AndGeoArea3Candidates" />
			
			<xsl:if test="PreferedArea1WithoutGeoArea or PreferedArea2WithoutGeoArea or PreferedArea3WithoutGeoArea">
				<div class="pdg-lft0px col-xs-6 withoutGeoAreaHeader mgn-btm8px">
					<div class="pdg-lft0px col-xs-8">
						<h2 class="mgn-top0px">Utan h�nsyn till geografiskt omr�de</h2>
					</div>
					<div class="col-xs-4"><div class="more-information-arrow"><span class="arrow-down glyphicon glyphicon-chevron-down collapse in"></span><span class="arrow-up glyphicon glyphicon-chevron-up collapse"></span></div></div>
				</div>
				<div class="withoutGeoAreaContainer collapse">
					<xsl:apply-templates select="PreferedArea1WithoutGeoArea" />
					<xsl:apply-templates select="PreferedArea2WithoutGeoArea" />
					<xsl:apply-templates select="PreferedArea3WithoutGeoArea" />
				</div>
			</xsl:if>
			
		</div>

		<footer class="footer-offset">


			<div class="navbar navbar-fixed-bottom navbar-bottom">
				<div class="container">
					<div class="navbar-collapse" id="footer-body">
						<div class="row">
							<div class="col-xs-6 col-md-2 mgn-top1px">
								<span class="navbar-info">
									Arbetsplats:
									<xsl:value-of select="MunicipalityJob/location" />
								</span>
							</div>

							<div class="col-xs-6 col-md-3 mgn-top1px">
								<span class="navbar-info">
									Avdelning:
									<xsl:value-of select="MunicipalityJob/department" />
								</span>
							</div>
							<div class="col-xs-6 col-md-2 mgn-top1px">
								<span class="navbar-info">
									Period:
									<xsl:value-of select="MunicipalityJob/Period/name" />
								</span>
							</div>
							<div class="col-xs-6 col-md-2 mgn-top1px">
								<div class="navbar-info">
									Kvar att matcha:
									<span id="availableToMatch">
										<xsl:value-of select="MunicipalityJob/openApplications" />
									</span>
								</div>
							</div>
							<div class="col-xs-offset-10 col-md-3 col-md-offset-0">
								<button class="btn btn-primary match-btn pull-right mgn-top4px">
								<xsl:if test="MunicipalityJob/isOpen = 'false'">
									<xsl:attribute name="class">btn btn-primary match-btn pull-right mgn-top4px disabled</xsl:attribute>
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
		<div class="row candidates-container">
			<div class="col-xs-18 col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<xsl:value-of select="$header"></xsl:value-of>
						</h3>
					</div>
					<div class="panel-body">
						<xsl:apply-templates select="MunicipalityJobApplication" />
					</div>
				</div>
			</div>
		</div>
	</xsl:template>

	<xsl:template match="Area1AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Matchande f�rstahandsval p� verksamhetsomr�de och geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area1AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Matchande f�rstahandsval p� verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area1AndGeoArea3Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Matchande f�rstahandsval p� verksamhetsomr�de och tredjehandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="AnyAreaAndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Godtyckligt verksamhetsomr�de och f�rstahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="AnyAreaAndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Godtyckligt verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="AnyAreaAndGeoArea3Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Godtyckligt verksamhetsomr�de och tredjehandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area2AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Andrahandsval p� verksamhetsomr�de och f�rstahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area2AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Andrahandsval p� verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area2AndGeoArea3Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Andrahandsval p� verksamhetsomr�de och tredjehandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area3AndGeoArea1Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Tredjehandsval p� verksamhetsomr�de och f�rstahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area3AndGeoArea2Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Tredjehandsval p� verksamhetsomr�de och andrahandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="Area3AndGeoArea3Candidates">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Tredjehandsval p� verksamhetsomr�de och tredjehandsval p� geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="PreferedArea1WithoutGeoArea">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Matchande f�rstahandsval p� verksamhetsomr�de utan h�nsyn till geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="PreferedArea2WithoutGeoArea">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Matchande andrahandsvall p� verksamhetsomr�de utan h�nsyn till geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="PreferedArea3WithoutGeoArea">
		<xsl:call-template name="candidatesTableTemplate">
			<xsl:with-param name="header"
				select="'Matchande tredjehandsval p� verksamhetsomr�de utan h�nsyn till geografiskt omr�de'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="MunicipalityJobApplication">
		<div class="mgn-btm8px candidate">

			<div class="row">
				<div class="col-xs-5 col-md-2 bold">Ranking</div>
				<div class="col-md-4">
					<xsl:value-of select="ranking" />
				</div>

				<xsl:if test="applicationType = 'REGULAR_ADMIN'">
					<div class="col-xs-5 col-md-2 bold">Typ</div>
					<div style="font-size: larger" class="col-md-4 bold">Inlagd av admin</div>
				</xsl:if>
				<xsl:if test="applicationType = 'PRIO'">
					<div class="col-xs-5 col-md-2 bold">Typ</div>
					<div style="font-size: larger" class="col-md-4 prio bold">Prioriterad</div>
				</xsl:if>
			</div>

			<div class="row">
				<div class="col-xs-5 col-md-2 bold">Namn</div>
				<div class="col-md-4 name">
					<xsl:value-of select="firstname" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="lastname" />
				</div>

				<div class="col-xs-5 col-md-2 bold">Personnummer</div>
				<div class="col-md-2 social-number">
					<xsl:value-of select="socialSecurityNumber" />
				</div>
			</div>

			<div class="row">
				<div class="col-xs-5 col-md-2 bold">K�rkort</div>
				<div class="col-md-4">
					<xsl:value-of select="DriversLicenseType/name" />
				</div>

				<div class="col-xs-5 col-md-2 bold">Skola</div>
				<div class="col-md-4">
					<xsl:value-of select="schoolName" />
				</div>
			</div>

			<div class="row">
				<div class="col-xs-5 col-md-2 bold">�nskad arbetsperiod</div>
				<xsl:if test="preferedPeriod = 'NONE'">
					<div class="col-md-4">Inget �nskem�l</div>
				</xsl:if>
				<xsl:if test="preferedPeriod = 'BEGINNING'">
					<div class="col-md-4">B�rjan av sommaren</div>
				</xsl:if>
				<xsl:if test="preferedPeriod = 'MIDDLE'">
					<div class="col-md-4">Mitten av sommaren</div>
				</xsl:if>
				<xsl:if test="preferedPeriod = 'END'">
					<div class="col-md-4">Slutet av sommaren</div>
				</xsl:if>
				<xsl:if test="not(preferedPeriod)">
					<div class="col-md-4"></div>
				</xsl:if>

				<div class="col-xs-5 col-md-2 bold">Cv</div>
				<div class="col-md-4 cv">
					<xsl:choose>
						<xsl:when test="cvFilename !=''">
							<a target="_blank"
								href="{/Document/requestinfo/contextpath}{/Document/CvMunicipalityApplicationUrl}?id={id}">Ladda ner</a>
						</xsl:when>
						<xsl:otherwise>
							CV saknas
						</xsl:otherwise>
					</xsl:choose>

				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Skoltyp</div>
				<div class="col-md-4">
					<xsl:value-of select="schoolType" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Omr�de 1</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="preferedArea1/name" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Omr�de 2</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="preferedArea2/name" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Omr�de 3</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="preferedArea3/name" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Plats 1</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="preferedGeoArea1/name" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Plats 2</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="preferedGeoArea2/name" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Plats 3</div>
				<div class="col-md-4 social-number">
					<xsl:value-of select="preferedGeoArea3/name" />
				</div>
			</div>

			<div class="row collapse">
				<div class="col-xs-5 col-md-2 bold">Personligt brev</div>
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
					V�lj:
				</div>
				<div class="col-xs-5 col-md-4">
					<input class="notMatched" type="checkbox" name="application-id" value="{id}" />
				</div>
			</div>
		</div>

	</xsl:template>

	<xsl:template match="MunicipalityJob">


		<input type="hidden" id="job-id" name="job-id" value="{id}" />

		<div class="row">
			<div class="col-md-3 col-xs-10">
				<button class="generate-workplace-document-button btn btn-primary"
					id="generate-workplace-document_{id}">
						<xsl:if test="matchedApplications = '0'">
							<xsl:attribute name="class">generate-workplace-document-button btn btn-primary disabled</xsl:attribute>
							<xsl:attribute name="disabled">disabled</xsl:attribute>
						</xsl:if>
						Generera dokument till arbetsplatsen
					</button>
			</div>
			<div class="col-md-3 col-xs-10 col-xs-top-margin">				
				<xsl:choose>
					<xsl:when test="isOpen = 'true'">
						<button class="close-job-button btn btn-danger" id="close-job_{id}">St�ng annons</button>
					</xsl:when>
					<xsl:otherwise>
						<button class="open-job-button btn btn-success" id="open-job_{id}">�ppna annons</button>
					</xsl:otherwise>
				</xsl:choose>
			</div>
		</div>

		<div class="job-info">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Information om arbetsplatsen</h3>
				</div>
				<div class="panel-body">

					<input type="hidden" id="jobIsOpenStatus" value="{isOpen}"></input>
					<xsl:if test="isOpen = 'false'">
						<div class="row">
							<div class="col-xs-8 col-md-4">
								<h2 style="margin-bottom: 20px; padding-bottom: 15px;" class="prio">Annonsen
									�r st�ngd</h2>
							</div>
						</div>
					</xsl:if>

					<div class="row">
						<div class="col-md-2 bold">Arbetsplats</div>
						<div class="col-md-2 bold">Avdelning</div>
						<div class="col-md-2 bold">Lediga platser</div>
						<div class="col-md-1 bold">Period</div>
					</div>

					<div class="row overview-row">
						<div class="col-md-2">
							<xsl:value-of select="location" />
						</div>
						<div class="col-md-2">
							<xsl:value-of select="department" />
						</div>
						<div class="col-md-2" id="availableSlotsToMatch">
							<xsl:value-of select="openApplications" />
						</div>
						<div class="col-md-2">
							<xsl:value-of select="Period/name" />
						</div>

					</div>
					
					<xsl:if test="adminNotes != ''">
						<div class="row">
							<div class="col-md-2 bold">Handl�ggarkommentar</div>
						</div>
						<div class="row overview-row">
							<div class="col-md-6 prio">
								<xsl:value-of select="adminNotes"></xsl:value-of>
							</div>
						</div>
					</xsl:if>					

<!-- 					<div class="row  mgn-top8px collapse"> -->

<!-- 						<div class="col-xs-5 col-md-3 bold">Rubrik</div> -->
<!-- 						<div class="col-md-3"> -->
<!-- 							<xsl:value-of select="workTitle" /> -->
<!-- 						</div> -->
<!-- 					</div> -->

					<div class="row mgn-top8px collapse">

						<div class="col-xs-5 col-md-3 bold">Verksamhetsomr�de</div>
						<div class="col-md-3">
							<xsl:value-of select="MunicipalityJobArea/name" />
						</div>

					</div>


					<div class="row mgn-top8px collapse">
						<div class="col-xs-5 col-md-3 bold">Antal platser</div>
						<div class="col-md-3">
							<xsl:value-of select="numberOfWorkersNeeded" />
						</div>
						<div class="col-xs-5 col-md-3 bold">Tillsatta platser</div>
						<div class="col-md-3">
							<xsl:value-of select="matchedApplications" />
						</div>

					</div>
					<div class="row mgn-top8px collapse">

						<div class="col-xs-5 col-md-3 bold">Adress</div>
						<div class="col-md-3">
							<xsl:value-of select="streetAddress" />
						</div>
						<div class="col-xs-5 col-md-3 bold">Geografisk plats</div>
						<div class="col-md-3">
							<xsl:value-of select="GeoArea/name" />
						</div>

					</div>



					<div class="row mgn-top8px collapse">
						<div class="col-xs-5 col-md-3 bold">Beskrivning</div>
						<div class="col-md-9 col-xs-12">
							<xsl:value-of select="workDescription" />
						</div>
					</div>

					<div class="row mgn-top8px collapse">
						<div class="col-xs-5 col-md-3 bold">�vriga krav och �nskem�l</div>
						<div class="col-md-9 col-xs-12">
							<xsl:value-of select="freeTextRequirements" />
						</div>
					</div>

					<div class="row  mgn-top8px collapse">

						<div class="col-xs-5 col-md-3 bold">K�rkort</div>
						<div class="col-md-3">
							<xsl:value-of select="DriversLicenseType/name" />
						</div>
						<div class="col-xs-5 col-md-3 bold">�ver 18</div>
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

					<div class="row collapse">
						<div class="col-xs-5 col-md-3 bold">Organisation</div>
						<div class="col-md-3">
							<xsl:value-of select="organization" />
						</div>
						<div class="col-xs-5 col-md-3 bold">F�rvaltning</div>
						<div class="col-md-3">
							<xsl:value-of select="administration" />
						</div>
					</div>

					<div class="collapse">
						<h3>Kontaktuppgifter</h3>
						<div class="row mgn-top8px">
							<div class="col-xs-5 col-md-3 bold">F�rnamn</div>
							<div class="col-md-3">
								<xsl:value-of select="MunicipalityManager/firstname" />
							</div>
							<div class="col-xs-5 col-md-3 bold">Efternamn</div>
							<div class="col-md-3">
								<xsl:value-of select="MunicipalityManager/lastname" />
							</div>
						</div>

						<div class="row">
							<div class="col-xs-5 col-md-3 bold">Telefonnummer</div>
							<div class="col-md-3">
								<xsl:value-of select="MunicipalityManager/mobilePhone" />
							</div>
							<div class="col-xs-5 col-md-3 bold">E-postadress</div>
							<div class="col-md-3">
								<xsl:value-of select="MunicipalityManager/email" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-8 bold">
							<a href="#" name="show-more-job-info">Visa mer</a>
							<a href="#" name="show-less-job-info" style="display:none">Minska</a>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div>


			<div class="row">
				<xsl:if test="applications/MunicipalityJobApplication[status !='DECLINED']">
					<div class="col-md-6 col-xs-12">
						<h3>Matchade sommarjobbare</h3>
						<form id="matched-workers-form">
							<div id="matched-applications-container">

								<xsl:for-each select="applications/MunicipalityJobApplication">
									<xsl:if test="status ='MATCHED'">
										<div class="matched-application">
											<xsl:if test="applicationType = 'REGULAR_ADMIN'">
												<div class="row">
													<div class="col-xs-5 col-md-3 bold">Typ</div>
													<div class="col-xs-6 bold">Inlagd av admin</div>
												</div>
											</xsl:if>
											<xsl:if test="applicationType = 'PRIO'">
												<div class="row">
													<div class="col-xs-5 col-md-3 bold">Typ</div>
													<div class="col-xs-5 bold prio">Prioriterad</div>
												</div>
											</xsl:if>
											<div class="row">
												<div class="col-xs-5 col-md-3 bold">Namn</div>
												<div class="col-md-9 name">
													<xsl:value-of select="firstname" />
													<xsl:text> </xsl:text>
													<xsl:value-of select="lastname" />
												</div>
											</div>
											<div class="row">
												<div class="col-xs-5 col-md-3 bold">Personnummer</div>
												<div class="col-md-9 social-number">
													<xsl:value-of select="socialSecurityNumber" />
												</div>
											</div>
											<div class="row">
												<div class="col-xs-5 col-md-3 bold">Telefonnummer</div>
												<div class="col-md-9 name">
													<xsl:value-of select="phoneNumber" />
												</div>
											</div>
											<div class="row">
												<div class="col-xs-5 col-md-3 bold">E-post</div>
												<div class="col-md-9 name">
													<xsl:value-of select="email" />
												</div>
											</div>
											<div class="mgn-top8px row">
												<div class="col-xs-5 col-md-2 bold">Handledare</div>
												<div class="col-md-5 col-xs-8 mentor-select-div">
													<xsl:variable name="mentorId" select="MunicipalityMentor/id" />
													<select id="personal-mentor_{id}" class="form-control personal-mentor">

														<option value="" />
														<xsl:for-each
															select="/Document/MatchMunicipalityJob/MunicipalityJob/mentors/MunicipalityMentor">
															<option value="{id}">
																<xsl:if test="id =$mentorId">
																	<xsl:attribute name="selected">selected</xsl:attribute>
																</xsl:if>

																<xsl:value-of select="firstname" />
																<xsl:text> </xsl:text>
																<xsl:value-of select="lastname" />

															</option>
														</xsl:for-each>
													</select>
												</div>
											</div>
											<div class="mgn-top8px row">
												<div class="col-md-3 col-xs-5 bold">Tid f�r samtal</div>
												<div class="col-md-4 col-xs-8">
													<input type="text" id="timeForInfo_{id}" class="form-control timeForInfo"
														value="{timeForInformation}"></input>
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
												<div class="col-md-7 col-xs-5">
													<button class="save-application-options btn btn-primary"
														id="{id}">Spara</button>
												</div>
											</div>
											<div class="mgn-top16px row">
												<div class="col-md-2 col-xs-5 bold">Dokument</div>
												<div class="col-md-6 col-xs-12">
													<xsl:choose>
														<xsl:when
															test="timeForInformation != '' and callStatus != 'NONE'">
															<select id="generate-document-select_{id}" class="form-control generate-document-select">
																<option value=""></option>
																<option value="ALL">Generera aktuella dokument</option>
																<option value="kallelse">Kallelse</option>
																<option value="bekraftelse">Bekr�ftelse f�r anst�llning</option>
																<option value="anstallningsbevis">Anst�llningsbevis</option>
																<option value="tjanstgoringsrapport">Tj�nstg�ringsrapport</option>
																<option value="bank">L�ntagaruppgifter</option>
																<option value="skattebefrielse">L�n utan skatteavdrag</option>
																<option value="belastningsregister">Utdrag ur belastningsregistret</option>
															</select>
														</xsl:when>
														<xsl:otherwise>
															<select disabled="disabled" id="generate-document-select_{id}"
																class="form-control"></select>
														</xsl:otherwise>
													</xsl:choose>
												</div>
												<div class="col-md-2 col-xs-5">
													<button disabled="disabled"
														class="generate-document-button btn btn-primary" id="generate-document_{id}">Generera</button>
												</div>
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
									<button type="submit"
										class="btn btn-danger remove-workers-btn common-button">Ta bort</button>
								</div>
								<div class="col-md-2">
									<button type="submit" class="btn btn-warning deny-btn common-button">Tackat nej</button>
								</div>
							</div>
						</form>
					</div>
				</xsl:if>


				<xsl:if test="applications/MunicipalityJobApplication[status='DECLINED']">
					<div class="col-md-6 col-xs-12">
						<h3>Personer som tackat nej</h3>

						<form id="denied-workers-form">
							<div id="denied-applications-container">
								<xsl:for-each select="applications/MunicipalityJobApplication">
									<xsl:if test="status ='DECLINED'">
										<div class="denied-application">
											<div class="row">
												<div class="col-xs-5 col-md-3 bold">Namn</div>
												<div class="col-md-9 name">
													<xsl:value-of select="firstname" />
													<xsl:text> </xsl:text>
													<xsl:value-of select="lastname" />
												</div>
											</div>
											<div class="row">
												<div class="col-xs-5 col-md-3 bold">Personnummer</div>
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
										class="btn btn-primary from-denied-to-matched-btn common-button">�ndra till matchad</button>
								</div>
							</div>
						</form>
					</div>
				</xsl:if>


			</div>

			<div id="matched-application-template" class="collapse">
				<div class="matched-application">
					<div class="row">
						<div class="col-xs-5 col-md-3 bold">Namn</div>
						<div class="col-md-9 name"></div>
					</div>
					<div class="row">
						<div class="col-xs-5 col-md-3 bold">Personnummer</div>
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


	</xsl:template>

</xsl:stylesheet>					
