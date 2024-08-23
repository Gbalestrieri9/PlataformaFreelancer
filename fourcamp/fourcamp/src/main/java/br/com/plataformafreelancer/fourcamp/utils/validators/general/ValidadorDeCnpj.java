package br.com.plataformafreelancer.fourcamp.utils.validators.general;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.CnpjInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class ValidadorDeCnpj {

    private static String cnpjValidado;

    private ValidadorDeCnpj() {
    }

    public static String validarCnpj(String cnpj) {
        if (cnpj == null || !isValidCNPJ(cnpj)) {
            throw new CnpjInvalidoException(ErrorCode.CNPJ_INVALIDO.getCustomMessage() + cnpj);
        } else {
            return cnpjValidado;
        }
    }

    private static boolean isValidCNPJ(String cnpj) {

        // Remover caracteres não numéricos
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // Verificar se o CNPJ tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verificar se todos os dígitos são iguais (ex. 11.111.111/1111-11)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcular os dígitos verificadores
        int[] digits = new int[14];
        for (int i = 0; i < 14; i++) {
            digits[i] = Character.getNumericValue(cnpj.charAt(i));
        }

        // Calcular primeiro dígito verificador
        int sum = 0;
        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            sum += digits[i] * weight1[i];
        }
        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier >= 10) {
            firstVerifier = 0;
        }

        // Calcular segundo dígito verificador
        sum = 0;
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            sum += digits[i] * weight2[i];
        }
        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier >= 10) {
            secondVerifier = 0;
        }

        // Verificar se os dígitos verificadores estão corretos
        if (digits[12] == firstVerifier && digits[13] == secondVerifier) {
            cnpjValidado = cnpj;
            return true;
        } else {
            return false;
        }
    }
}
