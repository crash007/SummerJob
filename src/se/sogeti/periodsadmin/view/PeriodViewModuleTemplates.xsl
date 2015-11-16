<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>
			
			<xsl:variable name="links">
				/css/flowengine.css
				/uitheme/jquery-ui-1.8.7.custom.css
			</xsl:variable>

	<xsl:template match="Document">	
			<script>
				var url = '<xsl:value-of select="requestinfo/uri"/>';
			</script>
			
			<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
			
			<script>
			$(function() {						
				$( "input[name*='new-period-startdate']" ).datepicker({
					minDate: new Date(),
					onSelect : function(selected) {
						$(this).focus();
						$("input[name*='new-period-enddate']").datepicker("option", "minDate", selected);
					},
				});
				
				$( "input[name*='new-period-enddate']" ).datepicker({
					minDate: new Date(),
					onSelect : function(selected) {
						$(this).focus();
						$("input[name*='new-period-startdate']").datepicker("option", "maxDate", selected);
					},
				});		
				
				$( "input.period_startdate" ).datepicker({
					minDate: new Date(),
					onSelect : function(selected) {
						$(this).focus();
						var endDate = $(this).parent().parent().find('input.period_enddate');
						$(endDate).datepicker("option", "minDate", selected);
					},
				});
				
				$( "input.period_enddate" ).datepicker({
					minDate: new Date(),
					onSelect : function(selected) {
						$(this).focus();
						var startDate = $(this).parent().parent().find('input.period_startdate');
						$(startDate).datepicker("option", "maxDate", selected);
					},
				});						    							  									
			});	
			</script>		
	
			<h1>Administera perioder</h1>
			
			<div class="well">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Befintliga perioder</h3>
					</div>
					<div class="panel-body">
						<form id="periods-list-form">
							<fieldset>
								<table id="periods-table">
									<thead>
										<th>Namn</th>
										<th>Startdatum</th>
										<th>Slutdatum</th>
									</thead>
									<tbody>
										<xsl:apply-templates select="Periods/Period"/>
									</tbody>
								</table>
							<input class="btn btn-primary" style="margin-top: 4px;" id="periods-save-changes" type="submit" value="Spara ändringar"></input>
							</fieldset>				
						</form>
					</div>
				</div>
			
				<div class="panel panel-default">
					<div class="panel-heading">
<!-- 						<h3 style="margin-top: 24px">Lägg till ny period</h3> -->
						<h3 class="panel-title">Lägg till ny period</h3>
					</div>
					<div class="panel-body">
						<form id="new-period-form">
							<div class="form-group col-md-2">
								<label>Namn</label>
								<input type="text" name="new-period-name" class="form-control" required="required"></input>
							</div>
							<div class="form-group col-md-2">
								<label for="new-period-startdate">Startdatum</label>
								<input type="text" name="new-period-startdate" class="form-control" required="required"></input>
							</div>
						
							<div class="form-group col-md-2">
								<label for="new-period-enddate">Slutdatum</label>
								<input type="text" name="new-period-enddate" class="form-control" required="required"></input>
							</div>
							<div class="form-group col-md-1">
								<label></label>
								<input class="btn btn-primary" style="margin-top: 4px;" id="period-save-new" type="submit" value="Spara"></input>
								<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
							</div> 	
						</form>
					</div>
				</div>
			</div>
						
	</xsl:template>
	
	<xsl:template match="Period">
		<tr id="periodId_{ID}">
			<td>	
					<input id="period_name_{ID}" class="form-control" type="text" value="{Name}" name="period_name_{ID}"></input>
			</td>
			<td>	
					<input id="period_startdate_{ID}" class="form-control period_startdate" type="text" value="{StartDate}" name="period_startdate_{ID}"></input>
			</td>
			<td>	
					<input id="period_enddate_{ID}" class="form-control period_enddate" type="text" value="{EndDate}" name="period_enddate_{ID}"></input>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>