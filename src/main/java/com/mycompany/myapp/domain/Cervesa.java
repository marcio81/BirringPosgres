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
 * A Cervesa.
 */
@Entity
@Table(name = "cervesa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cervesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cervesa_name")
    private String cervesaName;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fabricante")
    private String fabricante;

    @Column(name = "pais")
    private String pais;

    @Column(name = "graduacion")
    private Double graduacion;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @OneToMany(mappedBy = "cervesa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Precio> precios = new HashSet<>();

    @OneToMany(mappedBy = "cervesa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evaluar> evaluars = new HashSet<>();

    @OneToMany(mappedBy = "cervesa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comentario> comentarios = new HashSet<>();

    @ManyToMany(mappedBy = "cervesas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ubicacion> ubicacions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCervesaName() {
        return cervesaName;
    }

    public void setCervesaName(String cervesaName) {
        this.cervesaName = cervesaName;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(Double graduacion) {
        this.graduacion = graduacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
      return fotoContentType;
   }

   public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Set<Precio> getPrecios() {
        return precios;
    }

    public void setPrecios(Set<Precio> precios) {
        this.precios = precios;
    }

    public Set<Evaluar> getEvaluars() {
        return evaluars;
    }

    public void setEvaluars(Set<Evaluar> evaluars) {
        this.evaluars = evaluars;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Set<Ubicacion> getUbicacions() {
        return ubicacions;
    }

    public void setUbicacions(Set<Ubicacion> ubicacions) {
        this.ubicacions = ubicacions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cervesa cervesa = (Cervesa) o;
        if(cervesa.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cervesa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cervesa{" +
            "id=" + id +
            ", cervesaName='" + cervesaName + "'" +
            ", tipo='" + tipo + "'" +
            ", fabricante='" + fabricante + "'" +
            ", pais='" + pais + "'" +
            ", graduacion='" + graduacion + "'" +
            ", foto='" + foto + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            '}';
    }


}
