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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskCUIT {
    public static String maskCUIT(String cuit) {
        String regex = "(\\d{2})(\\d{8})(\\d{1})";
        String mask = "$1-$2-$3";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cuit);

        if (matcher.matches()) {
            return cuit.replaceAll(regex, mask);
        } else {
            return cuit;
        }
    }

    public static void main(String[] args) {
        String cuit = "20345678901";
        String maskedCUIT = maskCUIT(cuit);
        System.out.println("CUIT Original: " + cuit);
        System.out.println("CUIT Mascarado: " + maskedCUIT);
    }
}
