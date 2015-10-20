<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>
			<xsl:variable name="globalscripts">
				/jquery/jquery.js
				/jquery/jquery-ui.js
				/ckeditor/ckeditor.js
				/ckeditor/adapters/jquery.js
				/ckeditor/init.js
			</xsl:variable>

			<xsl:variable name="scripts">
				/js/periodsadmin.js
				/js/jquery.ui.datepicker.js
				/js/jquery.ui.datepicker-sv.js
			</xsl:variable>
			
			<xsl:variable name="links">
				/css/flowengine.css
				/uitheme/jquery-ui-1.8.7.custom.css
			</xsl:variable>

	<xsl:template match="Document">	
			<script>
				var url = '<xsl:value-of select="requestinfo/uri"/>';
			</script>
			
			<script>
			$(function() {						
				$( "input[name*='new-period-fromdate']" ).datepicker({
					showOn: "button",
					buttonImage: '<xsl:value-of select="/Document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/Document/module/sectionID"/>/<xsl:value-of select="/Document/module/moduleID"/>/pics/calendar_grid.png',
					buttonImageOnly: true,
					buttonText: 'Startdatum'
				});
				
				$( "input[name*='new-period-todate']" ).datepicker({
					showOn: "button",
					buttonImage: '<xsl:value-of select="/Document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/Document/module/sectionID"/>/<xsl:value-of select="/Document/module/moduleID"/>/pics/calendar_grid.png',
					buttonImageOnly: true,
					buttonText: 'Slutdatum'
				});								    							  									
			});	
			</script>		
	
			<h1>Administera perioder</h1>
			
<!-- 			<div id="new-period-info" class="info-box-message success"><p>Perioden har sparats.</p></div> -->
<!-- 			<div id="existing-periods-info" class="info-box-message success"><p>Ändringen har sparats.</p></div> -->
			
			<h2 style="margin-top: 24px">Befintliga perioder</h2>
			<form id="periods-list-form">
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
				<input style="margin-top: 4px;" id="periods-save-changes" type="submit" value="Spara ändringar"></input>
			</form>
			
			<h2 style="margin-top: 24px">Lägg till ny period</h2>
			<form id="new-period-form">
				<label for="new-period-name">Namn</label><input type="text" name="new-period-name" required="required"></input>
				<label for="new-period-fromdate">Startdatum</label><input type="text" name="new-period-fromdate" required="required"></input>
				<label for="new-period-todate">Slutdatum</label><input type="text" name="new-period-todate" required="required"></input>
				<br/>
				
				<input style="margin-top: 4px;" id="period-save-new" type="submit" value="Spara"></input>
			</form>
						
	</xsl:template>
	
	<xsl:template match="Period">
		<tr id="periodId_{ID}">
			<td>	
					<input id="period_name_{ID}" type="text" value="{Name}" name="period_name_{ID}"></input>
			</td>
			<td>	
					<input id="period_fromdate_{ID}" type="text" value="{FromDate}" name="period_fromdate_{ID}"></input>
			</td>
			<td>	
					<input id="period_todate_{ID}" type="text" value="{ToDate}" name="period_todate_{ID}"></input>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>