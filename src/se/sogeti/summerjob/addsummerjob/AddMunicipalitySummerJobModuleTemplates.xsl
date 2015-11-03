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
			 
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Plats</h3>
				  </div>
				  <div class="panel-body">
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="organisation">Ange organisation*</label>
							    <input type="text" class="form-control" id="organisation" name="organisation" placeholder="" required="required"/>				    					    
							    <p class="help-block with-errors">Tex, Sundsvalls eln�t, Sundsvalls kommun</p>
					    	</div>
					    </div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="administration">Ange f�rvaltning*</label>
							    <input type="text" class="form-control" id="administration" name="administration" placeholder="" required="required"/>
							    <p class="help-block with-errors">Tex Kultur och fritid</p>
				    		</div>
		    			</div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="location">Ange platsen</label>
							    <input type="text" class="form-control" id="location" name="location" placeholder="" required="required"/>
							    <p class="help-block with-errors">Tex Himlabadet</p>
						   	</div>
					   	</div>					    
					  </div>
					 
					  <div class="form-group">
					   <label for="area">Ange versamhetsomr�de*</label>
						  <table class="table">
						  	<thead>
						  		<tr>
						  			<th>V�lj</th>
						  			<th>Verksamhetsomr�de</th>
						  			<th>Beskrivning</th>
						  		</tr>
						  	</thead>
						  	<tbody>
						  		<xsl:for-each select="Areas/MunicipalityJobArea">
						  			<xsl:if test="canBeChosenInApplication = 'true'">
								  		<tr>
								  			<td><input type="radio" name="area" id="area_{id}" value="{id}" required="required"/></td>
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
							    <input type="text" class="form-control" id="street" name="street" placeholder="" required="required"/>
							    <p class="help-block with-errors"></p>					    
						    </div>
						    <div class="form-group col-md-3">
							    <label for="postalcode">Postnummer*</label>				    
							    <input type="number" data-error="Ett postnummer m�ste ha fem siffror." class="form-control" id="postalcode" name="postalcode" placeholder="" required="required" data-minlength="5"/>
							    <p class="help-block with-errors"></p>
						    </div>
						    <div class="form-group col-md-4">
							    <label for="postalarea">Postort*</label>				    
							    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required"/>
							    <p class="help-block with-errors"></p>
						    </div>
					    </div>
				  	<div class="form-group">
					  	<div class="row">
						  	<div class="col-md-5">
							    <label for="department">Ange avdelning</label>				    
							     <input type="text" class="form-control" id="department" name="department" placeholder=""/>
							    <p class="help-block">Avdelning �r inte obligatorisk</p>
						  	</div>
					  	</div>
				  	</div>
				  </div>
			  	</div>
			  	
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Geografiskt omr�de</h3>
				  </div>
				  <div class="panel-body">
					  	<div id="geoAreaSelect" class="row">
					  		<div class="col-md-4">
							    <label for="geoArea">V�lj geografiskt omr�de*</label>				    
							    <select class="form-control" name="geoArea" id="geoArea" required="required">
									<xsl:for-each select="GeoAreas/GeoArea">
										<option value="{id}"><xsl:value-of select="name" /></option>
									</xsl:for-each>
								</select>
								<p class="help-block with-errors">Det geografiska omr�de arbetsplatsen tillh�r</p>
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
							     <input type="text" class="form-control" id="work-title" name="work-title" placeholder="" required="required"/>
							     <p class="help-block with-errors"></p>							    
						  	</div>
					  	</div>
				  	</div>
				  	<div class="form-group">
					    <label for="work-description">Arbetsbeskrivning*</label>				    
					    <textarea class="form-control" rows="5" id="work-description" name="work-description" required="required"></textarea>							    
					    <p class="help-block with-errors">Beskriv vad arbetsuppgifterna kommer vara</p>
				  	</div>
				  	<div class="form-group">
				  		<div class="row">
				  			<div class="col-md-3">
				  				<label for="numberOfWorkersNeeded">Antal lediga platser*</label>
							    <input class="form-control" type="number" min="1" max="99" name="numberOfWorkersNeeded" id="numberOfWorkersNeeded" required="required"/>	
							    <p class="help-block">Skriv i heltal mellan 1 och 99</p>	    
							</div>
						</div>
				  	</div>
				  	
				  	<div class="form-group">
					  	<label for="period">V�lj perioder*</label>
						  <table id="period-table" class="table">
						  	<thead>
						  		<tr>
						  			<th>V�lj</th>
						  			<th>Namn</th>
						  			<th>Startdatum</th>
						  			<th>Slutdatum</th>
						  		</tr>
						  	</thead>
						  	<tbody>
								<div class="checkbox-group">
							  		<xsl:for-each select="Periods/Period">						  			
								  		<tr>
								  			<td>
								  			    <input type="checkbox" name="period_{id}"/>
											</td>							  			
								  			<td><xsl:value-of select="name"/></td>
								  			<td><xsl:value-of select="startDate"/></td>
								  			<td><xsl:value-of select="endDate"/></td>
								  		</tr>
							  		
							  		</xsl:for-each>	
							  	</div>					  		
						  	</tbody>
					  	</table>
					  	<p style="display: none; color: #a94442;" id="period-errors" class="help-block with-errors">Du m�ste v�lja minst en period</p>
					</div>
					
