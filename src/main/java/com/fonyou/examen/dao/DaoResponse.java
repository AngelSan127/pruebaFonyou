package com.fonyou.examen.dao;


import com.fonyou.examen.dto.resExamenOut;
import com.fonyou.examen.dto.respuestaEstudiante;
import com.fonyou.examen.dto.respuestaOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoResponse {


    @Autowired
    @Qualifier("jdbcExamen")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateOne;

    private Logger logger = LoggerFactory.getLogger(DaoResponse.class);


    public void  InsertResponse(respuestaEstudiante InsertResponse,int pos,respuestaOut objSQSReponse ) throws SQLException {

        String query =("INSERT INTO public.respuestaestudiante \n" +
              "(idexamen,idpregunta,idestudiante,idrespuesta,estadoresp) \n" +
              "VALUES("+InsertResponse.getIdExamen()+"," +
                    " "+InsertResponse.getCuestionario().get(pos).getIdPregunta()+"," +
                    " "+InsertResponse.getIdEstudiante()+"," +
                    "  "+InsertResponse.getCuestionario().get(pos).getIdRespuesta()+"," +
                    "  "+objSQSReponse.isCorrecta()+");");

        System.out.println(query);
        System.out.println(namedParameterJdbcTemplateOne);

        MapSqlParameterSource params = new MapSqlParameterSource();

        namedParameterJdbcTemplateOne.update(query,params);

    }

    public respuestaOut consulrasRespuesta(respuestaEstudiante InsertResponse,int pos) throws SQLException {
        respuestaOut  objSQSReponse = new respuestaOut();

        try {

            String query =("select idrespuesta,descrespuesta,idpregunta,idexamen,correcta from respuesta r2 where idpregunta = " +
                            " "+InsertResponse.getCuestionario().get(pos).getIdPregunta()+" and idrespuesta = " +
                            " "+InsertResponse.getCuestionario().get(pos).getIdRespuesta()+"");


            namedParameterJdbcTemplateOne.query(query, rs -> {

                if (rs!=null) {

                    objSQSReponse.setIdRespuesta(rs.getInt("idrespuesta"));
                    objSQSReponse.setDescrespuesta(rs.getString("descrespuesta"));
                    objSQSReponse.setIdPregunta(rs.getInt("idpregunta"));
                    objSQSReponse.setIdExamen(rs.getInt("idexamen"));
                    objSQSReponse.setCorrecta(rs.getBoolean("correcta"));
                }

            });

        }
        finally
        {
            return objSQSReponse;
        }
    }

    public resExamenOut calculoResultado(respuestaEstudiante InsertResponse, int pos) throws SQLException {

        resExamenOut response = new resExamenOut();

        try {


            String query =("select *\n" +
                    "\tfrom (\n" +
                    "\t\t(select ((count(idrespuesta)*100)/(select max(idpregunta) from respuesta r2  where idexamen="+InsertResponse.getIdExamen()+"))valor \n" +
                    "from respuestaestudiante r2 where idestudiante ="+InsertResponse.getIdEstudiante()+" and idexamen ="+InsertResponse.getIdExamen()+" and estadoresp = true)) p;");


            namedParameterJdbcTemplateOne.query(query, rs -> {

                if (rs!=null) {
                    response.setResult(rs.getInt("valor"));
                }

            });
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return response;
    }

    public void  InsertExamen(respuestaEstudiante InsertResponse,resExamenOut response) throws SQLException {

        String query =("INSERT INTO public.notafinal\n" +
                        "(idestudiante,idexamen,nota)\n" +
                        "VALUES("+InsertResponse.getIdEstudiante()+","+InsertResponse.getIdExamen()+",'"+response.getResult()+"');");

        System.out.println(query);
        System.out.println(namedParameterJdbcTemplateOne);

        MapSqlParameterSource params = new MapSqlParameterSource();

        namedParameterJdbcTemplateOne.update(query,params);

    }

}
