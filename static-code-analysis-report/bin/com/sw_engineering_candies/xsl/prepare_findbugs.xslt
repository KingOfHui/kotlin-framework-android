<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />

	<xsl:template match="/">
		<sca>
			<xsl:for-each select="//BugCollection/BugInstance/Class">
				<xsl:call-template name="File" />
			</xsl:for-each>
		</sca>
	</xsl:template>

	<xsl:template name="File">
		<xsl:variable name="catkey" select="SourceLine/@classname" />
		<file name="{$catkey}.java">
			<xsl:call-template name="Source_Line" />
		</file>
	</xsl:template>

	<xsl:template match="SourceLine" name="Source_Line">
		<xsl:variable name="type" select="../@type" />
		<message>
			<xsl:attribute name="tool">findbugs</xsl:attribute>
			<xsl:attribute name="line"><xsl:value-of select="./SourceLine/@start" /></xsl:attribute>
			<xsl:attribute name="message"><xsl:value-of select="../LongMessage" /></xsl:attribute>
			<xsl:attribute name="priority"><xsl:value-of
				select="../../BugInstance/@priority" /></xsl:attribute>
			<xsl:attribute name="rule"><xsl:value-of select="../ShortMessage" /> (<xsl:value-of
				select="../../..//BugPattern[$type=@type]/@abbrev" />)</xsl:attribute>
			<xsl:variable name="category1" select="../../..//BugPattern[$type=@type]/@category" />
			<xsl:variable name="smallCase" select="'abcdefghijklmnopqrstuvwxyz '"/>
			<xsl:variable name="upperCase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ_'"/>
			<xsl:attribute name="category"><xsl:value-of select="translate($category1,$upperCase,$smallCase)"/></xsl:attribute>
			<xsl:attribute name="rule_id"><xsl:value-of
				select="../../..//BugPattern[$type=@type]/@type" /></xsl:attribute>
		</message>
	</xsl:template>

	<xsl:template match="ShortMessage"></xsl:template>
	<xsl:template match="Field"></xsl:template>
	<xsl:template match="LongMessage"></xsl:template>

</xsl:stylesheet>
