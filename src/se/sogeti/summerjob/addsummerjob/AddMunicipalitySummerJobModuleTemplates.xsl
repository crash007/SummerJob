<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
			var isAdmin = '<xsl:value-of select="IsAdmin"/>'
		</script>
		<xsl:apply-templates select="MunicipalityJobForm" />
	</xsl:template>
	
	<xsl:template match="MunicipalityJobForm">
		<h1 class="createJobHeadline">Lägg till sommarjobb inom kommunal verksamhet</h1>
		<form role="form" method="POST" id="municipality-job-form" data-toggle="validator">
			 
			 <input name="jobId" type="hidden" value="{MunicipalityJob/id}"/>
			 
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Plats</h3>
				  </div>
				  <div class="panel-body">
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="organisation">Ange organisation*</label>
							    <input type="text" class="form-control" data-error="Tex, Sundsvalls elnät, Sundsvalls kommun" id="organisation" name="organisation" placeholder="" required="required" value="{MunicipalityJob/organization}"/>				    					    
							    <p class="help-block with-errors">Tex, Sundsvalls elnät, Sundsvalls kommun</p>
					    	</div>
					    </div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="administration">Ange förvaltning*</label>
							    <input type="text" class="form-control" data-error="Tex Kultur och fritid" id="administration" name="administration" placeholder="" required="required" value="{MunicipalityJob/administration}"/>
							    <p class="help-block with-errors">Tex Kultur och fritid</p>
				    		</div>
		    			</div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="location">Ange arbetsplatsen*</label>
							    <input type="text" class="form-control" data-error="Tex Himlabadet" id="location" name="location" placeholder="" required="required" value="{MunicipalityJob/location}"/>
							    <p class="help-block with-errors">Tex Himlabadet</p>
						   	</div>
					   	</div>					    
					  </div>
					 
					  <div class="form-group">
					  		<div class="row">
					  			<div class="col-md-4">
							  		<label for="area">Ange verksamhetsområde*</label>
							   		<select class="form-control" name="area" id="area" required="required">
							   			<option value="" />
								   		<xsl:for-each select="Areas/MunicipalityJobArea">
								   			<xsl:choose>
								   				<xsl:when test="/Document/IsAdmin = 'true'">
							   						<option value="{id}" >
														<xsl:if test="/Document/MunicipalityJobForm/MunicipalityJob/MunicipalityJobArea/id = id">
											    			<xsl:attribute name="selected">selected</xsl:attribute>															    			
											    		</xsl:if>
														<xsl:value-of select="name"></xsl:value-of>
													</option>
								   				</xsl:when>
								   				<xsl:otherwise>
								   					<xsl:if test="canBeChosenInApplication = 'true'">
														<option value="{id}" >
															<xsl:if test="/Document/MunicipalityJobForm/MunicipalityJob/MunicipalityJobArea/id = id">
												    			<xsl:attribute name="selected">selected</xsl:attribute>															    			
												    		</xsl:if>
															<xsl:value-of select="name"></xsl:value-of>
														</option>	   			
								   					</xsl:if>
								   				</xsl:otherwise>
								   			
								   			</xsl:choose>
								   		
								   		
								   		</xsl:for-each>
							  		 </select>
							   		<p class="help-block with-errors"></p>
						   		</div>
					   		</div>
					</div>
				</div>
			</div>
				
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Adress till arbetsplatsen</h3>
				</div>
				<div class="panel-body">
			  		<div class="row">
					    <div class="form-group col-md-5">
						    <label for="street">Gatuadress*</label>				    
						    <input type="text" class="form-control" id="street" name="street" placeholder="" required="required" value="{MunicipalityJob/streetAddress}"/>
						    <p class="help-block with-errors"></p>					    
					    </div>
					    <div class="form-group col-md-3">
						    <label for="postalcode">Postnummer*</label>				    
						    <input type="text" data-error="Ett postnummer måste ha fem siffror." class="numberValidation form-control" id="postalcode" name="postalcode" placeholder="" required="required" data-minlength="5" maxlength="5" value="{MunicipalityJob/zipCode}"/>
						    <p class="help-block with-errors"></p>
					    </div>
					    <div class="form-group col-md-4">
						    <label for="postalarea">Postort*</label>				    
						    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required" value="{MunicipalityJob/city}"/>
						    <p class="help-block with-errors"></p>
					    </div>
				    </div>
				  	<div class="form-group">
					  	<div class="row">
						  	<div class="col-md-5">
							    <label for="department">Ange avdelning</label>				    
							     <input type="text" class="form-control" id="department" name="department" placeholder="" value="{MunicipalityJob/department}"/>
							    <p class="help-block">Avdelning är inte obligatorisk</p>
						  	</div>
					  	</div>
				  	</div>
