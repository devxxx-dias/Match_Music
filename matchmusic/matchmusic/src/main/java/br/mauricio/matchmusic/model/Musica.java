package br.mauricio.matchmusic.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "musicas")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String album;
    private String nomeArtista;
    @ManyToOne
    private Artista artista;


    public Musica() {
    }


    public Musica(String nome, String album, Artista artista, String nomeArtista) {
        this.nome = nome;
        this.album = album;
        this.artista = artista;
        this.nomeArtista = nomeArtista;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;

    }

    @Override
    public String toString() {
        return "Musica: " +
                nome + '\'' +
                ", Álbum= '" + album + '\'' +
                ", Artista= " + artista
                ;
    }
}
