package com.fonyou.examen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;
import java.util.Set;

@Data
@ApiModel("respuestaEstudiante")
public class respuestaEstudiante {


    @JsonProperty("IdEstudiante")
    private int IdEstudiante;

    @JsonProperty("idExamen")
    private int idExamen;

    @JsonProperty("cuestionario")
    @Schema(name = "cuestionario")
    private List<preguntas> cuestionario;

}
