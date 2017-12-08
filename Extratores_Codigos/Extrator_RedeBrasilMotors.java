import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.List;

/**
 * Created by rodol_000 on 03/12/2017.
 */
public class Extrator_RedeBrasilMotors {

    public static void main(String [] args) throws IOException {
        File inputDirectory=new File("C:\\Users\\rodol_000\\CIn\\RI\\paginas_positivas\\rede_brasil_motors");


        File [] files=inputDirectory.listFiles();

        for(File htmlFile : files){

            String nameOfFile=htmlFile.getName();
            Document doc=Jsoup.parse(htmlFile,"utf-8");

            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\rodol_000\\CIn\\RI\\redeBrasilMotors_Extrator\\"+nameOfFile+".txt"));
            bw.write("Nome do html:"+nameOfFile);
            bw.newLine();



            // String ano=valores.get(0);







            String valor=doc.getElementsByClass("label_preco").text();

            Element tableDetails=doc.getElementsByTag("table").first();
            Elements details=doc.getElementsByTag("b");
            String[] titulo=details.get(1).text().split(" ");


            String marca=titulo[0];
            // String marca=doc.getElementsByClass("titulo-sm").text().split(" ").;
            String modelo=titulo[1];
            //String modelo=doc.getElementById("ctl00_ContentPlaceHolder1_lblModelobox").text();
            String combustivel=details.get(4).text();//doc.getElementById("ctl00_ContentPlaceHolder1_lblCombustivel").text();
            //String Cambio=doc.getElementById("ctl00_ContentPlaceHolder1_lblCambio").text();
            String Cambio=details.get(6).text();;
            String km=details.get(5).text();
            //String km=doc.getElementById("ctl00_ContentPlaceHolder1_lblKM").text();
            //String cor=doc.getElementById("ctl00_ContentPlaceHolder1_lblCor").text();
            String cor=details.get(7).text();;
            // String portas=doc.getElementById("ctl00_ContentPlaceHolder1_lblPortas").text();
            String portas="";


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
