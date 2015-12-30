<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>
			

	<xsl:template match="Document">	
			<script>
				var url = '<xsl:value-of select="requestinfo/uri"/>';
			</script>
			
			<h1>Admin</h1>
			
			<h2>Administrera lön</h2>
			<div class="well">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Aktuell timlön</h3>
					</div>
					<div class="panel-body">
						<form id="salary-list-form">
							<fieldset>
								<table id="salary-table">
									<thead>
										<th>Namn</th>
										<th>Timlön</th>
									</thead>
									<tbody>
										<xsl:apply-templates select="Salaries/Salary"/>
									</tbody>
								</table>
							</fieldset>
							<input class="btn btn-primary mgn-top4px" id="salary-save" type="submit" value="Spara"></input>
						</form>
					</div>
					<div id="save-salary-failed" class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
						<span class="sr-only">Error:</span>
						<span class="message"></span>
					</div>
					<div id="save-salary-succeeded" class="alert alert-success" role="alert">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						<span class="sr-only">Success:</span>
						<span class="message"></span>
					</div>
				</div>
			</div>
			
			<h2>Administrera konteringar</h2>
			<div class="well">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Aktuella konteringar</h3>
					</div>
					<div class="panel-body">
						<form id="accounting-entries-list-form">
							<fieldset>
								<table id="accounting-entries-table">
									<thead>
										<th>Typ</th>
										<th>Ansvar</th>
										<th>Verksamhet</th>
										<th>Aktivitet</th>
									</thead>
									<tbody>
										<xsl:for-each select="AccountingEntries/AccountingEntry">
											<xsl:choose>
												<xsl:when test="isPrio = 'true'">
													<tr>
														<td>Prio</td>
														<td><input class="numberValidation form-control" name="ansvarPrio" type="text" value="{ansvar}" /></td>
														<td><input class="numberValidation form-control" name="verksamhetPrio" type="text" value="{verksamhet}" /></td>
														<td><input class="numberValidation form-control" name="aktivitetPrio" type="text" value="{aktivitet}" /></td>
													</tr>
												</xsl:when>
												<xsl:otherwise>
													<tr>
														<td>Vanlig</td>
														<td><input class="numberValidation form-control" name="ansvarRegular" type="text" value="{ansvar}" /></td>
														<td><input class="numberValidation form-control" name="verksamhetRegular" type="text" value="{verksamhet}" /></td>
														<td><input class="numberValidation form-control" name="aktivitetRegular" type="text" value="{aktivitet}" /></td>
													</tr>
												</xsl:otherwise>
											</xsl:choose>
										
										</xsl:for-each>
									</tbody>
								</table>
							</fieldset>
							<input class="btn btn-primary mgn-top4px" id="accounting-save" type="submit" value="Spara"></input>
						</form>
					</div>
					<div id="save-accounting-failed" class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
						<span class="sr-only">Error:</span>
						<span class="message"></span>
					</div>
					<div id="save-accounting-succeeded" class="alert alert-success" role="alert">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						<span class="sr-only">Success:</span>
						<span class="message"></span>
					</div>
				</div>
			</div>
			
			<h2>Administrera "Plats för samtal"</h2>
			<div class="well">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Aktuell "Plats för samtal"</h3>
					</div>
					<div class="panel-body">
						<form id="place-for-information-form">
							<input type="text" class="form-control" maxlength="255" name="placeforinformation" value="{PlaceForInformation/name}"/>
							<input class="btn btn-primary mgn-top8px" id="place-save-changes" type="submit" value="Spara"></input>
						</form>
						<div id="save-place-failed" class="alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span>
							<span class="message"></span>
						</div>
						<div id="save-place-succeeded" class="alert alert-success" role="alert">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
							<span class="sr-only">Success:</span>
							<span class="message"></span>
						</div>
					</div>
				</div>
			</div>
			
			<h2>Administrera kontaktperson</h2>
			<div class="well">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Aktuell kontaktperson</h3>
					</div>
					<div class="panel-body">
						<form id="contact-person-form">
							<fieldset>
								<table id="contact-person-table">
									<thead>
										<th>Typ</th>
										<th>Namn</th>
										<th>Telefonnummer</th>
									</thead>
									<tbody>
										<tr>
											<td>Kommun</td>
											<td><input class="form-control" name="municipality-contact-name" type="text" value="{MunicipalityContactPerson/name}" /></td>
											<td><input class="numberValidation form-control" name="municipality-contact-phone" type="text" value="{MunicipalityContactPerson/phoneNumber}" /></td>
										</tr>
										<tr>
											<td>Näringsliv</td>
											<td><input class="form-control" name="business-contact-name" type="text" value="{BusinessContactPerson/name}" /></td>
											<td><input class="numberValidation form-control" name="business-contact-phone" type="text" value="{BusinessContactPerson/phoneNumber}" /></td>
										</tr>
									</tbody>
								</table>
							</fieldset>
							<input class="btn btn-primary mgn-top4px" id="contact-person-save" type="submit" value="Spara"></input>
						</form>
					</div>
					<div id="save-contact-failed" class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
						<span class="sr-only">Error:</span>
						<span class="message"></span>
					</div>
					<div id="save-contact-succeeded" class="alert alert-success" role="alert">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						<span class="sr-only">Success:</span>
						<span class="message"></span>
					</div>
				</div>
			</div>
	
			<h2>Administera perioder</h2>
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
							<input class="btn btn-primary mgn-top4px" id="periods-save-changes" type="submit" value="Spara ändringar"></input>
							</fieldset>				
						</form>
						<div id="save-period-failed" class="alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span>
							<span class="message"></span>
						</div>
						<div id="save-period-succeeded" class="alert alert-success" role="alert">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
							<span class="sr-only">Success:</span>
							<span class="message"></span>
						</div>
					</div>
				</div>
			
				<div class="panel panel-default">
					<div class="panel-heading">
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
								<input class="btn btn-primary mgn-top4px" id="period-save-new" type="submit" value="Spara"></input>
								<span class="glyphicon glyphicon-ok collapse" aria-hidden="true"></span><span class="glyphicon glyphicon-remove collapse" aria-hidden="true"></span>
							</div> 	
						</form>
					</div>
				</div>
			</div>
						
	</xsl:template>
	
	<xsl:template match="Salary">
		<tr>
			<td><xsl:value-of select="name"></xsl:value-of></td>
			<td><input class="form-control" name="salary_{id}" type="number" value="{amountInSEK}" /></td>
		</tr>
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