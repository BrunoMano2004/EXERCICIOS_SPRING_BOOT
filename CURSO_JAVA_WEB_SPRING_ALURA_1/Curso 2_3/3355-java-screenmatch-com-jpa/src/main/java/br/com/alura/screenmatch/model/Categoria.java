package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOMNDB;
    private String categoriaPortugues;

    Categoria(String categoriaOMNDB, String categoriaPortugues){
        this.categoriaOMNDB = categoriaOMNDB;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOMNDB.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada!");
    }

    public static Categoria fromPortugues(String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada!");
    }
}
