import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by rodol_000 on 05/12/2017.
 */
public class Index_IDF {

    public static HashMap<String,String> retrieveDocumentsIds()throws IOException{
        HashMap<String,String> documentsId=new HashMap<>();

        BufferedReader reader=new BufferedReader(new FileReader("C:\\Users\\rodol_000\\CIn\\RI\\idsDoc.txt"));
        boolean hasNextLine=true;
        String linha;
       while((linha=reader.readLine())!=null){
         // String linha=reader.readLine();
          String [] palavras=linha.split(";");
          documentsId.put(palavras[0],palavras[1]);
        }
        return documentsId;
    }
    public static String makeAtributo(String attr1,String attr2){
        return attr1+"."+attr2;
    }

    public static String discretizarValor(String valor){


        if(valor.contains("R$")){
            valor=valor.replace("R$ ","");
            valor=valor.replace(".","");
            valor=valor.replace(",00","");

        }


        Double numero=Double.parseDouble(valor);
        Double retornoNumero;
        if(numero<25000){
            retornoNumero=1.0;
        }else if(numero <50000){
            retornoNumero=2.0;
        }else if(numero <75000){
            retornoNumero=3.0;
        }else{
            retornoNumero=4.0;
        }

        return retornoNumero.toString();


    }
    public static void main(String [] args) throws IOException{

        ArrayList<String> nomeAtributos=new ArrayList<>();
        HashMap<String,String> documentsID=retrieveDocumentsIds();
        HashMap<String,atributo> index_IDF=new HashMap<>();//a string e o nome do atributo(tipo cor.prata) e atributo eh o objeto que tem a lista

        String vitalyExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\vitallyMotors_Extrator";
        String meuCarangoExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\MeuCarango_Extrator";
        String mercadoLivreExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\MercadoLivre_Extrator";
        String icarrosExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\Icarros_Extrator";

        ArrayList<String> directoriesExtratores=new ArrayList<String>();

        directoriesExtratores.add(vitalyExtratorDirectory);
        directoriesExtratores.add(meuCarangoExtratorDirectory);
        directoriesExtratores.add(mercadoLivreExtratorDirectory);
        directoriesExtratores.add(icarrosExtratorDirectory);

        try  {
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\rodol_000\\CIn\\RI\\Index_IDF.txt"));
            int indexId=0;
            for(String dir:directoriesExtratores){
                File inputDirectory=new File(dir);

                File [] files=inputDirectory.listFiles();//files[] tem a lista de docs txt com os atributos extraidos

                for(File extratorTxt:files){//extratorTxt eh o .txt com os valores extraidos


                    BufferedReader reader = new BufferedReader(new FileReader(extratorTxt.getAbsolutePath()));
                    String titulo=reader.readLine();
                    String[] tituloPalavras=titulo.split(":");
                    String nomeHtml=tituloPalavras[1];
                    String idDocumentofHtml=documentsID.get(nomeHtml); //faz a busca do id do documento com o nome igual a nomeHtml

                    String valor=reader.readLine();
                    String[] valorPalavras=valor.split(";");
                    if(valorPalavras.length>1){
                        String valorDiscretizado=discretizarValor(valorPalavras[1]);

                    String valorAtributo=makeAtributo(valorPalavras[0],valorDiscretizado);


                    if(nomeAtributos.contains(valorAtributo)){
                        atributo attr=index_IDF.get(valorAtributo);
                        attr.IdDocs.add(idDocumentofHtml);
                    }else{
                        nomeAtributos.add(valorAtributo);
                        atributo attr=new atributo(valorAtributo,new ArrayList<String>());
                        attr.IdDocs.add(idDocumentofHtml);
                        index_IDF.put(valorAtributo,attr);
                    }
                    }

                    String marca=reader.readLine();
                    String [] marcaPalavras=marca.split(";");
                    if(marcaPalavras.length>1){
                    String marcaAtributo=makeAtributo(marcaPalavras[0],marcaPalavras[1]);
                        if(nomeAtributos.contains(marcaAtributo)){
                            atributo attr=index_IDF.get(marcaAtributo);
                            attr.IdDocs.add(idDocumentofHtml);
                        }else{
                            nomeAtributos.add(marcaAtributo);
                            atributo attr=new atributo(marcaAtributo,new ArrayList<String>());
                            attr.IdDocs.add(idDocumentofHtml);
                            index_IDF.put(marcaAtributo,attr);
                        }
                    }

                    String modelo=reader.readLine();
                    String [] modeloPalavras=modelo.split(";");
                    if(modeloPalavras.length>1) {
                        String modelorAtributo = makeAtributo(modeloPalavras[0], modeloPalavras[1]);
                        if(nomeAtributos.contains(modelorAtributo)){
                            atributo attr=index_IDF.get(modelorAtributo);
                            attr.IdDocs.add(idDocumentofHtml);
                        }else{
                            nomeAtributos.add(modelorAtributo);
                            atributo attr=new atributo(modelorAtributo,new ArrayList<String>());
                            attr.IdDocs.add(idDocumentofHtml);
                            index_IDF.put(modelorAtributo,attr);
                        }
                    }
                    String combustivel=reader.readLine();
                    String [] combustivelPalavras=combustivel.split(";");

                    String Cambio=reader.readLine();
                    String [] CambioPalavras=Cambio.split(";");

                    String km=reader.readLine();
                    String [] kmPalavras=km.split(";");
                    if(kmPalavras.length>1) {
                        String kmDiscretizado=discretizarValor(kmPalavras[1]);
                        String kmAtributo = makeAtributo(kmPalavras[0], kmDiscretizado);
                        if(nomeAtributos.contains(kmAtributo)){
                            atributo attr=index_IDF.get(kmAtributo);
                            attr.IdDocs.add(idDocumentofHtml);
                        }else{
                            nomeAtributos.add(kmAtributo);
                            atributo attr=new atributo(kmAtributo,new ArrayList<String>());
                            attr.IdDocs.add(idDocumentofHtml);
                            index_IDF.put(kmAtributo,attr);
                        }
                    }
                    String cor=reader.readLine();
                    String [] corPalavras=cor.split(";");
                    if(corPalavras.length>1) {
                        String corAtributo = makeAtributo(corPalavras[0], corPalavras[1]);
                        if(nomeAtributos.contains(corAtributo)){
                            atributo attr=index_IDF.get(corAtributo);
                            attr.IdDocs.add(idDocumentofHtml);
                        }else{
                            nomeAtributos.add(corAtributo);
                            atributo attr=new atributo(corAtributo,new ArrayList<String>());
                            attr.IdDocs.add(idDocumentofHtml);
                            index_IDF.put(corAtributo,attr);
                        }
                    }
                    String portas=reader.readLine();
                    String [] portasPalavras=portas.split(";");

                    reader.close();
                }

            }
            Collection<atributo> index=index_IDF.values();
            for (atributo attr:index
                 ) {
                bw.write(attr.nome);
                for (String s:attr.IdDocs
                     ) {
                    bw.write(";"+s);
                }
                bw.newLine();
            }
            bw.close();


        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
class atributo{
    String nome;
    ArrayList<String> IdDocs;

    public atributo(String n,ArrayList<String> documents){
        nome=n;
        IdDocs=documents;
    }
}