function verBatalla() {
    const numeroInput = document.getElementById("battleNumber");
    const error = document.getElementById("error");
    const numero = parseInt(numeroInput.value.trim(), 10);
    const maxBatallas = 5; // Cambia esto si tienes más batallas
  
    // Limpiar mensajes de error previos
    error.textContent = '';
  
    // Verificar si el input es un número válido
    if (isNaN(numero)) {
      error.textContent = "Por favor, introduce un número válido.";
      return;
    }
  
    // Verificar que el número esté dentro del rango de batallas disponibles
    if (numero < 1 || numero > maxBatallas) {
      error.textContent = `La batalla #${numero} no existe.`;
      return;
    }
  
    // Comprobamos si el archivo de la batalla existe
    const url = `batalla${numero}.html`;  // Usamos una ruta relativa
  
    // Usamos fetch para verificar la existencia del archivo
    fetch(url)
      .then((response) => {
        if (response.ok) {
          // Si el archivo existe, redirigimos a la batalla
          window.location.href = url;
        } else {
          // Si el archivo no existe, mostramos un error
          error.textContent = `La batalla #${numero} no está disponible en este momento.`;
        }
      })
      .catch((err) => {
        // Si hubo un error en la solicitud, mostramos un error genérico
        error.textContent = "Error al intentar acceder al archivo de la batalla.";
        console.error(err); // Para depuración
      });
  }
  