<!-- 				  	<div id="geoAreaSelect" class="row"> -->
<!-- 				  		<div class="col-md-4 form-group"> -->
<!-- 						    <label for="geoArea">Välj geografiskt område*</label>				     -->
<!-- 						    <select class="form-control" data-error="Det geografiska område arbetsplatsen tillhör." name="geoArea" id="geoArea" required="required"> -->
<!-- 						    	<option value=""/> -->
<!-- 								<xsl:for-each select="GeoAreas/GeoArea"> -->
<!-- 									<option value="{id}" > -->
<!-- 										<xsl:if test="/Document/MunicipalityJobForm/MunicipalityJob/GeoArea/id = id"> -->
<!-- 							    			<xsl:attribute name="selected">selected</xsl:attribute>															    			 -->
<!-- 							    		</xsl:if> -->
<!-- 										<xsl:value-of select="name"></xsl:value-of> -->
<!-- 									</option>	   		 -->
<!-- 								</xsl:for-each> -->
<!-- 							</select> -->
<!-- 							<p class="help-block with-errors">Det geografiska område arbetsplatsen tillhör.</p> -->
<!-- 						</div> -->
<!-- 						<div class="col-md-6"> -->
<!-- 							<label>Området omfattar</label> -->
<!-- 							<xsl:for-each select="GeoAreas/GeoArea"> -->
<!-- 								<div class="mgn-top8px" style="display: none;" id="geoarea-description_{id}"><xsl:value-of select="description"/></div> -->
<!-- 							</xsl:for-each> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div id="geoAreaSelect" class="row">
						<div class="col-md-8 col-md-push-4 form-horizontal text-left">
					  		<h3 class="mgn-top0px">Beskrivning geografiska områden</h3>
					  		<xsl:for-each select="GeoAreas/GeoArea">
						  		<div class="form-group mgn-btm8px">
							    	<label class="col-sm-3"><xsl:value-of select="name"/></label>
								    <div class="col-sm-9">
								      <p class=""><xsl:value-of select="description"/></p>
								    </div>
							  	</div>
						  	</xsl:for-each>
					  	</div>
						
				  		<div class="col-md-4 col-md-pull-8">
				  			<div class="row">
				  				<div class="form-group col-md-12">
								    <label for="geoArea">Välj geografiskt område*</label>				    
								    <select class="form-control" data-error="Det geografiska område arbetsplatsen tillhör." name="geoArea" id="geoArea" required="required">
								    	<option value=""/>
										<xsl:for-each select="GeoAreas/GeoArea">
											<option value="{id}" >
												<xsl:if test="/Document/MunicipalityJobForm/MunicipalityJob/GeoArea/id = id">
									    			<xsl:attribute name="selected">selected</xsl:attribute>															    			
									    		</xsl:if>
												<xsl:value-of select="name"></xsl:value-of>
											</option>	   		
										</xsl:for-each>
									</select>
									<p class="help-block with-errors">Det geografiska område arbetsplatsen tillhör.</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Arbete</h3>
				  </div>
				  <div class="panel-body">
				  
