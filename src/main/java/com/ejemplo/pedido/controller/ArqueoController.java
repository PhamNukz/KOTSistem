package com.ejemplo.pedido.controller;

import com.ejemplo.pedido.model.Arqueo;
import com.ejemplo.pedido.model.Pedido;
import com.ejemplo.pedido.repository.ArqueoRepository;
import com.ejemplo.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/arqueos")
public class ArqueoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ArqueoRepository arqueoRepository;


    @PostMapping("/generar")
    public String generarArqueo(RedirectAttributes redirectAttributes) {
        try {
            List<Pedido> pedidosSinArqueo = pedidoRepository.findByArqueoIsNull();
            if (pedidosSinArqueo.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No hay pedidos pendientes para arqueo.");
                return "redirect:/arqueos";
            }

            // Calcula el siguiente ID de arqueo
            Long nextId = arqueoRepository.getMaxIdArqueo() != null ?
                    arqueoRepository.getMaxIdArqueo() + 1 : 1;

            // Crea y configura el arqueo con todos los campos requeridos
            Arqueo nuevoArqueo = new Arqueo();
            nuevoArqueo.setIdArqueo(nextId);
            nuevoArqueo.setDescripcion("Arqueo #" + nextId + " - " + LocalDate.now());
            nuevoArqueo.setFecha(LocalDate.now());
            nuevoArqueo.setTotal(0.0); // Valor inicial

            Arqueo arqueoGuardado = arqueoRepository.save(nuevoArqueo);

            // Actualiza pedidos y calcula total
            pedidosSinArqueo.forEach(pedido -> pedido.setArqueo(arqueoGuardado));
            pedidoRepository.saveAll(pedidosSinArqueo);

            double total = pedidosSinArqueo.stream()
                    .mapToDouble(p -> p.getTotal().doubleValue())
                    .sum();

            arqueoGuardado.setTotal(total);
            arqueoRepository.save(arqueoGuardado);

            redirectAttributes.addFlashAttribute("success",
                    "Arqueo #" + nextId + " generado con éxito");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al generar arqueo: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/arqueos";
    }

    // Método 2: Buscar pedidos por ID de arqueo
    @GetMapping("/{id}/pedidos")
    @ResponseBody
    public List<Pedido> buscarPedidosPorArqueo(@PathVariable Long id) {
        return pedidoRepository.findByArqueoId(id);
    }

    // Método 3: Listar todos los arqueos
    @GetMapping
    public String listarArqueos(Model model) {
        List<Arqueo> arqueos = arqueoRepository.findAll();
        model.addAttribute("arqueos", arqueos);

        return "arqueos"; // Vista arqueos.html
    }
}