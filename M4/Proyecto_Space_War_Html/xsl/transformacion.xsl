<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" indent="yes"/>

  <xsl:param name="battleID" select="'1'"/>

  <xsl:template match="/">
    <xsl:variable name="battle" select="/battles/battle[@id = $battleID]"/>
    <html>
      <head>
        <title>Reporte de Batalla</title>
        <meta charset="UTF-8"/>
        <style>
          body { font-family: Arial, sans-serif; background: #111; color: #eee; padding: 20px; }
          h1 { color: #00f5d4; }
          table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
          th, td { border: 1px solid #444; padding: 8px; text-align: center; }
          th { background-color: #222; }
          .section-title { margin-top: 30px; font-weight: bold; font-size: 1.2em; }
          .footer { margin-top: 40px; font-style: italic; }
        </style>
      </head>
      <body>
        <h1>BATTLE STATISTICS</h1>

        <div class="section-title">Army Planet Units Drops | Enemy Units Drops</div>
        <table>
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
              <td>
                <xsl:value-of select="$battle/stats/enemyArmy/unit[@name = $unitName]/@quantity"/>
              </td>
              <td>
                <xsl:value-of select="$battle/stats/enemyArmy/unit[@name = $unitName]/@drops"/>
              </td>
            </tr>
          </xsl:for-each>
        </table>

        <div class="section-title">Cost Army Planet | Cost Army Enemy</div>
        <table>
          <tr><th>Resource</th><th>Planet</th><th>Enemy</th></tr>
          <tr><td>Metal</td><td><xsl:value-of select="$battle/cost/planet/@metal"/></td><td><xsl:value-of select="$battle/cost/enemy/@metal"/></td></tr>
          <tr><td>Deuterium</td><td><xsl:value-of select="$battle/cost/planet/@deuterium"/></td><td><xsl:value-of select="$battle/cost/enemy/@deuterium"/></td></tr>
        </table>

        <div class="section-title">Losses Army Planet | Losses Army Enemy</div>
        <table>
          <tr><th>Resource</th><th>Planet</th><th>Enemy</th></tr>
          <tr><td>Metal</td><td><xsl:value-of select="$battle/losses/planet/@metal"/></td><td><xsl:value-of select="$battle/losses/enemy/@metal"/></td></tr>
          <tr><td>Deuterium</td><td><xsl:value-of select="$battle/losses/planet/@deuterium"/></td><td><xsl:value-of select="$battle/losses/enemy/@deuterium"/></td></tr>
          <tr><td>Weighted</td><td><xsl:value-of select="$battle/losses/planet/@weighted"/></td><td><xsl:value-of select="$battle/losses/enemy/@weighted"/></td></tr>
        </table>

        <div class="section-title">Waste Generated</div>
        <p>Metal: <xsl:value-of select="$battle/waste/metal"/></p>
        <p>Deuterium: <xsl:value-of select="$battle/waste/deuterium"/></p>

        <div class="footer">
          Battle Won by: <xsl:value-of select="$battle/winner"/>
        </div>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
