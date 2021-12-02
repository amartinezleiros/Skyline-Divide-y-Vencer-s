import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javafx.application.Application;

public class Skyline {

    public static ArrayList < Edificio > ListaEdificios = new ArrayList < Edificio > ();
    public static File archivo = null;
    public static FileReader fr = null;
    public static BufferedReader br = null;
    public static javax.swing.JPanel mainPanel;
    static boolean trazado = false;
    static boolean imprimir = false;
    
    //SEPARAR PROBLEMAS EN SUBPROBLEMAS Y COMBINAR
    public static ArrayList<Punto_Skyline> dividir(ArrayList < Edificio > edificios) {
        int n = edificios.size();
        int h1 = 0, h2 = 0, number = 0;
        boolean bool = false;
        ArrayList <Punto_Skyline> Lista = new ArrayList < Punto_Skyline > ();
        ArrayList <Punto_Skyline> ListaFinal = new ArrayList < Punto_Skyline > ();
        ArrayList <List<Punto_Skyline>> ListaDeListas = new ArrayList <List<Punto_Skyline>> ();
        ArrayList <Punto_Skyline> ListaDeListas1 = new ArrayList <Punto_Skyline> ();
        ArrayList <Punto_Skyline> ListaDeListas2 = new ArrayList <Punto_Skyline> ();
        
        if (n == 1) {
            Lista.add(new Punto_Skyline(edificios.get(0).getx1(), edificios.get(0).geth()));
            Lista.add(new Punto_Skyline(edificios.get(0).getx1(), 0));
            return Lista;
        }else{
            for (Edificio e: edificios) {
                List <Punto_Skyline> lista = new ArrayList <Punto_Skyline> ();
                lista.add(new Punto_Skyline(e.getx1(), e.geth()));
                lista.add(new Punto_Skyline(e.getx2(), 0));
                ListaDeListas.add(lista);
            }
        }
        int traza = 1;
        while(ListaDeListas.size() > 1){
            if(trazado == true){
                System.out.println("Llamada recursiva número " + traza + ": (" + ListaDeListas.get(0).get(0).x + "," + ListaDeListas.get(0).get(0).y + ")");
                traza++;
            }
            if(ListaDeListas.get(0).get(0).getX() < ListaDeListas.get(1).get(0).getX()){
                h1 = ListaDeListas.get(0).get(0).getY();
                if(bool){ListaDeListas2.add(new Punto_Skyline(ListaDeListas.get(0).get(0).getX(), Math.max(h1,h2)));}else{
                    ListaDeListas1.add(new Punto_Skyline(ListaDeListas.get(0).get(0).getX(), Math.max(h1,h2)));
                }
                ListaDeListas.get(0).remove(0);
            }else if(ListaDeListas.get(1).get(0).getX() < ListaDeListas.get(0).get(0).getX()){
                h2 = ListaDeListas.get(1).get(0).getY();
                if(bool){ListaDeListas2.add(new Punto_Skyline(ListaDeListas.get(1).get(0).getX(), Math.max(h1,h2)));}else{
                    ListaDeListas1.add(new Punto_Skyline(ListaDeListas.get(1).get(0).getX(), Math.max(h1,h2)));
                }
                ListaDeListas.get(1).remove(0);
            }else if(ListaDeListas.get(0).get(0).getX() == ListaDeListas.get(1).get(0).getX()){
                h1=ListaDeListas.get(0).get(0).getY();
                h2=ListaDeListas.get(1).get(0).getY();
                if(bool){ListaDeListas2.add(new Punto_Skyline(ListaDeListas.get(0).get(0).getX(), Math.max(h1,h2)));}else{
                    ListaDeListas1.add(new Punto_Skyline(ListaDeListas.get(0).get(0).getX(), Math.max(h1,h2)));
                }
                ListaDeListas.get(0).remove(0);
                ListaDeListas.get(1).remove(0);
            }

            if(ListaDeListas.get(0).isEmpty() && ListaDeListas.get(1).isEmpty()){
                ListaDeListas.remove(1);
                ListaDeListas.remove(0);
                bool = true;   
            }else if(ListaDeListas.get(0).isEmpty()){
                ListaDeListas.remove(0);
                if(bool){for(Punto_Skyline i: ListaDeListas.get(0)){
                        ListaDeListas2.add(i);
                    }}else{
                    for(Punto_Skyline i: ListaDeListas.get(0)){
                        ListaDeListas1.add(i);
                    }}
                ListaDeListas.remove(0);
                bool = true;      
            }else if(ListaDeListas.get(1).isEmpty()){
                ListaDeListas.remove(1);
                if(bool){for(Punto_Skyline i: ListaDeListas.get(0)){
                        ListaDeListas2.add(i);
                    }}else{
                    for(Punto_Skyline i: ListaDeListas.get(0)){
                        ListaDeListas1.add(i);
                    }}
                ListaDeListas.remove(0);
                bool = true;   
            }       
            if(!ListaDeListas1.isEmpty() && !ListaDeListas2.isEmpty()){
                ListaDeListas1 = Combinar(ListaDeListas1, ListaDeListas2);
                ListaDeListas2.clear();       
            }
        }       

        Lista = ListaDeListas1;

        return Lista;
    }    

