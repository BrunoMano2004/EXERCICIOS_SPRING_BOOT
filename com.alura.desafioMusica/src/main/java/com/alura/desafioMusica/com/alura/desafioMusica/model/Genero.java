package com.alura.desafioMusica.com.alura.desafioMusica.model;

public enum Genero {
    POP("pop"),
    ROCK("rock"),
    ELETRONICA("eletrônica");

    private String generoMusical;

    Genero(String generoMusical){
        this.generoMusical = generoMusical;
    }

    public static Genero fromString(String text){
        for (Genero genero : Genero.values()){
            if (genero.generoMusical.equalsIgnoreCase(text)){
                return genero;
            }

        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada!");
    }
}
