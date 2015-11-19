<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="MunicipalityJobForm"/>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobForm">
		<form role="form" method="POST" id="municipality-job-form" data-toggle="validator">
			 
			 <input name="jobId" style="display: none" class="form-control" type="text" value="{MunicipalityJob/id}"/>
			 
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Plats</h3>
				  </div>
				  <div class="panel-body">
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="organisation">Ange organisation*</label>
							    <input type="text" class="form-control" id="organisation" name="organisation" placeholder="" required="required" value="{MunicipalityJob/organization}"/>				    					    
							    <p class="help-block with-errors">Tex, Sundsvalls elnät, Sundsvalls kommun</p>
					    	</div>
					    </div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="administration">Ange förvaltning*</label>
							    <input type="text" class="form-control" id="administration" name="administration" placeholder="" required="required" value="{MunicipalityJob/administration}"/>
							    <p class="help-block with-errors">Tex Kultur och fritid</p>
				    		</div>
		    			</div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="location">Ange platsen</label>
							    <input type="text" class="form-control" id="location" name="location" placeholder="" required="required" value="{MunicipalityJob/location}"/>
							    <p class="help-block with-errors">Tex Himlabadet</p>
						   	</div>
					   	</div>					    
					  </div>
					 
					  <div class="form-group">
					   <label for="area">Ange versamhetsområde*</label>
						  <table class="table">
						  	<thead>
						  		<tr>
						  			<th>Välj</th>
						  			<th>Verksamhetsområde</th>
						  			<th>Beskrivning</th>
						  		</tr>
						  	</thead>
						  	<tbody>
						  		<xsl:for-each select="Areas/MunicipalityJobArea">
						  			<xsl:if test="canBeChosenInApplication = 'true'">
						  				<tr>
							  				<xsl:choose>
							  					<xsl:when test="selected = 'true'">
										  			<td><input type="radio" name="area" id="area_{id}" value="{id}" required="required" checked="checked"/></td>
							  					</xsl:when>
							  					<xsl:otherwise>
							  						<td><input type="radio" name="area" id="area_{id}" value="{id}" required="required"/></td>
							  					</xsl:otherwise>
							  				</xsl:choose>
								  			<td><xsl:value-of select="name"/></td>
								  			<td><xsl:value-of select="description"/></td>
								  		</tr>
						  				
							  		</xsl:if>
						  		</xsl:for-each>						  		
						  	</tbody>
						  </table>
						  <p class="help-block with-errors"></p>
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
							    <input type="number" data-error="Ett postnummer måste ha fem siffror." class="form-control" id="postalcode" name="postalcode" placeholder="" required="required" data-minlength="5" value="{MunicipalityJob/zipCode}"/>
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
				  	<div id="geoAreaSelect" class="row">
				  		<div class="col-md-4 form-group">
						    <label for="geoArea">Välj geografiskt område*</label>				    
						    <select class="form-control" name="geoArea" id="geoArea" required="required">
						    	<option value=""/>
								<xsl:for-each select="GeoAreas/GeoArea">
									<xsl:choose>
										<xsl:when test="selected = 'true'">
											<option selected="selected" value="{id}"><xsl:value-of select="name"/></option>
										</xsl:when>
										<xsl:otherwise>
											<option value="{id}"><xsl:value-of select="name" /></option>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>
							</select>
							<p class="help-block with-errors">Det geografiska område arbetsplatsen tillhör</p>
						</div>
						<div class="col-md-6">
							<label>Området omfattar</label>
							<xsl:for-each select="GeoAreas/GeoArea">
								<div class="mgn-top8px" style="display: none;" id="geoarea-description_{id}"><xsl:value-of select="description"></xsl:value-of></div>
							</xsl:for-each>
						</div>
					</div>
				  </div>
			  	</div>
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Arbete</h3>
				  </div>
				  <div class="panel-body">
				  
				  <div class="form-group">
					  	<div class="row">
						  	<div class="col-md-5">
							    <label for="work-title">Rubrik*</label>				    
							     <input type="text" class="form-control" id="work-title" name="work-title" placeholder="" required="required" value="{MunicipalityJob/workTitle}"/>
							     <p class="help-block with-errors"></p>							    
						  	</div>
					  	</div>
				  	</div>
				  	<div class="form-group">
					    <label for="work-description">Arbetsbeskrivning*</label>				    
					    <textarea class="form-control" rows="5" id="work-description" name="work-description" required="required"><xsl:value-of select="MunicipalityJob/workDescription"></xsl:value-of></textarea>							    
					    <p class="help-block with-errors">Beskriv vad arbetsuppgifterna kommer vara</p>
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
				  		<xsl:for-each select="Periods/Period">
				  			<div class="period-div" id="periodnr_{id}">
					  			<div style="margin-bottom: 0px" class="row form-group">
									<div class="col-md-1"><input class="period-checkbox" type="checkbox" name="period_{id}" /></div>
							  		<div class="col-md-2"><xsl:value-of select="name"/></div>
							  		<div class="col-md-2"><xsl:value-of select="startDate"/></div>
							  		<div class="col-md-2"><xsl:value-of select="endDate"/></div>
							  		<div class="col-md-3">
							  			<input class="form-control numberOfWorkersField" disabled="disabled" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded"/>
	 									<p class="help-block">Skriv ett heltal mellan 1 och 99</p>
							  		</div>		  			
					  			</div>
					  			<div style="margin-bottom: 8px" class="add-mentor-div hidden">
					  				<label>Ange handledare</label>
					  				<div id="mentors-wrapper">
							  			<xsl:for-each select="MunicipalityJob/mentors/MunicipalityMentor">
											<div class="row collapse in" style="margin-bottom: 8px;">
												<input style="display: none;" id="mentor-id-{id}" name="mentor-id-{id}" type="text" value="{id}"/>
												<div class="form-group col-md-3">
													<label for="mentor-firstname">Förnamn</label><input type="text" class="form-control" id="mentor-firstname" name="mentor-firstname_{id}" placeholder="" value="{firstname}"/>
												</div>
												<div class="form-group col-md-3">
													<label for="mentor-lastname">Efternamn</label><input type="text" class="form-control" id="mentor-lastname" name="mentor-lastname_{id}" placeholder="" value="{lastname}"/>
												</div>
												<div class="form-group col-md-2">
													<label for="mentor-phone">Telefonnummer</label><input type="text" class="numberValidation form-control" id="mentor-phone" name="mentor-phone_{id}" placeholder="" value="{mobilePhone}"/>
													<p class="help-block">Endast siffror</p>
												</div>
												<div class="form-group col-md-3">
													<label for="mentor-email">E-post</label><input type="email" class="col-md-3 form-control" id="mentor-email" name="mentor-email_{id}" placeholder="" value="{email}"/>
												</div>
												<div class="form-group col-md-1">
													<label>Ta bort</label>
													<div class="remove-mentor mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div>
												</div>
											</div>
										</xsl:for-each>
							    	</div>
					  				<a href="#" class="add-municipality-mentor-btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Lägg till handledare</a>
					  			</div>
				  			</div>
				  		</xsl:for-each>
				  	</div>
				  	
