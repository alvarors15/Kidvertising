// sortTable.js

var sortDirection = {};

// Nueva lógica para realizar la solicitud AJAX
    $.ajax({
        type: "GET",
        url: "/anuncios/sorted" + attributeName + "?direction=" + sortDirection[attributeName],
        success: function (data) {
            // Actualizar la tabla con los datos devueltos por el controlador
            // (Puedes modificar esto según la estructura de tu respuesta)
            $("#adsTable tbody").html(data);
            updateArrow(attributeName);
        },
        error: function () {
            console.error("Error al realizar la solicitud AJAX.");
        }
    });
}

function updateArrow(attributeName) {
    var arrow = document.getElementById(attributeName + 'Arrow');
    arrow.innerHTML = sortDirection[attributeName] === 'asc' ? '▼' : '▲'; // Utiliza caracteres Unicode para flechas más grandes
}
