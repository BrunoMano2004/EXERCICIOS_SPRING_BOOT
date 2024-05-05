package com.alura.desafioMusica.com.alura.desafioMusica.model;

public enum Categoria {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoria.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada!");
    }
}
