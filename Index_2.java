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
public class Index_2 {

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

    public static String SomarIDS(int base,String id){
        int idDoc=Integer.parseInt(id);
        String retorno;
        if(idDoc>base){
            retorno=String.valueOf((idDoc-base));
        }else{
            retorno=String.valueOf((base-idDoc));
        }
        return retorno;
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
        HashMap<String,atributo2> index_2=new HashMap<>();//a string e o nome do atributo(tipo cor.prata) e atributo eh o objeto que tem a lista

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
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\rodol_000\\CIn\\RI\\Index_Compressao.txt"));
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
                            atributo2 attr=index_2.get(valorAtributo);
                            String idCompressao=SomarIDS(attr.id_base,idDocumentofHtml);
                            attr.IdDocs.add(idCompressao);

                        }else{
                            nomeAtributos.add(valorAtributo);
                            atributo2 attr=new atributo2(valorAtributo,new ArrayList<String>());
                            attr.id_base=Integer.parseInt(idDocumentofHtml);
                            attr.IdDocs.add(idDocumentofHtml);
                            index_2.put(valorAtributo,attr);
                        }
                    }

                    String marca=reader.readLine();
                    String [] marcaPalavras=marca.split(";");
                    if(marcaPalavras.length>1){
                        String marcaAtributo=makeAtributo(marcaPalavras[0],marcaPalavras[1]);
                        if(nomeAtributos.contains(marcaAtributo)){
                            atributo2 attr=index_2.get(marcaAtributo);
                            String idCompressao=SomarIDS(attr.id_base,idDocumentofHtml);
                            attr.IdDocs.add(idCompressao);
                        }else{
                            nomeAtributos.add(marcaAtributo);
                            atributo2 attr=new atributo2(marcaAtributo,new ArrayList<String>());
                           // String idCompressao=SomarIDS(attr.id_base,idDocumentofHtml);
                           // attr.IdDocs.add(idCompressao);
                            attr.id_base=Integer.parseInt(idDocumentofHtml);
                            attr.IdDocs.add(idDocumentofHtml);
                            index_2.put(marcaAtributo,attr);
                        }
                    }

                    String modelo=reader.readLine();
                    String [] modeloPalavras=modelo.split(";");
                    if(modeloPalavras.length>1) {
                        String modelorAtributo = makeAtributo(modeloPalavras[0], modeloPalavras[1]);
                        if(nomeAtributos.contains(modelorAtributo)){
                            atributo2 attr=index_2.get(modelorAtributo);
                            String idCompressao=SomarIDS(attr.id_base,idDocumentofHtml);
                            attr.IdDocs.add(idCompressao);
                        }else{
                            nomeAtributos.add(modelorAtributo);
                            atributo2 attr=new atributo2(modelorAtributo,new ArrayList<String>());
                            attr.id_base=Integer.parseInt(idDocumentofHtml);
                            attr.IdDocs.add(idDocumentofHtml);
                            index_2.put(modelorAtributo,attr);
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
                            atributo2 attr=index_2.get(kmAtributo);
                            String idCompressao=SomarIDS(attr.id_base,idDocumentofHtml);
                            attr.IdDocs.add(idCompressao);

                        }else{
                            nomeAtributos.add(kmAtributo);
                            atributo2 attr=new atributo2(kmAtributo,new ArrayList<String>());
                            attr.id_base=Integer.parseInt(idDocumentofHtml);
                            attr.IdDocs.add(idDocumentofHtml);
                            index_2.put(kmAtributo,attr);
                        }
                    }
                    String cor=reader.readLine();
                    String [] corPalavras=cor.split(";");
                    if(corPalavras.length>1) {
                        String corAtributo = makeAtributo(corPalavras[0], corPalavras[1]);
                        if(nomeAtributos.contains(corAtributo)){
                            atributo2 attr=index_2.get(corAtributo);
                            String idCompressao=SomarIDS(attr.id_base,idDocumentofHtml);
                            attr.IdDocs.add(idCompressao);

                        }else{
                            nomeAtributos.add(corAtributo);
                            atributo2 attr=new atributo2(corAtributo,new ArrayList<String>());
                            attr.id_base=Integer.parseInt(idDocumentofHtml);
                            attr.IdDocs.add(idDocumentofHtml);
                            index_2.put(corAtributo,attr);
                        }
                    }
                    String portas=reader.readLine();
                    String [] portasPalavras=portas.split(";");

                    reader.close();
                }

            }
            Collection<atributo2> index=index_2.values();
            for (atributo2 attr:index
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
class atributo2{
    String nome;
    ArrayList<String> IdDocs;
    int id_base;

    public atributo2(String n,ArrayList<String> documents){

        nome=n;
        IdDocs=documents;
    }
}