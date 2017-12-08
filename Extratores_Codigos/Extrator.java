import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.List;

/**
 * Created by rodol_000 on 03/12/2017.
 */
public class Extrator {

    public static void main(String [] args) throws IOException {
        File inputDirectory=new File("C:\\Users\\rodol_000\\CIn\\RI\\paginas_positivas\\icarros");


        File [] files=inputDirectory.listFiles();

        for(File htmlFile : files){

            String nameOfFile=htmlFile.getName();
            Document doc=Jsoup.parse(htmlFile,"utf-8");

            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\rodol_000\\CIn\\RI\\vitallyMotors_Extrator\\"+nameOfFile+".txt"));
            bw.write("Nome do html:"+nameOfFile);
            bw.newLine();

            Element element=doc.getElementsByClass("listaHorizontal").first();
            List<String> valores=element.getElementsByClass("destaque").eachText();

            String ano=valores.get(0);


            String valor=doc.getElementsByClass("preco").text();
            String[] titulo=doc.getElementsByClass("titulo-sm").text().split(" ");
            String marca=titulo[0];
            // String marca=doc.getElementsByClass("titulo-sm").text().split(" ").;
            String modelo=titulo[1];
            //String modelo=doc.getElementById("ctl00_ContentPlaceHolder1_lblModelobox").text();
            String combustivel="";//doc.getElementById("ctl00_ContentPlaceHolder1_lblCombustivel").text();
            //String Cambio=doc.getElementById("ctl00_ContentPlaceHolder1_lblCambio").text();
            String Cambio=valores.get(3);
            String km=valores.get(1);
            //String km=doc.getElementById("ctl00_ContentPlaceHolder1_lblKM").text();
            //String cor=doc.getElementById("ctl00_ContentPlaceHolder1_lblCor").text();
            String cor=valores.get(2);
           // String portas=doc.getElementById("ctl00_ContentPlaceHolder1_lblPortas").text();
            String portas=valores.get(4);


            bw.write("valor;");
            bw.write(valor);
            bw.newLine();

            bw.write("marca;");
            bw.write(marca);
            bw.newLine();

            bw.write("modelo;");
            bw.write(modelo);
            bw.newLine();

            bw.write("combustivel;");
            bw.write(combustivel);
            bw.newLine();

            bw.write("Cambio;");
            bw.write(Cambio);
            bw.newLine();

            bw.write("km;");
            bw.write(km);
            bw.newLine();

            bw.write("cor;");
            bw.write(cor);
            bw.newLine();

            bw.write("portas;");
            bw.write(portas);
            bw.newLine();

            bw.close();




        }


    }
}
