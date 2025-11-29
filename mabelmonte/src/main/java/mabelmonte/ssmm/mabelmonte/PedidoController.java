package mabelmonte.ssmm.mabelmonte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/pedidos")
    public ResponseEntity<?> crearPedido(@RequestBody Map<String, Object> pedidoData) {
        try {
            String email = (String) pedidoData.get("email");
            String pedidoJson = (String) pedidoData.get("pedido");
            int totalCents = Integer.parseInt(pedidoData.get("total_cents").toString());
            double totalEuros = Double.parseDouble(pedidoData.get("total_euros").toString());
            String estado = (String) pedidoData.get("estado");

            Pedido pedido = new Pedido(email, pedidoJson, totalCents, totalEuros, estado);
            Pedido savedPedido = pedidoRepository.save(pedido);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("pedido_id", savedPedido.getId());
            response.put("mensaje", "Pedido creado correctamente");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "Error creando pedido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/pedidos")
    public ResponseEntity<?> obtenerPedidos() {
        try {
            List<Pedido> pedidos = pedidoRepository.findAll();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error obteniendo pedidos");
        }
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<?> obtenerPedido(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> ResponseEntity.ok(pedido))
                .orElse(ResponseEntity.notFound().build());
    }
}
