<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		
		<xsl:apply-templates select="JobInfo/BusinessSectorJob"/>
		<xsl:apply-templates select="JobApplicationForm"/>
		<xsl:apply-templates select="JobList"/>
	</xsl:template>
	
	
	<xsl:template match="JobInfo/BusinessSectorJob">
		<h1 class="createJobHeadline">Ansök till sommarjobb inom näringslivet</h1>
	  	<div class="panel panel-default">
		  	<div class="panel-heading">
		  		<h3 class="panel-title">Arbete</h3>
		  	</div>
		  	<div class="panel-body">
			  	<div class="row">
			  		<div class="col-md-5">
						<label for="work">Yrkestitel</label>
						<div id="work"><xsl:value-of select="workTitle"></xsl:value-of></div>
					</div>
			  	</div>
			  	<div class="mgn-top8px row">
			  		<div class="col-md-12">
						<label for="description">Yrkesbeskrivning</label>
						<div id="description"><xsl:value-of select="workDescription"></xsl:value-of></div>
					</div>
			  	</div>
			  	
			  	<div class="mgn-top8px row">
			  		<div class="col-md-3">
						<label for="numberNeeded">Antal platser</label>
						<span class="mgn-lft8px" id="numberNeeded"><xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of></span>
					</div>
				</div>
			  	
			  	<div class="mgn-top8px row">
					<div class="col-md-3">
						<label for="startDate">Startdatum</label>
						<span class="mgn-lft8px" id="startDate"><xsl:value-of select="substring(startDate, 1, 10)"></xsl:value-of></span>
					</div>
					<div class="col-md-3">
						<label for="endDate">Slutdatum</label>
						<span class="mgn-lft8px" id="endDate"><xsl:value-of select="substring(endDate, 1, 10)"></xsl:value-of></span>
					</div>
			  	</div>
			  	
			  	<xsl:if test="mentors">
				  	<div class="mgn-top8px row">
				  		<div class="col-md-12">
					  		<label>Handledare</label>
				  			<div class="row">
				  				<div class="col-md-2 bold col-xs-2" style="font-size: 90%; padding-right: 0px;">Förnamn</div>
				  				<div class="col-md-2 bold col-xs-2" style="font-size: 90%; padding-right: 0px;">Efternamn</div>
				  				<div class="col-md-2 bold col-xs-3" style="font-size: 90%; padding-right: 0px;">Telefonnummer</div>
				  				<div class="col-md-2 bold col-xs-3" style="font-size: 90%; padding-right: 0px;">E-postadress</div>
				  			</div>
				  			<xsl:for-each select="mentors/BusinessSectorMentor">
				  				<div class="row">
				  					<div class="col-md-2 col-xs-2" style="padding-right: 0px;"><xsl:value-of select="firstname" /></div>
				  					<div class="col-md-2 col-xs-2" style="padding-right: 0px;"><xsl:value-of select="lastname" /></div>
				  					<div class="col-md-2 col-xs-3" style="padding-right: 0px;"><xsl:value-of select="mobilePhone" /></div>
				  					<div class="col-md-2 col-xs-3" style="padding-right: 0px;"><xsl:value-of select="email" /></div>
				  				</div>
				  			</xsl:for-each>
						</div>
				  	</div>
			  	</xsl:if>
		  	</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Uppgifter om arbetsplatsen</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-5">
						<label for="company">Företag</label>
						<div id="company">
							<xsl:value-of select="company"></xsl:value-of>
						</div>
					</div>
				</div>
				<div class="mgn-top8px row">
					<div class="col-md-3">
						<label for="company">Gatuadress</label>
						<div id="company">
							<xsl:value-of select="streetAddress"></xsl:value-of>
						</div>
					</div>
					<div class="col-md-2">
						<label for="zipcode">Postnummer</label>
						<div id="zipcode">
							<xsl:value-of select="zipCode"></xsl:value-of>
						</div>
					</div>
					<div class="col-md-2">
						<label for="city">Postort</label>
						<div id="city">
							<xsl:value-of select="city"></xsl:value-of>
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
						<div>
							<xsl:value-of select="DriversLicenseType/name"/>								
						</div>
					</div>
				</div>
				<div class="mgn-top8px row">
					<div class="col-md-4">
						<label>Ålder</label>
						<div>
							<xsl:choose>
								<xsl:when test="mustBeOverEighteen = 'true'">Tjänsten kräver att sökande är över 18 år.</xsl:when>
								<xsl:otherwise>Tjänsten kräver <i>ej</i> att sökande är över 18 år.</xsl:otherwise>
							</xsl:choose>
						</div>
					</div>
				</div>
				<div class="mgn-top8px row">
					<div class="col-md-12">
						<label>Övriga krav och önskemål</label>
						<div>
							<xsl:value-of select="freeTextRequirements"></xsl:value-of>
						</div>
					</div>
				</div>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="JobList">
		<h1>Lediga sommarjobb inom privata sektorn</h1>
		<div id="page-tabs" class="vacancy-list mgn-top16px">
	        <div class="content dynamic-content">
		        <ul class="pagelist">
		        	<xsl:for-each select="BusinessSectorJob">
		        	 	<li>
		        	 	
							<xsl:if test="(position() mod 2) = 1">							
								<xsl:attribute name="class">alternate</xsl:attribute>
							</xsl:if>
						
		        	 		<a href="?jobId={id}" title="{workTitle}">		                    
		                        <span class="h3span"><xsl:value-of select="workTitle"/></span>
		                        <span class="lh140"><xsl:value-of select="workDescription" /></span>
		                        <span class="last-appliance">Sista ansökningsdag <xsl:value-of select="substring(lastApplicationDay, 1, 10)" /> 
	                        	</span>
		                    </a>
				        </li>				        
		        	</xsl:for-each>				        
				</ul>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="JobApplicationForm">
		<form method="POST" role="form" id="business-job-application-form" enctype="multipart/form-data" data-toggle="validator">
			<input type="hidden" name="jobId" value="{jobId}"/>
			<input type="hidden" name="appId" value="{BusinessSectorJobApplication/id}"/>
			<div class="panel panel-default">
		 		<div class="panel-heading">
		 			<h3 class="panel-title">Personuppgifter</h3>
		 		</div>
		 		<div class="panel-body">
		    	 	<div class="row">
				    	<div class="col-md-3">
					    	<div class="form-group">
							    <label>Personnummer*</label>				    
							    <input type="text" value="{BusinessSectorJobApplication/socialSecurityNumber}" data-error="ÅÅÅÅDDMMxxxx" data-minlength="12" maxlength="12" class="numberValidation form-control" id="socialSecurityNumber" name="socialSecurityNumber" placeholder="" required="required"/>
							    <p class="help-block with-errors">ÅÅÅÅDDMMxxxx</p>				    
						  	</div>
						</div>
					</div> 	
				  	<div class="row">
		  				<div class="form-group col-md-3">
						    <label for="firstname">Förnamn*</label>				    
						     <input type="text" value="{BusinessSectorJobApplication/firstname}" class="form-control" id="firstname" name="firstname" placeholder="" required="required"/>	
						     <p class="help-block with-errors"></p>						    
				    	</div>
				    	<div class="form-group col-md-3">
						    <label for="lastname">Efternamn*</label>				    
						     <input type="text" value="{BusinessSectorJobApplication/lastname}" class="form-control" id="lastname" name="lastname" placeholder="" required="required"/>	
						     <p class="help-block with-errors"></p>							    
				    	</div>
				    
		  				<div class="form-group col-md-3">
						    <label for="phone">Telefonnummer*</label>				    
						    <input type="text" value="{BusinessSectorJobApplication/phoneNumber}" class="form-control numberValidation" id="phone" name="phone" placeholder="" required="required"/>
						    <p class="help-block with-errors">Endast siffror</p>	
				    	</div>
		  				<div class="form-group col-md-3">
						    <label for="email">E-post*</label>				    
						    <input type="email" value="{BusinessSectorJobApplication/email}" class="form-control" id="email" name="email" placeholder="" required="required"/>
						    <p class="help-block with-errors"></p>
				    	</div>
			    	</div>
			    	<div class="form-group">
				  		<div class="row">
						    <div class="form-group col-md-5">
							    <label for="street">Gatuadress*</label>				    
							    <input type="text" value="{BusinessSectorJobApplication/streetAddress}" class="form-control" id="street" name="street" placeholder="" required="required"/>
							    <p class="help-block with-errors"></p>		    
						    </div>
						    <div class="form-group col-md-3">
							    <label for="postalcode">Postnummer*</label>				    
							    <input type="text" value="{BusinessSectorJobApplication/zipCode}" class="numberValidation form-control" data-error="Ett postnummer måste ha fem siffror." data-minlength="5" maxlength="5" id="postalcode" name="postalcode" placeholder="" required="required"/>
							    <p class="help-block with-errors"></p>	
						    </div>
						    <div class="form-group col-md-4">
							    <label for="postalarea">Postort*</label>				    
							    <input type="text" value="{BusinessSectorJobApplication/city}" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required"/>
							    <p class="help-block with-errors"></p>
						    </div>
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
					    <textarea class="form-control" rows="7" id="personal-letter" name="personal-letter" required="required">
					    	<xsl:value-of select="BusinessSectorJobApplication/personalLetter"></xsl:value-of>
					    </textarea>							    
					    <p class="help-block with-errors">Skriv lite information om vem du är, vad du är intresserad av och vad du är duktig på</p>
				  	</div>
					<div class="form-group">
