package mabelmonte.ssmm.mabelmonte;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private String email;

    private String password;

    private int mapoints;  // Nuevo campo agregado

    // Getters y setters para cada atributo
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getMapoints() { return mapoints; }
    public void setMapoints(int mapoints) { this.mapoints = mapoints; }

}
