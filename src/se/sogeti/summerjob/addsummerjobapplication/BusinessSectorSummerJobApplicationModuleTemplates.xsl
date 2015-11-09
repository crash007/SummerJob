<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		
		<xsl:apply-templates select="JobInfo/BusinessSectorJob"/>
		<xsl:apply-templates select="JobApplicationForm"/>
		<xsl:apply-templates select="JobList"/>
	</xsl:template>
	
	
	<xsl:template match="JobInfo/BusinessSectorJob">
	<div class="well">
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
									<span class="mgn-lft8px" id="startDate"><xsl:value-of select="startDate"></xsl:value-of></span>
								</div>
								<div class="col-md-3">
									<label for="endDate">Slutdatum</label>
									<span class="mgn-lft8px" id="endDate"><xsl:value-of select="endDate"></xsl:value-of></span>
								</div>
						  	</div>
						  	<div class="mgn-top8px row">
						  		<div class="col-md-12">
							  		<label>Handledare</label>
							  		<table class="table">
										<thead>
											<tr>
												<th class="overview">Förnamn</th>
												<th class="overview">Efternamn</th>							
												<th class="overview">Telefonnummer</th>
												<th class="overview">E-postadress</th>
											</tr>
										</thead>
						
										<tbody>
											<xsl:for-each select="mentors/BusinessSectorMentor">
											<tr>
									  			<td><xsl:value-of select="firstname"></xsl:value-of></td>
									  			<td><xsl:value-of select="lastname"></xsl:value-of></td>
									  			<td><xsl:value-of select="mobilePhone"></xsl:value-of></td>
									  			<td><xsl:value-of select="email"></xsl:value-of></td>
									  		</tr>
								  			</xsl:for-each>
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
							<div class="mgn-top8px row">
								<div class="col-md-2">
									<label>Förnamn</label>
									<div>
										<xsl:value-of select="BusinessSectorManager/firstname"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-2">
									<label>Efternamn</label>
									<div>
										<xsl:value-of select="BusinessSectorManager/lastname"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-2">
									<label>Telefonnummer</label>
									<div>
										<xsl:value-of select="BusinessSectorManager/mobilePhone"></xsl:value-of>
									</div>
								</div>
								<div class="col-md-3">
									<label>E-postadress</label>
									<div>
										<xsl:value-of select="BusinessSectorManager/email"></xsl:value-of>
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
								<xsl:choose>
									<xsl:when test="hasDriversLicense = 'true'">Tjänsten kräver att sökande har körkort av typ <xsl:value-of select="DriversLicenseType/name"></xsl:value-of>.</xsl:when>
									<xsl:otherwise>Tjänsten kräver <i>ej</i> körkort</xsl:otherwise>
								</xsl:choose>
							</div>
						</div>
					</div>
					<div class="mgn-top8px row">
						<div class="col-md-4">
							<label>Ålder</label>
							<div>
								<xsl:choose>
									<xsl:when test="isOverEighteen = 'true'">Tjänsten kräver att sökande är över 18 år.</xsl:when>
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
		</div>
		<!-- <div class="well">
			<div class="row">
			<form class="form-horizontal">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Företag</label>
			    <div class="col-sm-10">
			      <p class="form-control-static"><xsl:value-of select="company"/></p>
			    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Beskrivning</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="workDescription"/></p>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Antal platser</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="numberOfWorkersNeeded"/></p>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">Address</label>
				    <div class="col-sm-10">
				      <p class="form-control-static"><xsl:value-of select="streetAddress"/></p>
				    </div>
			  </div>
			</form>
			</div>
	  	</div> -->
	
	</xsl:template>
	
	<xsl:template match="JobList">
	<h1>Lediga sommarjobb inom privata sektorn</h1>
		<div class="row well">
		  
		  <div class="col-xs-9 col-md-6">
		  	<div class="panel panel-default">
			  <div class="panel-heading">
			    <h3 class="panel-title">Sommarjobb inom näringslivet</h3>
			  </div>
			  <div class="panel-body">
			    <table class="table table-bordered">
				  <thead>
				  	<tr>
				  		<th>Yrke</th>
	     				<th>Företag</th>
	     				<th>Antal platser</th>
	     				<th>Inkommen</th>
	     				<th>Sök detta jobb</th>	     				
				  	</tr>
				  </thead>
				  
				  <tbody>
