package br.com.southsystem.cooperative.service.util;

public final class ParametersUtil {
   private ParametersUtil(){

   }
    /**
     * Remove the non digits of a string
     *
     * @return the string with just digits.
     */
    public static String justDigits(String cpf) {
        return cpf != null ? cpf.replaceAll("[^\\d]", "") : cpf;
    }
}