<!-- 				  <div class="form-group"> -->
<!-- 					  	<div class="row"> -->
<!-- 						  	<div class="col-md-5"> -->
<!-- 							    <label for="work-title">Rubrik*</label>				     -->
<!-- 							     <input type="text" class="form-control" id="work-title" name="work-title" placeholder="" required="required" value="{MunicipalityJob/workTitle}"/> -->
<!-- 							     <p class="help-block with-errors"></p>							     -->
<!-- 						  	</div> -->
<!-- 					  	</div> -->
<!-- 				  	</div> -->
				  	<div class="form-group">
					    <label for="work-description">Arbetsbeskrivning*</label>				    
					    <textarea class="form-control" data-error="Beskriv vad arbetsuppgifterna kommer vara." rows="10" id="work-description" name="work-description" required="required"><xsl:value-of select="MunicipalityJob/workDescription"></xsl:value-of></textarea>							    
					    <p class="help-block with-errors">Beskriv vad arbetsuppgifterna kommer vara.</p>
				  	</div>				  	
				  	
				  	<div id="periods-group" class="form-group">
				  		<label>Välj perioder*</label>
				  		<div class="row">
					  		<div class="col-md-1 bold">Välj</div>
					  		<div class="col-md-2 bold">Periodnamn</div>
					  		<div class="col-md-2 bold">Startdatum</div>
					  		<div class="col-md-2 bold">Slutdatum</div>
					  		<div class="col-md-3 bold">Antal platser*</div>
				  		</div>
				  		<xsl:choose>
				  			<xsl:when test="MunicipalityJob">
				  				<xsl:variable name="numberOfWorkers" select="MunicipalityJob/numberOfWorkersNeeded"></xsl:variable>
				  				<xsl:for-each select="Periods/Period">
						  			<div class="period-wrapper" id="periodnr_{id}">
							  			<div class="row form-group period-container">
						  					<xsl:choose>
						  						<xsl:when test="selected = 'true'">
						  							<div class="col-md-1"><input class="period-checkbox" type="checkbox" name="period_{id}" checked="checked" disabled="disabled"/></div>
						  						</xsl:when>
						  						<xsl:otherwise>
													<div class="col-md-1"><input class="period-checkbox" type="checkbox" name="period_{id}" disabled="disabled" /></div>
						  						</xsl:otherwise>
						  					</xsl:choose>
									  		<div class="col-md-2 periodName"><xsl:value-of select="name"/></div>
									  		<div class="col-md-2 periodStartDate"><xsl:value-of select="startDate"/></div>
									  		<div class="col-md-2 periodEndDate"><xsl:value-of select="endDate"/></div>
									  		<div class="col-md-3">
									  			<xsl:choose>
									  				<xsl:when test="selected = 'true'">
									  					<input value="{$numberOfWorkers}" class="form-control numberOfWorkersField" required="required" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded"/>
			 											<p class="help-block">Skriv ett heltal mellan 1 och 99</p>
									  				</xsl:when>
									  				<xsl:otherwise>
									  					<input class="form-control numberOfWorkersField" disabled="disabled" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded"/>
			 											<p class="help-block">Skriv ett heltal mellan 1 och 99</p>
									  				</xsl:otherwise>
									  			</xsl:choose>
									  		</div>		  			
							  			</div>
							  			<div class="add-mentor hidden">
							  				<label>Ange handledare <span class="mentor-description-text"><i>(Förnamn, efternamn och telefonnummer krävs för att en handledare ska sparas)</i></span></label>
												<div class="mentors-wrapper">
							  					<xsl:if test="selected = 'true'">
										  			<xsl:for-each select="mentors/MunicipalityMentor">
														<div class="mgn-btm8px row collapse in">
															<input id="mentor-id-{id}" name="mentor-id-{id}" type="hidden" value="{id}"/>
															<div class="form-group col-md-3">
																<label for="mentor-firstname">Förnamn</label><input type="text" class="form-control mentor-firstname" name="mentor-firstname_{id}" placeholder="" value="{firstname}"/>
															</div>
															<div class="form-group col-md-3">
																<label for="mentor-lastname">Efternamn</label><input type="text" class="form-control mentor-lastname" name="mentor-lastname_{id}" placeholder="" value="{lastname}"/>
															</div>
															<div class="form-group col-md-2">
																<label for="mentor-phone">Telefonnummer</label><input type="text" class="numberValidation form-control mentor-phone" name="mentor-phone_{id}" placeholder="" value="{mobilePhone}"/>
																<p class="help-block">Endast siffror</p>
															</div>
															<div class="form-group col-md-3">
																<label for="mentor-email">E-post</label><input type="email" class="col-md-3 form-control mentor-email" name="mentor-email_{id}" placeholder="" value="{email}"/>
															</div>
															<div class="remove-mentor form-group col-md-1">
																<label>Ta bort</label>
																<div class="mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div>
															</div>
														</div>
													</xsl:for-each>
												</xsl:if>
									    	</div>
							  				<a href="#" class="add-municipality-mentor-btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Lägg till handledare</a>
							  			</div>
						  			</div>
						  		</xsl:for-each>
				  			</xsl:when>
				  			<xsl:otherwise>
				  				<xsl:for-each select="Periods/Period">
						  			<div class="period-wrapper" id="periodnr_{id}">
							  			<div class="row form-group period-container">
											<div class="col-md-1"><input class="period-checkbox" type="checkbox" name="period_{id}" /></div>
									  		<div class="col-md-2 periodName"><xsl:value-of select="name"/></div>
									  		<div class="col-md-2 periodStartDate"><xsl:value-of select="startDate"/></div>
									  		<div class="col-md-2 periodEndDate"><xsl:value-of select="endDate"/></div>
									  		<div class="col-md-3">
									  			<input class="form-control numberOfWorkersField" disabled="disabled" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded"/>
			 									<p class="help-block">Skriv ett heltal mellan 1 och 99</p>
									  		</div>		  			
							  			</div>
							  			<div class="add-mentor hidden">
							  				<label>Ange handledare <span class="mentor-description-text"><i>(Förnamn, efternamn och telefonnummer krävs för att en handledare ska sparas)</i></span></label>
											<div class="mentors-wrapper">
									    	</div>
							  				<a href="#" class="add-municipality-mentor-btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Lägg till handledare</a>
							  			</div>
						  			</div>
						  		</xsl:for-each>
								<div class="period-wrapper" id="periodunique_1337">
						  			<div class="row form-group period-container">
										<div class="col-md-1"><input class="period-checkbox" type="checkbox" name="period_unique_checkbox" /></div>
								  		<div class="col-md-2 form-group">
								  			<input disabled="disabled" placeholder="Periodnamn" class="form-control periodName" type="text" name="unique-period-name"></input>
								  			<p class="help-block with-errors"></p>
								  		</div>
								  		<div class="col-md-2 form-group">
								  			<input disabled="disabled" placeholder="Startdatum" class="form-control periodStartDate" type="text" name="unique-period-startdate"></input>
								  			<p class="help-block with-errors"></p>	
								  		</div>
								  		<div class="col-md-2 form-group">
								  			<input disabled="disabled" placeholder="Slutdatum" class="form-control periodEndDate" type="text" name="unique-period-enddate"></input>
								  			<p class="help-block with-errors"></p>
								  		</div>
								  		<div class="col-md-3 form-group">
								  			<input class="form-control numberOfWorkersField" disabled="disabled" type="number" min="1" max="99" name="unique_numberOfWorkersNeeded" id="unique_numberOfWorkersNeeded"/>
		 									<p class="help-block with-errors">Skriv ett heltal mellan 1 och 99</p>
								  		</div>
						  			</div>
						  			<div class="add-mentor hidden">
						  				<label>Ange handledare <span class="mentor-description-text"><i>(Förnamn, efternamn och telefonnummer krävs för att en handledare ska sparas)</i></span></label>
										<div class="mentors-wrapper">
								    	</div>
						  				<a href="#" class="add-municipality-mentor-btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Lägg till handledare</a>
						  			</div>
					  			</div>
				  			</xsl:otherwise>
				  		</xsl:choose>
				  		<p id="period-errors" class="help-block with-errors">Du måste välja minst en period.</p>
				  	</div>
				  	
					<div>
			  			<label>Ange chef på arbetsplatsen</label>
			  			<div class="row">
				  				<div class="form-group col-md-3">
								    <label for="manager">Förnamn*</label>				    
								     <input type="text" class="form-control" id="manager-firstname" name="manager-firstname" placeholder="" required="required" value="{MunicipalityJob/MunicipalityManager/firstname}"/>
								     <p class="help-block with-errors"></p>							    
						    	</div>
						    	<div class="form-group col-md-3">
								    <label for="manager">Efternamn*</label>				    
								     <input type="text" class="form-control" id="manager-lastname" name="manager-lastname" placeholder="" required="required" value="{MunicipalityJob/MunicipalityManager/lastname}"/>	
								     <p class="help-block with-errors"></p>						    
						    	</div>
						    	<div class="form-group col-md-3">
								    <label for="manager">Telefonnummer*</label>				    
								     <input type="text" class="numberValidation form-control" data-error="Endast siffror" id="manager-phone" name="manager-phone" placeholder="" required="required" value="{MunicipalityJob/MunicipalityManager/mobilePhone}"/>
								     <p class="help-block with-errors">Endast siffror</p>
						    	</div>
				  				<div class="col-md-3">
								    <label for="manager">E-post*</label>				    
								     <input type="email" class="form-control" data-error="Fyll i en giltig e-postadress" id="manager-email" name="manager-email" required="required" placeholder="" value="{MunicipalityJob/MunicipalityManager/email}"/>
								    <p class="help-block with-errors"></p>
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
						    	<input type="checkbox" id="mustBeOverEighteen" name="mustBeOverEighteen">
						    		<xsl:if test="MunicipalityJob/mustBeOverEighteen ='true'">
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
											<xsl:if test="/Document/MunicipalityJobForm/MunicipalityJob/DriversLicenseType/id = id">
								    			<xsl:attribute name="selected">selected</xsl:attribute>															    			
								    		</xsl:if>		
								    		<xsl:value-of select="name" /> - <xsl:value-of select="description"/>																		
										</option>																		
									</xsl:for-each>
								</select>	
							</div>
							<p class="help-block with-errors"></p>
						</div>
					  	
						<div class="form-group">
						    <label>Övriga önskemål och krav</label>				    
						    <textarea class="form-control" rows="3" id="other-requirements" name="other-requirements"><xsl:value-of select="MunicipalityJob/freeTextRequirements"></xsl:value-of></textarea>							    
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
					    <textarea class="form-control" rows="6" id="freetext" name="freetext"><xsl:value-of select="MunicipalityJob/freeText"></xsl:value-of></textarea>							    
					    <p class="help-block">Övriga kommentarer du vill ge oss handläggare</p>
				  	</div> 
				  </div>
			  	</div>
		  		
		  		<div class="panel panel-default">
			  		<div class="panel-heading">
			  			<h3 class="panel-title">Förhandsgranska annons</h3>
			  		</div>  
			  		<div class="panel-body">
						<div class="save-failed alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span>
							<span class="message"></span>
						</div>
						<div class="save-succeeded alert alert-success" role="alert">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
							<span class="sr-only">Success:</span>
							<span class="message"></span>
						</div>
						
			  			<button id="preview-municipality-job" type="submit" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
							Förhandsgranska
			  			</button>
			  			<xsl:if test="MunicipalityJob">
			  				<a href="{manageJobURL}" class="btn-mgn-top pull-right btn btn-primary">Tillbaka</a>
			  			</xsl:if>
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div> 					  	
		  		
			</form>
			
			<div class="mentor-template" >
	    		<div class="row collapse">
	  				<div class="col-md-3">
					    <label for="mentor">Förnamn</label>				    
			    		<input type="text" class="form-control mentor-firstname" name="mentor-firstname" placeholder=""/>
			    	</div>
			    	<div class="col-md-3">
					    <label for="mentor">Efternamn</label>				    
			    		<input type="text" class="form-control mentor-lastname" name="mentor-lastname" placeholder=""/>
			    	</div>
			    
	  				<div class="col-md-2">
					    <label for="mentor">Telefonnummer</label>				    
					    <input type="text" class="numberValidation form-control mentor-phone" name="mentor-phone" placeholder=""/>
					    <p class="help-block">Endast siffror</p>
			    	</div>
			    
	  				<div class="col-md-3">
					    <label for="mentor">E-post</label>				    
						<input type="text" class="form-control mentor-email" name="mentor-email" placeholder=""/>
					    <p class="help-block">Valfri</p>
			    	</div>
			    	
			    	<div class="remove-mentor form-group col-md-1">
						<label>Ta bort</label>
						<div class="mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div>
					</div>
		    	</div>
	    	</div>
	    	
	    	
	    <div id="preview-template">
    		<h1>Förhandsgranska annons</h1>
		  	<div class="panel panel-default">
			  	<div class="panel-heading">
			  		<h3 class="panel-title">Plats</h3>
			  	</div>
			  	<div class="panel-body">
				  	<div class="row">
				  		<div class="col-md-3">
							<label>Organisation</label>
							<p id="preview-organisation"></p>
						</div>
						<div class="col-md-3">
							<label>Förvaltning</label>
							<p id="preview-administration"></p>
						</div>
						<div class="col-md-3">
							<label>Arbetsplats</label>
							<p id="preview-location"></p>
						</div>
				  	</div>
				  	<div class="mgn-top8px row">
						<div class="col-md-4">
							<label>Verksamhetsområde</label>
							<p id="preview-area"></p>
						</div>
				  	</div>
			  	</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Adress till arbetsplatsen</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-3">
							<label>Gatuadress</label>
							<p id="preview-street"></p>
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
					<div class="mgn-top8px row">
						<div class="col-md-3">
							<label>Avdelning</label>
							<p id="preview-department"></p>							
						</div>
						<div class="col-md-3">
							<label>Geografiskt område</label>
							<p id="preview-geoarea"></p>							
						</div>
					</div>
				</div>
			</div>
					
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Arbete</h3>
				</div>
				<div class="panel-body">
					<div class="mgn-top8px row">