<!-- 							    <label for="cvInputFile">Ladda upp ditt cv*</label> -->
					    <xsl:choose>
					    	<xsl:when test="BusinessSectorJobApplication/cvFilename">
					    		<label for="cvInputFile">Ladda upp ditt cv</label>
					    		<p><strong>Befintlig: </strong><i id="currentCV"><xsl:value-of select="BusinessSectorJobApplication/cvFilename"></xsl:value-of></i></p>
					    		<input type="file" class="form-control" id="cvInputFile" name="cvFile"/>
					    	</xsl:when>
					    	<xsl:otherwise>
					    		<label for="cvInputFile">Ladda upp ditt cv*</label>
					    		<input type="file" class="form-control" id="cvInputFile" name="cvFile" required="required"/>
							    	</xsl:otherwise>
							    </xsl:choose>
<!-- 							    <input type="file" class="form-control" id="cvInputFile" name="cvFile" required="required"/> -->
					    <p class="help-block with-errors"></p>
					</div>
					
					<div class="form-group">														
					  	<div id="driverslicense_select" class="row">
					  		<div class="form-group col-md-4">
							    <label for="driversLicenseType">Välj körkortstyp</label>				    
							    <select class="form-control" name="driversLicenseType" id="driversLicenseType">									    	
									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
										<option value="{id}">
											<xsl:if test="/Document/JobApplicationForm/BusinessSectorJobApplication/DriversLicenseType/id = id">
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
		  									
					<button id="preview-business-job-application" type="submit" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
		  				Förhandsgranska
		  			</button>
					
					<xsl:if test="BusinessSectorJobApplication">
	  					<a href="{manageAppURL}?appId={BusinessSectorJobApplication/id}" class="btn-mgn-top pull-right btn btn-primary">Tillbaka</a>
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
				  	<div class="mgn-top8px row">
				  		<div class="col-md-5">
				  			<label>Körkort</label>
				  			<input type="hidden" id="hasDriversLicenseText" value="Ja, jag har körkort av typ " />
							<input type="hidden" id="noDriversLicenseText" value="Nej, jag har inget körkort" />
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
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
						<span class="sr-only">Error:</span>
						<span class="message"></span>
					</div>
					<div class="save-succeeded alert alert-success" role="alert">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						<span class="sr-only">Success:</span>
						<span class="message"></span>
					</div>
					<xsl:choose>
						<xsl:when test="BusinessSectorJobApplication">
							<button id="submit-business-job-application" type="submit" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
		  						Spara
		  					</button>
						</xsl:when>
						<xsl:otherwise>
							<button id="submit-business-job-application" type="submit" class="btn-mgn-top pull-right mgn-lft8px btn btn-success">
		  						Skicka
		  					</button>
						</xsl:otherwise>
					</xsl:choose>
					
					<button id="cancel-preview-business-sector-application" class="btn-mgn-top mgn-lft8px btn btn-warning">Redigera</button>
					<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
				</div>
		  	</div> 	
		</div>
			
	</xsl:template>
</xsl:stylesheet>					