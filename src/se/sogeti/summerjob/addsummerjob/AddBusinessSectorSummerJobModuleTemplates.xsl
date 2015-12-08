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
		<h1 class="createJobHeadline">L�gg till sommarjobb inom n�ringslivet</h1>
		<form class="well" role="form" method="POST" id="business-sector-add-job-form" data-toggle="validator">
		
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
								<input type="text" class="form-control" data-error="����-MM-DD" id="startDate" name="startDate" placeholder="" required="required" value="{BusinessSectorJob/startDate}"/>
								<div class="help-block with-errors">Datum d� tj�nsten b�rjar</div>
					    	</div>
					    	<div class="form-group col-md-3">
								<label for="date">Slutdatum*</label>
								<input type="text" class="form-control" data-error="����-MM-DD" id="endDate" name="endDate" placeholder="" required="required" value="{BusinessSectorJob/endDate}"/>
								<div class="help-block with-errors">Datum d� tj�nsten slutar</div>
					    	</div>
					    	<div class="form-group col-md-3">
								<label for="date">Sista ans�kningsdag*</label>
								<input type="text" class="form-control" data-error="����-MM-DD" id="lastApplicationDay" name="lastApplicationDay" placeholder="" required="required" value="{BusinessSectorJob/lastApplicationDay}"/>
								<div class="help-block with-errors"></div>
						    </div>
			    		</div>
					</div>
			  		
			  		<div style="margin-top: 8px;">
			  			<label>Ange handledare <span style="font-weight: normal; font-size: 90%;"><i>(F�rnamn, efternamn och telefonnummer kr�vs f�r att en handledare ska sparas)</i></span></label>
			  			
			  			<div id="mentors-wrapper">
							<xsl:for-each select="BusinessSectorJob/mentors/BusinessSectorMentor">
								<div class="row collapse in" style="margin-bottom: 8px;">
									<input style="display: none;" id="mentor-id-{id}" name="mentor-id-{id}" type="text" value="{id}"/>
									<div class="form-group col-md-3">
										<label for="mentor-firstname">F�rnamn</label><input type="text" class="form-control" id="mentor-firstname" name="mentor-firstname_{id}" placeholder="" value="{firstname}"/>
									</div>
									<div class="form-group col-md-3">
										<label for="mentor-lastname">Efternamn</label><input type="text" class="form-control" id="mentor-lastname" name="mentor-lastname_{id}" placeholder="" value="{lastname}"/>
									</div>
									<div class="form-group col-md-2">
										<label for="mentor-phone">Telefonnummer</label><input type="text" class="numberValidation form-control" id="mentor-phone" name="mentor-phone_{id}" placeholder="" value="{mobilePhone}"/>
										<p class="help-block">Endast siffror</p>
									</div>
									<div class="form-group col-md-3">
										<label for="mentor-email">E-post</label>
										<input type="email" class="col-md-3 form-control" id="mentor-email" name="mentor-email_{id}" placeholder="" value="{email}"/>
									</div>
									<div class="form-group col-md-1">
										<label>Ta bort</label>
										<div class="remove-mentor mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div>
									</div>
								</div>

							</xsl:for-each>
				    	</div>
				    	<a href="#" class="add-business-mentor-btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> L�gg till ny handledare</a>
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
							    <label for="company">F�retag*</label>				    
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
							    <input type="text" data-error="Ett postnummer m�ste ha fem siffror." class="numberValidation form-control" id="postalcode" name="postalcode" placeholder="" required="required" data-minlength="5" maxlength="5" value="{BusinessSectorJob/zipCode}"/>
							    <div class="help-block with-errors"></div>
						    </div>
						    <div class="form-group col-md-4">
							    <label for="postalarea">Postort*</label>				    
							    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required" value="{BusinessSectorJob/city}"/>
							    <div class="help-block with-errors"></div>
						    </div>
					    </div>
				  	</div>
				  	
				  	<div style="margin-bottom: 8px;">
				  		<label>Ange chef p� arbetsplatsen</label>
				  		<div class="row">
							<div class="form-group col-md-3">
								<label for="manager-firstname">F�rnamn*</label>
								<input type="text" class="form-control" id="manager-firstname"
									name="manager-firstname" placeholder="" required="required" value="{BusinessSectorJob/BusinessSectorManager/firstname}"/>
								<p class="help-block with-errors">F�rnamn till ansvarig p� platsen</p>
							</div>
							
							<div class="form-group col-md-3">
								<label for="manager-lastname">Efternamn*</label>
								<input type="text" class="form-control" id="manager-lastname"
									name="manager-lastname" placeholder="" required="required" value="{BusinessSectorJob/BusinessSectorManager/lastname}"/>
								<p class="help-block with-errors">Efternamn till ansvarig p� platsen</p>
							</div>
						
							<div class="form-group col-md-3">
								<label for="manager-phone">Telefonnummer*</label>
								<input type="text" data-error="Ange ett telefonnummer utan bindestreck" class="numberValidation form-control" id="manager-phone" name="manager-phone"
									placeholder="" required="required" value="{BusinessSectorJob/BusinessSectorManager/mobilePhone}"/>
								<p class="help-block with-errors">Telefonnummer till ansvarig p� platsen</p>
							</div>
						
							<div class="form-group col-md-3">
								<label for="manager-email">E-post*</label>
								<input type="email" class="form-control" id="manager-email" name="manager-email"
									placeholder="" value="{BusinessSectorJob/BusinessSectorManager/email}" required="required"/>
								<p class="help-block with-errors">E-post till ansvarig p� platsen</p>
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
					  	<div class="checkbox">
						    <label>
						    	<xsl:choose>
						    		<xsl:when test="BusinessSectorJob/isOverEighteen ='true'">
						    			<input type="checkbox" id="isOverEighteen" name="isOverEighteen" checked="checked">M�ste vara �ver 18 �r</input>
						    		</xsl:when>
						    		<xsl:otherwise>
						    			<input type="checkbox" id="isOverEighteen" name="isOverEighteen">M�ste vara �ver 18 �r</input>
						    		</xsl:otherwise>
						    	</xsl:choose>
						    </label>
					  	</div>
						<div class="checkbox">
						    <label>
						    	<xsl:choose>
						    		<xsl:when test="BusinessSectorJob/hasDriversLicense = 'true'">
						    			<input type="checkbox" id="hasDriversLicense" name="hasDriversLicense" checked="checked">M�ste ha k�rkort</input>
						    		</xsl:when>
						    		<xsl:otherwise>
						    			<input type="checkbox" id="hasDriversLicense" name="hasDriversLicense">M�ste ha k�rkort</input>
						    		</xsl:otherwise>
						    	</xsl:choose>
						    </label>
					  	</div>
					  	
					  	<div id="driverslicense_select" class="row">
					  		<div class="form-group col-md-3">
							    <label for="driversLicenseType">V�lj k�rkortstyp*</label>				    
							    <select class="form-control" name="driversLicenseType" id="driversLicenseType">
							    	<option value="" />
									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
										<xsl:choose>
											<xsl:when test="selected = 'true'">
												<option value="{id}" selected="selected"><xsl:value-of select="name" /> - <xsl:value-of select="description"/></option>
											</xsl:when>
											<xsl:otherwise>
												<option value="{id}"><xsl:value-of select="name" /> - <xsl:value-of select="description" /></option>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
								</select>
								<p class="help-block with-errors"></p>
							</div>
						</div>
					  	
						<div class="form-group">
						    <label for="work-description">�vriga �nskem�l och krav</label>				    
						    <textarea class="form-control" rows="3" id="other-requirements" name="other-requirements"><xsl:value-of select="BusinessSectorJob/freeTextRequirements"></xsl:value-of></textarea>							    
						    <p class="help-block">�vriga �nskem�l</p>
					  	</div>    	
				  	
				  </div>
			  	</div>
			  	
			  	<div class="panel panel-default">
			  		<div class="panel-heading">
			  			<h3 class="panel-title">
			  				F�rhandsgranska annons
			  			</h3>
			  		</div>  
			  		<div class="panel-body">
						<button style="margin-top: 4px;" id="preview-business-sector-job" type="submit" class="float-rgt mgn-lft8px btn btn-success questions-submit">
							F�rhandsgranska
			  			</button>
						
			  			<xsl:if test="BusinessSectorJob">
			  				<a href="{manageJobURL}?jobId={BusinessSectorJob/id}" style="margin-top: 4px;" class="float-rgt btn btn-primary">Tillbaka</a>
			  			</xsl:if>
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div>
			</form>
			
			<div id="preview-template">
				<h1>F�rhandsgranska sommarjobb</h1>
				<div class="well">
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
											<label for="lastApplicationDay">Sista ans�kningsdag</label>
											<span class="mgn-lft8px" id="preview-lastApplicationDay"></span>
										</div>
								  	</div>
								  	<div class="mgn-top8px row">
								  		<div class="col-md-12 preview-mentors-list">
									  		<label>Handledare</label>
									  		<table class="table">
												<thead>
													<tr>
														<th class="overview">F�rnamn</th>
														<th class="overview">Efternamn</th>							
														<th class="overview">Telefonnummer</th>
														<th class="overview">E-postadress</th>
													</tr>
												</thead>
												<tbody id="preview-mentor-body">
													<tr style="display: none" id="preview-mentor-row">
											  			<td id="preview-mentor-firstname"></td>
											  			<td id="preview-mentor-lastname"></td>
											  			<td id="preview-mentor-phonenumber"></td>
											  			<td id="preview-mentor-email"></td>
											  		</tr>
												</tbody>
											</table>
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
											<label>F�retag</label>
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
										<label class="mgn-top8px">Ansvarig p� arbetsplatsen</label>
										<div class="row">
											<div class="col-md-2">
												<label>F�rnamn</label>
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
									<label>K�rkort</label>
									<input type="hidden" id="driversLicenseNeededText" value="Ja, tj�nsten kr�ver k�rkort av typ " />
									<input type="hidden" id="driversLicenseNotNeededText" value="Nej, tj�nsten kr�ver EJ k�rkort" />
									<p id="preview-driverslicense"></p>
								</div>
							</div>
							<div class="mgn-top8px row">
								<div class="col-md-4">
									<label>�lder</label>
									<input type="hidden" id="overEighteenNeededText" value="Tj�nsten kr�ver att s�kande �r �ver 18 �r" />
									<input type="hidden" id="overEighteenNotNeededText" value="Tj�nsten kr�ver EJ att s�kande �r �ver 18 �r" />
									<p id="preview-age"></p>
								</div>
							</div>
							<div class="mgn-top8px row">
								<div class="col-md-12">
									<label>�vriga krav och �nskem�l</label>
									<p id="preview-otherrequirements"></p>
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel panel-default">
				  		<div class="panel-heading">
				  			<h3 class="panel-title">
								<xsl:choose>
									<xsl:when test="BusinessSectorJob">Spara �ndringar i annons</xsl:when>
									<xsl:otherwise>Skicka in annons</xsl:otherwise>
								</xsl:choose>			  			
				  			</h3>
				  		</div>  
				  		<div class="panel-body">
							<div id="save-failed" class="alert alert-danger" role="alert">
								<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
								<span class="sr-only">Error:</span>
								<span class="message"></span>
							</div>
							<div id="save-succeeded" class="alert alert-success" role="alert">
								<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								<span class="sr-only">Success:</span>
								<span class="message"></span>
							</div>
							
				  			<button style="margin-top: 4px;" id="submit-business-sector-job" class="float-rgt mgn-lft8px btn btn-success questions-submit">
				  				<xsl:choose>
				  					<xsl:when test="BusinessSectorJob">Spara</xsl:when>
				  					<xsl:otherwise>Skicka</xsl:otherwise>
				  				</xsl:choose>
				  			</button>
				  			
				  			<button style="margin-top: 4px;" id="cancel-preview-business-sector-job" class="mgn-lft8px btn btn-warning questions-submit">Redigera</button>
				  			
