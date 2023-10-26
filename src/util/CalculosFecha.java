/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author djaime
 */
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class CalculosFecha {
    public static final String DATE_FORMAT_D_M_Y = "dd-MM-yy";
    public static final String DATE_FORMAT_Y_M_D = "yy-MM-dd";
    public static final String DATE_EXTJS = "";

     public static Date retornarFechaDate(String fecha) {
        Calendar cal = null;
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = new GregorianCalendar();
            date = sdf.parse(fecha);
            cal.setTime(date);
        } catch (ParseException ex) {
        }
        return date;
    }

    public static Timestamp convertDateToTimeStamp(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Timestamp(date.getTime());
    }

    public static JSONObject convertDateToJSON(Date date) throws JSONException {
		if (date == null) return null;

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		// Switch to UTC time
		calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.ZONE_OFFSET));
		calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.DST_OFFSET));
		JSONObject dateInJSON = new JSONObject();
		dateInJSON.put("year", calendar.get(Calendar.YEAR));
		dateInJSON.put("month", calendar.get(Calendar.MONTH));
		dateInJSON.put("day", calendar.get(Calendar.DAY_OF_MONTH));
		dateInJSON.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
		dateInJSON.put("min", calendar.get(Calendar.MINUTE));
		dateInJSON.put("sec", calendar.get(Calendar.SECOND));
		return dateInJSON;
	}

    public static JSONObject convertDateStringToJSON(String date)throws JSONException {
		Timestamp timestamp;
		timestamp = Timestamp.
				valueOf(date.
						replaceAll("T", " ").
						replaceAll("Z", ""));
		return convertDateToJSON(timestamp);
	}

    public static Timestamp convertDateStringToTimestamp(String date)throws JSONException {
	try {
            Timestamp timestamp;
            timestamp = Timestamp.
            valueOf(date.
                replaceAll("T", " ").
		replaceAll("Z", ""));
            return timestamp;
        } catch (Exception e) {}
        return null;
    }

        public static Date convertDateFromJSON(JSONObject dateInJSON) throws JSONException {
		if (dateInJSON == null) return null;
		GregorianCalendar calendar = new GregorianCalendar(
				dateInJSON.getInt("year"),
				dateInJSON.getInt("month"),
				dateInJSON.getInt("day"),
				dateInJSON.getInt("hour"),
				dateInJSON.getInt("min"),
				dateInJSON.getInt("sec"));
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		return calendar.getTime();
	}

    public static String getDateWithFormat(Timestamp ts, String sSimpleDateFormat) {
		if (ts != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(ts.getTime());
			SimpleDateFormat formatDate = new SimpleDateFormat(sSimpleDateFormat);
			return formatDate.format(calendar.getTime());
		}
		return "";
	}
    /**
     * Este metodo calcula la cantidad de dias que hay entre dos fechas (tipo Date).
     * @param fechaInicial
     * @param fechaFinal
     * @return: la cantidad de dias que hay entre dos fechas.
     */
    public static int diferenciaEntreFechas(Date fechaInicial, Date fechaFinal)
    {
        try
        {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String fechaInicioString = df.format(fechaInicial);
            fechaInicial = df.parse(fechaInicioString);
            String fechaFinalString = df.format(fechaFinal);
            fechaFinal = df.parse(fechaFinalString);
        }
        catch (java.text.ParseException ex)
        {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return (int) dias;
}

    /**
     * Este metodo suma una cantidad de dias a una fecha determinada y retorna
     * una nueva fecha con los dias sumados.
     * @param fecha: es la fecha a la cual desea sumarle una cantidad de dias.
     * @param dias: es la catidad de dias que desea sumarle a la fecha.
     * @return Retorna la nueva fecha ya sumados los dias.
     * @throws java.text.ParseException
     */


    public static String sumarDias(String fecha,int dias)
    {System.out.println("fecha en la clase calcular "+fecha+" dias: "+dias);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        Calendar cal = new GregorianCalendar();
        try {
            date = sdf.parse(fecha);
            cal.setTime(date);
            cal.add(Calendar.DATE, dias);

        } catch (java.text.ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formatearFechaConCeros(cal.get(Calendar.DAY_OF_MONTH), (cal.get(Calendar.MONTH) + 1),cal.get(Calendar.YEAR), 1 );
    }

    /**
     * Este metodo suma una cantidad de dias a una fecha determinada y retorna
     * una nueva fecha con los dias sumados.
     * @param fecha: es la fecha a la cual desea sumarle una cantidad de dias.
     * @param dias: es la catidad de dias que desea sumarle a la fecha.
     * @return Retorna la nueva fecha ya sumados los dias yyyy-MM-dd.
     * @throws java.text.ParseException
     */


    public static String sumarDiasFormatoSql(String fecha,int dias)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        Calendar cal = new GregorianCalendar();
        try {
            date = sdf.parse(fecha);
            cal.setTime(date);
            cal.add(Calendar.DATE, dias);

        } catch (java.text.ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formatearFechaConCeros(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1),cal.get(Calendar.DAY_OF_MONTH), 1 );
    }

    /**
     * Este metodo retorna la fecha con el formato que le pases por parametros.
     * @param chooser: debe pasarle el JDatechooser donde selecciona la fecha.
     * @param formato: Ej: (yyyy-MM-dd = 2011-08-04) - (dd-MM-yyyy = 04-08-2011)
     * @return
     */
    public static String retornarFechaConFormatoDesado(Date date, String formato)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        String fecha = simpleDateFormat.format(date);
        return fecha;
    }

    /**
     * Este metodo convierte una fecha tipo Date a un String
     * @param fecha: es la fecha que desea trnsformar a un String
     * @return retorna la fecha en formato string yyyy-MM-dd
     */
    public static String transformarFecha(Date fecha)
    {
        String formato = "yyyy-MM-dd";
        Date dateVenc = fecha;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        String fechaVenc = simpleDateFormat.format(dateVenc);
        return fechaVenc;
    }

    /**
     * Este metodo convierte una fecha tipo String en tipo Date y con el formato que recibe por parametro.
     * @param fecha -- es la fecha a la cual desea trasformarla en date
     * @param formato -- es el formato con el desea recibir la fecha. Ej: yyyy-MM-dd (2011-12-31) o dd-MM-yyyy (31-12-2011)
     * @return -- retorna un String con la fecha recibida en el formato deseado.
     * @throws java.text.ParseException
     */
    public static String transformarFechaConFormato(String fecha,String formato)
    {
        Date date=null;
        String fechaTransformada="";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            date = sdf.parse(fecha);
            fechaTransformada = sdf.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaTransformada;
    }

    public static String transformarFechaConFormatoDiaMesAnio(String fecha){
        String fechaVenc = null;
         try {
            Calendar cal = null;
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = new GregorianCalendar();
            date = sdf.parse(fecha);
            cal.setTime(date);
            ////////////////////////////////
            Date dateVenc = date;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            fechaVenc = simpleDateFormat.format(dateVenc);
        } catch (ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaVenc;
    }

    public static String transformarFechaConFormatoANIO_MES_DIA(String fecha){
        String fechaVenc = null;
         try {
            Calendar cal = null;
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            cal = new GregorianCalendar();
            date = sdf.parse(fecha);
            cal.setTime(date);
            ////////////////////////////////
            Date dateVenc = date;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaVenc = simpleDateFormat.format(dateVenc);
        } catch (ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaVenc;
    }

    public static Date retornarFechaDateDos(String fecha)
    {
        Calendar cal=null;
        Date date=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = new GregorianCalendar();
            date = sdf.parse(fecha);
            cal.setTime(date);


//        return cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);

        } catch (ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public static Date transformarFechaConFormatoDate(String fecha)
    {
        Date date=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(fecha);
//        return cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);

        } catch (ParseException ex) {
            Logger.getLogger(CalculosFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * Este metodo retorna la fecha actual con formato yyyy-MM-dd
     * @return  retorna la fecha actual
     */
    public static String getFechaActualFormatoSQL()
    {
     Calendar calendario = new GregorianCalendar();
     return formatearFechaConCeros(calendario.get(Calendar. DAY_OF_MONTH),
     calendario.get(Calendar.MONTH) + 1, calendario.get(Calendar.YEAR ),0);
    }

    /**
     * Este metodo retorna la fecha actual con formato dd-MM-yyyy.
     * @return  retorna la fecha actual.
     */
    public static String getFecha()
    {
     Calendar calendario = new GregorianCalendar();
     return formatearFechaConCeros(calendario.get(Calendar. DAY_OF_MONTH),
     calendario.get(Calendar.MONTH) + 1, calendario.get(Calendar.YEAR ),1);

    }

    /**
     *
     * @param dia
     * @param mes
     * @param anio
     * @param formato 1: formato dd-MM-yyyy :::: 2: formato yyyy-MM-dd
     * @return
     */
    public static String formatearFechaConCeros(int dia, int mes, int anio,int formato)
    {
        String DIA;
	String MES;

                if(dia <10){
			DIA = "0" + dia;
		}else{
			DIA = Integer.toString(dia);
                     }

		if(mes <10){
			MES = "0" + mes;
		}else{
//			MES = "0" + Integer.toString(mes);
                        MES = Integer.toString(mes);
		}
                if(formato==1)
                {
                    String fecha = DIA + "-" + MES+ "-" + anio;
                    return fecha;
                }
                else
                {
                    String fecha = anio + "-" + MES+ "-" + DIA;
                    return fecha;
                }
    }

    /**
     * Este metodo recibe una fecha String en formato dd-MM-yyyy. La da vuelta
     * y la devuelve con el formato yyyy-MM-dd
     * @param fecha
     * @return la fecha con formato yyyy-MM-dd
     */
    public static String rotarFecha(String fecha)
    {
        String fechaRotada = fecha.substring(6,10)+"-"+fecha.substring(3,5)+"-"+fecha.substring(0, 2);
        System.out.println(fecha+ "   ----   "+fechaRotada);
        return fechaRotada;
    }

    public String getFechaCast(Date date){
        String fecha;
        try{
             String formato = "yyyy-MM-dd";
             SimpleDateFormat sdf = new SimpleDateFormat(formato);
             fecha = sdf.format(date);
             System.out.println(fecha);
             }catch(Exception e)
             {
             System.out.println("Excepcion");
             fecha = "0000-00-00";
             }
        return(fecha);
    }

    public static boolean compareFechaHora(String fehcaHoraSeleccionada, String fechaHoraHoy) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _fehcaHoraSeleccionada = sdf.parse(fehcaHoraSeleccionada);
        Date _fechaHoraHoy = sdf.parse(fechaHoraHoy);
        int diff = _fehcaHoraSeleccionada.compareTo(_fechaHoraHoy);
        return (diff >= 0);
    }

    public static Date convertISO8601_TO_String(String fecha) throws ParseException{
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        try {
          return df.parse(fecha);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        return null;
    }
}