<!-- 						<div class="col-md-3 "> -->
<!-- 							<label>Rubrik</label> -->
<!-- 							<p id="preview-worktitle"></p> -->
<!-- 						</div> -->
						<div class="col-md-9">
							<label>Arbetsbeskrivning</label>
							<p id="preview-workdescription"></p>
						</div>
					</div>
					
					<div class="row mgn-top8px">
						<div class="col-md-2">
							<label>Perioder</label>
						</div>
						<br/>
						<div id="preview-period-wrapper"></div>
					</div>
					
					
					<div class="row mgn-top8px">
						<div class="col-md-3">
							<label>Ansvarig på arbetsplatsen</label>
						</div>
						<div class="col-md-12 row">
							<div class="col-md-2">
								<label>Förnamn</label>
								<p id="preview-manager-firstname"></p>
							</div>
							<div class="col-md-3">
								<label>Efternamn</label>
								<p id="preview-manager-lastname"></p>
							</div>
							<div class="col-md-2">
								<label>Telefonnummer</label>
								<p id="preview-manager-phoneNumber"></p>
							</div>
							<div class="col-md-5">
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
						<div class="col-md-4">
							<label>Körkort</label>
							<input type="hidden" id="driversLicenseNeededText" value="Ja, tjänsten kräver körkort av typ " />
							<input type="hidden" id="driversLicenseNotNeededText" value="Nej, tjänsten kräver EJ körkort" />
							<p id="preview-driverslicense"></p>
						</div>
						<div class="col-md-4">
							<label>Ålder</label>
							<input type="hidden" id="overEighteenNeededText" value="Tjänsten kräver att sökande är över 18 år" />
							<input type="hidden" id="overEighteenNotNeededText" value="Tjänsten kräver EJ att sökande är över 18 år" />
							<p id="preview-age"></p>
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
							<xsl:when test="MunicipalityJob">Spara ändringar i annons</xsl:when>
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
<!-- 							<div id="save-succeeded" class="alert alert-success" role="alert"> -->
<!-- 								<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> -->
<!-- 								<span class="sr-only">Success:</span> -->
<!-- 								<span class="message"></span> -->
<!-- 							</div> -->
					
		  			<button id="submit-municipality-job" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
		  				<xsl:choose>
		  					<xsl:when test="MunicipalityJob">Spara</xsl:when>
		  					<xsl:otherwise>Skicka</xsl:otherwise>
		  				</xsl:choose>
		  			</button>
		  			
		  			<button id="cancel-preview-municipality-job" class="btn-mgn-top mgn-lft8px btn btn-warning">Redigera</button>
		  			
					<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
				</div>
		  	</div>
		</div>
	
	
		<div class="preview-period-template">
			<div class="row">
				<div class="col-md-2 bold preview-period-name"></div>
				<div class="col-md-2 bold preview-period-startdate"></div>
				<div class="col-md-2 bold preview-period-enddate"></div>
				<div class="col-md-3"><span class="bold">Antal arbetare: </span> <span class="preview-period-numberOfWorkers"></span></div>
			</div>
			<br/>
			<label>Handledare under perioden <span class="mentor-description-text"><i>(Förnamn, efternamn och telefonnummer krävs för att en handledare ska sparas)</i></span></label>
			<div class="preview-mentor-body">
				<div class="row preview-mentor-row" style="display: none">
					<div class="col-md-2 preview-mentor-firstname"></div>
					<div class="col-md-2 preview-mentor-lastname"></div>
					<div class="col-md-2 preview-mentor-phonenumber"></div>
					<div class="col-md-2 preview-mentor-email"></div>
				</div>
			</div>
		</div>
	    	
	    	
	</xsl:template>
	
</xsl:stylesheet>					