<!-- 				  			<xsl:if test="BusinessSectorJob"> -->
<!-- 				  				<a href="{manageJobURL}?jobId={BusinessSectorJob/id}" style="margin-top: 4px;" class="float-rgt btn btn-primary">Hantera annons</a> -->
<!-- 				  			</xsl:if> -->
							<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
						</div>
				  	</div>
					
				</div>
			</div>
			
			<div id="mentor-template">
				<div class="row collapse" style="margin-bottom: 8px;">
<!-- 					<input style="display: none;" id="mentor-id" name="mentor-id" type="text"></input> -->
				
					<div class="form-group col-md-3">
						<label for="mentor-firstname">F�rnamn</label>
						<input type="text" class="form-control" id="mentor-firstname"
							name="mentor-firstname" placeholder="" />
					</div>
			
					<div class="form-group col-md-3">
						<label for="mentor-lastname">Efternamn</label>
						<input type="text" class="form-control" id="mentor-lastname"
							name="mentor-lastname" placeholder="" />
					</div>
			
					<div class="form-group col-md-2">
						<label for="mentor-phone">Telefonnummer</label>
						<input type="text" class="numberValidation form-control" id="mentor-phone" name="mentor-phone"
							placeholder="" />
						<p class="help-block">Endast siffror</p>
					</div>
			
					<div class="form-group col-md-3">
						<label for="mentor-email">E-post</label>
						<input type="email" class="form-control" id="mentor-email" name="mentor-email"
							placeholder="" />
					</div>
					
					<div class="form-group col-md-1">
						<label>Ta bort</label>
						<div class="remove-mentor mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div>
					</div>
				</div>
			</div>	
	</xsl:template>
	
</xsl:stylesheet>					