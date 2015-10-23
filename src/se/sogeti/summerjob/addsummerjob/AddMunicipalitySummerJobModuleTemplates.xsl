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
		<form method="POST" id="municipality-job-form">
			 
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Plats</h3>
				  </div>
				  <div class="panel-body">
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="organisation">Ange organisation</label>
							    <input type="text" class="form-control" id="organisation" name="organisation" placeholder=""/>				    					    
							    <p class="help-block">Tex, Sundsvalls elnät, Sundsvalls kommun</p>
					    	</div>
					    </div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="location">Ange förvaltning</label>
							    <input type="text" class="form-control" id="location" name="location" placeholder=""/>
							    <p class="help-block">Tex Kultur och fritid</p>
				    		</div>
		    			</div>
					  </div>
					  <div class="form-group">
					  	<div class="row">
			  				<div class="col-md-4">
							    <label for="place">Ange platsen</label>
							    <input type="text" class="form-control" id="place" name="place" placeholder=""/>
							    <p class="help-block">Tex Himlabadet</p>
						   	</div>
					   	</div>					    
					  </div>
					 
					
					  <div class="form-group">
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
						  		<tr>
						  			<td><input type="radio" name="area" id="area1" value="Barnomsorg" checked="true"/></td>
						  			<td>Barnomsorg</td>
						  			<td>Du kan exempelvis arbeta inom förskola, fritids</td>
						  		</tr>
						  		<tr>
						  			<td><input type="radio" name="area" id="area1" value="Barnomsorg"/></td>
						  			<td>Äldreomsorg</td>
						  			<td>Du kan exempelvis arbeta inom hemtjänst, servicehus och äldreboenden.</td>
						  		</tr>
						  	</tbody>
						  </table>
					  
						
					  
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
				  	<div class="form-group">
					  	<div class="row">
						  	<div class="col-md-5">
							    <label for="section">Ange avdelning </label>				    
							     <input type="text" class="form-control" id="section" name="section" placeholder=""/>
							    <p class="help-block">Avdelning är inte obligatorisk</p>
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
				  		<label for="period">Välj perioder</label>
				  		<div class="checkbox">
						  <label>
						    <input type="checkbox" value="period1">
						    	2016-06-01 -- 2016-07-15
						    </input>
						  </label>
						</div>
						<div class="checkbox">
						  <label>
						    <input type="checkbox" value="period2">
						    	2016-07-17 -- 2016-08-30
						    </input>
						  </label>
						</div>				  	
				  	</div>
			  		
			  		<div class="form-group">
			  			<label>Ange chef på arbetsplatsen</label>
			  			<div class="row">
				  				<div class="col-md-3">
								    <label for="manager">Namn</label>				    
								     <input type="text" class="form-control" id="manager-name" name="manager-name" placeholder=""/>							    
						    	</div>
						    	<div class="col-md-3">
								    <label for="manager">Telefonnummer</label>				    
								     <input type="text" class="form-control" id="manager-phone" name="manager-phone" placeholder=""/>
								    
						    	</div>
						    
						    
				  				<div class="col-md-3">
								    <label for="manager">E-post</label>				    
								     <input type="text" class="form-control" id="manager-email" name="manager-email" placeholder=""/>
								    <p class="help-block">Valfri</p>
						    	</div>
				    	</div>
			    	</div>
			  		
			  		<div class="form-group">
			  			<label>Ange Handledare</label>
			  			<div id="mentors-wrapper">
				  			
				    	</div>
				    	
				    	<div id="mentor-template">
				    		<div class="row">
				  				<div class="col-md-3">
								    <label for="mentor">Namn</label>				    
								     <input type="text" class="form-control" id="mentor-name" name="mentor-name" placeholder=""/>							    
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
				    	
				    	<a href="#" class="add-mentor-btn">Lägg till handledare</a>
				  	</div>
				  	
				  </div>
		  		</div>		  					  	
		  		
		  		<button type="submit" class="btn btn-default questions-submit">Submit</button>
				 <span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
			</form>
	</xsl:template>
	
</xsl:stylesheet>					