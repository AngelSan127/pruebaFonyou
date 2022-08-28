package com.fonyou.examen.service.impl;

import com.fonyou.examen.dao.DaoResponse;
import com.fonyou.examen.dto.resExamenOut;
import com.fonyou.examen.dto.respuestaEstudiante;
import com.fonyou.examen.dto.respuestaOut;
import com.fonyou.examen.service.ServiceFonyou;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class ServiceFonyouImpl implements ServiceFonyou {

    private final Logger logger = Logger.getLogger(ServiceFonyouImpl.class.getName());


    @Autowired
    private DaoResponse SQSResponse;


    @Override
    public void insertResponse(respuestaEstudiante InsertResponse)
    {
        try {

            int pos= InsertResponse.getCuestionario().size();
            respuestaOut objSQSReponse = new respuestaOut();
            resExamenOut response = new resExamenOut();


            for(int posArreglo=0;posArreglo<pos;posArreglo++)
            {
                objSQSReponse=SQSResponse.consulrasRespuesta(InsertResponse,posArreglo);
                SQSResponse.InsertResponse(InsertResponse,posArreglo,objSQSReponse);
                response=SQSResponse.calculoResultado(InsertResponse,posArreglo);
            }

            SQSResponse.InsertExamen(InsertResponse,response);


        }catch (Exception e)
        {
            logger.severe(e.getMessage());
        }
    }

}
