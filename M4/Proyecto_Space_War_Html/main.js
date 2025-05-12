function verBatalla() {
  const battleNumber = document.getElementById("battleNumber").value;
  const errorMessage = document.getElementById("error");

  if (battleNumber && !isNaN(battleNumber) && battleNumber > 0) {
    // Redirigir a la URL de la batalla
    const battleURL = `salida/batalla${battleNumber}.html`;
    console.log("Redirigiendo a: ", battleURL);  // Mostrar la URL generada en la consola

    // Redirigir a la página correspondiente
    window.location.href = battleURL;
  } else {
    // Mostrar mensaje de error si el número no es válido
    errorMessage.textContent = "Por favor, ingresa un número de batalla válido.";
  }
}
