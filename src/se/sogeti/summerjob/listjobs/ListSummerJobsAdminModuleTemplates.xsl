<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="Document">
		 
		<script>
			var url = '<xsl:value-of select="requestinfo/uri"/>';
		</script>
		<xsl:variable name="isAdmin" select="IsAdmin"/>

		<h3><xsl:value-of select="Heading/title"></xsl:value-of></h3>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Godkända annonser</h3>
			</div>
			<div class="panel-body">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Rubrik</th>
							<th>Period / datum</th>							
							<th>Antal platser</th>
							<th>Skapad</th>
						</tr>
					</thead>
	
					<tbody>
						<xsl:apply-templates select="ControlledJobs/Job" />
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Ej kontrollerade annonser</h3>
			</div>
			<div class="panel-body">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Rubrik</th>
							<th>Period / datum</th>			
							<th>Antal platser</th>
							<th>Skapad</th>
						</tr>
					</thead>
	
					<tbody>
						<xsl:apply-templates select="UncontrolledJobs/Job" />
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Nekade annonser</h3>
			</div>
			<div class="panel-body">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Rubrik</th>
							<th>Period / datum</th>			
							<th>Antal platser</th>
							<th>Skapad</th>
						</tr>
					</thead>
	
					<tbody>
						<xsl:apply-templates select="ControlledDisapprovedJobs/Job" />
					</tbody>
				</table>
			</div>
		</div>
	</xsl:template>
	
	<xsl:template match="Job">
		<tr>
			<td>
	   			<xsl:value-of select="workTitle"></xsl:value-of>
	   		</td>
	   		<td>
				<xsl:value-of select="dates"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="numberOfWorkersNeeded"></xsl:value-of>
	   		</td>
	   		<td>
	   			<xsl:value-of select="created"></xsl:value-of>
	   		</td>
	   		<td>
	   			<a href='{url}'><strong>Hantera</strong></a>
	   		</td>
   		</tr>
	</xsl:template>
	
</xsl:stylesheet>					