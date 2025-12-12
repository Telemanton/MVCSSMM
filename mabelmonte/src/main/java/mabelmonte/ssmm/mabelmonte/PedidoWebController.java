package mabelmonte.ssmm.mabelmonte;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
public class PedidoWebController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/consulta-pedido")
    public String consultarPedidos(
            @RequestParam(required = false) String email,
            Model model) {

        List<Pedido> resultados;
        if (email != null && !email.isEmpty()) {
            resultados = pedidoRepository.findByEmail(email);
        } else {
            resultados = pedidoRepository.findAll();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("emailBuscado", email);

        return "resultado"; // Thymeleaf template "resultado.html"
    }
}
