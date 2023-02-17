package com.cailangrott.peoplemanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValorOuDefaultTest {

    @Test
    void deveRetornarValorDefault_quandoValorIsNull() {
        String valor = null;
        String valorDefault = "default";
        String valorAtual = ValorOuDefault.getValorOuDefault(valor, valorDefault);

        assertEquals(valorDefault, valorAtual);
    }

    @Test
    void deveRetornaValorDefault_quandoValorIsEmpty() {
        String valor = "";
        String valorDefault = "default";
        String valorAtual = ValorOuDefault.getValorOuDefault(valor, valorDefault);

        assertEquals(valorDefault, valorAtual);
    }

    @Test
    void deveRetornarValorDefault_quandoValorIsBlank() {
        String valor = " ";
        String valorDefault = "default";
        String valorAtual = ValorOuDefault.getValorOuDefault(valor, valorDefault);

        assertEquals(valorDefault, valorAtual);
    }
    
}