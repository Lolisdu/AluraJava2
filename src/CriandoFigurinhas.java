import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

 //Fazer leitura da imagem
    //cria nova imagem em memoria com tranparencia e com tamanho novo
    //copiar a imagem  original para novas imagens (em memoria)
    //escrever nova imagem em arquivo
    //frase 
public class CriandoFigurinhas {
    
    public void cria(InputStream inputStream, String nomeArquivo, String texto, InputStream inputStreamSobreposicao) throws Exception {
        //InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies_1.jpg".openStream();
        //InputStream inputStream = new FileInputStream(new File ("imagem/DrEstranho.jpg"));
        BufferedImage imagemOriginal = ImageIO.read(new File ("figurinhas/sobreposicao/DrEstranho.jpg"));

        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null );

        BufferedImage imagemSobreposicao = ImageIO.read(inputStreamSobreposicao);
        int posicaoimagemSobreposicaoY = novaAltura - imagemSobreposicao.getHeight();
        graphics.drawImage(imagemSobreposicao, 0, posicaoimagemSobreposicaoY, null );

        ImageIO.write(novaImagem, "png", new File("figurinhas/sobreposicao/DrEstranho.png"));

        var fonte = new Font( "impact", Font.BOLD, 62);
        graphics.setFont(fonte);
        graphics.setColor(Color.ORANGE);


     
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguratexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguratexto) /2;
        int posicaoTextoY = novaAltura - 100;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout =  new TextLayout(texto, fonte, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);


        var outlineStroke = new BasicStroke(largura * 0.003f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.PINK);
        graphics.draw(outline);
        graphics.setClip(outline);
 

    }
    //public static void main(String[] args) throws Exception {
        //var geradora = new CriandoFigurinhas();
        //geradora.cria();
    }

