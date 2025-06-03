
document.addEventListener("DOMContentLoaded", function () {
    // Eventos de navegación
    document.getElementById("Agregar-btn")?.addEventListener("click", function () {
        window.location.href = "/productos";
    });

    document.getElementById("Historial-btn")?.addEventListener("click", function () {
        window.location.href = "/historial";
    });

    // Lógica del carrito (tu código actual)
    const btn = document.getElementById('agregar-btn');
    const productoSelect = document.querySelector('select[name="producto"]');
    const cantidadInput = document.querySelector('input[name="cantidad"]');
    const carrito = document.getElementById('cart-items');
    const totalDisplay = document.getElementById('total');
    let total = 0;

    btn?.addEventListener('click', () => {
        const selectedOption = productoSelect.selectedOptions[0];
        const producto = selectedOption.value;
        const precio = parseFloat(selectedOption.dataset.precio);
        const cantidad = parseInt(cantidadInput.value);

        if (producto && cantidad > 0) {
            const subtotal = precio * cantidad;
            total += subtotal;

            const li = document.createElement('li');
            li.textContent = `${producto} - Cantidad: ${cantidad} - $${precio.toFixed(2)}`;
            carrito.appendChild(li);

            totalDisplay.textContent = `Total: $${total.toFixed(2)}`;
            productoSelect.selectedIndex = 0;
            cantidadInput.value = '';
        } else {
            alert('Selecciona un producto y una cantidad válida.');
        }
    });

    // Formulario de pedido (tu código actual)
    const generarForm = document.getElementById('pedidoForm');
    const hiddenInput = document.getElementById('pedidoJson');

    generarForm?.addEventListener('submit', (e) => {
        const productos = [];
        const items = document.querySelectorAll('#cart-items li');
        const cliente = document.getElementById('cliente-input').value.trim();

        if (!cliente) {
            e.preventDefault();
            alert('Por favor ingresa el nombre del cliente antes de generar el pedido.');
            return;
        }

        items.forEach(item => {
            const texto = item.textContent;
            const partes = texto.split(' - ');
            const nombre = partes[0];
            const cantidad = parseInt(partes[1].split(': ')[1]);
            const precio = parseFloat(partes[2].split('$')[1]);

            productos.push({ nombre, cantidad, precio, cliente });
        });

        hiddenInput.value = JSON.stringify(productos);
    });
});

