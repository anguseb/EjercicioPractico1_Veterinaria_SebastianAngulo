document.addEventListener('DOMContentLoaded', function () {

    // Muestra los avisos de resultado y los oculta solos a los 5 segundos
    document.querySelectorAll('.toast').forEach(function (elemento) {
        new bootstrap.Toast(elemento, {delay: 5000}).show();
    });

    // Pasa el id y el nombre del registro a la ventana modal de confirmación
    var modalBorrar = document.getElementById('modalBorrar');
    if (modalBorrar) {
        modalBorrar.addEventListener('show.bs.modal', function (evento) {
            var boton = evento.relatedTarget;
            document.getElementById('borrarId').value = boton.getAttribute('data-id');
            document.getElementById('borrarNombre').textContent = boton.getAttribute('data-nombre');
        });
    }
});
