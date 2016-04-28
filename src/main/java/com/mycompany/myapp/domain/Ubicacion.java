package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ubicacion.
 */
@Entity
@Table(name = "ubicacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ubi_name")
    private String ubiName;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "longitud")
    private Float longitud;

    @Column(name = "latitud")
    private Float latitud;

    @OneToMany(mappedBy = "ubicacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Precio> precios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ubicacion_cervesa",
               joinColumns = @JoinColumn(name="ubicacions_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="cervesas_id", referencedColumnName="ID"))
    private Set<Cervesa> cervesas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUbiName() {
        return ubiName;
    }

    public void setUbiName(String ubiName) {
        this.ubiName = ubiName;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Set<Precio> getPrecios() {
        return precios;
    }

    public void setPrecios(Set<Precio> precios) {
        this.precios = precios;
    }

    public Set<Cervesa> getCervesas() {
        return cervesas;
    }

    public void setCervesas(Set<Cervesa> cervesas) {
        this.cervesas = cervesas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ubicacion ubicacion = (Ubicacion) o;
        if(ubicacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ubicacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
            "id=" + id +
            ", ubiName='" + ubiName + "'" +
            ", direccion='" + direccion + "'" +
            ", longitud='" + longitud + "'" +
            ", latitud='" + latitud + "'" +
            '}';
    }
}
