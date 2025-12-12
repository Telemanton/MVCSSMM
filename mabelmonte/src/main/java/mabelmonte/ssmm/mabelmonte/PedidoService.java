package mabelmonte.ssmm.mabelmonte;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> buscar(String userName) {
       return pedidoRepository.findByEmail(userName);
    }
}

