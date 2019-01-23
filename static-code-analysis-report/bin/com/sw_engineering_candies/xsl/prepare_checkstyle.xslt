<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="xml" indent="yes" encoding="ISO-8859-1" />

	<xsl:template match="/">
		<sca>
			<xsl:for-each select="//checkstyle">
				<xsl:apply-templates />
			</xsl:for-each>
		</sca>
	</xsl:template>

	<xsl:template match="file">
		<xsl:variable name="new_name" select="translate(@name, '/', '.')" />
		<xsl:variable name="new_name" select="translate($new_name, '\', '.')" />
		<xsl:variable name="new_name"
			select="concat(substring-after($new_name,'.src.'),  substring-after($new_name,'.source.'))" />

		<xsl:variable name="msg" select="./error" />
		<xsl:if test="($msg='')">
			<file name="{$new_name}">
				<xsl:apply-templates select="node()" />
			</file>
		</xsl:if>
	</xsl:template>

	<xsl:template match="error">
		<xsl:variable name="priority">
			<xsl:if test="@severity='error'">1</xsl:if>
			<xsl:if test="@severity='warning'">2</xsl:if>
			<xsl:if test="@severity='info'">3</xsl:if>
		</xsl:variable>
		<message>
			<xsl:attribute name="tool">checkstyle</xsl:attribute>
			<xsl:attribute name="line"><xsl:value-of select="@*" /></xsl:attribute>
			<xsl:attribute name="message"><xsl:value-of select="@severity" /></xsl:attribute>
			<xsl:attribute name="priority"><xsl:value-of select="+$priority" /></xsl:attribute>
			<xsl:attribute name="rule"><xsl:value-of select="@message" /></xsl:attribute>
			<xsl:attribute name="category">style</xsl:attribute>
		</message>
	</xsl:template>

</xsl:stylesheet>
