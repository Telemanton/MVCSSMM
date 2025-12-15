package mabelmonte.ssmm.mabelmonte;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime; 

@Controller
public class PedidoWebController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/consulta-pedido")
    public String consultarPedidos(
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "false") boolean today, // Recibe el parámetro today=true
            Model model) {

        List<Pedido> resultados;

        if (email != null && !email.isEmpty()) {
            // Caso 1: Buscar por Email
            resultados = pedidoRepository.findByEmail(email);

        } else if (today) {
            // Caso 2: Buscar solo los de HOY
            
            LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
            
            // Llama al método corregido
            resultados = pedidoRepository.findByCreatedAtGreaterThanEqual(inicioDia);

        } else {
            // Caso 3: Ver TODOS (si no hay filtros)
            resultados = pedidoRepository.findAll();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("emailBuscado", email);
        model.addAttribute("esHoy", today); // Se puede usar para cambiar el título en la vista

        return "resultado"; // Template Thymeleaf "resultado.html"
    }
}