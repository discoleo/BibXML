<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:param name="skipInstitutions" as="xs:boolean" select="true()"></xsl:param>

<xsl:template match="/">
	<html>
	<body>
    <h2>Bib</h2>
	
	<xsl:if test="not($skipInstitutions)">
		<!-- Only Institutions, without Departments -->
		<xsl:apply-templates select="//BibManagement/Affiliations/Institutions" />
	</xsl:if>
	
	
	<xsl:apply-templates select="//BibManagement/Affiliations" />
	
	</body>
	</html>
</xsl:template>

<xsl:template match="//BibManagement/Affiliations/Institutions">
	<table border="1">
			<tr bgcolor="#9acd32">
				<th>ID</th>
				<th>Institution</th>
				<th>Type</th>
			</tr>
			<xsl:for-each select="./Institution">
			<tr>
				<td><xsl:value-of select="@idInst" /></td>
				<td><xsl:value-of select="./Name" /></td>
				<td><xsl:value-of select="./Type" /></td>
			</tr>
			</xsl:for-each>
		</table>
</xsl:template>


<xsl:template match="//BibManagement/Affiliations">
	<table border="1">
		<tr bgcolor="#9acd32">
			<th>ID</th>
			<th>Dept</th>
			<th>Institution</th>
			<th>Type</th>
		</tr>
		
		<xsl:for-each select="./Affiliation">
			<xsl:variable name="idInst" select="./Institution/@idInstRef"/>
			<tr>
				<td><xsl:value-of select="@idAffil" /></td>
				<td><xsl:value-of select="./Department" /></td>
				<td><xsl:value-of select="//BibManagement/Affiliations/Institutions/Institution[@idInst=$idInst]/Name" /></td>
				<td><xsl:value-of select="//BibManagement/Affiliations/Institutions/Institution[@idInst=$idInst]/Type" /></td>
			</tr>
		</xsl:for-each>
	</table>
</xsl:template>

</xsl:stylesheet>