<!-- 				  	<div class="form-group"> -->
<!-- 					  	<label for="period">Välj perioder*</label> -->
<!-- 						  <table id="period-table" class="table"> -->
<!-- 						  	<thead> -->
<!-- 						  		<tr> -->
<!-- 						  			<th>Välj</th> -->
<!-- 						  			<th>Periodnamn</th> -->
<!-- 						  			<th>Startdatum</th> -->
<!-- 						  			<th>Slutdatum</th> -->
<!-- 						  			<th>Antal platser*</th> -->
<!-- 						  		</tr> -->
<!-- 						  	</thead> -->
<!-- 						  	<tbody> -->
<!-- 								<div class="checkbox-group"> -->
<!-- 									<xsl:choose> -->
<!-- 									<xsl:when test="MunicipalityJob"> -->
<!-- 										<xsl:variable name="numberOfWorkers" select="MunicipalityJob/numberOfWorkersNeeded"></xsl:variable> -->
<!-- 										<xsl:for-each select="Periods/Period"> -->
<!-- 										<xsl:choose> -->
<!-- 									  		<xsl:when test="selected = 'true'"> -->
<!-- 								  				<tr class="form-group"> -->
<!-- 										  			<td> -->
<!-- 										  			    <input class="period-checkbox" type="checkbox" name="period_{id}" disabled="disabled" checked="checked"/> -->
<!-- 													</td>							  			 -->
<!-- 										  			<td><xsl:value-of select="name"/></td> -->
<!-- 										  			<td><xsl:value-of select="startDate"/></td> -->
<!-- 										  			<td><xsl:value-of select="endDate"/></td> -->
<!-- 										  			<td>								  				 -->
<!-- 									    				<input value="{$numberOfWorkers}" class="form-control numberOfWorkersField" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded"/>	 -->
<!-- 									    				<p class="help-block">Skriv ett heltal mellan 1 och 99</p>	  -->
<!-- 								    				</td> -->
<!-- 									  			</tr> -->
<!-- 								  			</xsl:when> -->
<!-- 								  			<xsl:otherwise> -->
<!-- 								  				<tr class="form-group"> -->
<!-- 										  			<td> -->
<!-- 										  			    <input class="period-checkbox" type="checkbox" name="period_{id}" disabled="disabled"/> -->
<!-- 													</td>							  			 -->
<!-- 										  			<td><xsl:value-of select="name"/></td> -->
<!-- 										  			<td><xsl:value-of select="startDate"/></td> -->
<!-- 										  			<td><xsl:value-of select="endDate"/></td> -->
<!-- 										  			<td>								  				 -->
<!-- 									    				<input class="form-control numberOfWorkersField" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded" disabled="disabled"/>	 -->
<!-- 									    				<p class="help-block">Skriv ett heltal mellan 1 och 99</p>	  -->
<!-- 								    				</td> -->
<!-- 									  			</tr> -->
<!-- 								  			</xsl:otherwise> -->
<!-- 								  			</xsl:choose> -->
<!-- 							  			</xsl:for-each>	 -->
<!-- 									</xsl:when> -->
<!-- 									<xsl:otherwise> -->
<!-- 										<xsl:for-each select="Periods/Period"> -->
<!-- 									  			<tr class="form-group"> -->
<!-- 										  			<td> -->
<!-- 										  			    <input class="period-checkbox" type="checkbox" name="period_{id}" /> -->
<!-- 													</td>							  			 -->
<!-- 										  			<td><xsl:value-of select="name"/></td> -->
<!-- 										  			<td><xsl:value-of select="startDate"/></td> -->
<!-- 										  			<td><xsl:value-of select="endDate"/></td> -->
<!-- 										  			<td>								  				 -->
<!-- 									    				<input class="form-control numberOfWorkersField" type="number" min="1" max="99" name="{name}_numberOfWorkersNeeded" id="{name}_numberOfWorkersNeeded" />	 -->
<!-- 									    				<p class="help-block">Skriv ett heltal mellan 1 och 99</p>	  -->
<!-- 								    				</td> -->
<!-- 									  			</tr> -->
<!-- 								  		</xsl:for-each>	 -->
<!-- 									</xsl:otherwise> -->
<!-- 									</xsl:choose> -->
<!-- 							  	</div>					  		 -->
<!-- 						  	</tbody> -->
<!-- 					  	</table> -->
<!-- 					  	<p style="display: none; color: #a94442;" id="period-errors" class="help-block with-errors">Du måste välja minst en period</p> -->
<!-- 					</div> -->
					
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
			  		
