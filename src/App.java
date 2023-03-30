import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        //Retirar senha de acesso da API
        //String imdKey = System.getenv("IMDB_API_KEY");
        //acrescentar concatenação da url + imdbKey;

        // fazer uma conexão HTTP e buscar os tops Movies filmes
        String url = "figurinhas/sobreposicao.DrEstranho.png";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();

        HttpResponse<String> response= client.send(request, BodyHandlers.ofString()); 
        String body = response.body();
        System.out.println(body);

        //extrair os dados (Titulo, Post (img), classificação)
        var parser = new JsonParser();
        List<Map<String, String >> listaDeFilmes = parser.parse(body);

       // System.out.println(listaDeFilmes.size()); Ex de busca
       // System.out.println(listaDeFilmes.get(0)); Ex de busca

       var diretorio = new File("figurinhas/");
       diretorio.mkdir();
        
       //exibir e manipular os dados
         var geradora =  new CriandoFigurinhas();
         for (int index = 0; index < 3; index++) {
            var filme = listaDeFilmes.get(index);

            String urlImagem = filme.get("imagem");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRanting"));

            String textoFigurinha;
            InputStream imagemTop;
            if (classificacao >= 8.0) {
              textoFigurinha = "TOPZERA";
              imagemTop = new FileInputStream(new File("figurinhas/sobreposicao.top.jpeg"));
            }else{
              textoFigurinha = " BLÁ BLÁ BLÁ";
              imagemTop = new FileInputStream(new File("figurinh/sobreposicao.judy.webp" ));
            }


          InputStream inputStream = new URL(urlImagem).openStream();
          String nomeArquivo = "figurinhas/" + titulo + ".png";

           geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemTop);

           System.out.println("titulo");
           System.out.println();
         } 
            
         }
        
           }
    


