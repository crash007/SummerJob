<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
			var isAdmin = '<xsl:value-of select="IsAdmin" />';
		</script>
<!-- 		<xsl:variable name="isAdmin" select="IsAdmin"/> -->
		<xsl:apply-templates select="MunicipalityJobApplicationForm"/>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplicationForm">
		<h1 class="createJobHeadline">Ansök om sommarjobb hos Sundsvalls kommun</h1>
		<form method="POST" role="form" id="municipality-job-application-form" enctype="multipart/form-data" data-toggle="validator">
			<input type="hidden" name="appId" value="{MunicipalityJobApplication/id}" />
			<div class="panel panel-default">
				<div class="panel-heading">
			    	<h3 class="panel-title">Personuppgifter</h3>
			  	</div>
			  	<div class="panel-body">
			  
			  	<div class="row">
			    	<div class="col-md-3">
				    	<div class="form-group">
						    <label>Personnummer*</label>				    
						    <input type="text" value="{MunicipalityJobApplication/socialSecurityNumber}" data-error="ÅÅÅÅMMDDxxxx" data-minlength="12" maxlength="12" class="form-control numberValidation" id="socialSecurityNumber" name="socialSecurityNumber" placeholder="" required="required"/>
						    <p class="help-block with-errors">ÅÅÅÅMMDDxxxx</p>				    
					  	</div>
					</div>
				</div> 
				<div class="row">
	  				<div class="col-md-3 form-group">
					    <label for="firstname">Förnamn*</label>				    
					    <input type="text" value="{MunicipalityJobApplication/firstname}" class="form-control" id="firstname" name="firstname" placeholder="" required="required"/>
					    <p class="help-block with-errors"></p>						    
			    	</div>
			    	<div class="col-md-3 form-group">
					    <label for="lastname">Efternamn*</label>				    
					    <input type="text" value="{MunicipalityJobApplication/lastname}" class="form-control" id="lastname" name="lastname" placeholder="" required="required"/>
					    <p class="help-block with-errors"></p>						    
			    	</div>
			    
	  				<div class="col-md-3 form-group">
					    <label for="phone">Telefonnummer*</label>				    
					    <input type="text" value="{MunicipalityJobApplication/phoneNumber}" class="numberValidation form-control" id="phone" name="phone" placeholder="" required="required"/>
					    <p class="help-block with-errors">Endast siffror</p>
			    	</div>
			    
	  				<div class="col-md-3 form-group">
					    <label for="email">E-post*</label>				    
					    <input type="email" value="{MunicipalityJobApplication/email}" class="form-control" id="email" name="email" placeholder="" required="required"/>
					    <p class="help-block with-errors"></p>
			    	</div>
		    	</div>
				<div class="row">
				    <div class="form-group col-md-5">
					    <label for="street">Gatuadress*</label>				    
					    <input type="text" value="{MunicipalityJobApplication/streetAddress}" class="form-control" id="street" name="street" placeholder="" required="required"/>
					    <p class="help-block with-errors"></p>				    
				    </div>
				    <div class="form-group col-md-3">
					    <label for="postalcode">Postnummer*</label>				    
					    <input type="text" value="{MunicipalityJobApplication/zipCode}" data-error="Ett postnummer måste ha fem siffror." class="numberValidation form-control" data-minlength="5" maxlength="5" id="postalcode" name="postalcode" placeholder="" required="required" />
					    <p class="help-block with-errors"></p>
				    </div>
				    <div class="form-group col-md-4">
					    <label for="postalarea">Postort*</label>				    
					    <input type="text" value="{MunicipalityJobApplication/city}" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required"/>
					    <p class="help-block with-errors"></p>
				    </div>
				 </div>
			</div>
		</div>
	
		<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Ansökan</h3>
			  </div>
			  <div class="panel-body">
			  	<div class="form-group">
				    <label for="personal-letter">Personligt brev*</label>				    
				    <textarea class="form-control" rows="7" id="personal-letter" name="personal-letter" required="required"><xsl:value-of select="MunicipalityJobApplication/personalLetter"></xsl:value-of></textarea>							    
				    <p class="help-block with-errors">Skriv lite information om vem du är, vad du är intresserad av och vad du är duktig på</p>
			  	</div>
		  		<div class="form-group">
				    <label for="cvInputFile">Ladda upp ditt cv</label>
				    <xsl:if test="MunicipalityJobApplication/cvFilename != ''">
				    	<p><strong>Befintlig: </strong><i id="currentCV"><xsl:value-of select="MunicipalityJobApplication/cvFilename"></xsl:value-of></i></p>
				    </xsl:if>
				    <input type="file" class="form-control" id="cvInputFile" name="cvFile"/>
				    <p class="help-block">Om du har ett cv kan du ladda upp det.</p>
			 	</div>
			 	
			 	<div class="row">
			 		<div class="col-md-3 col-xs-12 form-group">
			 			<label>Önskad arbetsperiod*</label>
						<select class="form-control" required="required" name="preferedPeriod">
							<option value=""></option>
							<option value="NONE">
								<xsl:if test="MunicipalityJobApplication/preferedPeriod = 'NONE'">
									<xsl:attribute name="selected">selected</xsl:attribute>	   	
								</xsl:if>
								Inget önskemål
							</option>
							<option value="BEGINNING">
								<xsl:if test="MunicipalityJobApplication/preferedPeriod = 'BEGINNING'">
									<xsl:attribute name="selected">selected</xsl:attribute>	   	
								</xsl:if>
								Början av sommaren
							</option>
							<option value="MIDDLE">
								<xsl:if test="MunicipalityJobApplication/preferedPeriod = 'MIDDLE'">
									<xsl:attribute name="selected">selected</xsl:attribute>	   	
								</xsl:if>
								Mitten av sommaren
							</option>
							<option value="END">
								<xsl:if test="MunicipalityJobApplication/preferedPeriod = 'END'">
									<xsl:attribute name="selected">selected</xsl:attribute>	   	
								</xsl:if>
								Slutet av sommaren
							</option>
						</select>
						<p class="help-block with-errors">Period du önskar arbeta</p> 		
			 		</div>
			 	</div>
			 	
			 	<div class="row">
				 	<div class="col-md-8 col-md-push-4 form-horizontal text-left">
				  		<h3>Beskrivning arbetsområden</h3>
				  		<xsl:for-each select="Areas/Area">
				  			<xsl:if test="canBeChosenInApplication ='true'">
						  		<div class="form-group">
							    	<label class="col-sm-2"><xsl:value-of select="name"/></label>
								    <div class="col-sm-10">
								      <p class=""><xsl:value-of select="description"/></p>
								    </div>
							  	</div>
					  		</xsl:if>
					  	</xsl:for-each>
				  	</div>
				  	
				 	<div class="col-md-4 col-md-pull-8">
				 		<div class="row">
							<div class="col-md-12">
						  		<div class="checkbox">
								  <label for="noPreferedArea">
								  	<xsl:choose>
								  		<xsl:when test="MunicipalityJobApplication">
								  			<xsl:choose>
										  		<xsl:when test="MunicipalityJobApplication/preferedArea1 != '' and MunicipalityJobApplication/preferedArea2 != '' and MunicipalityJobApplication/preferedArea3 != ''">
										  			<input type="checkbox" value="true" name="noPreferedArea" id="noPreferedArea">
										    			Jag kan tänka mig jobba med vad som helst
										    		</input>
										  		</xsl:when>
										  		<xsl:otherwise>
										  			<input type="checkbox" value="true" name="noPreferedArea" id="noPreferedArea" checked="checked">
										    			Jag kan tänka mig jobba med vad som helst
										    		</input>
										  		</xsl:otherwise>
									  		</xsl:choose>
								  		</xsl:when>
								  		<xsl:otherwise>
								  			<input type="checkbox" value="true" name="noPreferedArea" id="noPreferedArea">
										    			Jag kan tänka mig jobba med vad som helst
										    </input>
								  		</xsl:otherwise>
								  	</xsl:choose>
								  </label>
								</div>
							</div>
						</div>
				 	
				  		<div class="row">
				  			<div class="col-md-12 form-group">
							    <label for="preferedArea1">Önskat arbetsområde - prio 1*</label>				    
							    <select class="form-control" name="preferedArea1" id="preferedArea1" required="required">
									<option value=""/>
									<xsl:for-each select="Areas/Area">
										<xsl:choose>
											<xsl:when test="/Document/IsAdmin = 'true'">
												<xsl:choose>
									  					<xsl:when test="selectedArea1 = 'true'">
									  						<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>
									  					</xsl:when>
									  					<xsl:otherwise>
													  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  					</xsl:otherwise>
									  				</xsl:choose>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="canBeChosenInApplication = 'true'">
									  				<xsl:choose>
									  					<xsl:when test="selectedArea1 = 'true'">
									  						<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>
									  					</xsl:when>
									  					<xsl:otherwise>
													  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  					</xsl:otherwise>
									  				</xsl:choose>
										  		</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
						  			</xsl:for-each>
								</select>
								<p class="help-block with-errors"></p>
							</div>
							
							<div class="col-md-12 form-group">
							    <label for="preferedArea2">Önskat arbetsområde - prio 2*</label>				    
							    <select class="form-control" name="preferedArea2" id="preferedArea2" required="required">
							    	<option value=""/>
								  	<xsl:for-each select="Areas/Area">
							  			<xsl:choose>
											<xsl:when test="/Document/IsAdmin = 'true'">
												<xsl:choose>
									  					<xsl:when test="selectedArea2 = 'true'">
									  						<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>
									  					</xsl:when>
									  					<xsl:otherwise>
													  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  					</xsl:otherwise>
									  				</xsl:choose>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="canBeChosenInApplication = 'true'">
									  				<xsl:choose>
									  					<xsl:when test="selectedArea2 = 'true'">
									  						<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>
									  					</xsl:when>
									  					<xsl:otherwise>
													  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  					</xsl:otherwise>
									  				</xsl:choose>
										  		</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
							  		</xsl:for-each>									  
								</select>
								<p class="help-block with-errors"></p>
							</div>
							<div class="col-md-12 form-group">
							    <label for="preferedArea3">Önskat arbetsområde - prio 3*</label>				    
							    <select class="form-control" name="preferedArea3" id="preferedArea3" required="required">
							    	<option value=""/>
								 	<xsl:for-each select="Areas/Area">
							  			<xsl:choose>
											<xsl:when test="/Document/IsAdmin = 'true'">
												<xsl:choose>
									  					<xsl:when test="selectedArea3 = 'true'">
									  						<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>
									  					</xsl:when>
									  					<xsl:otherwise>
													  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  					</xsl:otherwise>
									  				</xsl:choose>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="canBeChosenInApplication = 'true'">
									  				<xsl:choose>
									  					<xsl:when test="selectedArea3 = 'true'">
									  						<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>
									  					</xsl:when>
									  					<xsl:otherwise>
													  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  					</xsl:otherwise>
									  				</xsl:choose>
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
			  	
			  	<div class="row">
			  		<div class="col-md-8 col-md-push-4 form-horizontal text-left">
				  		<h3>Beskrivning geografiska områden</h3>
				  		<xsl:for-each select="GeoAreas/GeoArea">
				  			
					  		<div class="form-group">
						    	<label class="col-sm-2"><xsl:value-of select="name"/></label>
							    <div class="col-sm-10">
							      <p class=""><xsl:value-of select="description"/></p>
							    </div>
						  	</div>
				  		
					  	</xsl:for-each>
				  	</div>
			  	
			  	<div class="col-md-4 col-md-pull-8">
			  		<div class="row">
			  			<div class="form-group col-md-12">
						    <label for="geoArea1">Önskat geografiskt område 1*</label>				    
						    <select class="form-control" name="geoArea1" id="geoArea1" required="required">
								<option value=""/>
								<xsl:for-each select="GeoAreas/GeoArea">
									<xsl:choose>
										<xsl:when test="selectedGeoArea1 = 'true'">
											<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>	
										</xsl:when>
										<xsl:otherwise>
											<option value="{id}"><xsl:value-of select="name"/> </option>	
										</xsl:otherwise>
									</xsl:choose>
					  			</xsl:for-each>
							</select>
							<p class="help-block with-errors"></p>
						</div>
						
						<div class="form-group col-md-12">
						    <label for="geoArea2">Önskat geografiskt område 2*</label>				    
						    <select class="form-control" name="geoArea2" id="geoArea2" required="required">
								<option value=""/>
								<xsl:for-each select="GeoAreas/GeoArea">
									<xsl:choose>
										<xsl:when test="selectedGeoArea2 = 'true'">
											<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>	
										</xsl:when>
										<xsl:otherwise>
											<option value="{id}"><xsl:value-of select="name"/> </option>	
										</xsl:otherwise>
									</xsl:choose>
					  			</xsl:for-each>
							</select>
							<p class="help-block with-errors"></p>
						</div>
						<div class="col-md-12">
						    <label for="geoArea3">Önskat geografiskt område 3</label>				    
						    <select class="form-control" name="geoArea3" id="geoArea3">
								<option value=""/>
								<xsl:for-each select="GeoAreas/GeoArea">
									<xsl:choose>
										<xsl:when test="selectedGeoArea3 = 'true'">
											<option value="{id}" selected="selected"><xsl:value-of select="name"/> </option>	
										</xsl:when>
										<xsl:otherwise>
											<option value="{id}"><xsl:value-of select="name"/> </option>	
										</xsl:otherwise>
									</xsl:choose>
					  			</xsl:for-each>
							</select>
						</div>
					</div>
			  	</div>
			  	</div>
			  	
				<div id="driverslicense_select" class="row">
			  		<div class="form-group col-md-4">
					    <label for="driversLicenseType">Välj körkortstyp</label>				    
					   		<select class="form-control" name="driversLicenseType" id="driversLicenseType">							    	
								<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
									<option value="{id}">
										<xsl:if test="/Document/MunicipalityJobApplicationForm/MunicipalityJobApplication/DriversLicenseType/id = id">
							    			<xsl:attribute name="selected">selected</xsl:attribute>															    			
							    		</xsl:if>		
							    		<xsl:value-of select="name" /> - <xsl:value-of select="description"/>																		
									</option>																		
								</xsl:for-each>
							</select>										
						<p class="help-block with-errors"></p>
					</div>
				</div>
			  </div>
		  </div>
			
			<div class="panel panel-default">
		  		<div class="panel-heading">
		  			<h3 class="panel-title">Förhandsgranska ansökan</h3>
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
					
					<xsl:if test="/Document/IsAdmin = 'true'">
						<div class="row">
							<div class="form-group col-md-5">
								<label>Typ av ansökan</label><br/>
								<xsl:choose>
									<xsl:when test="MunicipalityJobApplication/applicationType = 'REGULAR'">
										<label class="radio-inline"><input type="radio" checked="checked" required="required" name="applicationType" value="REGULAR" />Vanlig</label>
									</xsl:when>
									<xsl:otherwise>
										<label class="radio-inline"><input type="radio" required="required" name="applicationType" value="REGULAR" />Vanlig</label>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="MunicipalityJobApplication/applicationType = 'REGULAR_ADMIN'">
										<label class="radio-inline"><input type="radio" checked="checked" required="required" name="applicationType" value="REGULAR_ADMIN" />Vanlig (inlagd av admin)</label>
									</xsl:when>
									<xsl:otherwise>
										<label class="radio-inline"><input type="radio" required="required" name="applicationType" value="REGULAR_ADMIN" />Vanlig (inlagd av admin)</label>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="MunicipalityJobApplication/applicationType = 'PRIO'">
										<label class="radio-inline"><input type="radio" checked="checked" required="required" name="applicationType" value="PRIO" />Prioriterad</label>
									</xsl:when>
									<xsl:otherwise>
										<label class="radio-inline"><input type="radio" required="required" name="applicationType" value="PRIO" />Prioriterad</label>
									</xsl:otherwise>
								</xsl:choose>
								<p class="help-block with-errors"></p>
							</div>
						</div>
					</xsl:if>
					
					<!-- Dummyfält för att förhindra att multipartrequest dör när man använder IE -->
					<input type="hidden" name="dummy" value="1337"></input>
					
		  			<button id="preview-municipality-job-application" type="submit" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
		  				Förhandsgranska
		  			</button>
		  			<xsl:if test="MunicipalityJobApplication">
		  					<a href="{manageAppURL}?appId={MunicipalityJobApplication/id}" class="btn-mgn-top pull-right btn btn-primary">Tillbaka</a>
		  			</xsl:if>
					<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
				</div>
		  	</div> 			
		</form>
	    	
	    
	    <div id="preview-template">
			<h1>Förhandsgranska ansökan</h1>
		  	<div class="panel panel-default">
			  	<div class="panel-heading">
			  		<h3 class="panel-title">Personuppgifter</h3>
			  	</div>
			  	<div class="panel-body">
			  		<div class="row">
			  			<div class="col-md-3">
			  				<label>Personnummer</label>
			  				<p id="preview-socialsecuritynumber"></p>
			  			</div>
			  		</div>
				  	<div class="mgn-top8px row">
		  				<div class="col-md-3">
						    <label>Förnamn</label>				    
						   	<p id="preview-firstname"></p>
				    	</div>
				    	<div class="col-md-3">
						    <label>Efternamn</label>				    
						    <p id="preview-lastname"></p>
				    	</div>
		  				<div class="col-md-3">
						    <label>Telefonnummer</label>				    
						    <p id="preview-phonenumber"></p>
				    	</div>
		  				<div class="col-md-3">
						    <label>E-post</label>				    
						    <p id="preview-email"></p>
				    	</div>
	    			</div>
	    			<div class="mgn-top8px row">
		  				<div class="col-md-3">
						    <label>Gatuadress</label>				    
						    <p id="preview-streetaddress"></p>
				    	</div>
				    	<div class="col-md-3">
						    <label>Postnummer</label>				    
						    <p id="preview-zipcode"></p>
				    	</div>
		  				<div class="col-md-3">
						    <label>Postort</label>				    
						    <p id="preview-city"></p>
				    	</div>
	    			</div>
			  	</div>
			</div>
			
			<div class="panel panel-default">
			 	<div class="panel-heading">
			   		<h3 class="panel-title">Ansökan</h3>
			 	</div>
			  <div class="panel-body">
			  	<div class="row">
			  		<div class="col-md-12">
						<label>Personligt brev</label>				    
				  		<p id="preview-personalletter"></p>
				  	</div>
			  	</div>
			  	<div class="row">
			  		<div class="col-md-6">
						<label>CV</label>				    
				  		<p id="preview-cv"></p>
				  	</div>
			  	</div>
			  	
			  	<div class="row">
					<div class="col-md-4">
						<label>Önskad arbetsperiod</label>
						<p id="preview-preferedPeriod"></p>
					</div>					  	
			  	</div>
			  	
			  	<div class="row">
			  		<div id="preview-nopreferedarea" class="col-md-4">
			  			<label>Önskat arbetsområde</label>
			  			<p>Jag kan tänka mig jobba med vad som helst</p>
			  		</div>
					<div class="col-md-4 preview-preferedarea">
						<label>Önskat arbetsområde - prio 1</label>
						<p id="preview-preferedarea1"></p>
					</div>
					<div class="col-md-4 preview-preferedarea">
						<label>Önskat arbetsområde - prio 2</label>
						<p id="preview-preferedarea2"></p>
					</div>
					<div class="col-md-4 preview-preferedarea">
						<label>Önskat arbetsområde - prio 3</label>
						<p id="preview-preferedarea3"></p>
					</div>
			  	</div>
			  	
			  	<div class="row">
			  		<div class="col-md-4">
			  			<label>Önskat geografiskt område 1</label>
			  			<p id="preview-geoarea1"></p>
			  		</div>
			  		<div class="col-md-4">
			  			<label>Önskat geografiskt område 2</label>
			  			<p id="preview-geoarea2"></p>
			  		</div>
			  		<div class="col-md-4">
			  			<label>Önskat geografiskt område 3</label>
			  			<p id="preview-geoarea3"></p>
			  		</div>
			  	</div>
			  	
			  	<div class="mgn-top8px row">
			  		<div class="col-md-5">
			  			<label>Körkort</label>					  			
						<p id="preview-driverslicense"></p>
			  		</div>						
				</div>
	  		</div>
	  	</div>
	  	
	  	<div class="panel panel-default">
	  		<div class="panel-heading">
	  			<h3 class="panel-title">Skicka in ansökan</h3>
	  		</div>  
	  		<div class="panel-body">
	  			<div class="save-failed alert alert-danger" role="alert">
<!-- 						<div id="save-failed" class="alert alert-danger" role="alert"> -->
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
					<span class="sr-only">Error:</span>
					<span class="message"></span>
				</div>
				<div class="save-succeeded alert alert-success" role="alert">
<!-- 						<div id="save-succeeded" class="alert alert-success" role="alert"> -->
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					<span class="sr-only">Success:</span>
					<span class="message"></span>
				</div>
				
	  			<button id="submit-municipality-job-application" type="submit" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
	  				<xsl:choose>
	  					<xsl:when test="MunicipalityJobApplication">Spara</xsl:when>
	  					<xsl:otherwise>Skicka</xsl:otherwise>
	  				</xsl:choose>
	  			</button>
	  			
	  			<button id="cancel-preview-municipality-application" class="btn-mgn-top mgn-lft8px btn btn-warning">Redigera</button>
				<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
			</div>
	  	</div> 
	</div>
	    	
	</xsl:template>
	
</xsl:stylesheet>					