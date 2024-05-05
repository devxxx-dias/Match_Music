package br.mauricio.matchmusic.model;

public enum Categoria {
    SOLO("Solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String categoriaArtista;

    Categoria(String categoriaArtista) {
        this.categoriaArtista = categoriaArtista;
    }

    public static Categoria definirTipo(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaArtista.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para o artista");
    }
}
