<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes" />
    
    <!-- Plantilla principal -->
    <xsl:template match="/battle">
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Legend Battle - Batallas</title>
            <link rel="stylesheet" href="style.css" />
        </head>
        <body>
            <!-- Barra de navegación -->
            <header class="navbar">
                <nav class="nav-wrapper">
                    <div class="nav-block"><a href="indice1.html" class="nav-link">Inicio</a></div>
                    <div class="nav-block"><a href="tutorial.html" class="nav-link">Tutorial</a></div>
                    <div class="nav-block logo-block">
                        <img src="img/titulo.png" alt="Legend Battle Logo" class="nav-logo" />
                    </div>
                    <div class="nav-block"><a href="batallas.html" class="nav-link">Batallas</a></div>
                    <div class="nav-block"><a href="about.html" class="nav-link">About Us</a></div>
                </nav>
            </header>

            <!-- Contenido principal -->
            <main class="batallas">
                <div class="batallas-content">
                    <h1>Reporte de Batalla</h1>
                    <h2>Resultado: <xsl:value-of select="winner" /></h2>
                    
                    <h3>Ejército del Planeta</h3>
                    <table border="1">
                        <tr>
                            <th>Unidad</th>
                            <th>Total</th>
                            <th>Caídas</th>
                        </tr>
                        <xsl:for-each select="statistics/planetArmy/unit">
                            <tr>
                                <td><xsl:value-of select="@name" /></td>
                                <td><xsl:value-of select="@total" /></td>
                                <td><xsl:value-of select="@drops" /></td>
                            </tr>
                        </xsl:for-each>
                    </table>

                    <h3>Ejército del Enemigo</h3>
                    <table border="1">
                        <tr>
                            <th>Unidad</th>
                            <th>Total</th>
                            <th>Caídas</th>
                        </tr>
                        <xsl:for-each select="statistics/enemyArmy/unit">
                            <tr>
                                <td><xsl:value-of select="@name" /></td>
                                <td><xsl:value-of select="@total" /></td>
                                <td><xsl:value-of select="@drops" /></td>
                            </tr>
                        </xsl:for-each>
                    </table>

                    <h3>Costos</h3>
                    <p>Planeta: <xsl:value-of select="costs/planet/metal" /> Metal, <xsl:value-of select="costs/planet/deuterium" /> Deuterio</p>
                    <p>Enemigo: <xsl:value-of select="costs/enemy/metal" /> Metal, <xsl:value-of select="costs/enemy/deuterium" /> Deuterio</p>

                    <h3>Pérdidas</h3>
                    <p>Planeta: <xsl:value-of select="losses/planet/metal" /> Metal, <xsl:value-of select="losses/planet/deuterium" /> Deuterio</p>
                    <p>Enemigo: <xsl:value-of select="losses/enemy/metal" /> Metal, <xsl:value-of select="losses/enemy/deuterium" /> Deuterio</p>

                    <h3>Rubble Generado</h3>
                    <p>Metal: <xsl:value-of select="rubble/metal" /> Deuterio: <xsl:value-of select="rubble/deuterium" /></p>
                </div>
            </main>

            <!-- Footer -->
            <footer>
                <p>&copy; Copyright.</p>
            </footer>

            <!-- JS -->
            <script src="main.js"></script>
        </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
