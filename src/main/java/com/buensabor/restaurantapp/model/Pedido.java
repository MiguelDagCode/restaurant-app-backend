package com.buensabor.restaurantapp.model;

import com.buensabor.restaurantapp.enums.EstadoPedido;
import com.buensabor.restaurantapp.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER para ver la mesa rápido en pantalla
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // El Mozo

    // --- Datos Cliente ---
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_doc")
    private TipoDocumento tipoDoc;

    @Column(name = "num_doc", length = 20)
    private String numDoc;

    @Column(name = "nombres_razon_social", nullable = false, length = 150)
    private String nombresRazonSocial;

    @Column(name = "telefono_cliente", length = 20)
    private String telefonoCliente;

    // --- Tiempos y Estados ---
    @Column(name = "fecha_apertura")
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_inicio_cocina")
    private LocalDateTime fechaInicioCocina;

    @Column(name = "fecha_fin_cocina")
    private LocalDateTime fechaFinCocina;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido")
    private EstadoPedido estadoPedido;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;
    
 // Opcional: Para acceder a los detalles desde el pedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles;

    // Inicializar fecha al crear
    @PrePersist
    public void prePersist() {
        if (this.fechaApertura == null) {
            this.fechaApertura = LocalDateTime.now();
        }
        if (this.estadoPedido == null) {
            this.estadoPedido = EstadoPedido.PENDIENTE;
        }
    }
}