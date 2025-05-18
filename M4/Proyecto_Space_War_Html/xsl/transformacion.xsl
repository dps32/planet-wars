<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" indent="yes"/>

  <xsl:param name="battleID" select="'1'"/>

  <xsl:template match="/">
    <xsl:variable name="battle" select="/battles/battle[@id = $battleID]"/>

    <div class="battle-report">
      <h1 class="section-title">Battle Statistics</h1>

      <h2 class="section-title">Army Planet Units Drops | Enemy Units Drops</h2>
      <table class="battle-table">
        <tr>
          <th>Unit</th>
          <th>Units</th>
          <th>Drops</th>
          <th>Enemy Units</th>
          <th>Enemy Drops</th>
        </tr>
        <xsl:for-each select="$battle/stats/planetArmy/unit">
          <xsl:variable name="unitName" select="@name"/>
          <tr>
            <td><xsl:value-of select="@name"/></td>
            <td><xsl:value-of select="@quantity"/></td>
            <td><xsl:value-of select="@drops"/></td>
            <td><xsl:value-of select="$battle/stats/enemyArmy/unit[@name = $unitName]/@quantity"/></td>
            <td><xsl:value-of select="$battle/stats/enemyArmy/unit[@name = $unitName]/@drops"/></td>
          </tr>
        </xsl:for-each>
      </table>

      <h2 class="section-title">Cost Army Planet | Cost Army Enemy</h2>
      <table class="battle-table">
        <tr><th>Resource</th><th>Planet</th><th>Enemy</th></tr>
        <tr><td>Metal</td><td><xsl:value-of select="$battle/cost/planet/@metal"/></td><td><xsl:value-of select="$battle/cost/enemy/@metal"/></td></tr>
        <tr><td>Deuterium</td><td><xsl:value-of select="$battle/cost/planet/@deuterium"/></td><td><xsl:value-of select="$battle/cost/enemy/@deuterium"/></td></tr>
      </table>

      <h2 class="section-title">Losses Army Planet | Losses Army Enemy</h2>
      <table class="battle-table">
        <tr><th>Resource</th><th>Planet</th><th>Enemy</th></tr>
        <tr><td>Metal</td><td><xsl:value-of select="$battle/losses/planet/@metal"/></td><td><xsl:value-of select="$battle/losses/enemy/@metal"/></td></tr>
        <tr><td>Deuterium</td><td><xsl:value-of select="$battle/losses/planet/@deuterium"/></td><td><xsl:value-of select="$battle/losses/enemy/@deuterium"/></td></tr>
        <tr><td>Weighted</td><td><xsl:value-of select="$battle/losses/planet/@weighted"/></td><td><xsl:value-of select="$battle/losses/enemy/@weighted"/></td></tr>
      </table>

      <h2 class="section-title">Waste Generated</h2>
      <p>Metal: <xsl:value-of select="$battle/waste/metal"/></p>
      <p>Deuterium: <xsl:value-of select="$battle/waste/deuterium"/></p>

      <p class="footer">Battle Won by: <xsl:value-of select="$battle/winner"/></p>
    </div>
  </xsl:template>

</xsl:stylesheet>
