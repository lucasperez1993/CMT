// 
// Decompiled by Procyon v0.5.36
// 

package otro;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FechaHora
{
    public static String getF() {
        return getFecha();
    }
    
    public static String getH() {
        return getHora();
    }
    
    public static String getFecha() {
        final Calendar calendario = new GregorianCalendar();
        return formatearFecha(calendario.get(5), calendario.get(2) + 1, calendario.get(1));
    }
    
    public static String getFechaFoormat() {
        final Calendar calendario = new GregorianCalendar();
        return formatearFechaFormat(calendario.get(5), calendario.get(2) + 1, calendario.get(1));
    }
    
    public static String getFechaHoy() {
        final Calendar calendario = new GregorianCalendar();
        return getFormatAMD(calendario.get(5), calendario.get(2) + 1, calendario.get(1));
    }
    
    public static Date retornarFechaDate(final String fecha) {
        Calendar cal = null;
        Date date = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = new GregorianCalendar();
            date = sdf.parse(fecha);
            cal.setTime(date);
        }
        catch (Exception ex) {}
        return date;
    }
    
    public static String formatearFechaFormat(final int dia, final int mes, final int anio) {
        String DIA;
        if (dia < 10) {
            DIA = "0" + dia;
        }
        else {
            DIA = Integer.toString(dia);
        }
        String MES;
        if (mes < 10) {
            MES = "0" + mes;
        }
        else {
            MES = Integer.toString(mes);
        }
        final String fecha = DIA + "-" + MES + "-" + anio;
        return fecha;
    }
    
    public static String getFormatAMD(final int dia, final int mes, final int anio) {
        String DIA;
        if (dia < 10) {
            DIA = "0" + dia;
        }
        else {
            DIA = Integer.toString(dia);
        }
        String MES;
        if (mes < 10) {
            MES = "0" + mes;
        }
        else {
            MES = Integer.toString(mes);
        }
        final String fecha = anio + "-" + MES + "-" + DIA;
        return fecha;
    }
    
    public static String formatearFecha(final int dia, final int mes, final int anio) {
        String DIA;
        if (dia < 10) {
            DIA = "0" + dia;
        }
        else {
            DIA = Integer.toString(dia);
        }
        String MES;
        if (mes < 10) {
            MES = "0" + mes;
        }
        else {
            MES = Integer.toString(mes);
        }
        final String fecha = DIA + "/" + MES + "/" + anio;
        return fecha;
    }
    
    public static String getHora() {
        final Calendar calendario = new GregorianCalendar();
        return formatearHora(calendario.get(11), calendario.get(12), calendario.get(13));
    }
    
    public static String formatearHora(final int hora, final int minuto, final int segundo) {
        String HORA;
        if (hora < 10) {
            HORA = "0" + hora;
        }
        else {
            HORA = Integer.toString(hora);
        }
        String MINUTO;
        if (minuto < 10) {
            MINUTO = "0" + minuto;
        }
        else {
            MINUTO = Integer.toString(minuto);
        }
        String SEGUNDO;
        if (segundo < 10) {
            SEGUNDO = "0" + segundo;
        }
        else {
            SEGUNDO = Integer.toString(segundo);
        }
        final String tiempo = HORA + ":" + MINUTO + ":" + SEGUNDO;
        return tiempo;
    }
    
    public static long diferenciaEntreFechas(final String fechaInicio, final String fechaFin, final String retorno) {
        final SimpleDateFormat formatoTexto = new SimpleDateFormat("dd/MM/yyyy");
        try {
            final Date inicio = formatoTexto.parse(fechaInicio);
            final Date fin = formatoTexto.parse(fechaFin);
            final long diferencia = (fin.getTime() - inicio.getTime()) / 1000L;
            if (diferencia > 0L) {
                long diferenciaReal = 0L;
                if (retorno.equalsIgnoreCase("A\u00d1OS")) {
                    diferenciaReal = diferencia / 60L / 60L / 24L / 365L;
                }
                else if (retorno.equalsIgnoreCase("MESES")) {
                    diferenciaReal = diferencia / 60L / 60L / 24L / 30L;
                }
                else if (retorno.equalsIgnoreCase("DIAS")) {
                    diferenciaReal = diferencia / 60L / 60L / 24L;
                }
                else if (retorno.equalsIgnoreCase("HORAS")) {
                    diferenciaReal = diferencia / 60L / 60L;
                }
                else if (retorno.equalsIgnoreCase("MINUTOS")) {
                    diferenciaReal = diferencia / 60L;
                }
                else if (retorno.equalsIgnoreCase("SEGUNDOS")) {
                    diferenciaReal = diferencia;
                }
                return diferenciaReal;
            }
            return 0L;
        }
        catch (Exception e) {
            return -1L;
        }
    }
    
    public static long diferenciaEntreHoras(final String fechaInicio, final String fechaFin, final String retorno) {
        final SimpleDateFormat formatoTexto = new SimpleDateFormat("hh:mm:ss");
        try {
            final Date inicio = formatoTexto.parse("08:00:00");
            final Date fin = formatoTexto.parse("14:00:00");
            final long diferencia = (inicio.getTime() - fin.getTime()) / 1000L;
            System.out.println(fin.getTime());
            System.out.println(inicio.getTime());
            System.out.println(diferencia);
            if (diferencia > 0L) {
                long diferenciaReal = 0L;
                if (retorno.equalsIgnoreCase("A\u00d1OS")) {
                    diferenciaReal = diferencia / 60L / 60L / 24L / 365L;
                }
                else if (retorno.equalsIgnoreCase("MESES")) {
                    diferenciaReal = diferencia / 60L / 60L / 24L / 30L;
                }
                else if (retorno.equalsIgnoreCase("DIAS")) {
                    diferenciaReal = diferencia / 60L / 60L / 24L;
                }
                else if (retorno.equalsIgnoreCase("HORAS")) {
                    diferenciaReal = diferencia / 60L / 60L;
                }
                else if (retorno.equalsIgnoreCase("MINUTOS")) {
                    diferenciaReal = diferencia / 60L;
                }
                else if (retorno.equalsIgnoreCase("SEGUNDOS")) {
                    diferenciaReal = diferencia;
                }
                return diferenciaReal;
            }
            return 0L;
        }
        catch (Exception e) {
            return -1L;
        }
    }
    
    public static int diferenciaEntreFechas(Date fechaInicial, Date fechaFinal) {
        try {
            final DateFormat df = DateFormat.getDateInstance(2);
            final String fechaInicioString = df.format(fechaInicial);
            fechaInicial = df.parse(fechaInicioString);
            final String fechaFinalString = df.format(fechaFinal);
            fechaFinal = df.parse(fechaFinalString);
        }
        catch (ParseException ex) {}
        final long fechaInicialMs = fechaInicial.getTime();
        final long fechaFinalMs = fechaFinal.getTime();
        final long diferencia = fechaFinalMs - fechaInicialMs;
        final double dias = Math.floor((double)(diferencia / 86400000L));
        return (int)dias;
    }
    
    public static Timestamp convertStringToTimeStamp(final String fecha) {
        return Timestamp.valueOf(fecha);
    }
    
    public static Timestamp convertDateStringToTimestamp(final String date) {
        final Timestamp timestamp = Timestamp.valueOf(date.replaceAll("T", " ").replaceAll("Z", ""));
        return timestamp;
    }
    
    public static Timestamp convertDateToTimeStamp(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(14, 0);
        System.out.println(new Timestamp(date.getTime()));
        System.out.println(new Timestamp(cal.getTimeInMillis()));
        return new Timestamp(date.getTime());
    }
    
    public static Date getFechaHoyDate() {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        final String dateInString = getFecha();
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    public static Date convertFromSQLDateToJAVADate(final java.sql.Date sqlDate) {
        Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        return javaDate;
    }
}
