function verBatalla() {
  const battleNumber = document.getElementById('battleNumber').value.trim();
  const errorEl = document.getElementById('error');
  const resultado = document.getElementById('resultado');
  resultado.innerHTML = '';
  errorEl.textContent = '';

  if (!battleNumber || isNaN(battleNumber) || battleNumber < 1) {
    errorEl.textContent = 'Introduce un número de batalla válido.';
    return;
  }

  // Construir la ruta del XML basado en el número de batalla
  const xmlPath = `xml/batalla${battleNumber}.xml`;

  Promise.all([
    fetch(xmlPath).then(res => {
      if (!res.ok) throw new Error('Esta batalla aun no ha ocurrido');
      return res.text();
    }),
    fetch('xsl/transformacion.xsl').then(res => res.text())
  ])
  .then(([xmlText, xslText]) => {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlText, "application/xml");
    const xslDoc = parser.parseFromString(xslText, "application/xml");

    const xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(xslDoc);

    // Si tu XSL no usa parámetros para elegir batalla, puedes omitir esta línea
    // xsltProcessor.setParameter(null, "battleID", battleNumber);

    const resultDocument = xsltProcessor.transformToFragment(xmlDoc, document);
    resultado.appendChild(resultDocument);
  })
  .catch(err => {
    errorEl.textContent = err.message;
  });
}