<!-- 			  		<div class="form-group"> -->
<!-- 			  			<label>Ange handledare <span style="font-weight: normal; font-size: 90%;"><i>(Förnamn, efternamn och telefonnummer krävs för att en handledare ska sparas)</i></span></label> -->
<!-- 			  			<div id="mentors-wrapper"> -->
<!-- 				  			<xsl:for-each select="MunicipalityJob/mentors/MunicipalityMentor"> -->
<!-- 								<div class="row collapse in" style="margin-bottom: 8px;"> -->
<!-- 									<input style="display: none;" id="mentor-id-{id}" name="mentor-id-{id}" type="text" value="{id}"/> -->
<!-- 									<div class="form-group col-md-3"> -->
<!-- 										<label for="mentor-firstname">Förnamn</label><input type="text" class="form-control" id="mentor-firstname" name="mentor-firstname_{id}" placeholder="" value="{firstname}"/> -->
<!-- 									</div> -->
<!-- 									<div class="form-group col-md-3"> -->
<!-- 										<label for="mentor-lastname">Efternamn</label><input type="text" class="form-control" id="mentor-lastname" name="mentor-lastname_{id}" placeholder="" value="{lastname}"/> -->
<!-- 									</div> -->
<!-- 									<div class="form-group col-md-2"> -->
<!-- 										<label for="mentor-phone">Telefonnummer</label><input type="text" class="numberValidation form-control" id="mentor-phone" name="mentor-phone_{id}" placeholder="" value="{mobilePhone}"/> -->
<!-- 										<p class="help-block">Endast siffror</p> -->
<!-- 									</div> -->
<!-- 									<div class="form-group col-md-3"> -->
<!-- 										<label for="mentor-email">E-post</label><input type="email" class="col-md-3 form-control" id="mentor-email" name="mentor-email_{id}" placeholder="" value="{email}"/> -->
<!-- 									</div> -->
<!-- 									<div class="form-group col-md-1"> -->
<!-- 										<label>Ta bort</label> -->
<!-- 										<div class="remove-mentor mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</xsl:for-each> -->
<!-- 				    	</div> -->
				    	
