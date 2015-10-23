<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>


	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>
		<xsl:apply-templates select="BusinessSectorJobForm"/>
	</xsl:template>
	
	<xsl:template match="BusinessSectorJobForm">
		<form method="POST" id="municipality-job-form">
		
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Arbete</h3>
				  </div>
				  <div class="panel-body">
				   <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="profession">Ange yrke</label>
							    <input type="text" class="form-control" id="profession" name="profession" placeholder=""/>
							    
				    		</div>
		    			</div>
					  </div>
				  	<div class="form-group">
					    <label for="work-description">Arbetsbeskrivning</label>				    
					    <textarea class="form-control" rows="5" id="work-description" name="work-description"></textarea>							    
					    <p class="help-block">Beskriv vad arbetet går ut på.</p>
				  	</div>
				  	<div class="form-group">
				  		<div class="row">
				  			<div class="col-md-3">
							    <label for="numberOfWorkersNeeded">Väl hur många arbetare som önskas</label>				    
							    <select class="form-control" name="numberOfWorkersNeeded" id="numberOfWorkersNeeded">
								  <option>1</option>
								  <option>2</option>
								  <option>3</option>
								  <option>4</option>
								  <option>5</option>
								  <option>6</option>
								</select>
							</div>
						</div>
				  	</div>
				  	
				  	<div class="form-group">
				  	<label for="period">Period</label>
					  	<div class="form-group">
						  	<div class="row">
				  				<div class="col-md-4">
								    <label for="startDate">Startdatum</label>
								    <input type="text" class="form-control" id="startDate" name="startDate" placeholder=""/>
								    <p class="help-block">Datum när arbetet börjar</p>
					    		</div>
					    		<div class="col-md-4">
								    <label for="endDate">Slutdatum</label>
								    <input type="text" class="form-control" id="endDate" name="endDate" placeholder=""/>
								    <p class="help-block">Datum när arbetet avslutas</p>
					    		</div>
			    			</div>
						  </div>
				  	</div>
			  		
			  		
			  		<div class="form-group">
			  			<label>Ange kontaktpersoner</label>
			  			<div id="contacts-wrapper">
				  			
				    	</div>
				    	
				    	<div id="contact-template">
				    		<div class="row">
				  				<div class="col-md-3">
								    <label for="mentor">Namn</label>				    
								     <input type="text" class="form-control" id="contact-name" name="contact-name" placeholder=""/>							    
						    	</div>
						    
						    
				  				<div class="col-md-3">
								    <label for="mentor">Telefonnummer</label>				    
								     <input type="text" class="form-control" id="contact-phone" name="contact-phone" placeholder=""/>
								    
						    	</div>
						    
						    
				  				<div class="col-md-3">
								    <label for="mentor">E-post</label>				    
								     <input type="text" class="form-control" id="contact-email" name="contact-email" placeholder=""/>
								    <p class="help-block">Valfri</p>
						    	</div>
					    	</div>
				    	
				    	</div>
				    	
				    	<a href="#" class="add-mentor-btn">Lägg till ny kontakt</a>
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
			  				<div class="col-md-4">
							    <label for="company">Arbetsgivare</label>				    
							     <input type="text" class="form-control" id="company" name="company" placeholder=""/>							    
					    	</div>
				    	</div>
			    	</div>
						    	
			  		<div class="form-group">
				  		<div class="row">
						    <div class="col-md-5">
							    <label for="street">Gatuadress</label>				    
							    <input type="text" class="form-control" id="street" name="stret" placeholder=""/>					    
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
			  	
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Krav</h3>
				  </div>
				  <div class="panel-body">
					  	<div class="checkbox">
						  <label>
						    <input type="checkbox" value="period1">
						    	Över 18 år
						    </input>
						  </label>
						</div>
						<div class="checkbox">
						  <label>
						    <input type="checkbox" value="period2">
						    	Har körkort
						    </input>
						  </label>
						</div>
					<div class="form-group">
					    <label for="work-description">övriga önskemål</label>				    
					    <textarea class="form-control" rows="3" id="other-requirements" name="other-requirements"></textarea>							    
					    <p class="help-block">övriga önskemål</p>
				  	</div>    	
			  		
				  	
				  </div>
			  	</div>
			  			  	
		  		
		  		<button type="submit" class="btn btn-default questions-submit">Submit</button>
				 <span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
			</form>
	</xsl:template>
	
</xsl:stylesheet>					