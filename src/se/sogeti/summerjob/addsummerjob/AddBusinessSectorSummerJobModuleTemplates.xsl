<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>
	
	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:variable name="links">
		/css/flowengine.css
		/uitheme/jquery-ui-1.8.7.custom.css
	</xsl:variable>

	<xsl:template match="Document">
		 
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		
		<xsl:variable name="scripts">
			/js/jquery.ui.datepicker.js
			/js/jquery.ui.datepicker-sv.js
		</xsl:variable>
		
		<script>
			$(function() {						
				$( "input[name*='startDate']" ).datepicker({
					showOn: "button",
					buttonImage: '<xsl:value-of select="/Document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/Document/module/sectionID"/>/<xsl:value-of select="/Document/module/moduleID"/>/pics/calendar_grid.png',
					buttonImageOnly: true,
					buttonText: 'Startdatum'
				});
				
				$( "input[name*='endDate']" ).datepicker({
					showOn: "button",
					buttonImage: '<xsl:value-of select="/Document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/Document/module/sectionID"/>/<xsl:value-of select="/Document/module/moduleID"/>/pics/calendar_grid.png',
					buttonImageOnly: true,
					buttonText: 'Slutdatum'
				});								    							  									
			});	
		</script>		
		
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="BusinessSectorJobForm"/>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobForm">
		<form method="POST" id="business-sector-job-form">
		
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Arbete</h3>
				  </div>
				  <div class="panel-body">
				   <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="profession">Yrkestitel*</label>
							    <input type="text" class="form-control" id="profession" name="profession" placeholder="" required="required"/>
				    		</div>
		    			</div>
					  </div>
				  	<div class="form-group">
					    <label for="work-description">Arbetsbeskrivning*</label>				    
					    <textarea class="form-control" rows="5" id="work-description" name="work-description" required="required"></textarea>							    
					    <p class="help-block">Beskriv vad arbetet g�r ut p�</p>
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
<!-- 				  	<label for="period">Period</label> -->
					  	<div class="form-group form-inline">
						  	<div class="row">
				  				<div class="col-md-3">
								    <label for="startDate">Startdatum*</label>
								    <input type="text" class="form-control" id="startDate" name="startDate" placeholder="" required="required"/>
<!-- 								    <p class="help-block">Datum n�r arbetet b�rjar</p> -->
					    		</div>
					    		<div class="col-md-3">
								    <label for="endDate">Slutdatum*</label>
								    <input type="text" class="form-control" id="endDate" name="endDate" placeholder="" required="required"/>
<!-- 								    <p class="help-block">Datum n�r arbetet avslutas</p> -->
					    		</div>
			    			</div>
						  </div>
				  	</div>
			  		
			  		
			  		<div class="form-group" style="margin-top: 8px;">
			  			<label>Ange handledare</label>
			  			<div id="mentors-wrapper">
			  			
				    	</div>
				    	
<!-- 				    	<div id="mentor-template"> -->
<!-- 				    		<div class="row" style="margin-bottom: 8px;"> -->
<!-- 				  				<div class="col-md-3"> -->
<!-- 								    <label for="mentor-firstname">Förnamn</label>				     -->
<!-- 								    <input type="text" class="form-control" id="mentor-firstname" name="mentor-firstname" placeholder=""/>	 -->
<!-- 						    	</div> -->
						    
<!-- 						    	<div class="col-md-3"> -->
<!-- 						    	 	<label for="mentor-lastname">Efternamn</label>	 -->
<!-- 								    <input type="text" class="form-control" id="mentor-lastname" name="mentor-lastname" placeholder=""/>						   -->
<!-- 						    	</div> -->
						    
<!-- 				  				<div class="col-md-3"> -->
<!-- 								    <label for="mentor-phone">Telefonnummer</label>				     -->
<!-- 								     <input type="text" class="form-control" id="mentor-phone" name="mentor-phone" placeholder=""/> -->
								    
<!-- 						    	</div> -->
						    
<!-- 				  				<div class="col-md-3"> -->
<!-- 								    <label for="mentor-email">E-post</label>				     -->
<!-- 								     <input type="text" class="form-control" id="mentor-email" name="mentor-email" placeholder=""/> -->
<!-- 							    <p class="help-block">Valfri</p> -->
<!-- 						    	</div> -->
<!-- 					    	</div> -->
				    	
