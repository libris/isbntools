<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:strip-space elements="*"/>


<xsl:template match="ISBNRangeMessage">
<xsl:apply-templates select="RegistrationGroups"/>
</xsl:template>

<xsl:template match="RegistrationGroups">
<xsl:apply-templates select="Group"/>
</xsl:template>

<xsl:template match="Group">
<xsl:variable name="prefix" select="translate(Prefix, '-', '')"/>
<xsl:text># </xsl:text><xsl:value-of select="Agency"/>
<xsl:text>&#xa;</xsl:text>
<xsl:for-each select="Rules/Rule">
<xsl:if test="Length &gt; 0">
<xsl:variable name="start" select="substring(substring-before(Range, '-'), 1, Length)"/>
<xsl:variable name="end" select="substring(substring-after(Range, '-'), 1, Length)"/>
<xsl:value-of select="$prefix"/><xsl:text> </xsl:text><xsl:value-of select="$start"/><xsl:text>-</xsl:text><xsl:value-of select="$end"/>
<xsl:text>&#xa;</xsl:text>
</xsl:if>
</xsl:for-each>

</xsl:template>

<!--<xsl:template match="node()|@*|text()|processing-instruction()">
</xsl:template> -->

</xsl:stylesheet>
