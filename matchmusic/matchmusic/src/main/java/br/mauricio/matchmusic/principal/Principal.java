package br.mauricio.matchmusic.principal;

import br.mauricio.matchmusic.model.Artista;
import br.mauricio.matchmusic.model.Categoria;
import br.mauricio.matchmusic.model.Musica;
import br.mauricio.matchmusic.repository.ArtistaRepository;
import br.mauricio.matchmusic.service.ConsultarChatGPT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ArtistaRepository repository;
    private Optional<Musica> musicas;


    private String displayInicial = """
            *** Match Music ***
                        
            1 - Cadastrar artistas
            2 - Cadastrar músicas
            3 - Listar músicas
            4 - Buscar músicas por artistas
            5 - Pesquisar dados sobre um artista
            6 - Listar Artistas
                                   
            9 - Sair
                        
                        
            """;

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 9) {
            System.out.println(displayInicial);
            opcao = leitura.nextInt();
            leitura.nextLine();


            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtistas();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 6:
                    listarArtistas();
                    break;
                case 9:
                    System.out.println("Saindo...");
//                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção Inválida, selecione uma opção do menu");
                    break;

            }
        }


    }


    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }

    private void cadastrarArtista() {
        var opcao = "s";
        while (!opcao.equals("n")) {
            System.out.println("Digite o nome do artista:");
            var nome = leitura.nextLine().toLowerCase();
            String nomeArtista = toCamelCase(nome);
            System.out.println("Informe o tipo desse artista: (solo, dupla, banda)");
            var tipoArtista = leitura.nextLine();
            System.out.println("Deseja cadastrar artista? (S/N)");
            opcao = leitura.nextLine().toLowerCase();
            System.out.println(opcao);
            var categoria = Categoria.definirTipo(tipoArtista);
            Artista novoArtista = new Artista(nomeArtista, categoria);
            repository.save(novoArtista);
        }
        var artistas = repository.findAll();
        artistas.forEach(System.out::println);
    }


    private void cadastrarMusicas() {
        System.out.println("*** Lista de artista cadastrado ***");
        var artistas = repository.findAll();
        artistas.forEach(System.out::println);
        System.out.println("Digite o nome de um artista da lista:");
        var nomeArtista = toCamelCase(leitura.nextLine().toLowerCase());
        Optional<Artista> artistaEncontrado = artistas.stream().filter(a -> a.getNome().contains(nomeArtista)).findFirst();


        if (artistaEncontrado.isPresent()) {
            var artista = artistaEncontrado.get();
            System.out.println("Digite a música desejada:");
            var nomeMusica = toCamelCase(leitura.nextLine().toLowerCase());
            System.out.println("Digite o álbum da música:");
            var nomeAlbum = toCamelCase(leitura.nextLine().toLowerCase());
            Musica newMusica = new Musica(nomeMusica, nomeAlbum, artista, artista.getNome());
            List<Musica> musicaList = new ArrayList<>();
            musicaList.add(newMusica);

            var opcao = "s";
            while (!opcao.equals("n")) {
                System.out.println("Gostaria de adicionar mais alguma Musica a esse Álbum? (S/N)");
                opcao = leitura.nextLine().toLowerCase();
                System.out.println("Digite a música desejada:");
                nomeMusica = toCamelCase(leitura.nextLine().toLowerCase());
                Musica novaMusica = new Musica(nomeMusica, nomeAlbum, artista, artista.getNome());
                musicaList.add(novaMusica);
            }
            artista.setMusica(musicaList);
            repository.save(artista);
            musicaList.forEach(System.out::println);
        } else {
            System.out.println("Artista nao encontrado");
        }
    }


    private void listarMusicas() {
        var listarMusicar = repository.listarTodasMusicas();
        listarMusicar.forEach(System.out::println);
    }

    private void buscarMusicasPorArtistas() {
        System.out.println("Digite o nome de um artista para a busca:");
        var nomeArtista = toCamelCase(leitura.nextLine().toLowerCase());
        var listarMusicar = repository.listarMusicasPorArtista(nomeArtista);
        listarMusicar.forEach(System.out::println);
    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Informe o artita que deseja pesquisar:");
        var nomeArtista = toCamelCase(leitura.nextLine().toLowerCase());
        System.out.println(ConsultarChatGPT.obterDadosDoArtista(nomeArtista));

    }

    private void listarArtistas() {
        var artistas = repository.findAll();
        artistas.forEach(System.out::println);
    }


    public static String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Split the string into words
        String[] words = str.split(" ");

        // Capitalize the first letter of each word
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
            sb.append(" ");
        }

        // Remove the trailing space
        return sb.toString().trim();
    }
}