<!-- 				    	</div> -->
				    	<a href="#" class="add-mentor-btn">L�gg till ny handledare</a>
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
			  				<div class="col-md-4">
							    <label for="company">F�retag*</label>				    
							     <input type="text" class="form-control" id="company" name="company" placeholder="" required="required"/>							    
					    	</div>
				    	</div>
			    	</div>
						    	
			  		<div class="form-group">
				  		<div class="row">
						    <div class="col-md-5">
							    <label for="street">Gatuadress*</label>				    
							    <input type="text" class="form-control" id="street" name="street" placeholder="" required="required"/>					    
							    <p class="help-block">Adress till platsen d�r arbetet ska genomf�ras</p>
						    </div>
						    <div class="col-md-3">
							    <label for="postalcode">Postnummer*</label>				    
							    <input type="text" class="form-control" id="postalcode" name="postalcode" placeholder="" required="required"/>
						    </div>
						    <div class="col-md-4">
							    <label for="postalarea">Postort*</label>				    
							    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder="" required="required"/>
						    </div>
					    </div>
				  	</div>
				  	
				  	<div class="row" style="margin-bottom: 8px;">
						<div class="col-md-3">
							<label for="manager-firstname">F�rnamn*</label>
							<input type="text" class="form-control" id="manager-firstname"
								name="manager-firstname" placeholder="" required="required"/>
							<p class="help-block">Uppgifter till ansvarig p� arbetsplatsen</p>
						</div>
						
						<div class="col-md-3">
							<label for="manager-lastname">Efternamn*</label>
							<input type="text" class="form-control" id="manager-lastname"
								name="manager-lastname" placeholder="" required="required"/>
						</div>
					
						<div class="col-md-3">
							<label for="manager-phone">Telefonnummer*</label>
							<input type="text" class="form-control" id="manager-phone" name="manager-phone"
								placeholder="" required="required"/>
					
						</div>
					
						<div class="col-md-3">
							<label for="manager-email">E-post</label>
							<input type="text" class="form-control" id="manager-email" name="manager-email"
								placeholder="" />
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
									<option>AM - Moped klass I</option>
									<option>A1 - L�tt motorcykel</option>
									<option>A2 - Mellanstor motorcykel</option>
									<option>A - Tung motorcykel</option>
									<option>B - Personbil och l�tt lastbil</option>
									<option>BE - Personbil med tungt sl�p</option>
									<option>C1/C1E - Medeltung lastbil</option>
									<option>C/CE - Tung lastbil</option>
									<option>D1/D1E - Mellanstor buss</option>
									<option>D/DE - Buss</option>
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
			  			<div>Efter att annonsen skickats in kommer en handl�ggare beh�va godk�nna den. N�r en handl�ggare godk�nt annonsen kommer den vara synlig f�r s�kande.</div>
			  			<button style="margin-top: 4px;" type="submit" class="btn btn-default questions-submit">Skicka</button>
						 <span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
					</div>
			  </div>
			</form>
			
			
			<div id="mentor-template">
				<div class="row collapse" style="margin-bottom: 8px;">
					<div class="col-md-3">
						<label for="mentor-firstname">F�rnamn</label>
						<input type="text" class="form-control" id="mentor-firstname"
							name="mentor-firstname" placeholder="" />
					</div>
			
					<div class="col-md-3">
						<label for="mentor-lastname">Efternamn</label>
						<input type="text" class="form-control" id="mentor-lastname"
							name="mentor-lastname" placeholder="" />
					</div>
			
					<div class="col-md-3">
						<label for="mentor-phone">Telefonnummer</label>
						<input type="text" class="form-control" id="mentor-phone" name="mentor-phone"
							placeholder="" />
			
					</div>
			
					<div class="col-md-3">
						<label for="mentor-email">E-post</label>
						<input type="text" class="form-control" id="mentor-email" name="mentor-email"
							placeholder="" />
						<!-- <p class="help-block">Valfri</p> -->
					</div>
				</div>
			</div>	
	</xsl:template>
	
</xsl:stylesheet>					