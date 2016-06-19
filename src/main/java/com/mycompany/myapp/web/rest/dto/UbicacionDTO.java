package com.mycompany.myapp.web.rest.dto;

import com.mycompany.myapp.domain.Precio;
import com.mycompany.myapp.domain.Ubicacion;

import java.util.List;

/**
 * Created by Víctor on 19/06/2016.
 */
public class UbicacionDTO {

    private Long id;
    private String ubiName;
    private String direccion;
    private Float longitud;
    private Float latitud;
    private Double precio;

    //public UbicacionDTO(Long id, String ubiName, String direccion, Float longitud, Float latitud, Double precio) {
    public UbicacionDTO(Ubicacion ubi , Precio prec) {
        this.id = ubi.getId();
        this.ubiName = ubi.getUbiName();
        this.direccion = ubi.getDireccion();
        this.longitud = ubi.getLongitud();
        this.latitud = ubi.getLatitud();
        this.precio = prec.getPrecio();
    }

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


}
