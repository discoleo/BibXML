<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:param name="skipInstitutions" as="xs:boolean" select="true()"></xsl:param>

<xsl:template match="/">
	<html>
	<body>
    <h2>Bib</h2>
	<p>Created by Leo</p>
	
	<xsl:if test="not($skipInstitutions)">
		<!-- Only Institutions, without Departments -->
		<xsl:apply-templates select="//BibManagement/Affiliations/Institutions" />
	</xsl:if>
	
	<h3>Institutions</h3>
	<xsl:apply-templates select="//BibManagement/Affiliations" />
	
	<h3>Authors</h3>
	<xsl:apply-templates select="//BibManagement/Authors" />
	
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
			<xsl:variable name="varInst" select="//BibManagement/Affiliations/Institutions/Institution[@idInst=$idInst]"/>
			<tr>
				<td><xsl:value-of select="@idAffil" /></td>
				<td><xsl:value-of select="./Department" /></td>
				<td><xsl:value-of select="$varInst/Name" /></td>
				<td><xsl:value-of select="$varInst/Type" /></td>
			</tr>
		</xsl:for-each>
	</table>
</xsl:template>


<xsl:template match="//BibManagement/Authors">
	<table border="1">
		<tr bgcolor="#9acd32">
			<th>ID</th>
			<th>Author</th>
			<th>Given Name</th>
			<th>Dept</th>
			<th>Institution</th>
			<th>Type</th>
		</tr>
		
		<xsl:for-each select="./Author">
			<xsl:variable name="idAut" select="@idAut"/>
			<xsl:variable name="nameAut" select="./Name"/>
			<xsl:variable name="gvNameAut" select="./GivenName"/>
			
			<xsl:for-each select="./Affiliations/Affiliation">
				<xsl:variable name="idAffil" select="@idAffilRef"/>
				
				<xsl:call-template name="templAuthor">
					<xsl:with-param name="idAffil" select="$idAffil"/>
					<xsl:with-param name="idAut" select="$idAut"/>
					<xsl:with-param name="nameAut" select="$nameAut"/>
					<xsl:with-param name="gvNameAut" select="$gvNameAut"/>
				</xsl:call-template>
			</xsl:for-each>
			
		</xsl:for-each>
	</table>
</xsl:template>

<xsl:template name="templAuthor">
	<xsl:param name="idAffil"/>
	<!-- Author -->
	<xsl:param name="idAut"/>
	<xsl:param name="nameAut"/>
	<xsl:param name="gvNameAut"/>
	
	<xsl:variable name="varAffil" select="//BibManagement/Affiliations/Affiliation[@idAffil=$idAffil]"/>
	<xsl:variable name="idInst" select="//BibManagement/Affiliations/Affiliation[@idAffil=$idAffil]/Institution/@idInstRef"/>
	<xsl:variable name="varInst" select="//BibManagement/Affiliations/Institutions/Institution[@idInst=$idInst]"/>
			
		<tr>
			<td><xsl:value-of select="$idAut" /></td>
			<td><xsl:value-of select="$nameAut" /></td>
			<td><xsl:value-of select="$gvNameAut" /></td>
			<td><xsl:value-of select="$varAffil/Department" /></td>
			<td><xsl:value-of select="$varInst/Name" /></td>
			<td><xsl:value-of select="$varInst/Type" /></td>
		</tr>
</xsl:template>

</xsl:stylesheet>