package com.fonyou.examen.rest.controller;


import com.fonyou.examen.dto.respuestaEstudiante;
import com.fonyou.examen.service.ServiceFonyou;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/api/v1/fonyou", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class fonyouController {

    @Autowired
    private ServiceFonyou serviceFonyou;

    @ApiOperation(value = "Apply examen student")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS",
            examples = @Example(value = @ExampleProperty(value = "", mediaType = MediaType.APPLICATION_JSON_VALUE))),
            @ApiResponse(code = 400, message = "Error has occurred, timestamp: 2021-10-27 15:18:17"),
            @ApiResponse(code = 500, message = "Error has occurred, timestamp: 2021-10-27 15:18:17")})
    @PostMapping(value = "/form", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> applyGMFTax(@RequestBody respuestaEstudiante reponse) {

       serviceFonyou.insertResponse(reponse);

        return ResponseEntity.ok().build();
    }

}
