package mabelmonte.ssmm.mabelmonte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reservas")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

   

    @PostMapping("/mapoints")
    public ResponseEntity<?> pagarconmapoints(@RequestBody Map<String, Object> request) {
        try {
            String nombreUsuario = (String) request.get("email");

            Usuario usuario = usuarioRepository.findByNombre(nombreUsuario);
            Number puntosAPagar = (Integer) request.get("total_cents");
            int puntosAPagarInt = puntosAPagar.intValue();
            if (usuario.getMapoints() < puntosAPagarInt) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Puntos insuficientes"));
            }

            usuario.setMapoints(usuario.getMapoints() - puntosAPagarInt);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "mensaje", "Pago con mapoints realizado correctamente",
                "mapoints_restantes", usuario.getMapoints()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error procesando el pago: " + e.getMessage()));
        }
    }
    

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

            
            Usuario usuario = usuarioRepository.findByNombre(email);
            int puntosGanados = totalCents;
            usuario.setMapoints(usuario.getMapoints() + puntosGanados);
            usuarioRepository.save(usuario);
        


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
