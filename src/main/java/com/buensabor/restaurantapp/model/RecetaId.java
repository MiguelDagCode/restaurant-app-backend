package com.buensabor.restaurantapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "id_insumo")
    private Long idInsumo;

    // Obligatorio sobrescribir equals y hashCode en llaves compuestas para que JPA compare correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetaId recetaId = (RecetaId) o;
        return Objects.equals(idProducto, recetaId.idProducto) &&
               Objects.equals(idInsumo, recetaId.idInsumo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idInsumo);
    }
}