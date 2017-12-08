import java.io.*;
import java.util.ArrayList;

/**
 * Created by rodol_000 on 05/12/2017.
 */
public class Indexing_Creator {


    public static void main(String[] args){

        String vitalyExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\vitallyMotors_Extrator";
        String meuCarangoExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\MeuCarango_Extrator";
        String mercadoLivreExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\MercadoLivre_Extrator";
        String icarrosExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\Icarros_Extrator";
        String vivalocalExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\VivaLocal_Extrator";
        String redebrasilmotorsExtratorDirectory="C:\\Users\\rodol_000\\CIn\\RI\\redeBrasilMotors_Extrator";

        ArrayList<String> directoriesExtratores=new ArrayList<String>();

        directoriesExtratores.add(vitalyExtratorDirectory);
        directoriesExtratores.add(meuCarangoExtratorDirectory);
        directoriesExtratores.add(mercadoLivreExtratorDirectory);
        directoriesExtratores.add(icarrosExtratorDirectory);
        directoriesExtratores.add(vivalocalExtratorDirectory);
        directoriesExtratores.add(redebrasilmotorsExtratorDirectory);

        try  {
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\rodol_000\\CIn\\RI\\idsDoc.txt"));
            int indexId=0;
        for(String dir:directoriesExtratores){
            File inputDirectory=new File(dir);

            File [] files=inputDirectory.listFiles();//files[] tem a lista de docs txt com os atributos extraidos

            for(File extratorTxt:files){//extratorTxt eh o .txt com os valores extraidos


                    BufferedReader reader = new BufferedReader(new FileReader(extratorTxt.getAbsolutePath()));
                    String linhaLida=reader.readLine();
                    String[] linha=linhaLida.split(":");
                    String nomeHtml=linha[1];
                    bw.write(nomeHtml+";"+indexId);
                    bw.newLine();
                    indexId++;
                    reader.close();


            }

        }
        bw.close();

        }catch (IOException ioe){
        ioe.printStackTrace();
    }




    }
}
