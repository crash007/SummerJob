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
							    <label for="administration">Ange förvaltning</label>
							    <input type="text" class="form-control" id="administration" name="administration" placeholder=""/>
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
						</div>
					<!--	
						<div class="form-group">
					  		<div class="row">
					  			<div class="col-md-3">
								    <label for="preferedArea1">Välj verksamhetsområde 1</label>				    
								    <select class="form-control" name="preferedArea1" id="preferedArea1">
										<option value="-1"/>
										<xsl:for-each select="Areas/Area">
								  			<xsl:if test="canBeChosenInApplication = 'true'">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  		</xsl:if>
							  			</xsl:for-each>
									</select>
								</div>
								
								<div class="col-md-3">
								    <label for="preferedArea2">Välj verksamhetsområde 2</label>				    
								    <select class="form-control" name="preferedArea2" id="preferedArea2">
								    	<option value="-1"/>
									  	<xsl:for-each select="Areas/Area">
								  			<xsl:if test="canBeChosenInApplication = 'true'">
										  		<option value="{id}"><xsl:value-of select="name"/> </option>									  			
									  		</xsl:if>
								  		</xsl:for-each>									  
									</select>
								</div>
								<div class="col-md-3">
								    <label for="preferedArea3">Välj verksamhetsområde 3</label>				    
								    <select class="form-control" name="preferedArea3" id="preferedArea3">
								    	<option value="-1"/>
									 	<xsl:for-each select="Areas/Area">
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
					  	</div> -->
					  		
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
				  	<div class="form-group">
					  	<div class="row">
						  	<div class="col-md-5">
							    <label for="department">Ange avdelning </label>				    
							     <input type="text" class="form-control" id="department" name="department" placeholder=""/>
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
					  	<div class="row">
						  	<div class="col-md-5">
							    <label for="work-title">Rubrik</label>				    
							     <input type="text" class="form-control" id="work-title" name="work-title" placeholder=""/>							    
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
								  <option>8</option>
								  <option>9</option>
								  <option>10</option>
								  <option>11</option>
								  <option>12</option>
								  <option>13</option>
								  <option>14</option>
								  <option>15</option>
								  <option>16</option>
								  <option>17</option>
								  <option>18</option>
								  <option>19</option>
								  <option>20</option>
								</select>
							</div>
						</div>
				  	</div>
				  	
				  	<div class="form-group">
					  	<label for="period">Välj perioder</label>
						  <table class="table">
						  	<thead>
						  		<tr>
						  			<th>Välj</th>
						  			<th>Namn</th>
						  			<th>Startdatum</th>
						  			<th>Slutdatum</th>
						  		</tr>
						  	</thead>
						  	<tbody>
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
						  	</tbody>
					  	</table>
					</div>
					
					<div class="form-group">
						<div class="checkbox">
						    <label>
						      <input type="checkbox" name="isOverEighteen">Måste vara över 18 år </input>
						    </label>
					  	</div>
						<div class="checkbox">
						    <label>
						      <input type="checkbox" name="hasDriversLicense">Måste ha körkort </input>
						    </label>
					  	</div>
						  	
							
					</div>
				  	
			  		<div class="form-group">
			  			<label>Ange chef på arbetsplatsen</label>
			  			<div class="row">
				  				<div class="col-md-3">
								    <label for="manager">Förnamn</label>				    
								     <input type="text" class="form-control" id="manager-firstname" name="manager-firstname" placeholder=""/>							    
						    	</div>
						    	<div class="col-md-3">
								    <label for="manager">Efternamn</label>				    
								     <input type="text" class="form-control" id="manager-lastname" name="manager-lastname" placeholder=""/>							    
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
				    	
				    	<a href="#" class="add-mentor-btn">Lägg till handledare</a>
				  	</div>
				  	
				  </div>
		  		</div>		  					  	
		  		
		  		<button type="submit" class="btn btn-default questions-submit">Submit</button>
				 <span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
			</form>
			
			<div id="mentor-template" >
	    		<div class="row collapse">
	  				<div class="col-md-3">
					    <label for="mentor">Förnamn</label>				    
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