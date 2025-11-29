package mabelmonte.ssmm.mabelmonte;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    
    @Column(columnDefinition = "TEXT")
    private String pedido; // JSON string con la lista de productos
    
    private int totalCents;
    private double totalEuros;
    private String estado; // "pendiente", "pagado", "cancelado"
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructores
    public Pedido() {}
    
    public Pedido(String email, String pedido, int totalCents, double totalEuros, String estado) {
        this.email = email;
        this.pedido = pedido;
        this.totalCents = totalCents;
        this.totalEuros = totalEuros;
        this.estado = estado;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPedido() { return pedido; }
    public void setPedido(String pedido) { this.pedido = pedido; }
    
    public int getTotalCents() { return totalCents; }
    public void setTotalCents(int totalCents) { this.totalCents = totalCents; }
    
    public double getTotalEuros() { return totalEuros; }
    public void setTotalEuros(double totalEuros) { this.totalEuros = totalEuros; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { 
        this.estado = estado; 
        this.updatedAt = LocalDateTime.now(); 
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
