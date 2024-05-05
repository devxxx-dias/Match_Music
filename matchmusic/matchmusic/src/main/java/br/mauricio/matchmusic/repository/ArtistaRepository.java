package br.mauricio.matchmusic.repository;

import br.mauricio.matchmusic.model.Artista;
import br.mauricio.matchmusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    //    SELECT e from Serie s join s.episodios e where s = :serie order by e.avaliacao desc limit 5
    @Query("SELECT m from Artista a join a.musicas m")
    List<Musica> listarTodasMusicas();

    @Query("SELECT m from Artista a join a.musicas m where m.nomeArtista ilike %:nomeArtista%")
    List<Musica> listarMusicasPorArtista(String nomeArtista);

}
