package com.mycompany.myapp.web.rest.dto;

import java.util.List;

/**
 * Created by victor.correas on 26/5/16.
 */
public class Top10DTO {
    List<Object[]> cervezas;

    public List<Object[]> getCervezas() {
        return cervezas;
    }

    public void setCervezas(List<Object[]> cerveza) {
        this.cervezas = cerveza;
    }
}
