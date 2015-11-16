<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		 <h1>Ansök om sommarjobb hos Sundsvalls kommun</h1>
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="MunicipalityJobApplicationForm"/>
	</xsl:template>
	
	<xsl:template match="MunicipalityJobApplicationForm">
		<form method="POST" role="form" id="municipality-job-application-form" data-toggle="validator">
		<div class="well">
			<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Ansökan</h3>
				  </div>
				  <div class="panel-body">
				  	<div class="form-group">
					    <label for="personal-letter">Personligt brev*</label>				    
					    <textarea class="form-control" rows="7" id="personal-letter" name="personal-letter" required="required"></textarea>							    
					    <p class="help-block with-errors">Skriv lite information om vem du är, vad du är intresserad av och vad du är duktig på</p>
				  	</div>
			  		<div class="form-group">
					    <label for="cvInputFile">Ladda upp ditt cv</label>
					    <input type="file" id="cvInputFile" name="cvFile"/>
					    <p class="help-block">Om du har ett cv kan du ladda upp det.</p>
				 	</div>
				 	<div class="form-group">
				  		<div class="row">
				  			<div class="col-md-4 form-group">
							    <label for="preferedArea1">Önskat arbetsområde - prio 1*</label>				    
							    <select class="form-control" name="preferedArea1" id="preferedArea1" required="required">
									<option value=""/>
									<xsl:for-each select="Areas/MunicipalityJobArea">
							  			<xsl:if test="canBeChosenInApplication = 'true'">
									  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
								  		</xsl:if>
						  			</xsl:for-each>
								</select>
								<p class="help-block with-errors"></p>
							</div>
							
							<div class="col-md-4 form-group">
							    <label for="preferedArea2">Önskat arbetsområde - prio 2*</label>				    
							    <select class="form-control" name="preferedArea2" id="preferedArea2" required="required">
							    	<option value=""/>
								  	<xsl:for-each select="Areas/MunicipalityJobArea">
							  			<xsl:if test="canBeChosenInApplication = 'true'">
									  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
								  		</xsl:if>
							  		</xsl:for-each>									  
								</select>
								<p class="help-block with-errors"></p>
							</div>
							<div class="col-md-4 form-group">
							    <label for="preferedArea3">Önskat arbetsområde - prio 3*</label>				    
							    <select class="form-control" name="preferedArea3" id="preferedArea3" required="required">
							    	<option value=""/>
								 	<xsl:for-each select="Areas/MunicipalityJobArea">
							  			<xsl:if test="canBeChosenInApplication = 'true'">
									  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
								  		</xsl:if>
						  			</xsl:for-each>									  
								</select>
								<p class="help-block with-errors"></p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-5">
						  		<div class="checkbox">
								  <label for="noPreferedArea">
								    <input type="checkbox" value="true" name="noPreferedArea" id="noPreferedArea">
								    	Jag kan tänka mig jobba med vad som helst
								    </input>
								  </label>
								</div>
							</div>
						</div>
				  	</div>
				  	
				  	<div>
				  		<div class="row">
				  			<div class="form-group col-md-4">
							    <label for="geoArea1">Önskat geografiskt område 1*</label>				    
							    <select class="form-control" name="geoArea1" id="geoArea1" required="required">
									<option value=""/>
									<xsl:for-each select="GeoAreas/GeoArea">
									  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
						  			</xsl:for-each>
								</select>
								<p class="help-block with-errors"></p>
							</div>
							
							<div class="form-group col-md-4">
							    <label for="geoArea2">Önskat geografiskt område 2*</label>				    
							    <select class="form-control" name="geoArea2" id="geoArea2" required="required">
									<option value=""/>
									<xsl:for-each select="GeoAreas/GeoArea">
									  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
						  			</xsl:for-each>
								</select>
								<p class="help-block with-errors"></p>
							</div>
							<div class="col-md-4">
							    <label for="geoArea3">Önskat geografiskt område 3</label>				    
							    <select class="form-control" name="geoArea3" id="geoArea3">
									<option value=""/>
									<xsl:for-each select="GeoAreas/GeoArea">
									  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
						  			</xsl:for-each>
								</select>
							</div>
						</div>
				  	</div>
				  	<div class="form-group">							
						<div class="checkbox">
						    <label>
						      <input type="checkbox" name="hasDriversLicense">Har du körkort?</input>
						    </label>
					  	</div>
					</div>
					<div id="driverslicense_select" class="row">
				  		<div class="form-group col-md-4">
						    <label for="driversLicenseType">Välj körkortstyp*</label>				    
						    <select class="form-control" name="driversLicenseType" id="driversLicenseType">
						    	<option value="" />
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
							<p class="help-block with-errors"></p>
						</div>
					</div>
				  </div>
			  </div>
			 
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Personuppgifter</h3>
				  </div>
				  <div class="panel-body">
				  
				  	<div class="row">
				    	<div class="col-md-3">
					    	<div class="form-group">
							    <label for="street">Personnummer*</label>				    
							    <input type="text" data-error="Med sekel angivet. 12 tecken." data-minlength="12" data-maxlength="12" class="form-control numberValidation" id="street" name="socialSecurityNumber" placeholder="" required="required"/>
							    <p class="help-block with-errors"></p>				    
						  	</div>
						</div>
					</div> 
				  
					  <div class="row">
		  				<div class="col-md-3 form-group">
						    <label for="firstname">Förnamn*</label>				    
						    <input type="text" class="form-control" id="firstname" name="firstname" placeholder="" required="required"/>
						    <p class="help-block with-errors"></p>						    
				    	</div>
				    	<div class="col-md-3 form-group">
						    <label for="lastname">Efternamn*</label>				    
						    <input type="text" class="form-control" id="lastname" name="lastname" placeholder="" required="required"/>
						    <p class="help-block with-errors"></p>						    
				    	</div>
				    
		  				<div class="col-md-3 form-group">
						    <label for="phone">Telefonnummer*</label>				    
						    <input type="text" class="numberValidation form-control" id="phone" name="phone" placeholder="" required="required"/>
						    <p class="help-block with-errors">Endast siffror</p>
				    	</div>
				    
		  				<div class="col-md-3">
						    <label for="email">E-post</label>				    
						    <input type="text" class="form-control" id="email" name="email" placeholder=""/>
						    <p class="help-block">Valfri</p>
				    	</div>
			    	</div>
			    	
					<div class="row">
					    <div class="form-group col-md-5">
						    <label for="street">Gatuadress*</label>				    
						    <input type="text" class="form-control" id="street" name="street" placeholder="" required="required"/>
						    <p class="help-block with-errors"></p>				    
					    </div>
					    <div class="form-group col-md-3">
						    <label for="postalcode">Postnummer*</label>				    
						    <input type="text" class="form-control" id="postalcode" name="postalcode" placeholder="" required="required" />
						    <p class="help-block with-errors"></p>
					    </div>
					    <div class="form-group col-md-4">
						    <label for="postalarea">Postort*</label>				    
						    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required"/>
						    <p class="help-block with-errors"></p>
					    </div>
					 </div>
					</div>
				</div>
				
				<div class="panel panel-default">
			  		<div class="panel-heading">
			  			<h3 class="panel-title">
			  				Skicka in ansökan
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
						
			  			<button style="margin-top: 4px;" id="submit-municipality-job-application" type="submit" class="float-rgt btn btn-success questions-submit">
			  				Skicka
			  			</button>
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div> 			
			</div>
			</form>
	    	
	    	
	</xsl:template>
	
</xsl:stylesheet>					