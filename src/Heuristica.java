import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Heuristica {
    public static void main(String [] args){
        ArrayList<String> marcas=new ArrayList<String>();
        String s="Alfa Romeo \n" +
                "Aston Martin \n" +
                "Audi\n" +
                "Bentley \n" +
                "Benz \n" +
                "BMW \n" +
                "Bugatti  \n" +
                "Cadillac \n" +
                "Chevrolet \n" +
                "Chrysler \n" +
                "Citroën \n" +
                "Corvette  \n" +
                "DAF \n" +
                "Dacia \n" +
                "Daewoo \n" +
                "Daihatsu \n" +
                "Datsun \n" +
                "De Lorean \n" +
                "Dino \n" +
                "Dodge  \n" +
                "Farboud \n" +
                "Ferrari \n" +
                "Fiat \n" +
                "Ford\n" +
                "Honda \n" +
                "Hummer \n" +
                "Hyundai\n" +
                "Jaguar \n" +
                "Jeep  \n" +
                "KIA \n" +
                "Koenigsegg  \n" +
                "Lada \n" +
                "Lamborghini \n" +
                "Lancia \n" +
                "Land Rover \n" +
                "Lexus \n" +
                "Ligier \n" +
                "Lincoln \n" +
                "Lotus  \n" +
                "Martini \n" +
                "Maserati \n" +
                "Maybach \n" +
                "Mazda \n" +
                "McLaren \n" +
                "Mercedes-Benz \n" +
                "Mini \n" +
                "Mitsubishi  \n" +
                "Nissan \n" +
                "Noble  \n" +
                "Opel  \n" +
                "Peugeot \n" +
                "Pontiac \n" +
                "Porsche  \n" +
                "Renault \n" +
                "Rolls-Royce  \n" +
                "Saab \n" +
                "Seat \n" +
                "Škoda \n" +
                "Smart \n" +
                "Spyker  \n" +
                "Subaru \n" +
                "Suzuki  \n" +
                "Toyota  \n" +
                "Vauxhall \n" +
                "Volkswagen \n" +
                "Volvo";
       String [] marcasArray=s.split("\n");
       for(String m:marcasArray){
           marcas.add(m);
       }
    }
}
