package mabelmonte.ssmm.mabelmonte;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEmail(String email);
    List<Pedido> findByEstado(String estado);

}
