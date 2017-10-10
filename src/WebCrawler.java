import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class WebCrawler extends Thread{
    private LinkedList<String> fila;
    Repositorio_Paginas repositorio;
    private String url_base;
    private String href;
    private String pasta;

    public WebCrawler(String url_base,String pasta) {
        fila = new LinkedList<String>();
        repositorio = new Repositorio_Paginas(url_base);
        this.url_base = url_base;
        String href = "";
        this.pasta=pasta;
    }

    public boolean ehPermitidoBaixar(String url) {
        String robotsTxt ="http://www.icarros.com.br" + "/robots.txt";
        try {
            Document doc = Jsoup.connect(robotsTxt).get();
            if (doc == null) {
                return true;
            }
            String[] areasNegadas = doc.text().split("Disallow: ");
            for (String s : areasNegadas) {
                if (url.matches("(.*)"+s+"(.*)")) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public void gravarArquivo(String titulo, String conteudo,String pasta) {
        try {
            PrintWriter out
                    = new PrintWriter(new BufferedWriter(new FileWriter("C:\\"+pasta+"\\" + titulo + ".html")));
            out.print(conteudo);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String url = this.url_base;
        fila.add(url);
        ArrayList<String> visitados=new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            url = fila.getFirst();
            if(visitados.contains(url)){
                fila.remove();      //verifica se foi visitado para evitar ciclos no grafo
                i--;
            }else{
                visitados.add(url);

                try {
                    Thread.sleep(500);//espera para não dar sobrecarga
                   // if(ehPermitidoBaixar(url)){
                        Connection.Response res = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).execute();
                        String contentType = res.contentType();
                        if(!(contentType==null)){


                            if (!contentType.matches("text/(.*)")||res.statusCode()==404) {
                                i--;
                                fila.remove();
                            } else {
                                Document doc=res.parse();

                                String html = doc.text();

                                repositorio.adicionar_Pagina(html);
                                String indice = Integer.toString(i);
                                gravarArquivo(indice, html,this.pasta);


                                Elements elements = doc.getElementsByTag("a");
                                for (Element link : elements) {
                                    href = link.attr("abs:href");
                                    if (href.matches("(.*)http(.*)|(.*)https(.*)")) {
                                        fila.add(href);
                                    }
                                }
                                fila.remove();

                            }
                     //   }else{
                       //     i--;
                         //   fila.remove();
                        //}
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}