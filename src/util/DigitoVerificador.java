/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author djaime
 */
public class DigitoVerificador {

    public static void main(String arg[]) {
        try {
            //System.out.println(MD5.getEncodedString("15652"));
            //System.out.println(MD5.getEncodedString("cmt496"));
//            System.out.println("Numero Verificador: "+getDigitoVerificadorSocio("1626"));
            //System.out.println("Numero Orden: "+getDigitoVerificadorOrdenAsunt("1199462"));
            //System.out.println("Numero Orden: "+getDigitoVerificadorOrdenAsunt("1210111"));
            //System.out.println("Numero Orden: "+getDigitoVerificadorOrdenAsunt("1252940"));
        } catch (Exception ex) {
            Logger.getLogger(DigitoVerificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getDigitoVerificadorSocio(String codme) throws Exception {
        int t = 0;
        codme = getCodmeCompleto(codme);
        for (int indice = 0; indice < 4; indice++) {
            String valor = codme.charAt(indice) + "";
            t = t + Integer.valueOf(valor) * (6 - (indice + 1));
        }
        t = MOD(t, 11);
        t = 11 - t;
        if (t >= 10) {
            t = 0;
        }
        return t;
    }

    private static int MOD(int dividendo, int divisor) {
        int resto = -1;
        if (divisor <= 0) {
            return resto;
        } else {
            while (dividendo > divisor) {
                dividendo = dividendo - divisor;
            }
//            int var = resto - divisor;
        }
        resto = dividendo;
        return resto;
    }

    public static String getCodmeCompleto(String codme) throws IOException, Exception {
        String codmeCompleto = "";
        if (codme.length() == 1) {
            codmeCompleto = "000" + codme;
        } else {
            if (codme.length() == 2) {
                codmeCompleto = "00" + codme;
            } else {
                if (codme.length() == 3) {
                    codmeCompleto = "0" + codme;
                } else {
                    codmeCompleto = codme;
                }
            }
        }
        return codmeCompleto;
    }

    public static String getOrdenCompleto(String orden) throws IOException, Exception {
        if (orden.length() == 10) {
            return orden;
        } else {
            orden = "0" + orden;
            orden = getOrdenCompleto(orden);
        }
        return orden;
    }

    public static int getDigitoVerificadorOrden(String orden) throws Exception {
        int t = 0;
        orden = getOrdenCompleto(orden);
        for (int indice = 0; indice < 10; indice++) {
            String valor = orden.charAt(indice) + "";
            t = t + Integer.valueOf(valor) * (12 - (indice + 1));
        }
        t = MOD(t, 11);
        t = 11 - t;
        if (t > 10) {
            t = 0;
        }
        return t;
    }

    public static int getDigitoVerificadorOrdenAsunt(String orden) throws Exception {
        int t = 0;
        orden = getOrdenCompleto(orden);
        for (int indice = 0; indice < 10; indice++) {
            String valor = orden.charAt(indice) + "";
            t = t + Integer.valueOf(valor) * (12 - (indice + 1));
        }
        t = MOD(t, 11);
        t = 11 - t;
        if (t > 20) {
            t = 0;
        }
        return t;
    }

    public static String getCompletarDigito(String edit, int valor) {
        try {
            if (edit.length() <= valor) {
                if (edit.length() == valor) {
                    return edit;
                } else {
                    edit = "0" + edit;
                    edit = getCompletarDigito(edit, valor);
                }
            } else {
            }

            return edit;
        } catch (Exception e) {
            return "00";
        }
    }

    public static String getCompletarCeroDerecha(String edit, int valor) {
        if (edit.length() == valor) {
            return edit;
        } else {
            edit = edit + "0";
            edit = getCompletarCeroDerecha(edit, valor);
        }
        return edit;
    }

    public static String getCompletarBlancoIzquierdaDigito(String edit, int valor) {
        if (edit.length() == valor) {
            return edit;
        } else {
            edit = " " + edit;
            edit = getCompletarBlancoIzquierdaDigito(edit, valor);
        }
        return edit;
    }

    public static String getCompletarBlancoDerechaDigito(String edit, int valor) {
        try {
            int cantidad = edit.length();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (edit.length() == valor) {
            return edit;
        } else {
            edit = edit + " ";
            edit = getCompletarBlancoDerechaDigito(edit, valor);
        }
        return edit;
    }

    public static String getValorLimitado(String edit, int valor) throws IOException, Exception {
        String campo = "";
        for (int indice = 0; indice < valor; indice++) {
            if (!Caracter.esLetra(edit.charAt(indice))) {
                campo += edit.charAt(indice);
            }
            if (edit.length() == (indice + 1)) {
                return campo;
            }
        }
        return campo;
    }

    public static javax.swing.JTextField setValorControlado(
            java.awt.event.KeyEvent evt,
            javax.swing.JTextField textField,
            int valor,
            javax.swing.JTextField textFieldFocus
    ) throws IOException, Exception {
        try {
            if (evt.getKeyCode() != KeyEvent.VK_LEFT
                    && evt.getKeyCode() != KeyEvent.VK_RIGHT
                    && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                String filtro = getValorLimitado(textField.getText(), valor);
                textField.setText(filtro);
            }

        } catch (Exception ex) {
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                textField.setText(DigitoVerificador.getCompletarDigito(textField.getText(), valor));
                textFieldFocus.requestFocus();
            } catch (Exception ex) {
            }
        }
        return textField;
    }

    public static void completarDigitoPorFocus(javax.swing.JTextField textField, int valor) {
        try {
            textField.setText(DigitoVerificador.getCompletarDigito(textField.getText(), valor));
        } catch (Exception ex) {
        }
    }
}
