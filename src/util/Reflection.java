package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Reflection {
  public static final int INSERT = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  public static int ERROR_SQL = 0;
  
  public static String INSERT_INTO = "insert into ";
  public static String INSERT_VALUES_NULL_AI = " values (";
  public static String INSERT_VALUES = " values (";
  public static String UPDATE_VALUES = "update ";
  public static String UPDATE_SET = " set ";
  public static String STMT_PARAMETER = "?)";
  public static String STMT_FINAL = "?,";
  public static String STMT_IGUAL_PARAMETER = " =?";
  public static String STMT_IGUAL_FINAL = " =?,";
  public static String SELECT_FROM = "select * from ";
  public static String SELECT = "select ";
  public static String WHERE = " where ";
  
  public static String DATE_FORMAT = "_df";
  public static String DATE_EXTJS = "_js";

  public static ArrayList<Object> getReflectionListaObjeto(String clase,String condicion ,Connection con) throws SQLException, IllegalArgumentException, 
   IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
       ArrayList arrayObjeto = new ArrayList();
       try {
           Object objeto;
           Class userClass = Class.forName(clase);
           Field[] nombres = userClass.getFields();
           ResultSet resultado = getResultSet(userClass.getSimpleName(), condicion, con);
           while(resultado.next()){
               objeto = userClass.newInstance();
               for (Field nombreTipo : nombres) {
                   if (nombreTipo.getType().equals(String.class)) {
                       nombreTipo.set(objeto, resultado.getString(nombreTipo.getName()));
                   } else 
                       if (nombreTipo.getType().equals(long.class)) {
                       nombreTipo.set(objeto, resultado.getLong(nombreTipo.getName()));
                    }else
                       if (nombreTipo.getType().equals(int.class)) {
                       nombreTipo.set(objeto, resultado.getInt(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(float.class)) {
                       nombreTipo.set(objeto, resultado.getFloat(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(Timestamp.class)) {
                           try {
                               nombreTipo.set(objeto, resultado.getTimestamp(nombreTipo.getName()));
                           } catch (Exception e) {}
                   }
               }
               arrayObjeto.add(objeto);
           }
            return arrayObjeto;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

   }
   public static JSONArray getAllReflectionListaObjetoJSON(String filtro, String clase, String dateFormat, Connection con) throws SQLException, IllegalArgumentException, 
   IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException,
    Exception{
       JSONArray  arrayJson = new JSONArray();
       JSONObject jsonObject;
       ResultSet resultado = null;
       try {
           Class userClass = Class.forName(clase);
           Field[] nombres = userClass.getFields();
           resultado = getResultSet(userClass.getSimpleName(), filtro, con);
           while(resultado.next()){
               jsonObject = new JSONObject();
               for (Field nombreTipo : nombres) {
                   if (nombreTipo.getType().equals(String.class)) {
                       jsonObject.put(nombreTipo.getName(), resultado.getString(nombreTipo.getName().trim()));
                   } else if (nombreTipo.getType().equals(int.class)) {
                      jsonObject.put(nombreTipo.getName(), resultado.getString(nombreTipo.getName()));
                   }else if (nombreTipo.getType().equals(long.class)) {
                      jsonObject.put(nombreTipo.getName(), resultado.getLong(nombreTipo.getName()));
                   } 
                   else if (nombreTipo.getType().equals(float.class)) {
                      jsonObject.put(nombreTipo.getName(), resultado.getString(nombreTipo.getName()));
                   }else
                       if (nombreTipo.getType().equals(Timestamp.class)) {
                           //jsonObject.put(nombreTipo.getName()+DATE_FORMAT, calculos.CalculosFecha.getDateWithFormat(resultado.getTimestamp(nombreTipo.getName()), dateFormat));
                           //jsonObject.put(nombreTipo.getName()+DATE_EXTJS, calculos.CalculosFecha.convertDateToJSON(resultado.getTimestamp(nombreTipo.getName())));
                       }
               }
               arrayJson.put(jsonObject);
           }
           return arrayJson;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

   }

   public static int createReflection(String clase, Object objeto, Connection con) throws ClassNotFoundException,
       InstantiationException, IllegalAccessException{
       Class userClass = Class.forName(clase);
       Field[] nombres = userClass.getFields();
       return sqlPstmtCreate(userClass.getSimpleName(), nombres, objeto, true, con);
   }
   
   public static Object getObjectByJSONOBJECT(String clase, Object objeto, JSONObject data) throws ClassNotFoundException,
       InstantiationException, IllegalAccessException{
       Class userClass = Class.forName(clase);
       Field[] nombres = userClass.getFields();
       for (Field atributos : nombres) {
           if (atributos.getType().equals(String.class)) {
               try{
                    atributos.set(objeto, data.get(atributos.getName()));
                }catch(Exception e){atributos.set(objeto, "");
                }
            } else
                if (atributos.getType().equals(int.class)) {
                      try{
                          atributos.set(objeto, data.getInt(atributos.getName()));
                      }catch(Exception e){atributos.set(objeto, 0);
                      }
                    } else
                    if (atributos.getType().equals(long.class)) {
                      try{
                          atributos.set(objeto, data.getLong(atributos.getName()));
                      }catch(Exception e){atributos.set(objeto, 0);
                      }
                    }else
                        if (atributos.getType().equals(float.class)) {
                            try{
                                atributos.set(objeto, data.getDouble(atributos.getName()));
                                }catch(Exception e){atributos.set(objeto, 0);
                                }
                        } 
       }
       return objeto;//sqlPstmt(userClass.getSimpleName(), nombres, objeto, true, con);
   }

   public static void storeReflection(String clase, Object objeto, Connection con) throws ClassNotFoundException,
       InstantiationException, IllegalAccessException{
       Class userClass = Class.forName(clase);
       Field[] nombres = userClass.getFields();
       update_Reflection(userClass.getSimpleName(), nombres, objeto, con);
   }

   public static String getNombreClase(String clase){
       try {
           Class userClass = Class.forName(clase);
           //return userClass.getSimpleName();
           return userClass.getName();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   public static String getSimpleNombreClase(String clase){
       try {
           Class userClass = Class.forName(clase);
           return userClass.getSimpleName();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }

   private static int sqlPstmtCreate (String tabla, Field[] camposTypos, Object objeto, boolean clavePrimaria, Connection con){
        try {
            PreparedStatement preparedStatement = null;
            boolean clavePrimariaCampo = clavePrimaria;
            ResultSet key = null;
            String sqlStmt = "insert into " +tabla+ "(";
            String signos = "values(";
            int indiceValues = 0;
            int indice = 0;
            int campoLength = camposTypos.length -1;
            for (Field nombreTipo : camposTypos) {
                if(!clavePrimariaCampo){
                    if(indiceValues++ != campoLength){
                        sqlStmt += nombreTipo.getName() + ",";
                        signos += "?,";
                    }else{
                        sqlStmt += nombreTipo.getName() + ") ";
                        signos += "?)";
                    }
                }else{
                    campoLength = campoLength - 1;
                    clavePrimariaCampo = false;
                }
            }
            
            sqlStmt += signos;
            clavePrimariaCampo = clavePrimaria;
            preparedStatement = con.prepareStatement(sqlStmt, PreparedStatement.RETURN_GENERATED_KEYS);            
            for (Field nombreTipo : camposTypos) {
                if(!clavePrimariaCampo){
                    indice++;
                if (nombreTipo.getType().equals(String.class)) {
                        preparedStatement.setString(indice, (String) nombreTipo.get(objeto));
                    } else
                        if (nombreTipo.getType().equals(int.class)) {
                            preparedStatement.setInt(indice, (Integer) nombreTipo.get(objeto));
                        } else
                        if (nombreTipo.getType().equals(long.class)) {
                            preparedStatement.setLong(indice, (Long) nombreTipo.get(objeto));
                        } else
                            if (nombreTipo.getType().equals(float.class)) {
                                preparedStatement.setFloat(indice, (Float) nombreTipo.get(objeto));
                            } else
                                if (nombreTipo.getType().equals(Blob.class)) {
                                    preparedStatement.setBlob(indice, (Blob) nombreTipo.get(objeto));
                                } else
                                    if (nombreTipo.getType().equals(Date.class)) {
                                        Date fecha = (Date) nombreTipo.get(objeto);
                                        java.sql.Date sqlStartDate = new java.sql.Date(fecha.getTime());
                                        preparedStatement.setDate(indice, sqlStartDate);
                                    } else
                                        if (nombreTipo.getType().equals(Timestamp.class)) {
                                            preparedStatement.setTimestamp(indice, (Timestamp) nombreTipo.get(objeto));
                                        }
                }else{
                    clavePrimariaCampo = false;
                }
            }
            preparedStatement.execute();
            if(clavePrimaria){
                key = preparedStatement.getGeneratedKeys();
                if (key.next()) {
                    return key.getInt(1);
                 }
            }
        } 
        catch (Exception ex) {
            String error = ex.getMessage();
        }
        return 0;
    }
   
    private static int sqlPstmt (String tabla, Field[] camposTypos, Object objeto, boolean clavePrimaria, Connection con){
        try {
            PreparedStatement preparedStatement = null;
            String sqlStmt = "";
            int index = 1;  
            int indexPstmp = 0;
            int indexKey = 0;
            ResultSet key = null;
            if(clavePrimaria){
                indexKey = 1;
                sqlStmt = INSERT_INTO+tabla+INSERT_VALUES_NULL_AI;
            }else{
                sqlStmt = INSERT_INTO+tabla+INSERT_VALUES;
            }
            int cantidadCamposPstmt = camposTypos.length;
            for (int x = indexKey; x < cantidadCamposPstmt; x++) {
                if(x == (cantidadCamposPstmt - 1)){
                       sqlStmt += STMT_PARAMETER;
                   }else{
                       sqlStmt += STMT_FINAL;
                   }
            }
            
            if(clavePrimaria){
                preparedStatement = con.prepareStatement(sqlStmt, PreparedStatement.RETURN_GENERATED_KEYS); 
            }else{
                preparedStatement = con.prepareStatement(sqlStmt); 
            }
            
            for (Field nombreTipo : camposTypos) {
                if(index != 1){
                    if (nombreTipo.getType().equals(String.class)) {
                        try{
                            String value = "";
                            if(nombreTipo.get(objeto) == null){
                                value = "";
                            }else{
                                value= (String) nombreTipo.get(objeto);
                            }
                            preparedStatement.setString(indexPstmp, value);
                        }catch(Exception e){
                            preparedStatement.setString(indexPstmp, "");
                        }
                    } else
                        if (nombreTipo.getType().equals(int.class)) {
                            try{
                            preparedStatement.setInt(indexPstmp, (Integer) nombreTipo.get(objeto));
                            }catch(Exception e){
                                preparedStatement.setInt(indexPstmp, 0);
                            }
                            } else
                                if (nombreTipo.getType().equals(long.class)) {
                                try{
                                preparedStatement.setLong(indexPstmp, (Long) nombreTipo.get(objeto));
                                }catch(Exception e){
                                    preparedStatement.setInt(indexPstmp, 0);
                                }
                            }else
                            if (nombreTipo.getType().equals(float.class)) {
                                try{
                                    preparedStatement.setFloat(indexPstmp, (Float) nombreTipo.get(objeto));
                                }catch(Exception e){
                                    preparedStatement.setFloat(indexPstmp, 0);
                                }
                            } else
                                if (nombreTipo.getType().equals(Blob.class)) {
                                    try{
                                        preparedStatement.setBlob(indexPstmp, (Blob) nombreTipo.get(objeto));
                                    }catch(Exception e){
                                        preparedStatement.setBlob(indexPstmp, getImagenPorDefecto(con));
                                    }
                                }else
                                    if (nombreTipo.getType().equals(Timestamp.class)) {
                                        try{
                                            Timestamp value = null;
                                            if(nombreTipo.get(objeto) == null){
                                                value = getFechaHoy();
                                            }else{
                                                value= (Timestamp) nombreTipo.get(objeto);
                                            }
                                            preparedStatement.setTimestamp(indexPstmp, value);
                                        }catch(Exception e){
                                            preparedStatement.setTimestamp(indexPstmp, getFechaHoy());
                                        }
                                }
                }else{
                    if (!clavePrimaria) {
                        if (nombreTipo.getType().equals(String.class)) {
                        try{
                            String value = "";
                            if(nombreTipo.get(objeto) == null){
                                value = "";
                            }else{
                                value= (String) nombreTipo.get(objeto);
                            }
                            preparedStatement.setString(indexPstmp, value);
                        }catch(Exception e){
                            preparedStatement.setString(indexPstmp, "");
                        }
                    } else
                        if (nombreTipo.getType().equals(int.class)) {
                            try{
                            preparedStatement.setInt(indexPstmp, (Integer) nombreTipo.get(objeto));
                            }catch(Exception e){
                                preparedStatement.setInt(indexPstmp, 0);
                            }
                        }else
                        if (nombreTipo.getType().equals(long.class)) {
                            try{
                            preparedStatement.setLong(indexPstmp, (Long) nombreTipo.get(objeto));
                            }catch(Exception e){
                                preparedStatement.setInt(indexPstmp, 0);
                            }
                        }else
                            if (nombreTipo.getType().equals(float.class)) {
                                try{
                                    preparedStatement.setFloat(indexPstmp, (Float) nombreTipo.get(objeto));
                                }catch(Exception e){
                                    preparedStatement.setFloat(indexPstmp, 0);
                                }
                            } else
                                if (nombreTipo.getType().equals(Blob.class)) {
                                    try{
                                        preparedStatement.setBlob(indexPstmp, (Blob) nombreTipo.get(objeto));
                                    }catch(Exception e){
                                        preparedStatement.setBlob(indexPstmp, getImagenPorDefecto(con));
                                    }
                                 } else
                                    if (nombreTipo.getType().equals(Timestamp.class)) {
                                        try{
                                            Timestamp value = null;
                                            if(nombreTipo.get(objeto) == null){
                                                value = getFechaHoy();
                                            }else{
                                                value= (Timestamp) nombreTipo.get(objeto);
                                            }
                                            preparedStatement.setTimestamp(indexPstmp, value);
                                        }catch(Exception e){
                                            preparedStatement.setTimestamp(indexPstmp, getFechaHoy());
                                        }
                                    }
                        indexPstmp++;
                    }
                }
                index++;
                indexPstmp++;
            }
            preparedStatement.execute();
            if(clavePrimaria){
                key = preparedStatement.getGeneratedKeys();
                if (key.next()) {
                    return key.getInt(1);
                 }
            }
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return sqlPstmt(tabla, camposTypos, objeto, false, con);
         }
        return 0;
    }

    private static void update_Reflection(String tabla, Field[] camposTypos, Object objeto, Connection con){
         try {
            PreparedStatement preparedStatement = null;
            String key = "";
            int idPrimaria = -1;
            int indexPstmp = 0;
            String sqlStmt = "";
            int index = 1;  
            sqlStmt = UPDATE_VALUES+tabla+UPDATE_SET;
            int cantidadCamposPstmt = camposTypos.length;
            int x = 0;
            for (Field nombreTipo : camposTypos) {
                if(index != 1){
                    if(x == (cantidadCamposPstmt - 1)){
                        sqlStmt += nombreTipo.getName()+STMT_IGUAL_PARAMETER;
                    }else{
                        sqlStmt += nombreTipo.getName()+STMT_IGUAL_FINAL;
                    }
                }else{
                    key = nombreTipo.getName().toString();
                }
                index++;
                x++;
            }
            sqlStmt += WHERE+key+STMT_IGUAL_PARAMETER;
            index = 1;
            preparedStatement = con.prepareStatement(sqlStmt); 
            for (Field nombreTipo : camposTypos) {
                if(index != 1){
                    if (nombreTipo.getType().equals(String.class)) {
                        preparedStatement.setString(indexPstmp, (String) nombreTipo.get(objeto));
                    } else
                        if (nombreTipo.getType().equals(int.class)) {
                            preparedStatement.setInt(indexPstmp, (Integer) nombreTipo.get(objeto));
                        } else
                            if (nombreTipo.getType().equals(long.class)) {
                            preparedStatement.setLong(indexPstmp, (Long) nombreTipo.get(objeto));
                            }else
                                if (nombreTipo.getType().equals(float.class)) {
                                    preparedStatement.setFloat(indexPstmp, (Float) nombreTipo.get(objeto));
                                } else
                                    if (nombreTipo.getType().equals(Blob.class)) {
                                        preparedStatement.setBlob(indexPstmp, (Blob) nombreTipo.get(objeto));
                                    }else
                                        if (nombreTipo.getType().equals(Date.class)) {
                                            java.util.Date date = (Date) nombreTipo.get(objeto);
                                            java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
                                            preparedStatement.setDate(indexPstmp, sqlDate);
                                    } else
                                        if (nombreTipo.getType().equals(Timestamp.class)) {
                                            preparedStatement.setTimestamp(indexPstmp, (Timestamp) nombreTipo.get(objeto));
                                        }
                }else{
                    idPrimaria = (Integer) nombreTipo.get(objeto);
                }
                index++;
                indexPstmp++;
            }
            preparedStatement.setInt(indexPstmp, idPrimaria);
            preparedStatement.execute();
            con.commit();
        } 
        catch (Exception ex) {
            ex.getMessage();
         }
    }

    public static ResultSet getResultSet(String tabla, String condicion, Connection con) throws SQLException{
        return con.createStatement().executeQuery(SELECT_FROM+tabla+" "+condicion );
    }
    
  public static ArrayList<Class> getReflectionListaObjetoParametrizado(String clase,String anteCondicion,String condicion ,Connection con) throws SQLException, IllegalArgumentException, 
   IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
       ArrayList arrayObjeto = new ArrayList();
       try {
           Object objeto;
           Class userClass = Class.forName(clase);
           Field[] nombres = userClass.getFields();
           ResultSet resultado = getResultSetParametrizado(userClass.getSimpleName(), anteCondicion ,condicion, con);
           while(resultado.next()){
               objeto = userClass.newInstance();
               for (Field nombreTipo : nombres) {
                   if (nombreTipo.getType().equals(String.class)) {
                       nombreTipo.set(objeto, resultado.getString(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(int.class)) {
                       nombreTipo.set(objeto, resultado.getInt(nombreTipo.getName()));
                   }else if (nombreTipo.getType().equals(long.class)) {
                       nombreTipo.set(objeto, resultado.getLong(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(float.class)) {
                       nombreTipo.set(objeto, resultado.getFloat(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(Timestamp.class)) {
                       nombreTipo.set(objeto, resultado.getTimestamp(nombreTipo.getName()));
                   }
               }
               arrayObjeto.add(objeto);
           }
            return arrayObjeto;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

   }

    public static ResultSet getResultSetParametrizado(String tabla, String anteCondicion, String condicion, Connection con) throws SQLException{
        return con.createStatement().executeQuery(SELECT+anteCondicion+" "+tabla+" "+condicion );
    }
    public static void getResult(String sql, Connection con) throws SQLException{
        con.createStatement().execute(sql);
        con.commit();
    }

    public static float getResultValue(String sql,String columna, Connection con) throws SQLException{
        float retorno = 0.0f;
        ResultSet rs = con.createStatement().executeQuery(sql);
        if(rs.next()){
            retorno = rs.getFloat(columna);
        }
        return retorno;
    }
    
    public static void getResultExecute(String sql, Connection con) throws SQLException{
        con.createStatement().execute(sql);
    }
    
    public static Blob getImagenPorDefecto(Connection con) throws SQLException{
        ResultSet rs = con.createStatement().executeQuery("select imagen from persona where idpersona = 0");
        rs.first();
        return rs.getBlob("imagen");
    }
    
    public static Timestamp getFechaHoy(){
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return currentTimestamp;
    }
    
    public static ResultSet getResulSetByStatement(String sql, Connection con) throws SQLException{
        return con.createStatement().executeQuery(sql);
    }
    
    public static boolean executeStatement(String sql, Connection con) throws SQLException{
        return con.createStatement().execute(sql);
    }
    
    public static ResultSet getResulSetByPrepareStatement(String sql, ArrayList<Object> arrayList, Connection con) throws SQLException{
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        int indice = 1;
        for (Object object : arrayList) {
            if(object.equals(Timestamp.class)){
                preparedStatement.setBlob(indice, (Blob) object);
            }else
                if(object.equals(Blob.class)){
                    preparedStatement.setTimestamp(indice, (Timestamp) object);
                }else
                    if(object.equals(String.class)){
                        preparedStatement.setString(indice, object.toString());
                    }else
                        if(object.equals(int.class)){
                            preparedStatement.setInt(indice, (Integer) object);
                        }else
                            if(object.equals(long.class)){
                            preparedStatement.setLong(indice, (Long) object);
                        }else
                            if(object.equals(float.class)){
                                preparedStatement.setFloat(indice, (Float) object);
                            }
            indice ++;
        }
        return preparedStatement.executeQuery();
    }
    
    public static List<Map<String, Object>> getMapQueryResultByPreparedStatement(String sql, ArrayList<Object> listaDeFiltros, Connection con) throws SQLException{
        con.commit();
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        if(listaDeFiltros.size() > 0){
            int indice = 1;
            for (Object object : listaDeFiltros) {
            preparedStatement.setObject(indice, object);
            indice ++;
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map<String, Object>> filas = new ArrayList<Map<String, Object>>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cantidadColumnas = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> columnas = new LinkedHashMap<String, Object>();
            for (int index = 1; index <= cantidadColumnas; index++) {
                String tabla = "";
                if(metaData.getTableName(index).length() > 3)
                    tabla = metaData.getTableName(index).substring(0, 3);
                else
                    tabla = metaData.getTableName(index);
                try{
                    columnas.put(tabla+"."+metaData.getColumnLabel(index), resultSet.getObject(index));
                }catch(Exception e){
                    columnas.put(tabla+"."+metaData.getColumnLabel(index), "Error");
                }
            }
            filas.add(columnas);
        }
        return filas;
    }
    
    public static Object executeStorProcedure(String procedimiento, ArrayList arrayCampos, Connection connection) {
        try {
            String campos = "";
            for (int indice = 0; indice < arrayCampos.size(); indice++) {
                campos += "?";
                if(indice < (arrayCampos.size() - 1)){
                    campos += ",";
                }
            }
            int indicePstmt = 1;
            CallableStatement callableStatement = null;
            String sql = "{call "+procedimiento+"("+campos+",?)}";
            callableStatement = connection.prepareCall(sql);
            for (int indice = 0; indice < arrayCampos.size(); indice++) {
                callableStatement.setObject(indicePstmt, arrayCampos.get(indice));
                indicePstmt++;
            }
            callableStatement.registerOutParameter(indicePstmt, java.sql.Types.JAVA_OBJECT);
            callableStatement.executeUpdate();
            connection.commit();
            return callableStatement.getObject(indicePstmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static JSONArray getReflectionListaObjetoJSONPorId(String clase, int id, String filtro, String dateFormat, Connection connection) throws JSONException {
       JSONArray  arrayJson = new JSONArray();
       JSONObject jsonObject;
       try {
           String CampoPrimario = null;
           int index = 1;
           Class userClass = Class.forName(clase);
           Field[] nombres = userClass.getFields();
           for (Field nombreTipo : nombres) {
               if(index == 1){
                   CampoPrimario = nombreTipo.getName();
                   break;
               }
               index++;
           }
           ResultSet resultado = null;
           try {
               resultado = getResultSet(userClass.getSimpleName(), WHERE+CampoPrimario+" = "+id+" "+filtro, connection);
           } catch (SQLException ex) {
               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
           }
           try {
               while(resultado.next()){
                   jsonObject = new JSONObject();
                   for (Field nombreTipo : nombres) {
                       if (nombreTipo.getType().equals(String.class)) {
                           try {
                               jsonObject.put(nombreTipo.getName(), resultado.getString(nombreTipo.getName()));
                           } catch (JSONException ex) {
                               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
                           }
                       } else if (nombreTipo.getType().equals(int.class)) {
                           try {
                               jsonObject.put(nombreTipo.getName(), resultado.getString(nombreTipo.getName()));
                           } catch (JSONException ex) {
                               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
                           }
                       } else if (nombreTipo.getType().equals(float.class)) {
                           try {
                               jsonObject.put(nombreTipo.getName(), resultado.getString(nombreTipo.getName()));
                           } catch (JSONException ex) {
                               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
                           }
                       }else
                       if (nombreTipo.getType().equals(Timestamp.class)) {
                           //jsonObject.put(nombreTipo.getName()+DATE_FORMAT, calculos.CalculosFecha.getDateWithFormat(resultado.getTimestamp(nombreTipo.getName()), dateFormat));
                           try {
                               //jsonObject.put(nombreTipo.getName()+DATE_EXTJS, calculos.CalculosFecha.convertDateToJSON(resultado.getTimestamp(nombreTipo.getName())));
                           } catch (Exception ex) {
                               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
                           }
                       }
                   }
                   arrayJson.put(jsonObject);
               }
           } catch (SQLException ex) {
               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
           }
           return arrayJson;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       
   }
   
   public static Object getObjetoPorId(
           String clase,
           int id,
           String filtro,
           Connection connection
   ) throws Exception {
       Object objeto;
       try {
           String CampoPrimario = null;
           int index = 1;
           Class userClass = Class.forName(clase);
           Field[] nombres = userClass.getFields();
           for (Field nombreTipo : nombres) {
               if(index == 1){
                   CampoPrimario = nombreTipo.getName();
                   break;
               }
               index++;
           }
           ResultSet resultado = null;
           connection.commit();
           try {
               resultado = getResultSet(userClass.getSimpleName(), WHERE+CampoPrimario+" = "+id+" "+filtro, connection);
           } catch (Exception ex) {
               Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
           }
           objeto = userClass.newInstance();
           while(resultado.next()){
               for (Field nombreTipo : nombres) {
                   if (nombreTipo.getType().equals(String.class)) {
                       nombreTipo.set(objeto, resultado.getString(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(long.class)) {
                       nombreTipo.set(objeto, resultado.getLong(nombreTipo.getName()));
                   }else if (nombreTipo.getType().equals(int.class)) {
                       nombreTipo.set(objeto, resultado.getInt(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(float.class)) {
                       nombreTipo.set(objeto, resultado.getFloat(nombreTipo.getName()));
                   } else if (nombreTipo.getType().equals(Timestamp.class)) {
                       nombreTipo.set(objeto, resultado.getTimestamp(nombreTipo.getName()));
                   }
               }
           }
           return objeto;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   }
   
   public static Object getObjectByJSONOBJECT(String clase, Object objeto, String prefijo, JSONObject formJson) 
           throws ClassNotFoundException,
           InstantiationException,
           IllegalAccessException
   {
       JSONObject jsondata = new JSONObject();
       Iterator<?> keys = formJson.keys();
        while(keys.hasNext() ){
            String key = (String) keys.next();
            try {
                String campo = key.replace(prefijo, "");
                jsondata.put(campo, formJson.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getObjectByJSONOBJECT(clase, objeto, jsondata);
   }
}