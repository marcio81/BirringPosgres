package com.mycompany.myapp.web.rest.dto;

/**
 * Created by victor.correas on 31/5/16.
 */
public class CervezaDTO {
    private Long id;
    private String cervesaName;
    private byte[] foto;
    private Double mediaEvaluacion;

    public CervezaDTO(Long id, String cervesaName, byte[] foto, Double mediaEvaluacion) {
        this.id = id;
        this.cervesaName = cervesaName;
        this.foto = foto;
        this.mediaEvaluacion = mediaEvaluacion;
    }

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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Double getMediaEvaluacion() {
        return mediaEvaluacion;
    }

    public void setMediaEvaluacion(Double mediaEvaluacion) {
        this.mediaEvaluacion = mediaEvaluacion;
    }
}
