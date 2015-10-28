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
<!-- 		<form method="POST" id="municipality-job-form" enctype="multipart/form-data"> -->
		<form method="POST" id="municipality-job-form">
			<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Ansökan</h3>
				  </div>
				  <div class="panel-body">
				  	<div class="form-group">
					    <label for="personal-letter">Personligt brev*</label>				    
					    <textarea class="form-control" rows="7" id="personal-letter" name="personal-letter" required="required"></textarea>							    
					    <p class="help-block">Berätta något</p>
				  	</div>
				  	 <div class="form-group">
					    <label for="cvInputFile">Ladda upp ditt cv</label>
					    <input type="file" id="cvInputFile" name="cvFile"/>
					    <p class="help-block">Om du har ett cv kan du ladda upp det.</p>
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
			    	
		    	 	<div class="row">
				    	<div class="col-md-3">
					    	<div class="form-group">
							    <label for="street">Personnummer</label>				    
							    <input type="text" class="form-control" id="street" name="socialSecurityNumber" placeholder=""/>					    
						  	</div>
						</div>
					</div> 	

					 
					 <!--  <div class="form-group">
					   <label for="area">Ange versamhetsområde</label>
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
								  			<td><input type="radio" name="area" id="area_{id}" value="{id}" /></td>
								  			<td><xsl:value-of select="name"/></td>
								  			<td><xsl:value-of select="description"/></td>
								  		</tr>
							  		</xsl:if>
						  		</xsl:for-each>						  		
						  	</tbody>
						  </table>
						</div> -->
					
						<div class="form-group">
					  		<div class="row">
					  			<div class="col-md-3">
								    <label for="preferedArea1">Önskat arbetsområde - prio 1 *</label>				    
								    <select class="form-control" name="preferedArea1" id="preferedArea1">
										<option value="-1"/>
										<xsl:for-each select="Areas/MunicipalityJobArea">
								  			<xsl:if test="canBeChosenInApplication = 'true'">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  		</xsl:if>
							  			</xsl:for-each>
									</select>
								</div>
								
								<div class="col-md-3">
								    <label for="preferedArea2">Önskat arbetsområde - prio 2 *</label>				    
								    <select class="form-control" name="preferedArea2" id="preferedArea2">
								    	<option value="-1"/>
									  	<xsl:for-each select="Areas/MunicipalityJobArea">
								  			<xsl:if test="canBeChosenInApplication = 'true'">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  		</xsl:if>
								  		</xsl:for-each>									  
									</select>
								</div>
								<div class="col-md-3">
								    <label for="preferedArea3">Önskat arbetsområde - prio 3 *</label>				    
								    <select class="form-control" name="preferedArea3" id="preferedArea3">
								    	<option value="-1"/>
									 	<xsl:for-each select="Areas/MunicipalityJobArea">
								  			<xsl:if test="canBeChosenInApplication = 'true'">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  		</xsl:if>
							  			</xsl:for-each>									  
									</select>
								</div>
						
						  		<div class="col-md-3">
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
					  	
					  	<div class="form-group">
					  		<div class="row">
					  			<div class="col-md-3">
								    <label for="geoArea1">Önskat geografiskt område 1 *</label>				    
								    <select class="form-control" name="geoArea1" id="geoArea1">
										<option value="-1"/>
										<xsl:for-each select="GeoAreas/GeoArea">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
							  			</xsl:for-each>
									</select>
								</div>
								
								<div class="col-md-3">
								    <label for="geoArea2">Önskat geografiskt område 2 *</label>				    
								    <select class="form-control" name="geoArea2" id="geoArea2">
										<option value="-1"/>
										<xsl:for-each select="GeoAreas/GeoArea">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
							  			</xsl:for-each>
									</select>
								</div>
								<div class="col-md-3">
								    <label for="geoArea3">Önskat geografiskt område 3</label>				    
								    <select class="form-control" name="geoArea3" id="geoArea3">
										<option value="-1"/>
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
							      <input type="checkbox" name="isOverEighteen">Är du över 18 år gammal? </input>
							    </label>
						  	</div>
							<div class="checkbox">
							    <label>
							      <input type="checkbox" name="hasDriversLicense">Har du körkort? </input>
							    </label>
						  	</div>
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
			
	    	
	    	
	</xsl:template>
	
</xsl:stylesheet>					