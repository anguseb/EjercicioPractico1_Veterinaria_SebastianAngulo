package CasoPractico.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "servicio")
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El precio no puede estar vacío.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;

    @NotNull(message = "La duración no puede estar vacía.")
    @Min(value = 1, message = "La duración debe ser de al menos 1 minuto.")
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    // Relación de muchos a uno: cada servicio pertenece a un tipo
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_servicio_id", nullable = false)
    private TipoServicio tipoServicio;
}
