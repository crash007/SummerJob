<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="BusinessSectorJobForm"/>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobForm">
		<h1 class="createJobHeadline">Lägg till sommarjobb inom näringslivet</h1>
		<form role="form" method="POST" id="business-sector-add-job-form" data-toggle="validator">
		
				<input name="jobId" style="display: none" class="form-control" type="text" value="{BusinessSectorJob/id}"/>
				
			  	<div class="panel panel-default">
					<div class="panel-heading">
				    	<h3 class="panel-title">Arbete</h3>
				  	</div>
				  	<div class="panel-body">
					  	<div class="form-group">
						  	<div class="row">
				  				<div class="col-md-4">
								    <label for="profession">Yrkestitel*</label>
								    <input type="text" class="form-control" id="profession" name="profession" placeholder="" required="required" value="{BusinessSectorJob/workTitle}"/>
								    <div class="help-block with-errors"></div>
					    		</div>
			    			</div>
						</div>
					  	<div class="form-group">
						    <label for="work-description">Arbetsbeskrivning*</label>				    
						    <textarea class="form-control" rows="5" id="work-description" name="work-description" required="required"><xsl:value-of select="BusinessSectorJob/workDescription"></xsl:value-of></textarea>							    
						    <div class="help-block with-errors">Beskriv vad arbetsuppgifterna kommer vara</div>
					  	</div>
					  	<div class="form-group">
					  		<div class="row">
					  			<div class="col-md-3">
					  				<label for="numberOfWorkersNeeded">Antal lediga platser*</label>
								    <input class="form-control" type="number" min="1" max="99" name="numberOfWorkersNeeded" id="numberOfWorkersNeeded" required="required" value="{BusinessSectorJob/numberOfWorkersNeeded}"/>	
								    <p class="help-block">Skriv i heltal mellan 1 och 99</p>	    
								</div>
							</div>
					  	</div>
					  	
						<div class="form-inline">
							<div class="row">
					  			<div class="form-group col-md-3">
									<label for="startDate">Startdatum*</label>
									<input type="text" class="form-control" data-error="ÅÅÅÅ-MM-DD" id="startDate" name="startDate" placeholder="" required="required" value="{substring(BusinessSectorJob/startDate, 1, 10)}"/>
									<div class="help-block with-errors">Datum då tjänsten börjar</div>
						    	</div>
						    	<div class="form-group col-md-3">
									<label for="date">Slutdatum*</label>
									<input type="text" class="form-control" data-error="ÅÅÅÅ-MM-DD" id="endDate" name="endDate" placeholder="" required="required" value="{substring(BusinessSectorJob/endDate, 1, 10)}"/>
									<div class="help-block with-errors">Datum då tjänsten slutar</div>
						    	</div>
						    	<div class="form-group col-md-3">
									<label for="date">Sista ansökningsdag*</label>
									<input type="text" class="form-control" data-error="ÅÅÅÅ-MM-DD" id="lastApplicationDay" name="lastApplicationDay" placeholder="" required="required" value="{substring(BusinessSectorJob/lastApplicationDay, 1, 10)}"/>
									<div class="help-block with-errors"></div>
							    </div>
				    		</div>
						</div>
				  		
				  		<div class="mgn-top8px">
				  			<label>Ange kontaktperson på arbetsplatsen <span style="font-weight: normal; font-size: 90%;"><i>(Förnamn, efternamn och telefonnummer krävs för att en kontaktperson ska sparas)</i></span></label>
				  			<div class="row">
								<div class="form-group col-md-3">
									<label>Förnamn</label>
									<input type="text" class="form-control" id="mentor-firstname"
										name="mentor-firstname" placeholder="" value="{BusinessSectorJob/BusinessSectorMentor/firstname}"/>
									<p class="help-block with-errors">Förnamn till kontaktperson på arbetsplatsen</p>
								</div>
								
								<div class="form-group col-md-3">
									<label>Efternamn</label>
									<input type="text" class="form-control" id="mentor-lastname"
										name="mentor-lastname" placeholder="" value="{BusinessSectorJob/BusinessSectorMentor/lastname}"/>
									<p class="help-block with-errors">Efternamn till kontaktperson på arbetsplatsen</p>
								</div>
							
								<div class="form-group col-md-3">
									<label>Telefonnummer</label>
									<input type="text" data-error="Ange ett telefonnummer utan bindestreck" class="numberValidation form-control" id="mentor-phone" name="mentor-phone"
										placeholder="" value="{BusinessSectorJob/BusinessSectorMentor/mobilePhone}"/>
									<p class="help-block with-errors">Telefonnummer till kontaktperson på arbetsplatsen</p>
								</div>
							
								<div class="form-group col-md-3">
									<label>E-post</label>
									<input type="email" class="form-control" id="mentor-email" name="mentor-email"
										placeholder="" value="{BusinessSectorJob/BusinessSectorMentor/email}" />
									<p class="help-block with-errors">E-post till kontaktperson på arbetsplatsen</p>
								</div>
							</div>
					  	</div>
		  			</div>
		  		</div>
				
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Uppgifter om arbetsplatsen</h3>
				  </div>
				  <div class="panel-body">
				  	<div class="form-group">
				  		<div class="row">
				  			<div class="col-md-3">
							    <label for="corporate-number">Organisationsnummer*</label>				    
							     <input type="text" class="form-control" id="corporate-number" name="corporate-number" placeholder="" required="required" value="{BusinessSectorJob/corporateNumber}"/>
							     <div class="help-block with-errors"></div>						    
					    	</div>
			  				<div class="col-md-4">
							    <label for="company">Företag*</label>				    
							     <input type="text" class="form-control" id="company" name="company" placeholder="" required="required" value="{BusinessSectorJob/company}"/>
							     <div class="help-block with-errors"></div>						    
					    	</div>
				    	</div>
			    	</div>
						    	
			  		<div>
				  		<div class="row">
						    <div class="form-group col-md-5">
							    <label for="street">Gatuadress*</label>				    
							    <input type="text" class="form-control" id="street" name="street" placeholder="" required="required" value="{BusinessSectorJob/streetAddress}"/>	
							    <div class="help-block with-errors"></div>				    
						    </div>
						    <div class="form-group col-md-3">
							    <label for="postalcode">Postnummer*</label>				    
							    <input type="text" data-error="Ett postnummer måste ha fem siffror." class="numberValidation form-control" id="postalcode" name="postalcode" placeholder="" required="required" data-minlength="5" maxlength="5" value="{BusinessSectorJob/zipCode}"/>
							    <div class="help-block with-errors"></div>
						    </div>
						    <div class="form-group col-md-4">
							    <label for="postalarea">Postort*</label>				    
							    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required" value="{BusinessSectorJob/city}"/>
							    <div class="help-block with-errors"></div>
						    </div>
					    </div>
				  	</div>
				  	
				  	<div class="mgn-top8px">
				  		<label>Ange chef på arbetsplatsen</label>
				  		<div class="row">
							<div class="form-group col-md-3">
								<label for="manager-firstname">Förnamn*</label>
								<input type="text" class="form-control" id="manager-firstname"
									name="manager-firstname" placeholder="" required="required" value="{BusinessSectorJob/BusinessSectorManager/firstname}"/>
								<p class="help-block with-errors">Förnamn till ansvarig på platsen</p>
							</div>
							
							<div class="form-group col-md-3">
								<label for="manager-lastname">Efternamn*</label>
								<input type="text" class="form-control" id="manager-lastname"
									name="manager-lastname" placeholder="" required="required" value="{BusinessSectorJob/BusinessSectorManager/lastname}"/>
								<p class="help-block with-errors">Efternamn till ansvarig på platsen</p>
							</div>
						
							<div class="form-group col-md-3">
								<label for="manager-phone">Telefonnummer*</label>
								<input type="text" data-error="Ange ett telefonnummer utan bindestreck" class="numberValidation form-control" id="manager-phone" name="manager-phone"
									placeholder="" required="required" value="{BusinessSectorJob/BusinessSectorManager/mobilePhone}"/>
								<p class="help-block with-errors">Telefonnummer till ansvarig på platsen</p>
							</div>
						
							<div class="form-group col-md-3">
								<label for="manager-email">E-post*</label>
								<input type="email" class="form-control" id="manager-email" name="manager-email"
									placeholder="" value="{BusinessSectorJob/BusinessSectorManager/email}" required="required"/>
								<p class="help-block with-errors">E-post till ansvarig på platsen</p>
							</div>
						</div>
				  	</div>
				  </div>
			  	</div>
			  	
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Krav och önskemål</h3>
				  </div>
				  <div class="panel-body">
					  	<div class="checkbox">
						    <label>

					    		<input type="checkbox" id="mustBeOverEighteen" name="mustBeOverEighteen">
						    		<xsl:if test="BusinessSectorJob/mustBeOverEighteen ='true'">
						    			<xsl:attribute name="checked">checked</xsl:attribute>							    			
						    		</xsl:if>
						    		Måste vara över 18 år
					    		</input>						    	
						    </label>
					  	</div>
											  	
					  	<div id="driverslicense_select" class="row">
					  		<div class="form-group col-md-3">
							    <label for="driversLicenseType">Välj körkortstyp*</label>				    
							    <select class="form-control" name="driversLicenseType" id="driversLicenseType">							    	
									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
										<option value="{id}">
											<xsl:if test="/Document/BusinessSectorJobForm/BusinessSectorJob/DriversLicenseType/id = id">
								    			<xsl:attribute name="selected">selected</xsl:attribute>															    			
								    		</xsl:if>		
								    		<xsl:value-of select="name" /> - <xsl:value-of select="description"/>																		
										</option>																		
									</xsl:for-each>
								</select>								
								<p class="help-block with-errors"></p>
							</div>
						</div>
						
						<div class="checkbox">
						    <label>
						    	<input type="checkbox" id="inChargeOfInterviews" name="inChargeOfInterviews">
					  					<xsl:if test="BusinessSectorJob/inChargeOfInterviews = 'true'">
   											<xsl:attribute name="checked">checked</xsl:attribute>	   	
   										</xsl:if>
					  				Jag vill överlåta intervjuarbetet till kommunen
					  			</input>
						    </label>
					  	</div>
				  		
						<div class="form-group">
						    <label for="work-description">Övriga önskemål och krav</label>				    
						    <textarea class="form-control" rows="3" id="other-requirements" name="other-requirements"><xsl:value-of select="BusinessSectorJob/freeTextRequirements"></xsl:value-of></textarea>							    
						    <p class="help-block">Övriga önskemål</p>
					  	</div>    	
				  </div>
			  	</div>
			  	
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Övrigt</h3>
				  </div>
				  <div class="panel-body">
				  	<div class="form-group">
					    <label>Övriga kommentarer</label>				    
					    <textarea class="form-control" rows="6" id="freetext" name="freetext"><xsl:value-of select="BusinessSectorJob/freeText"></xsl:value-of></textarea>							    
					    <p class="help-block">Övriga kommentarer du vill ge oss handläggare</p>
				  	</div> 
				  </div>
			  	</div>
			  	
			  	<div class="panel panel-default">
			  		<div class="panel-heading">
			  			<h3 class="panel-title">
			  				Förhandsgranska annons
			  			</h3>
			  		</div>  
			  		<div class="panel-body">
			  		
			  			<div class="save-succeeded alert alert-success" role="alert">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
							<span class="sr-only">Success:</span>
							<span class="message"></span>
						</div>
			  		
						<button id="preview-business-sector-job" type="submit" class="pull-right mgn-lft8px btn-mgn-top btn btn-success">
							Förhandsgranska
			  			</button>
						
			  			<xsl:if test="BusinessSectorJob">
			  				<a href="{manageJobURL}?jobId={BusinessSectorJob/id}" class="pull-right mgn-top4px btn btn-primary">Tillbaka</a>
			  			</xsl:if>
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div>
			</form>
			
			<div id="preview-template">
				<h1>Förhandsgranska sommarjobb</h1>
			  	<div class="panel panel-default">
				  	<div class="panel-heading">
				  		<h3 class="panel-title">Arbete</h3>
				  	</div>
				  	<div class="panel-body">
					  	<div class="row">
					  		<div class="col-md-5">
								<label>Yrkestitel</label>
								<p id="preview-worktitle"></p>
							</div>
					  	</div>
					  	<div class="mgn-top8px row">
					  		<div class="col-md-12">
								<label>Arbetsbeskrivning</label>
								<p id="preview-workdescription"></p>
							</div>
					  	</div>
					  	
					  	<div class="mgn-top8px row">
					  		<div class="col-md-3">
								<label>Antal platser</label>
								<span class="mgn-lft8px" id="preview-numberNeeded"></span>
							</div>
						</div>
					  	
					  	<div class="mgn-top8px row">
							<div class="col-md-3">
								<label for="startDate">Startdatum</label>
								<span class="mgn-lft8px" id="preview-startDate"></span>
							</div>
							<div class="col-md-3">
								<label for="endDate">Slutdatum</label>
								<span class="mgn-lft8px" id="preview-endDate"></span>
							</div>
							<div class="col-md-4">
								<label for="lastApplicationDay">Sista ansökningsdag</label>
								<span class="mgn-lft8px" id="preview-lastApplicationDay"></span>
							</div>
					  	</div>
					  	<div class="mgn-top8px row">
					  		<div class="col-md-12">
						  		<label>Kontaktperson <span style="font-weight: normal; font-size: 90%;"><i>(Förnamn, efternamn och telefonnummer krävs för att en handledare ska sparas)</i></span></label>
								<div class="row">
									<div class="col-md-2">
										<label>Förnamn</label>
										<p id="preview-mentor-firstname"></p>
									</div>
									<div class="col-md-2">
										<label>Efternamn</label>
										<p id="preview-mentor-lastname"></p>
									</div>
									<div class="col-md-2">
										<label>Telefonnummer</label>
										<p id="preview-mentor-phonenumber"></p>
									</div>
									<div class="col-md-3">
										<label>E-postadress</label>
										<p id="preview-mentor-email"></p>
									</div>
								</div>
							</div>
					  	</div>
				  	</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Uppgifter om arbetsplatsen</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-3">
								<label>Organisationsnummer</label>
								<div id="preview-corporate-number"></div>
							</div>
							<div class="col-md-5">
								<label>Företag</label>
								<p id="preview-company"></p>
							</div>
						</div>
						<div class="mgn-top8px row">
							<div class="col-md-3">
								<label>Gatuadress</label>
								<p id="preview-streetAddress"></p>
							</div>
							<div class="col-md-2">
								<label>Postnummer</label>
								<p id="preview-zipcode"></p>
							</div>
							<div class="col-md-2">
								<label>Postort</label>
								<p id="preview-city"></p>
							</div>
						</div>
						<div class="mgn-top8px">
							<label class="mgn-top8px">Ansvarig på arbetsplatsen</label>
							<div class="row">
								<div class="col-md-2">
									<label>Förnamn</label>
									<p id="preview-manager-firstname"></p>
								</div>
								<div class="col-md-2">
									<label>Efternamn</label>
									<p id="preview-manager-lastname"></p>
								</div>
								<div class="col-md-2">
									<label>Telefonnummer</label>
									<p id="preview-manager-phonenumber"></p>
								</div>
								<div class="col-md-3">
									<label>E-postadress</label>
									<p id="preview-manager-email"></p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Krav</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-5">
								<label>Körkort</label>
								<input type="hidden" id="driversLicenseNeededText" value="Ja, tjänsten kräver körkort av typ " />
								<input type="hidden" id="driversLicenseNotNeededText" value="Nej, tjänsten kräver EJ körkort" />
								<p id="preview-driverslicense"></p>
							</div>
						</div>
						<div class="mgn-top8px row">
							<div class="col-md-4">
								<label>Ålder</label>
								<input type="hidden" id="overEighteenNeededText" value="Tjänsten kräver att sökande är över 18 år" />
								<input type="hidden" id="overEighteenNotNeededText" value="Tjänsten kräver EJ att sökande är över 18 år" />
								<p id="preview-age"></p>
							</div>
						</div>
						<div class="mgn-top8px row">
							<div class="col-md-12">
								<label>Intervjuer</label>
								<input type="hidden" id="inChargeOfInterviewsText" value="Jag låter kommunen sköta arbetet med intervjuer" />
								<input type="hidden" id="notInChargeOfInterviewsText" value="Jag vill själv sköta arbetet med intervjuer" />
								<p id="preview-inChargeOfInterviews"></p>
							</div>
						</div>
						<div class="mgn-top8px row">
							<div class="col-md-12">
								<label>Övriga krav och önskemål</label>
								<p id="preview-otherrequirements"></p>
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Övrigt</h3>
					</div>
					<div class="panel-body">
						
						<div class="mgn-top8px row">
							<div class="col-md-12">
								<label>Övrigt</label>
								<p id="preview-freetext"></p>
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel panel-default">
			  		<div class="panel-heading">
			  			<h3 class="panel-title">
							<xsl:choose>
								<xsl:when test="BusinessSectorJob">Spara ändringar i annons</xsl:when>
								<xsl:otherwise>Skicka in annons</xsl:otherwise>
							</xsl:choose>			  			
			  			</h3>
			  		</div>  
			  		<div class="panel-body">
						<div class="save-failed alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span>
							<span class="message"></span>
						</div>
						
			  			<button id="submit-business-sector-job" class="pull-right btn-mgn-top mgn-lft8px btn btn-success">
			  				<xsl:choose>
			  					<xsl:when test="BusinessSectorJob">Spara</xsl:when>
			  					<xsl:otherwise>Skicka</xsl:otherwise>
			  				</xsl:choose>
			  			</button>
			  			
			  			<button id="cancel-preview-business-sector-job" class="mgn-lft8px btn-mgn-top btn btn-warning">Redigera</button>				  			
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div>
			</div>
			
	</xsl:template>
	
</xsl:stylesheet>					
