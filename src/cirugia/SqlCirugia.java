/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cirugia;

import Conexion.Conexion;
import static cirugia.ControladorCirugia.CIRUJANO;
import static cirugia.ControladorCirugia.INTERCONSULTA;
import static cirugia.ControladorCirugia.MIO;
import static cirugia.ControladorCirugia.VIDEO_ENDOSCOPIA;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import otro.ConstanteObjeto;
import util.CalculosFecha;
import util.DigitoVerificador;
import util.Reflection;
import util.Sql;

/**
 *
 * @author djaime
 */
public class SqlCirugia {

    public static List<Map<String, Object>> getCirugiaCMTPorCodmeOrderByFechaVto(boolean isCloseConnection, boolean esMatricula, int matricula, int idorganizacion, Connection connection) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> lista = null;
        String contieneCodme = "p.matric = ? AND";
        try {
            String query = "SELECT CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto, c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    + "LEFT JOIN prestadores p ON c.codme = p.codme "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE " + (esMatricula ? contieneCodme : "") + " c.idorganizacion = ? AND c.estado IN(?, ?, ?) ORDER BY c.estado, c.fechavto DESC";
            ArrayList<Object> listaParametro = new ArrayList<>();
            if (esMatricula) {
                listaParametro.add(matricula);
            }
            listaParametro.add(idorganizacion);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_PENDIENTE);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_POR_VENCER);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_URGENTE);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                lista.forEach(l -> {
                    Map<String, Object> map = new HashMap<>();
//                    ArrayList<Object> listaPracticas = new ArrayList<>();
                    JSONArray lis = null;
                    try {
                        lis = new JSONArray(l.get("practicajson").toString());
                    } catch (Exception e) {
                    }
//                    for (int i = 0; i < lis.length(); i++) {
//                        try {
//                            JSONObject j = lis.getJSONObject(i);
//                            listaPracticas.add(j.get("practica"));
//                        } catch (Exception e) {
//                            listaPracticas.add(000000);
//                        }
//
//                    }
                    map.put("idcirugiapractica", l.get("idcirugiapractica"));
                    map.put("idusuario", l.get("idusuario"));
                    map.put("codme", l.get("codme"));
                    map.put("tipoobjeto", l.get("tipoobjeto"));
                    map.put("ois", l.get("ois"));
                    map.put("matricsol", l.get("matricsol"));
                    map.put("nombre", l.get("nombre"));
                    map.put("afiliado", l.get("afiliado"));
                    map.put("nombreafiliado", l.get("nombreafiliado"));
                    map.put("sanatorio", l.get("sanatorio"));
                    map.put("fechasolicitud", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("fechaSoli").toString()));
                    map.put("fechavto", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("vto").toString()));
                    map.put("nota", l.get("nota"));
                    //map.put("checkestado", getEstadoDocumentos(Long.valueOf(l.get("idcirugiapractica").toString()), idorganizacion));
                    map.put("practicajson", lis.toString());
                    map.put("hora", DigitoVerificador.getCompletarDigito(l.get("hora").toString(), 2));
                    map.put("minuto", DigitoVerificador.getCompletarDigito(l.get("minuto").toString(), 2));
                    map.put("estado", l.get("descripcion"));
                    map.put("valorestado", l.get("valorestado"));
                    list.add(map);
                });
            }
        } catch (Exception e) {
            //throw new JDBException(null, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
                //throw new JDBException(null, "Error: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
            }
        }
        return list;
    }

    public static JSONObject getMedicoPorMatricula(int matricula, Connection connection) {
        JSONObject json = null;
        try {
            List<Map<String, Object>> lista = null;
            json = new JSONObject().put("codme", 0).put("nombre", "").put("result", false);
            String sql1 = "SELECT codme, nombre FROM prestadores WHERE matric = ? AND tsocio in (1,2,3,4,5,6,9)";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(matricula);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql1, listaParametro, connection);
            if (lista.size() > 0) {
                try {
                    int codme = Integer.valueOf(lista.get(0).get(".codme").toString());
                    String nombre = lista.get(0).get(".nombre").toString();
                    json.put("matricula", matricula).put("nombre", nombre).put("result", true).put("codme", codme);
                } catch (Exception ex2) {
                }
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return json;
    }

    public static JSONObject getMedicoPorCodme(int codme, Connection connection) {
        JSONObject json = null;
        try {
            List<Map<String, Object>> lista = null;
            json = new JSONObject().put("codme", 0).put("nombre", "").put("matricula", 0).put("result", false);
            String sql1 = "SELECT matric, nombre FROM prestadores WHERE codme = ? AND tsocio in (1,2,3,4,5,6,9)";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(codme);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql1, listaParametro, connection);
            if (lista.size() > 0) {
                try {
                    int matricula = Integer.valueOf(lista.get(0).get(".matric").toString());
                    String nombre = lista.get(0).get(".nombre").toString();
                    json.put("matricula", matricula).put("nombre", nombre).put("result", true).put("codme", codme);
                } catch (Exception ex2) {
                }
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return json;
    }

    public static List<Map<String, Object>> getCirugiaAfiliadoPorCuiL(boolean isCloseConnection, String afiliado, int idorganizacion, Connection connection) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> lista = null;
        try {
            String query = "SELECT CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto, c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    + "LEFT JOIN prestadores p ON c.codme = p.codme "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE c.afiliado = ? AND c.idorganizacion = ? AND c.estado IN(?, ?, ?) ORDER BY c.estado, c.fechavto DESC";
            ArrayList<Object> listaParametro = new ArrayList<>();
            listaParametro.add(afiliado);
            listaParametro.add(idorganizacion);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_PENDIENTE);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_POR_VENCER);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_URGENTE);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                lista.forEach(l -> {
                    Map<String, Object> map = new HashMap<>();
//                    ArrayList<Object> listaPracticas = new ArrayList<>();
                    JSONArray lis = null;
                    JSONArray lisAyudante = null;
                    try {
                        lis = new JSONArray(l.get("practicajson").toString());
                    } catch (Exception e) {
                        lis = new JSONArray();
                    }
                    try {
                        lisAyudante = new JSONArray(l.get("ayudantejson").toString());
                    } catch (Exception e) {
                        lisAyudante = new JSONArray();
                    }
//                    for (int i = 0; i < lis.length(); i++) {
//                        try {
//                            JSONObject j = lis.getJSONObject(i);
//                            listaPracticas.add(j.get("practica"));
//                        } catch (Exception e) {
//                            listaPracticas.add(000000);
//                        }
//
//                    }
                    map.put("idcirugiapractica", l.get("idcirugiapractica"));
                    map.put("idusuario", l.get("idusuario"));
                    map.put("codme", l.get("codme") != null ? l.get("codme") : 0);
                    map.put("tipoobjeto", l.get("tipoobjeto") != null ? l.get("tipoobjeto") : 0);
                    map.put("ois", l.get("ois") != null ? l.get("ois") : 0);
                    map.put("matricsol", l.get("matricsol") != null ? l.get("matricsol") : 0);
                    map.put("nombre", l.get("nombre"));
                    map.put("afiliado", l.get("afiliado"));
                    map.put("nombreafiliado", l.get("nombreafiliado"));
                    map.put("idsanatorio", l.get("idsanatorio") != null ? l.get("idsanatorio") : 0);
                    map.put("sanatorio", l.get("sanatorio") != null ? l.get("sanatorio") : 0);
                    map.put("periodo", l.get("periodo") != null ? l.get("periodo") : 0);
                    map.put("fecha", l.get("fecha").toString());
                    map.put("fechasolicitud", l.get("fechaSoli"));
                    map.put("fechasolicitudDMA", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("fechaSoli").toString()));
                    map.put("fechavto", l.get("vto"));
                    map.put("fechavtoDMA", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("vto").toString()));
                    map.put("nota", l.get("nota") != null ? l.get("nota") : "");
                    map.put("checkestado", l.get("idcirugiapractica").toString());
                    //map.put("checkestado", getEstadoDocumentos(Long.valueOf(l.get("idcirugiapractica").toString()), idorganizacion));
                    map.put("practicajson", lis.toString());
                    map.put("ayudantejson", lisAyudante);
                    map.put("hora", l.get("hora") != null ? DigitoVerificador.getCompletarDigito(l.get("hora").toString(), 2) : "00");
                    map.put("minuto", l.get("minuto") != null ? DigitoVerificador.getCompletarDigito(l.get("minuto").toString(), 2) : "00");
                    map.put("idorganizacion", l.get("idorganizacion") != null ? l.get("idorganizacion") : 0);
                    map.put("estado", l.get("descripcion") != null ? l.get("descripcion") : "");
                    map.put("idestado", l.get("estado") != null ? l.get("estado") : "");
                    map.put("valorestado", l.get("valorestado"));
                    list.add(map);
                });
            }
        } catch (Exception e) {
            //throw new JDBException(null, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
                //throw new JDBException(null, "Error: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
            }
        }
        return list;
    }

    public static List<Map<String, Object>> getCirugiaAfiliadoGral(boolean isCloseConnection, int idusuario, int tipoUsuario, int idorganizacion, Connection connection) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> lista = null;
        String _periodo;
        String filtro = "";
        if (tipoUsuario != ConstanteObjeto.OBJETO_ADMINISTRADOR) {
            filtro = "c.idusuario = ? AND ";
        }
        String anio = LocalDate.now().getYear() + "";
        String mes = DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
        _periodo = anio + mes;
        int periodo = Integer.valueOf(_periodo);
        try {
            String query = "SELECT CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto, c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    //+ "LEFT JOIN prestadores p ON c.codme = p.codme "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE " + filtro + " c.periodo = ? AND c.idorganizacion = ? AND c.estado IN(?, ?, ?) "
                    + "ORDER BY c.estado, c.fechavto DESC";
            ArrayList<Object> listaParametro = new ArrayList<>();
            if (tipoUsuario != ConstanteObjeto.OBJETO_ADMINISTRADOR) {
                listaParametro.add(idusuario);
            }
            listaParametro.add(periodo);
            listaParametro.add(idorganizacion);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_PENDIENTE);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_POR_VENCER);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_URGENTE);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                lista.forEach(l -> {
                    Map<String, Object> map = new HashMap<>();
//                    ArrayList<Object> listaPracticas = new ArrayList<>();
                    JSONArray lis = null;
                    JSONArray lisAyudante = null;
                    try {
                        lis = new JSONArray(l.get("practicajson").toString());
                    } catch (Exception e) {
                        lis = new JSONArray();
                    }
                    try {
                        lisAyudante = new JSONArray(l.get("ayudantejson").toString());
                    } catch (Exception e) {
                        lisAyudante = new JSONArray();
                    }
//                    for (int i = 0; i < lis.length(); i++) {
//                        try {
//                            JSONObject j = lis.getJSONObject(i);
//                            listaPracticas.add(j.get("practica"));
//                        } catch (Exception e) {
//                            listaPracticas.add(000000);
//                        }
//
//                    }
                    map.put("idcirugiapractica", l.get("idcirugiapractica"));
                    map.put("idusuario", l.get("idusuario"));
                    map.put("codme", l.get("codme") != null ? l.get("codme") : 0);
                    map.put("tipoobjeto", l.get("tipoobjeto") != null ? l.get("tipoobjeto") : 0);
                    map.put("ois", l.get("ois") != null ? l.get("ois") : 0);
                    map.put("matricsol", l.get("matricsol") != null ? l.get("matricsol") : 0);
                    map.put("nombre", l.get("nombre"));
                    map.put("afiliado", l.get("afiliado"));
                    map.put("nombreafiliado", l.get("nombreafiliado"));
                    map.put("idsanatorio", l.get("idsanatorio") != null ? l.get("idsanatorio") : 0);
                    map.put("sanatorio", l.get("sanatorio") != null ? l.get("sanatorio") : 0);
                    map.put("periodo", l.get("periodo") != null ? l.get("periodo") : 0);
                    map.put("fecha", l.get("fecha").toString());
                    map.put("fechasolicitud", l.get("fechaSoli"));
                    map.put("fechasolicitudDMA", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("fechaSoli").toString()));
                    map.put("fechavto", l.get("vto"));
                    map.put("fechavtoDMA", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("vto").toString()));
                    map.put("nota", l.get("nota") != null ? l.get("nota") : "");
                    map.put("checkestado", l.get("idcirugiapractica").toString());
                    //map.put("checkestado", getEstadoDocumentos(Long.valueOf(l.get("idcirugiapractica").toString()), idorganizacion));
                    map.put("practicajson", lis.toString());
                    map.put("ayudantejson", lisAyudante);
                    map.put("hora", l.get("hora") != null ? DigitoVerificador.getCompletarDigito(l.get("hora").toString(), 2) : "00");
                    map.put("minuto", l.get("minuto") != null ? DigitoVerificador.getCompletarDigito(l.get("minuto").toString(), 2) : "00");
                    map.put("idorganizacion", l.get("idorganizacion") != null ? l.get("idorganizacion") : 0);
                    map.put("estado", l.get("descripcion") != null ? l.get("descripcion") : "");
                    map.put("idestado", l.get("estado") != null ? l.get("estado") : "");
                    map.put("valorestado", l.get("valorestado"));
                    list.add(map);
                });
            }
        } catch (Exception e) {
            //throw new JDBException(null, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
                //throw new JDBException(null, "Error: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
            }
        }
        return list;
    }

    public static List<Map<String, Object>> buscarCirugiaPorMatricula(boolean isCloseConnection, int matricula, int idorganizacion, Connection connection) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> lista = null;
        try {
            String query = "SELECT TOP 50 CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto,"
                    + " c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE  c.idorganizacion = ? "
                    + "ORDER BY c.estado, c.fechavto DESC";
            ArrayList<Object> listaParametro = new ArrayList<>();
            listaParametro.add(idorganizacion);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                lista.forEach(l -> {
                    try {
                        JSONArray lisAyudante = null;
                        try {
                            lisAyudante = new JSONArray(l.get("ayudantejson").toString());
                        } catch (Exception e) {
                            lisAyudante = new JSONArray();
                        }
                        int codme = 0;
                        int _matricula = 0;
                        try {
                            codme = Integer.valueOf(l.get("codme").toString());
                        } catch (Exception e) {
                        }
                        JSONObject medico = getMedicoPorCodme(codme, connection);
                        _matricula = medico.getInt("matricula");
                        if (codme > 0) {
                            if (matricula == _matricula) {
                                cargaCirugia(list, l, codme, matricula, connection);
                            }
                        }
                        for (int i = 0; i < lisAyudante.length(); i++) {
                            try {
                                JSONObject j = lisAyudante.getJSONObject(i);
                                if (j.getInt("matricula") == matricula) {
                                    cargaCirugia(list, l, j.getInt("codme"), j.getInt("matricula"), connection);
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(SqlCirugia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } catch (Exception e) {
        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public static List<Map<String, Object>> listaCirugiaParaReporte(boolean isCloseConnection, int idusuario, int tipoUsuario, int idorganizacion, Connection connection) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> lista = null;
        String _periodo;
        String filtro = "";
        if (tipoUsuario != ConstanteObjeto.OBJETO_ADMINISTRADOR) {
            filtro = "c.idusuario = ? AND ";
        }
        String anio = LocalDate.now().getYear() + "";
        String mes = DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
        _periodo = anio + mes;
        int periodo = Integer.valueOf(_periodo);
        try {
            String query = "SELECT CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto,"
                    + " c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    //+ "LEFT JOIN prestadores p ON c.codme = p.codme "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE " + filtro + " c.periodo = ? AND c.idorganizacion = ? "
                    + "ORDER BY c.estado, c.fechavto DESC";
            ArrayList<Object> listaParametro = new ArrayList<>();
            if (tipoUsuario != ConstanteObjeto.OBJETO_ADMINISTRADOR) {
                listaParametro.add(idusuario);
            }
            listaParametro.add(periodo);
            listaParametro.add(idorganizacion);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                lista.forEach(l -> {
                    try {
                        JSONArray lisAyudante = null;
                        try {
                            lisAyudante = new JSONArray(l.get("ayudantejson").toString());
                        } catch (Exception e) {
                            lisAyudante = new JSONArray();
                        }
                        int codme = 0;
                        int matricula = 0;
                        try {
                            codme = Integer.valueOf(l.get("codme").toString());
                        } catch (Exception e) {
                        }
                        JSONObject medico = getMedicoPorCodme(codme, connection);
                        matricula = medico.getInt("matricula");
                        if (codme > 0) {
                            cargaCirugia(list, l, codme, matricula, connection);
                        }
                        for (int i = 0; i < lisAyudante.length(); i++) {
                            JSONObject j = lisAyudante.getJSONObject(i);
                            cargaCirugia(list, l, j.getInt("codme"), j.getInt("matricula"), connection);
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(SqlCirugia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } catch (Exception e) {
        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public static List<Map<String, Object>> calcularListaCirugiaParaReporte(
            boolean isCloseConnection,
            boolean allUser,
            boolean allEstado,
            int idusuario,
            int estado,
            int idorganizacion,
            String desde,
            String hasta,
            Connection connection) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> lista = null;
        String _periodo;
        String filtroUsuaruio = "";
        String filtroEstado = "";
        if (!allUser) {
            filtroUsuaruio = "c.idusuario = ? AND ";
        }

        if (!allEstado) {
            filtroEstado = "c.estado = ? AND ";
        } else {
            filtroEstado = "c.estado IN(?,?,?,?,?,?,?,?) AND ";
        }

        String anio = LocalDate.now().getYear() + "";
        String mes = DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
        _periodo = anio + mes;
        int periodo = Integer.valueOf(_periodo);
        try {
            String query = "SELECT CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto,"
                    + " c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE " + filtroUsuaruio + " c.periodo = ? AND c.idorganizacion = ? AND " + filtroEstado
                    + "fecha BETWEEN ? AND ? ORDER BY c.estado, c.fechavto DESC";
            ArrayList<Object> listaParametro = new ArrayList<>();
            if (!allUser) {
                listaParametro.add(idusuario);
            }

            listaParametro.add(periodo);

            listaParametro.add(idorganizacion);

            if (!allEstado) {
                listaParametro.add(estado);
            } else {
                listaParametro.add(0);
                listaParametro.add(1);
                listaParametro.add(2);
                listaParametro.add(3);
                listaParametro.add(4);
                listaParametro.add(5);
                listaParametro.add(6);
                listaParametro.add(7);
            }
            listaParametro.add(desde);
            listaParametro.add(hasta);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                lista.forEach(l -> {
                    try {
                        JSONArray lisAyudante = null;
                        try {
                            lisAyudante = new JSONArray(l.get("ayudantejson").toString());
                        } catch (Exception e) {
                            lisAyudante = new JSONArray();
                        }
                        int codme = 0;
                        int matricula = 0;
                        try {
                            codme = Integer.valueOf(l.get("codme").toString());
                        } catch (Exception e) {
                        }
                        JSONObject medico = getMedicoPorCodme(codme, connection);
                        matricula = medico.getInt("matricula");
                        if (codme > 0) {
                            cargaCirugia(list, l, codme, matricula, connection);
                        }
                        for (int i = 0; i < lisAyudante.length(); i++) {
                            JSONObject j = lisAyudante.getJSONObject(i);
                            cargaCirugia(list, l, j.getInt("codme"), j.getInt("matricula"), connection);
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(SqlCirugia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } catch (Exception e) {
            e.getMessage();

        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public static void cargaCirugia(List<Map<String, Object>> list, Map<String, Object> l, int codme, int matricula, Connection connection) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Object> listaPracticas = new ArrayList<>();
        JSONArray lis = null;
        try {
            lis = new JSONArray(l.get("practicajson").toString());
        } catch (Exception e) {
            lis = new JSONArray();
        }
        for (int i = 0; i < lis.length(); i++) {
            try {
                JSONObject j = lis.getJSONObject(i);
                listaPracticas.add(j.get("practica"));
            } catch (Exception e) {
                listaPracticas.add(000000);
            }
        }
        map.put("idcirugiapractica", l.get("idcirugiapractica"));
        map.put("idusuario", l.get("idusuario"));
        map.put("autorizacion", l.get("autorizacion") != null ? l.get("autorizacion") : "0000000000000000");
        map.put("codme", codme);
        map.put("matricula", matricula);
        map.put("tipoobjeto", l.get("tipoobjeto") != null ? l.get("tipoobjeto") : 0);
        map.put("ois", l.get("ois") != null ? l.get("ois") : 0);
        map.put("matricsol", l.get("matricsol") != null ? l.get("matricsol") : 0);
        map.put("nombre", l.get("nombre"));
        map.put("afiliado", l.get("afiliado"));
        map.put("nombreafiliado", l.get("nombreafiliado"));
        map.put("idsanatorio", l.get("idsanatorio") != null ? l.get("idsanatorio") : 0);
        map.put("sanatorio", l.get("sanatorio") != null ? l.get("sanatorio") : 0);
        map.put("periodo", l.get("periodo") != null ? l.get("periodo") : 0);
        map.put("fecha", l.get("fecha").toString());
        map.put("fechasolicitud", l.get("fechaSoli"));
        map.put("fechasolicitudDMA", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("fechaSoli").toString()));
        map.put("fechavto", l.get("vto"));
        map.put("fechavtoDMA", CalculosFecha.transformarFechaConFormatoDiaMesAnio(l.get("vto").toString()));
        map.put("nota", l.get("nota") != null ? l.get("nota") : "");
        map.put("checkestado", l.get("idcirugiapractica").toString());
        map.put("practicajson", lis.toString());
        map.put("practica", listaPracticas.toString());
        map.put("hora", l.get("hora") != null ? DigitoVerificador.getCompletarDigito(l.get("hora").toString(), 2) : "00");
        map.put("minuto", l.get("minuto") != null ? DigitoVerificador.getCompletarDigito(l.get("minuto").toString(), 2) : "00");
        map.put("idorganizacion", l.get("idorganizacion") != null ? l.get("idorganizacion") : 0);
        map.put("estado", l.get("descripcion") != null ? l.get("descripcion") : "");
        map.put("idestado", l.get("estado") != null ? l.get("estado") : "");
        map.put("valorestado", l.get("valorestado"));
        list.add(map);
    }

    public static JSONObject getCantidadGral(boolean isCloseConnection, int idorganizacion, Connection connection) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("cirugia", 0)
                    .put("ayudantia", 0)
                    .put("video", 0)
                    .put("interconsulta", 0)
                    .put("mio", 0)
                    .put("suma", 0);

            int cirugia = 0;
            int ayudantia = 0;
            int video = 0;
            int interconsulta = 0;
            int mio = 0;
            int suma = 0;

            String _periodo;
            String anio = LocalDate.now().getYear() + "";
            String mes = DigitoVerificador.getCompletarDigito(LocalDate.now().getMonthValue() + "", 2);
            _periodo = anio + mes;
            int periodo = Integer.valueOf(_periodo);
            List<Map<String, Object>> lista = null;
            JSONArray lisAyudante = null;
            String query = "SELECT CAST(fechasolicitud AS DATE) AS fechaSoli, CAST(fechavto AS DATE) AS vto, c.estado as valorestado, * "
                    + "FROM jdb_cirugiapractica c "
                    + "LEFT JOIN prestadores p ON c.codme = p.codme "
                    + "LEFT JOIN cmt_estado e ON c.estado = e.estado "
                    + "LEFT JOIN cmt_sanatorio s ON s.idsanatorio = c.idsanatorio "
                    + "WHERE c.idorganizacion = ? AND c.periodo = ? AND c.estado != ?";
            ArrayList<Object> listaParametro = new ArrayList<>();
            listaParametro.add(idorganizacion);
            listaParametro.add(periodo);
            listaParametro.add(ConstanteObjeto.CIRUGIA_ESTADO_ANULADO);
            lista = Sql.getMapQueryResultByPreparedStatement(query, listaParametro, connection);
            if (lista.size() > 0) {
                for (int indice = 0; indice < lista.size(); indice++) {
                    int tipoobjeto = 0;
                    try {
                        lisAyudante = new JSONArray(lista.get(indice).get("ayudantejson").toString());
                    } catch (Exception e) {
                        lisAyudante = new JSONArray();
                    }
                    ayudantia = ayudantia + lisAyudante.length();
                    try {
                        tipoobjeto = Integer.valueOf(lista.get(indice).get("tipoobjeto").toString());
                    } catch (Exception e) {
                    }
                    switch (tipoobjeto) {
                        case CIRUJANO:
                            cirugia++;
                            break;
                        case VIDEO_ENDOSCOPIA:
                            video++;
                            break;
                        case INTERCONSULTA:
                            interconsulta++;
                            break;
                        case MIO:
                            mio++;
                            break;
                    }
                }
            }
            suma = cirugia + ayudantia + video + interconsulta + mio;
            json.put("cirugia", cirugia)
                    .put("ayudantia", ayudantia)
                    .put("video", video)
                    .put("interconsulta", interconsulta)
                    .put("mio", mio)
                    .put("suma", suma);
        } catch (Exception e) {
        } finally {
            try {
                if (isCloseConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return json;
    }

    public static JSONObject getAfiliadoPorCuil(String cuil, Connection connection) {
        JSONObject json = null;
        try {
            List<Map<String, Object>> lista = null;
            json = new JSONObject().put("afiliado", "").put("nombreafiliado", "").put("result", false);
            String sql1 = "SELECT afiliado, nombreafiliado FROM jdb_cirugiapractica WHERE afiliado = ?";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(cuil);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql1, listaParametro, connection);
            if (lista.size() > 0) {
                try {
                    String nombre = lista.get(0).get(".nombreafiliado").toString();
                    json.put("afiliado", cuil).put("nombreafiliado", nombre).put("result", true);
                } catch (Exception ex2) {
                }
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return json;
    }

    public static JSONArray getMedicoPorPractica(int periodo, int practica, Connection connection) {
        JSONArray listaJson = new JSONArray();
        try {
            String filtro = "IN(030305, 030531, 030524,030601,030705,030901,040102,040301,050101,050110,050206,050304,050401,050417,050418,050420,050421,050430, "
                    + "050435,050436,060101,060117,080101,080107,080506,080509,080514,080516,080515,080532,080534,080701,080732,080801,090114,100111,100117, "
                    + "100201,100204,100205,100215,100307,100401,100701,101104,101106,101107,110202,130205,130216,130217,130518)";
            List<Map<String, Object>> lista = null;
            String sql1 = "SELECT p.codme, p.matric, p.nombre, o.os_nombre, r.periodo FROM prestadores p "
                    + "INNER JOIN resumen r ON p.pre_id = r.pre_id "
                    + "INNER JOIN obrassociales o ON o.os_id = r.os_id "
                    + "WHERE r.periodo >= ? AND o.grupo IN(1, 3, 4) AND r.practica " + (practica > 0 ? " = ?" : filtro)
                    + " GROUP BY p.codme, p.nombre, p.matric, o.os_nombre, r.periodo";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(periodo);
            if (practica > 0) {
                listaParametro.add(practica);
            }
            lista = Reflection.getMapQueryResultByPreparedStatement(sql1, listaParametro, connection);
            if (lista.size() > 0) {
                for (int indice = 0; indice < lista.size(); indice++) {
                    JSONObject json = new JSONObject();
                    String codme = lista.get(indice).get(".codme").toString();
                    String matricula = lista.get(indice).get(".matric").toString();
                    String nombre = lista.get(indice).get(".nombre").toString();
                    String os_nombre = lista.get(indice).get(".os_nombre").toString();
                    String _periodo = lista.get(indice).get(".periodo").toString();
                    json
                            .put("codme", codme)
                            .put("matricula", matricula)
                            .put("nombre", nombre.trim())
                            .put("periodo", _periodo)
                            .put("os_nombre", os_nombre.trim());
                    listaJson.put(json);
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            Logger.getLogger(SqlCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaJson;
    }

    public static JSONArray getDocumentoPorIdcirugia(int idcirugiapractica, Connection connection) throws SQLException {
        JSONArray listaJson = new JSONArray();
        List<Map<String, Object>> lista = null;
        String sql1 = "SELECT iddocumento ,documento, path, descripcion FROM jdb_documento WHERE idcirugiapractica = ? AND estado = ?";
        ArrayList listaParametro = new ArrayList();
        listaParametro.add(idcirugiapractica);
        listaParametro.add(ConstanteObjeto.OBJETO_ACTIVO);
        lista = Reflection.getMapQueryResultByPreparedStatement(sql1, listaParametro, connection);
        if (lista.size() > 0) {
            for (int indice = 0; indice < lista.size(); indice++) {
                try {
                    JSONObject json = new JSONObject();
                    int iddocumento = Integer.valueOf(lista.get(indice).get(".iddocumento").toString());
                    String documento = lista.get(indice).get(".documento").toString().trim();
                    String path = lista.get(indice).get(".path").toString().trim();
                    String descripcion = lista.get(indice).get(".descripcion").toString().trim();
                    json.put("iddocumento", iddocumento);
                    json.put("documento", documento);
                    json.put("path", path);
                    json.put("descripcion", descripcion);
                    listaJson.put(json);
                } catch (JSONException ex) {
                    Logger.getLogger(SqlCirugia.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return listaJson;
    }

    public static JSONObject _getReglaOsNomenclador(String practica, Connection connection) throws JSONException {
        JSONObject json = new JSONObject().put("resultado", false);
        try {
            CallableStatement cst = connection.prepareCall("{call CMT_nombreCodigo(?,?)}");
            cst.setString(1, "177");
            cst.setString(2, practica);
            ResultSet resultado = cst.executeQuery();
            boolean capita = true;
            try {
                capita = Boolean.valueOf(resultado.getObject(3).toString());
            } catch (Exception e) {}
            while (resultado.next()) {
                json = new JSONObject()
                        .put("resultado", true)
                        .put("practica", practica)
                        .put("nombre", resultado.getObject(1).toString().trim())
                        .put("ayudante", resultado.getObject(2))
                        .put("valor", resultado.getObject(3))
                        .put("capita", !capita)
                        .put("nivel", resultado.getObject(4));
            }
        } catch (SQLException ex) {
        }
        return json;
    }
    
    public static JSONObject getReglaOsNomencladorV2(String practica, Connection connection) throws JSONException {
        JSONObject json = new JSONObject().put("resultado", false);
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT * FROM cmt_practicaoptativa WHERE codigo = ?";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(practica);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if (lista.size() > 0) {
                json = new JSONObject()
                        .put("resultado", true)
                        .put("practica", practica)
                        .put("nombre", lista.get(0).get(".nombre"))
                        .put("ayudante", lista.get(0).get(".cantayu"))
                        .put("valor", lista.get(0).get(".valor"))
                        .put("capita", lista.get(0).get(".capita"))
                        .put("nivel", lista.get(0).get(".nivel"));
            }
        } catch (SQLException ex) {
        }
                
        return json;
    }

    public static void updateTabla(){
        Conexion conection = new Conexion();
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT codigo,valor FROM cmt_practicaoptativa";
            ArrayList listaParametro = new ArrayList();
            //listaParametro.add(practica);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, conection.GetConnection5());
            if(lista.size() > 0){
                for (int indice = 0; indice < lista.size(); indice++) {
                    int codigo = Integer.valueOf(lista.get(indice).get(".codigo").toString());
                    JSONObject j = getNombrePractica(codigo, conection.GetConnection5());
                    if(j.getBoolean("resultado")){
                        boolean valor = Boolean.valueOf(lista.get(indice).get(".valor").toString());
                        String capita = "SI";
                        if(valor){
                            capita = "NO";
                        }
                        updateTablaOptativa(
                                codigo, 
                                j.getString("nombre"), 
                                j.getString("vigencia"), 
                                j.getString("hasta"), 
                                j.getInt("cantayu"), 
                                capita, 
                                j.getString("honorario"), 
                                j.getString("especialista"), 
                                conection.GetConnection5());
                    }
                }
            }
        } catch (SQLException ex) {
        } catch (JSONException ex) {
            Logger.getLogger(SqlCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static JSONObject getNombrePractica(int practica, Connection connection) throws JSONException {
        JSONObject json = new JSONObject().put("resultado", false);
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT * FROM nomenclador WHERE codigo = ? AND tipo = '177' AND hasta = 999999";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(practica);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if(lista.size() > 0){
                String nombre = lista.get(0).get(".nombre").toString();
                String vigencia = lista.get(0).get(".vigencia").toString();
                String hasta = lista.get(0).get(".hasta").toString();
                String especialista = lista.get(0).get(".especialista").toString();
                int cantayu = Integer.valueOf(lista.get(0).get(".cantayu").toString());
                String honorario = lista.get(0).get(".rhonorarios").toString();
                json
                        .put("nombre", nombre)
                        .put("vigencia", vigencia)
                        .put("hasta", hasta)
                        .put("especialista", especialista)
                        .put("cantayu", cantayu)
                        .put("honorario", honorario)
                        .put("resultado", true);
            }else{
                System.out.println("No encontrada: " + practica);
            }
        } catch (SQLException ex) {
        }
        return json;
    }
    public static JSONObject getIddocumento(int idcirugiapractica, int tipoobjeto, Connection connection) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("iddocumento", 0).put("documento", "");
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT iddocumento, documento FROM jdb_documento WHERE idcirugiapractica = ? AND tipoobjeto = ? AND estado = ?";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(idcirugiapractica);
            listaParametro.add(tipoobjeto);
            listaParametro.add(ConstanteObjeto.OBJETO_ACTIVO);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if (lista.size() > 0) {
                json
                        .put("iddocumento", Integer.valueOf(lista.get(0).get(".iddocumento").toString()))
                        .put("documento", Integer.valueOf(lista.get(0).get(".iddocumento").toString()));
            }
        } catch (SQLException ex) {
        }
        return json;
    }

    public static String seleccionarDesdeCombo(String tabla, String campo, String campo1, String filtro, Connection connection) throws Exception {
        String resultado = null;
        try {
            String sql = "select " + campo + " from " + tabla + " where " + campo1 + " = ?";
            List<Map<String, Object>> lista = null;
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(filtro);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if (lista.size() > 0) {
                resultado = lista.get(0).get("." + campo).toString();
            }
        } catch (Exception e) {
        }
        return resultado;
    }
//    public static  void main(String arg[]) throws JSONException{
//        Conexion conection = new Conexion();
//        System.out.println(getReglaOsNomenclador("117", "130101", conection.GetConnection5()).getJSONObject(0).toString());
//    }

    public static String getUsuarioPorId(int id, Connection connection) throws JSONException {
        String nombre = "NN";
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT * FROM cmt_usuarios_java WHERE id = ?";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(id);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if (lista.size() > 0) {
                nombre = lista.get(0).get(".nombre").toString();
            }
        } catch (SQLException ex) {
        }
        return nombre;
    }

    public static boolean existeCarga(String fecha, String fechavto, String cuil, Connection connection) throws JSONException {
        boolean existe = false;
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT * FROM jdb_cirugiapractica WHERE afiliado = ? AND fechasolicitud = ? AND fechavto = ?";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(cuil);
            listaParametro.add(fecha);
            listaParametro.add(fechavto);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            existe = lista.size() > 0;
        } catch (SQLException ex) {
        }
        return existe;
    }

    public static JSONObject getConfiguracion(int idusuario, Connection connection) throws JSONException {
        JSONObject configuracion = null;
        try {
            List<Map<String, Object>> lista = null;
            String sql = "SELECT * FROM cmt_configuracionmda WHERE idusuario = ? AND idorganizacion = ? AND estado = ?";
            ArrayList listaParametro = new ArrayList();
            listaParametro.add(idusuario);
            listaParametro.add(2);
            listaParametro.add(ConstanteObjeto.OBJETO_ACTIVO);
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if (lista.size() > 0) {
                configuracion = new JSONObject()
                        .put("idconfiguracionmda", lista.get(0).get(".idconfiguracionmda").toString())
                        .put("idusuario", lista.get(0).get(".idusuario").toString())
                        .put("configuracion", new JSONObject(lista.get(0).get("cmt.configuracion").toString()));
            } else {
                JSONObject config = new JSONObject().put("pathimg", "C:\\").put("pathpdf", "C:\\");
                configuracion = crearConfiguracion(idusuario, config, connection);
            }
        } catch (SQLException ex) {
        }
        return configuracion;
    }

    public static JSONObject crearConfiguracion(int idusuario, JSONObject configuracion, Connection connection) throws JSONException {
        JSONObject configuracionGral = null;
        try {
            ResultSet key = null;
            String sql = "INSERT INTO cmt_configuracionmda (idusuario, configuracion, idorganizacion, estado) "
                    + "values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, idusuario);
            preparedStatement.setObject(2, configuracion.toString());
            preparedStatement.setObject(3, 2);
            preparedStatement.setObject(4, ConstanteObjeto.OBJETO_ACTIVO);
            preparedStatement.execute();
            key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                configuracionGral = new JSONObject()
                        .put("idconfiguracionmda", key.getInt(1))
                        .put("idusuario", idusuario)
                        .put("configuracion", configuracion);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return configuracionGral;
    }

    public static void modificarConfiguracion(int idconfiguracionmda, JSONObject configuracion, Connection connection) throws JSONException {
        try {
            String sql = "UPDATE cmt_configuracionmda SET configuracion = ? WHERE idconfiguracionmda = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, configuracion.toString());
            preparedStatement.setObject(2, idconfiguracionmda);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    public static void updateTablaOptativa(int codigo, 
            String nombre,
            String vigencia,
            String hasta,
            int cantayu,
            String capita,
            String honorario,
            String especialista,
            Connection connection) throws JSONException {
        try {
            String sql = "UPDATE cmt_practicaoptativa "
                    + "SET nombre = ?, vigencia = ?, hasta = ?, cantayu = ?, capita = ?, honorario = ?, especialista = ? WHERE codigo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, nombre);
            preparedStatement.setObject(2, vigencia);
            preparedStatement.setObject(3, hasta);
            preparedStatement.setObject(4, cantayu);
            preparedStatement.setObject(5, capita);
            preparedStatement.setObject(6, honorario);
            preparedStatement.setObject(7, especialista);
            preparedStatement.setObject(8, codigo);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
//    public static void main(String[] args) {
//        updateTabla();
//    }
}