    //COMBINAR VARIOS SUBPROBLEMAS
    public static ArrayList <Punto_Skyline> Combinar(ArrayList <Punto_Skyline> LDL1, ArrayList <Punto_Skyline> LDL2){
        ArrayList <Punto_Skyline> LDLfinal = new ArrayList <Punto_Skyline>();
        int h1 = 0, h2 = 0;

        while(!LDL1.isEmpty() && !LDL2.isEmpty()){
            if(LDL1.get(0).getX() < LDL2.get(0).getX()){
                h1 = LDL1.get(0).getY();
                LDLfinal.add(new Punto_Skyline(LDL1.get(0).getX(), Math.max(h1,h2)));
                LDL1.remove(0);
            }else if(LDL2.get(0).getX() < LDL1.get(0).getX()){
                h2 = LDL2.get(0).getY();
                LDLfinal.add(new Punto_Skyline(LDL2.get(0).getX(), Math.max(h1,h2)));                
                LDL2.remove(0);
            }else if(LDL1.get(0).getX() == LDL2.get(0).getX()){
                h1=LDL1.get(0).getY();
                h2=LDL2.get(0).getY();
                LDLfinal.add(new Punto_Skyline(LDL1.get(0).getX(), Math.max(h1,h2)));    
                LDL1.remove(0);
                LDL2.remove(0);
            }

            if(LDL1.isEmpty()){
                for(Punto_Skyline i: LDL2){
                    LDLfinal.add(i);
                }
            }else if(LDL2.isEmpty()){
                for(Punto_Skyline i: LDL1){
                    LDLfinal.add(i);
                } 
            }
        }

        int x = 0;
        while (x < LDLfinal.size())
        {
            boolean bool = true;
            int i = x + 1;
            while ((i < LDLfinal.size()) &&  bool)
            {
                if (LDLfinal.get(x).getY() == LDLfinal.get(i).getY())
                {
                    bool = true;
                    LDLfinal.remove(i);
                }
                else
                {
                    bool = false;
                }
            }
            x += 1;
        }
        h1=0;
        h2=0;
        return LDLfinal;
    }


    
    
    
    
    public static void main(String[] args){
        long inicio = System.currentTimeMillis();
        if(args.length < 1){
            System.out.print("No hay suficientes argumentos para ejecutar el programa. Al menos necesitamos un fichero de entrada.");
            System.exit(0);
        }
        
        int contador = 0;
        for (String x: args) {
            if (x.equals("-h")) {
                System.out.println("SINTAXIS: skyline [-t][-s][-h] [fichero entrada] [fichero salida]\n     -t                  Traza cada llamada recursiva y sus parámetros\n     -s                  Crea un gráfico con el skyline final\n     -h                  Muestra esta ayuda\n     [fichero entrada]   Conjunto de edificios de la ciudad\n     [fichero salida]    Secuencia que representan el skyline de la ciudad\n");
                contador++;
            } else if (x.equals("-t")) {
                trazado = true;
                contador++;
            } else if (x.equals("-s"))
            {
                imprimir = true;
                contador++;
            }
        }
        //Montar ArrayList con los edificios
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(args[contador]);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea = br.readLine();
            while (linea != null) {
                String[] parts = linea.split(",");
                int x1 = Integer.parseInt(parts[0]);
                int x2 = Integer.parseInt(parts[1]);
                int h = Integer.parseInt(parts[2]);
                ListaEdificios.add(new Edificio(x1, x2, h));
                linea = br.readLine();
            }
        } catch (Exception e) {
            System.out.print("No hay un fichero de entrada, se cierra el programa");
            System.exit(0);
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        ArrayList<Punto_Skyline> Solucion = new ArrayList<Punto_Skyline>(dividir(ListaEdificios));
        int h = 1;
        if(imprimir == true){
        Grafico1 frame = new Grafico1(Solucion);
        frame.setVisible(true);
        try
        {
            Thread.sleep(5000);
        }
        catch (java.lang.InterruptedException ie)
        {
            ie.printStackTrace();
        }
        }
        try {
        if(!args[contador+1].isEmpty()){      
                    
            File file = new File(args[contador+1]);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(Punto_Skyline i: Solucion){
            bw.write("(" + i.getX() + "," + i.getY() + ") ");
            }
            bw.close();
        }else{
        for(Punto_Skyline i: Solucion){
            System.out.print("(" + i.getX() + "," + i.getY() + ") ");
        }
        }
        }catch (Exception e) {
            //System.out.print("Hay un error en la ruta del fichero de salida, se escribirá la solucion en consola");
            System.out.print("\n\n");
            for(Punto_Skyline i: Solucion){
            System.out.print("(" + i.getX() + "," + i.getY() + ") ");
            }
        }
        long fin = System.currentTimeMillis();
         
        long tiempo = fin-inicio;
        
        System.out.println("\n" + tiempo +" milisegundos en ejecutarse");
    
        System.exit(0);
    }
}