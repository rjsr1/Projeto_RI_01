import java.io.*;
import java.util.ArrayList;

public class Repositorio_Paginas {

    private String urlBase;
    private ArrayList<String> paginas;

    public Repositorio_Paginas(String url_base) {
        this.urlBase = url_base;
        this.paginas = new ArrayList<String>();
    }

    public void adicionar_Pagina(String html) {
        paginas.add(html);
    }

    public String getUrlBase() {
        return urlBase;
    }



}
