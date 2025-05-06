
package com.ejemplo.pedido.controller;

import com.ejemplo.pedido.model.Pedido;
import com.ejemplo.pedido.model.Producto;
import com.ejemplo.pedido.repository.PedidoRepository;
import com.ejemplo.pedido.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ejemplo.pedido.service.PedidoActualService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoActualService pedidoActualService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "index";
    }

    @PostMapping("/agregar")
    public String agregarPedido(@RequestParam String cliente,
                                @RequestParam String producto,
                                @RequestParam int cantidad,
                                Model model) {
        Producto prod = productoRepository.findAll()
                .stream()
                .filter(p -> p.getNombre().equals(producto))
                .findFirst()
                .orElse(null);

        if (prod == null || cantidad <= 0) {
            model.addAttribute("error", "Producto no encontrado o cantidad inválida");
            model.addAttribute("productos", productoRepository.findAll());
            return "index";
        }

        BigDecimal total = prod.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        Pedido nuevo = new Pedido(cliente, producto);
        nuevo.setCantidad(cantidad);
        nuevo.setTotal(total);

        try {
            pedidoRepository.save(nuevo);
            model.addAttribute("pedido", nuevo);
            return "voucher";
        } catch (Exception e) {
            e.printStackTrace(); // o log.error
            model.addAttribute("error", "Error al guardar el pedido");
            model.addAttribute("productos", productoRepository.findAll());
            return "index";
        }

    }

    @PostMapping("/productos/agregar")
    public String agregarProducto(@RequestParam String nombre,
                                  @RequestParam BigDecimal precio) {
        Producto nuevo = new Producto(nombre, precio);
        productoRepository.save(nuevo);
        return "redirect:/productos";
    }

    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        model.addAttribute("pedidos", pedidos);
        return "historial"; // sin "redirect:"
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            productoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado correctamente.");
        }
        return "redirect:/productos";
    }

    @PostMapping("/pedido/agregar")
    public String agregarAlPedido(@RequestParam Long productoId, @RequestParam int cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        productoOpt.ifPresent(producto -> pedidoActualService.agregarProducto(producto, cantidad));
        return "redirect:/"; // redirige a index con la previsualización actualizada
    }

}