<!-- 					<div class="form-group"> -->
<!-- 						<div class="checkbox"> -->
<!-- 						    <label> -->
<!-- 						      <input type="checkbox" name="isOverEighteen">M�ste vara �ver 18 �r </input> -->
<!-- 						    </label> -->
<!-- 					  	</div> -->
<!-- 						<div class="checkbox"> -->
<!-- 						    <label> -->
<!-- 						      <input type="checkbox" name="hasDriversLicense">M�ste ha k�rkort </input> -->
<!-- 						    </label> -->
<!-- 					  	</div> -->
						  	
<!-- 						<div id="driverslicense_select" class="row"> -->
<!-- 					  		<div class="col-md-3"> -->
<!-- 							    <label for="driversLicenseType">V�lj k�rkortstyp</label>				     -->
<!-- 							    <select class="form-control" name="driversLicenseType" id="driversLicenseType"> -->
<!-- 									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType"> -->
<!-- 										<option value="{id}"><xsl:value-of select="name" /> - <xsl:value-of select="description" /></option> -->
<!-- 									</xsl:for-each> -->
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 						</div>  	 -->
							
<!-- 					</div> -->
				  	
<!-- 			  		<div class="form-group"> -->
					<div>
			  			<label>Ange chef p� arbetsplatsen</label>
			  			<div class="row">
				  				<div class="form-group col-md-3">
								    <label for="manager">F�rnamn*</label>				    
								     <input type="text" class="form-control" id="manager-firstname" name="manager-firstname" placeholder="" required="required"/>							    
						    	</div>
						    	<div class="form-group col-md-3">
								    <label for="manager">Efternamn*</label>				    
								     <input type="text" class="form-control" id="manager-lastname" name="manager-lastname" placeholder="" required="required"/>							    
						    	</div>
						    	<div class="form-group col-md-3">
								    <label for="manager">Telefonnummer*</label>				    
								     <input type="text" class="form-control" id="manager-phone" name="manager-phone" placeholder="" required="required"/>
						    	</div>
				  				<div class="col-md-3">
								    <label for="manager">E-post</label>				    
								     <input type="text" class="form-control" id="manager-email" name="manager-email" placeholder=""/>
								    <p class="help-block">Valfri</p>
						    	</div>						    	
				    	</div>
			    	</div>
			  		
			  		<div class="form-group">
			  			<label>Ange handledare</label>
			  			<div id="mentors-wrapper">
				  			
				    	</div>
				    	
				    	<a href="#" class="add-mentor-btn">L�gg till handledare</a>
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
						    	<input type="checkbox" name="isOverEighteen">M�ste vara �ver 18 �r</input>
						    </label>
					  	</div>
						<div class="checkbox">
						    <label>
						    	<input type="checkbox" name="hasDriversLicense">M�ste ha k�rkort</input>
						    </label>
					  	</div>
					  	
					  	<div id="driverslicense_select" class="row">
					  		<div class="col-md-3">
							    <label for="driversLicenseType">V�lj k�rkortstyp</label>				    
							    <select class="form-control" name="driversLicenseType" id="driversLicenseType">
									<xsl:for-each select="DriversLicenseTypes/DriversLicenseType">
										<option value="{id}"><xsl:value-of select="name" /> - <xsl:value-of select="description" /></option>
									</xsl:for-each>
								</select>
							</div>
						</div>
					  	
						<div class="form-group">
						    <label for="work-description">�vriga �nskem�l och krav</label>				    
						    <textarea class="form-control" rows="3" id="other-requirements" name="other-requirements"></textarea>							    
						    <p class="help-block">�vriga �nskem�l</p>
					  	</div>    	
				  	
				  </div>
			  	</div> 
		  		
		  		<div class="panel panel-default">
			  		<div class="panel-heading">
			  			<h3 class="panel-title">Skicka in annons</h3>
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
						
			  			<button style="margin-top: 4px;" id="submit-municipality-job" type="submit" class="btn btn-default questions-submit">Skicka</button>
						<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  	</div> 					  	
		  		
<!-- 		  		<button type="submit" id="submit-municipality-job" class="btn btn-default questions-submit">Skicka</button> -->
<!-- 				<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span> -->
			</form>
			
			<div id="mentor-template" >
	    		<div class="row collapse">
	  				<div class="col-md-3">
					    <label for="mentor">F�rnamn</label>				    
					     <input type="text" class="form-control" id="mentor-firstname" name="mentor-firstname" placeholder=""/>							    
			    	</div>
			    	<div class="col-md-3">
					    <label for="mentor">Efternamn</label>				    
					     <input type="text" class="form-control" id="mentor-lastname" name="mentor-lastname" placeholder=""/>							    
			    	</div>
			    
	  				<div class="col-md-3">
					    <label for="mentor">Telefonnummer</label>				    
					     <input type="text" class="form-control" id="mentor-phone" name="mentor-phone" placeholder=""/>
					    
			    	</div>
			    
	  				<div class="col-md-3">
					    <label for="mentor">E-post</label>				    
					     <input type="text" class="form-control" id="mentor-email" name="mentor-email" placeholder=""/>
					    <p class="help-block">Valfri</p>
			    	</div>
		    	</div>
	    	
	    	</div>
	</xsl:template>
	
</xsl:stylesheet>					