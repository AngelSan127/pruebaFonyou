package com.fonyou.examen.dto;

import lombok.Data;

@Data
public class respuestaOut {

    private int idRespuesta;

    private String descrespuesta;

    private int idPregunta;

    private int idExamen;

    private boolean correcta;

}
