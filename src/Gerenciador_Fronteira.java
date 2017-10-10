import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;


//verificar se desejo ficar com linkedBlockQueue
public class Gerenciador_Fronteira {
    private LinkedBlockingDeque<String> fronteira;

    public Gerenciador_Fronteira() {
        this.fronteira = new LinkedBlockingDeque<String>();
    }

    public void setFronteira(LinkedBlockingDeque<String> fronteira) {
        this.fronteira = fronteira;
    }

    public Queue<String> getFronteira() {
        return fronteira;
    }

    public void addLink(String url) throws InterruptedException {
       fronteira.put(url);
    }
    public String getLink() throws InterruptedException {
        return fronteira.take();
    }
}
