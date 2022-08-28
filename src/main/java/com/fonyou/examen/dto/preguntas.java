package com.fonyou.examen.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("preguntas")
public class preguntas {

    @JsonProperty("IdPregunta")
    private int IdPregunta;

    @JsonProperty("IdRespuesta")
    private int IdRespuesta;

}
