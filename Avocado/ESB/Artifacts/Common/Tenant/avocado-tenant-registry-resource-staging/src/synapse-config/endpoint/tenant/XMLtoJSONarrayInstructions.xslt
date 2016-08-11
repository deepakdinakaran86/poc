<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <!-- Identity transform -->
   <xsl:template match="@* | node()">
      <xsl:copy>
         <xsl:apply-templates select="@* | node()"/>
      </xsl:copy>
   </xsl:template>
   <xsl:template match="jsonObject">
<jsonObject>
<xsl:apply-templates select="@* | resourceName"/>
<xsl:processing-instruction name="xml-multiple">
               <xsl:value-of select="local-name(//permissions)"></xsl:value-of>
            </xsl:processing-instruction>
<xsl:apply-templates select="@* | permissions"/>
</jsonObject>
   </xsl:template>
</xsl:stylesheet>