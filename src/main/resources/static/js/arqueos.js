// Función para generar arqueo
function generarArqueo() {
    if (confirm('¿Estás seguro de generar un nuevo arqueo con los pedidos actuales?')) {
        fetch('/arqueos/generar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('Arqueo generado con éxito');
                    window.location.href = '/arqueos'; // Redirige a la página de arqueos
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al generar arqueo: ' + error.message);
            });
    }
}

// Función para ver pedidos de un arqueo
function verPedidos(id) {
    fetch(`/arqueos/${id}/pedidos`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener pedidos');
            }
            return response.json();
        })
        .then(pedidos => {
            const modal = document.getElementById('modalPedidos');
            const lista = document.getElementById('listaPedidos');
            const arqueoIdSpan = document.getElementById('arqueoId');

            arqueoIdSpan.textContent = id;
            lista.innerHTML = '';

            if (pedidos.length === 0) {
                lista.innerHTML = '<p>No hay pedidos para este arqueo.</p>';
            } else {
                const table = document.createElement('table');
                table.className = 'styled-table';

                // Crear encabezados de tabla
                const thead = document.createElement('thead');
                thead.innerHTML = `
                <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Total</th>
                </tr>
            `;
                table.appendChild(thead);

                // Crear cuerpo de tabla
                const tbody = document.createElement('tbody');
                pedidos.forEach(pedido => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                    <td>${pedido.id}</td>
                    <td>${pedido.cliente}</td>
                    <td>${pedido.producto}</td>
                    <td>${pedido.cantidad}</td>
                    <td>$${pedido.total.toFixed(2)}</td>
                `;
                    tbody.appendChild(tr);
                });
                table.appendChild(tbody);
                lista.appendChild(table);
            }

            modal.style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
}

// Cerrar modal
document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#modalPedidos button').addEventListener('click', () => {
        document.getElementById('modalPedidos').style.display = 'none';
    });
});