<!-- 				  <xsl:apply-templates select="BusinessSectorJob"/> -->
				  
					  <xsl:for-each select="BusinessSectorJob">
					  	<tr>
							<td>
					   			<xsl:value-of select="workTitle"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="company"/>
					   		</td>
					   		<td>
					   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
					   		</td>
					   		<td>
					   			<xsl:value-of select="created"></xsl:value-of>
					   		</td>
					   		<td>
					   			<a href="?jobId={id}">Ansök</a>
					   		</td>					   		
				   		</tr>
					  </xsl:for-each>
				  </tbody>
				</table>
			  </div>
			</div>
		  </div>
		  </div>
	
	</xsl:template>
	
	<xsl:template match="JobApplicationForm">
		<div class="well">
		<fieldset>
		<label>Ansök</label>
		<form method="POST" id="business-job-form">
			<input type="hidden" name="jobId" value="{jobId}"/>

				<article>
				
				  	<div class="form-group">
					    <label for="personal-letter">Personligt brev*</label>				    
					    <textarea class="form-control" rows="7" id="personal-letter" name="personal-letter" required="required"></textarea>							    
					    <p class="help-block">Berätta något</p>
				  	</div>
			  	</article>
				<article>
				  	 <div class="form-group">
					    <label for="cvInputFile">Ladda upp ditt cv</label>
					    <input type="file" id="cvInputFile" name="cvFile"/>
					    <p class="help-block">Om du har ett cv kan du ladda upp det.</p>
					  </div>
				  </article>
				  
				  
			  
			 
				<article>
				
				  <h3 class="panel-title">Personuppgifter</h3>				  
				  	<div class="row">
		  				<div class="col-md-3">
						    <label for="firstname">Förnamn</label>				    
						     <input type="text" class="form-control" id="firstname" name="firstname" placeholder=""/>							    
				    	</div>
				    	<div class="col-md-3">
						    <label for="lastname">Efternamn</label>				    
						     <input type="text" class="form-control" id="lastname" name="lastname" placeholder=""/>							    
				    	</div>
				    
		  				<div class="col-md-3">
						    <label for="phone">Telefonnummer</label>				    
						     <input type="text" class="form-control" id="phone" name="phone" placeholder=""/>
						    
				    	</div>
				    
		  				<div class="col-md-3">
						    <label for="email">E-post</label>				    
						     <input type="text" class="form-control" id="email" name="email" placeholder=""/>
						    <p class="help-block">Valfri</p>
				    	</div>
			    	</div>
	    		</article>  
			    	
		    	 	<div class="row">
				    	<div class="col-md-3">
					    	<div class="form-group">
							    <label for="street">Personnummer</label>				    
							    <input type="text" class="form-control" id="street" name="socialSecurityNumber" placeholder=""/>					    
						  	</div>
						</div>
					</div> 	

					
				  	<div class="form-group">						
						<div class="checkbox">
						    <label>
						      <input type="checkbox" name="hasDriversLicense">Har du körkort? </input>
						    </label>
					  	</div>
					  	<div id="driverslicense_select" class="row">
					  		<div class="col-md-3">
							    <label for="driversLicenseType">Välj körkortstyp</label>				    
							    <select class="form-control" name="driversLicenseType" id="driversLicenseType">
									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
										<option value="{id}"><xsl:value-of select="name" /> - <xsl:value-of select="description" /></option>
									</xsl:for-each>
								</select>
							</div>
						</div>
					</div> 
					  		
				
				
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Adress</h3>
				  </div>
				  <div class="panel-body">
			  		<div class="form-group">
				  		<div class="row">
						    <div class="col-md-5">
							    <label for="street">Gatuadress</label>				    
							    <input type="text" class="form-control" id="street" name="street" placeholder=""/>					    
						    </div>
						    <div class="col-md-3">
							    <label for="postalcode">Postnumer</label>				    
							    <input type="text" class="form-control" id="postalcode" name="postalcode" placeholder=""/>
						    </div>
						    <div class="col-md-4">
							    <label for="postalarea">Postort</label>				    
							    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder=""/>
						    </div>
					    </div>
				  	</div>				  	
				  </div>
			  	</div>
			  	
		  		
		  		<button type="submit" class="btn btn-default questions-submit">Submit</button>
				 <span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
			</form>
			</fieldset>
			</div>
	    	
	    	
	</xsl:template>

	
</xsl:stylesheet>					