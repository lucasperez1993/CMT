/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author djaime
 */
public class Sql {
    public static List<Map<String, Object>> getMapQueryResultByPreparedStatement(String sql, ArrayList<Object> listaDeFiltros, Connection con) throws SQLException{
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        if(listaDeFiltros.size() > 0){
            int indice = 1;
            for (Object object : listaDeFiltros) {
            preparedStatement.setObject(indice, object);
            indice ++;
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map<String, Object>> filas = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cantidadColumnas = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> columnas = new LinkedHashMap<>();
            for (int index = 1; index <= cantidadColumnas; index++) {
                try{
                    columnas.put(metaData.getColumnLabel(index), resultSet.getObject(index));
                }catch(Exception e){
                    columnas.put(metaData.getColumnLabel(index), "Error");
                }
            }
            filas.add(columnas);
        }
        return filas;
    }
}
