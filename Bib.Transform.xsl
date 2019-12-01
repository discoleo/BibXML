<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:param name="skipInstitutions" as="xs:boolean" select="true()"></xsl:param>

<xsl:template match="/">
	<html>
	<style>
		td.name{
			color: blue;
			font-weight: bold;
			padding: 10px;
		}
		td.univ{
			color: #202890;
			font-weight: bold;
			padding: 10px;
		}
		span.title{
			color:blue;
			font-weight: bold;
		}
	</style>
	<body>
    <h2>Bib</h2>
	<p>Created by Leo</p>
	
	
	<h3>Institutions</h3>
	
	<xsl:if test="not($skipInstitutions)">
		<!-- Only Institutions, without Departments -->
		<xsl:apply-templates select="/BibManagement/Affiliations/Institutions" />
	</xsl:if>
	
	<xsl:apply-templates select="/BibManagement/Affiliations" />
	
	<h3>Authors</h3>
	<!-- Authors by Authors -->
	<xsl:apply-templates select="/BibManagement/Authors" />
	
	<p></p>
	<!-- Authors by Institutions -->
	<xsl:call-template name="AuthorsByAffil" />
	
	</body>
	</html>
</xsl:template>

<!-- Institutions -->

<!-- Only Institutions, without Departments -->
<xsl:template match="//BibManagement/Affiliations/Institutions">
	<table border="1">
		<tr bgcolor="#9acd32">
			<th>ID</th>
			<th>Institution</th>
			<th>Type</th>
		</tr>
		
		<xsl:apply-templates select="./Institution" />
	</table>
</xsl:template>

<xsl:template match="Institution">
	<tr>
		<td><xsl:value-of select="@idInst" /></td>
		<td><xsl:value-of select="./Name" /></td>
		<td><xsl:value-of select="./Type" /></td>
	</tr>
</xsl:template>

<!-- Institutions &amp; Departments -->
<xsl:template match="/BibManagement/Affiliations">
	<table border="1">
		<tr bgcolor="#9acd32">
			<th>ID</th>
			<th>Dept</th>
			<th>Institution</th>
			<th>Type</th>
		</tr>
		
		<xsl:apply-templates select="./Affiliation" />
		
	</table>
</xsl:template>


<xsl:template match="Affiliation">
	<xsl:variable name="idInst" select="./Institution/@idInstRef"/>
	<xsl:variable name="varInst" select="/BibManagement/Affiliations/Institutions/Institution[@idInst=$idInst]"/>
	<tr>
		<td><xsl:value-of select="@idAffil" /></td>
		<td><xsl:value-of select="./Department" /></td>
		<td><xsl:value-of select="$varInst/Name" /></td>
		<td><xsl:value-of select="$varInst/Type" /></td>
	</tr>
</xsl:template>


<!-- Authors -->

<!-- Authors: by Affiliation -->
<xsl:template name="AuthorsByAffil">
	<table border="1">
		<tr bgcolor="#288AD0">
		<!-- <tr bgcolor="#9acd32"> -->
			<th>ID</th>
			<th>Institution</th>
			<th>Dept</th>
			<th>Type</th>
		</tr><tr bgcolor="#288AD0">
			<!-- Authors -->
			<th>ID Author</th>
			<th>Author</th>
			<th>Given Name</th>
		</tr>
		
		<xsl:for-each select="/BibManagement/Affiliations/Institutions/Institution">
			<xsl:variable name="idInst" select="@idInst"/>
			<xsl:variable name="nodeInst" select="."/>
			<xsl:variable name="nodeAffil" select="/BibManagement/Affiliations/Affiliation[./Institution/@idInstRef=$idInst]"/>
			
			<xsl:for-each select="$nodeAffil">
				<tr>
					<td class="univ"><xsl:value-of select="$idInst" /></td>
					<td class="univ"><xsl:value-of select="$nodeInst/Name" /></td>
					<td class="univ"><xsl:value-of select="./Department" /></td>
					<td class="univ"><xsl:value-of select="$nodeInst/Type" /></td>
				</tr>
				<xsl:call-template name="templAuthorByAffil">
					<xsl:with-param name="idAffil" select="./@idAffil"/>
					<xsl:with-param name="idInst" select="$idInst"/>
				</xsl:call-template>
			</xsl:for-each>
		</xsl:for-each>
	</table>
</xsl:template>


<xsl:template name="templAuthorByAffil">
	<xsl:param name="idAffil"/>
	<xsl:param name="idInst"/>
	<xsl:for-each select="/BibManagement/Authors/Author[./Affiliations/Affiliation/@idAffilRef = $idAffil]">
			<xsl:variable name="idAut" select="@idAut"/>
			<xsl:variable name="nameAut" select="Name"/>
			<xsl:variable name="gvNameAut" select="GivenName"/>
				
				<tr>
					<!-- <td></td><td></td><td></td><td></td> -->
					<td><xsl:value-of select="concat($idInst, '.', $idAut)"/></td>
					<td><xsl:value-of select="$nameAut"/></td>
					<td><xsl:value-of select="$gvNameAut"/></td>
				</tr>
			
		</xsl:for-each>
</xsl:template>


<!-- Authors by Author: ALL Affiliations -->
<xsl:template match="/BibManagement/Authors">
	<table border="1">
		<tr bgcolor="#288AD0">
		<!-- bgcolor="#9acd32" -->
			<th>ID</th>
			<th>Author</th>
			<th>Given Name</th>
			<th>Dept / Institution</th>
		</tr>
		
		<xsl:apply-templates select="./Author" />
	</table>
</xsl:template>

<!-- Author's Details -->
<xsl:template match="/BibManagement/Authors/Author">
	<xsl:variable name="idAut" select="@idAut"/>
	<xsl:variable name="nameAut" select="./Name"/>
	<xsl:variable name="gvNameAut" select="./GivenName"/>
			
	<tr>
		<!-- Author -->
		<td><xsl:value-of select="$idAut" /></td>
		<td class="name"><xsl:value-of select="$nameAut" /></td>
		<td><xsl:value-of select="$gvNameAut" /></td>
		<td>
			<xsl:for-each select="./Affiliations/Affiliation">
				<xsl:variable name="idAffil" select="@idAffilRef"/>
				
				<xsl:call-template name="templAffiliationsOfAuthor">
					<xsl:with-param name="id" select="position()"/>
					<xsl:with-param name="idAffil" select="$idAffil"/>
				</xsl:call-template>
			</xsl:for-each>
		</td>
	</tr>
</xsl:template>

<!-- Affiliations for an Author -->
<xsl:template name="templAffiliationsOfAuthor">
	<xsl:param name="id"/>
	<xsl:param name="idAffil"/>
	
	<xsl:variable name="varAffil" select="/BibManagement/Affiliations/Affiliation[@idAffil=$idAffil]"/>
	<xsl:variable name="idInst" select="$varAffil/Institution/@idInstRef"/>
	<xsl:variable name="varInst" select="//BibManagement/Affiliations/Institutions/Institution[@idInst=$idInst]"/>
		
		<p><span class="title"><xsl:value-of select="concat($id, '.) ', $varAffil/Department)" /></span>
		<br/><xsl:value-of select="$varInst/Name" />
		<br/><xsl:value-of select="$varInst/Type" /></p>
</xsl:template>

</xsl:stylesheet>