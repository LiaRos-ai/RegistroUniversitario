package com.universidad.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materia")
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long id;

    @Column(name = "nombre_materia", nullable = false, length = 100)
    private String nombreMateria;

    @Column(name = "codigo_unico", nullable = false, unique = true, length = 20)
    private String codigoUnico;

    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Version
    private Long version;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Evita ciclos infinitos con @JsonBackReference en UnidadTematica
    private List<UnidadTematica> unidadesTematicas = new ArrayList<>();

    // Relación Many-to-Many para los prerequisitos
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "materia_prerequisito",
            joinColumns = @JoinColumn(name = "id_materia"),
            inverseJoinColumns = @JoinColumn(name = "id_prerequisito")
    )
    private List<Materia> prerequisitos;

    // Relación inversa de Many-to-Many
    @ManyToMany(mappedBy = "prerequisitos")
    private List<Materia> esPrerequisitoDe;

    // Método para verificar ciclos de prerequisitos
    public boolean formariaCirculo(Long prerequisitoId) {
        Set<Long> visitados = new HashSet<>();
        return formariaCirculoIterativo(prerequisitoId, visitados);
    }

    private boolean formariaCirculoIterativo(Long prerequisitoId, Set<Long> visitados) {
        Set<Materia> pendientes = new HashSet<>(this.prerequisitos);
        while (!pendientes.isEmpty()) {
            Materia materia = pendientes.iterator().next();
            pendientes.remove(materia);

            if (materia.getId().equals(prerequisitoId)) {
                return true;
            }

            if (!visitados.add(materia.getId())) {
                continue;
            }

            if (materia.getPrerequisitos() != null) {
                pendientes.addAll(materia.getPrerequisitos());
            }
        }
        return false;
    }

    // Métodos auxiliares para agregar y eliminar unidades temáticas
    public void addUnidadTematica(UnidadTematica unidadTematica) {
        unidadesTematicas.add(unidadTematica);
        unidadTematica.setMateria(this);
    }

    public void removeUnidadTematica(UnidadTematica unidadTematica) {
        unidadesTematicas.remove(unidadTematica);
        unidadTematica.setMateria(null);
    }
}
