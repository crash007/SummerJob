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
					    <label for="organisation">Ange organisation</label>
					    <input type="text" class="form-control" id="organisation" name="organisation" placeholder=""/>				    					    
					    <p class="help-block">Tex, Sundsvalls elnät, Sundsvalls kommun</p>
					  </div>
					  <div class="form-group">
					    <label for="location">Ange förvaltning</label>
					    <input type="text" class="form-control" id="location" name="location" placeholder=""/>
					    <p class="help-block">Tex Kultur och fritid</p>
					    
					  </div>
					  <div class="form-group">
					    <label for="place">Ange platsen</label>
					    <input type="text" class="form-control" id="place" name="place" placeholder=""/>
					    <p class="help-block">Tex Himlabadet</p>					    
					  </div>
					 
					 <div class="form-group">
					    <label for="area">Ange område</label>
					    <input type="text" class="form-control" id="area" name="area" placeholder=""/>
<!-- 					    <p class="help-block">Tex </p> -->
					  </div>
					  
					  <div class="radio">
						  <label>
						    <input type="radio" name="area" id="area1" value="Barnomsorg" checked="true">						    
						    Barnomsorg
						    </input>
						  </label>
						</div>
						<div class="radio">
						  <label>
						    <input type="radio" name="area" id="area2" value="Äldreomsorg">
						    	Äldreomsorg
						    </input>
						  </label>
						</div>
						<div class="radio disabled">
						  <label>
						    <input type="radio" name="area" id="area3" value="Omsorg">
						    	Omsorg
						    </input>
						  </label>
						</div>
					  
					</div>	
				</div>
				
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Adress</h3>
				  </div>
				  <div class="panel-body">
			  		<div class="form-group">
					    <label for="street">Gatuadress</label>				    
					    <input type="text" class="form-control" id="street" name="stret" placeholder=""/>					    
					    <label for="postalcode">Postnumer</label>				    
					    <input type="text" class="form-control" id="postalcode" name="postalcode" placeholder=""/>
					    <label for="postalarea">Postort</label>				    
					    <input type="text" class="form-control" id="postalarea" name="postalarea" placeholder=""/>
					    
				  	</div>
				  	<div class="form-group">
					    <label for="section">Ange avdelning </label>				    
					     <input type="text" class="form-control" id="section" name="section" placeholder=""/>
					    <p class="help-block">Avdelning är inte obligatorisk</p>
				  	</div>
				  	
				  </div>
			  	</div>
			  	
			  	<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Arbete</h3>
				  </div>
				  <div class="panel-body">
				  	<div class="form-group">
					    <label for="q7">Arbetsbeskrivning</label>				    
					    <textarea class="form-control" rows="3" id="work-description" name="work-description"></textarea>							    
					    <p class="help-block">Beskriv vad arbetet går ut på.</p>
				  	</div>
				  	<div class="form-group">
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
			  		<div class="form-group manager-wrapper">
					    <label for="manager">Ange handledare </label>				    
					     <input type="text" class="form-control" id="manager" name="manager" placeholder=""/>
					    <p class="help-block">Du kan ange flera handledare</p>
				  	</div>
				  	
				  </div>
		  		</div>		  					  	
		  		
		  		<button type="submit" class="btn btn-default questions-submit">Submit</button>
				 <span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
			</form>
	</xsl:template>
	
</xsl:stylesheet>					