<!-- 				    	<a href="#" class="add-mentor-btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Lägg till handledare</a> -->
<!-- 				  	</div> -->
				  	
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
						    		<xsl:when test="MunicipalityJob/isOverEighteen = 'true'">
										<input type="checkbox" name="isOverEighteen" checked="checked">Måste vara över 18 år</input>						    		
						    		</xsl:when>
						    		<xsl:otherwise>
								    	<input type="checkbox" name="isOverEighteen">Måste vara över 18 år</input>
						    		</xsl:otherwise>
						    	</xsl:choose>
						    </label>
					  	</div>
						<div class="checkbox">
						    <label>
						    	<xsl:choose>
						    		<xsl:when test="MunicipalityJob/hasDriversLicense = 'true'">
						    			<input type="checkbox" name="hasDriversLicense" checked="checked">Måste ha körkort</input>
						    		</xsl:when>
						    		<xsl:otherwise>
								    	<input type="checkbox" name="hasDriversLicense">Måste ha körkort</input>
						    		</xsl:otherwise>
						    	</xsl:choose>
						    </label>
					  	</div>
					  	
					  	<div id="driverslicense_select" class="row">
					  		<div class="form-group col-md-3">
							    <label for="driversLicenseType">Välj körkortstyp*</label>				    
							    <select class="form-control" name="driversLicenseType" id="driversLicenseType">
									<option value=""/>
									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
										<xsl:choose>
											<xsl:when test="selected = 'true'">
												<option selected="selected" value="{id}"><xsl:value-of select="name" /> - <xsl:value-of select="description" /></option>
											</xsl:when>
											<xsl:otherwise>
												<option value="{id}"><xsl:value-of select="name" /> - <xsl:value-of select="description" /></option>
											</xsl:otherwise>
										</xsl:choose>
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
			  			<h3 class="panel-title">
			  				<xsl:choose>
			  					<xsl:when test="MunicipalityJob">Spara ändringar i annons</xsl:when>
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
						
			  			<button style="margin-top: 4px;" id="submit-municipality-job" type="submit" class="float-rgt mgn-lft8px btn btn-success questions-submit">
			  				<xsl:choose>
			  					<xsl:when test="MunicipalityJob">Spara</xsl:when>
			  					<xsl:otherwise>Skicka</xsl:otherwise>
			  				</xsl:choose>
			  			</button>
			  			<xsl:if test="MunicipalityJob">
			  				<a href="{manageJobURL}?jobId={MunicipalityJob/id}" style="margin-top: 4px;" class="float-rgt btn btn-primary">Tillbaka</a>
			  			</xsl:if>
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div> 					  	
		  		
			</form>
			
			<div id="mentor-template" >
	    		<div class="row collapse">
	  				<div class="col-md-3">
					    <label for="mentor">Förnamn</label>				    
					     <input type="text" class="form-control" id="mentor-firstname" name="mentor-firstname" placeholder=""/>							    
			    	</div>
			    	<div class="col-md-3">
					    <label for="mentor">Efternamn</label>				    
					     <input type="text" class="form-control" id="mentor-lastname" name="mentor-lastname" placeholder=""/>							    
			    	</div>
			    
	  				<div class="col-md-2">
					    <label for="mentor">Telefonnummer</label>				    
					     <input type="text" class="numberValidation form-control" id="mentor-phone" name="mentor-phone" placeholder=""/>
					    <p class="help-block">Endast siffror</p>
			    	</div>
			    
	  				<div class="col-md-3">
					    <label for="mentor">E-post</label>				    
					     <input type="text" class="form-control" id="mentor-email" name="mentor-email" placeholder=""/>
					    <p class="help-block">Valfri</p>
			    	</div>
			    	
			    	<div class="form-group col-md-1">
						<label>Ta bort</label>
						<div class="remove-mentor mgn-top8px glyphicon glyphicon-remove" aria-hidden="true"></div>
					</div>
		    	</div>
	    	
	    	</div>
	</xsl:template>
	
</xsl:stylesheet>					