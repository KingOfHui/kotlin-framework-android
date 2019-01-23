<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:functx="http://www.functx.com" version="2.0">

	<xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />

	<xsl:template match="/">
		<sca>
			<xsl:for-each select="//pmd">
				<xsl:apply-templates />
			</xsl:for-each>
		</sca>
	</xsl:template>

 	<xsl:template match="file">
		 <xsl:variable name="package_tmp" select="./violation/@package" />
	     <xsl:variable name="package" select="distinct-values($package_tmp)" />	     
	     <xsl:variable name="class_tmp" select="./violation/@class" />
	     <xsl:variable name="class" select="distinct-values($class_tmp)" />
		<file name="{$package}.{$class}.java">
			<xsl:apply-templates select="node()" />
		</file>
	</xsl:template>

	<xsl:template match="violation">
		<message>
			<xsl:attribute name="tool">pmd</xsl:attribute>
			<xsl:attribute name="line"><xsl:value-of select="@beginline" /></xsl:attribute>
			<xsl:attribute name="message"><xsl:value-of select="@rule" /></xsl:attribute>
			<xsl:attribute name="priority"><xsl:value-of select="@priority" /></xsl:attribute>
			<xsl:attribute name="rule"><xsl:value-of select="@rule" /></xsl:attribute>

			<xsl:variable name="category1" select="@ruleset" />
			<xsl:variable name="smallCase" select="'abcdefghijklmnopqrstuvwxyz '" />
			<xsl:variable name="upperCase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ_'" />
			<xsl:attribute name="category"><xsl:value-of
				select="translate($category1,$upperCase,$smallCase)" /></xsl:attribute>
		</message>
	</xsl:template>

</xsl:stylesheet>
