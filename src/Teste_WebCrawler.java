import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Teste_WebCrawler {
    public static void main (String [] args){

        Thread wc1=new WebCrawler("http://www.icarros.com.br/comprar/usados","Resultados_ICarros");
        wc1.start();
      //  Thread wc3=new WebCrawler("http://www.redebrasilmotors.com.br/portal","Resultados_redebrasilmotors");
       // wc3.start();


        //Thread wc6=new WebCrawler("https://www.mercadolivre.com.br/veiculos","Resultados_MercadoLivre");
        //wc6.start();

    }
}
