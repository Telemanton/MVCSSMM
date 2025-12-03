package mabelmonte.ssmm.mabelmonte;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

     @GetMapping("/mapoints/{email}")
public ResponseEntity<?> getMapointsByEmail(@PathVariable String email) {
    try {
        Usuario usuario = usuarioRepository.findByNombre(email);
        if (usuario == null) {
            return ResponseEntity.ok(Map.of("mapoints", 0));
        }
        return ResponseEntity.ok(Map.of("mapoints", usuario.getMapoints()));
    } catch (Exception e) {
        return ResponseEntity.ok(Map.of("mapoints", 0));
    }
}

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByNombre(usuario.getNombre()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Usuario ya registrado"));
        }
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("nombre", usuario.getNombre());
        response.put("email", usuario.getEmail());
        response.put("mapoints", usuario.getMapoints());
        response.put("registrado", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario login) {
        Usuario usuario = usuarioRepository.findByNombre(login.getNombre());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Usuario no encontrado"));
        }
        if (!passwordEncoder.matches(login.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Contraseña incorrecta"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("nombre", usuario.getNombre());
        response.put("email", usuario.getEmail());
        response.put("mapoints", usuario.getMapoints());
        response.put("registrado", true);

        return ResponseEntity.ok(response);
    }
    // Y este autowired en la clase
@Autowired
private PedidoRepository pedidoRepository;

// Añade este método al final de la clase
@PostMapping("/pedidos/confirmar")
public ResponseEntity<?> confirmarPedido(@RequestBody Map<String, Object> data) {
    try {
        String pedidoId = (String) data.get("pedido_id");
        String stripePaymentId = (String) data.get("stripe_payment_intent_id");
        
        Pedido pedido = pedidoRepository.findById(Long.parseLong(pedidoId)).orElse(null);
        if (pedido != null) {
            pedido.setEstado("pagado");
            pedidoRepository.save(pedido);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("mensaje", "Pedido confirmado y pagado");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
}
