/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.experiments.prepare;
import antframework.experiments.common.Files;
import antframework.common.*;
import antframework.experiments.Dijkstra;

/**
 *
 * @author enriqueareyan
 */
public class SP_Prepare {
protected static int cantMuestras = 5;

    public static void main(String[] args) throws Exception{
        Files Graphs = new Files(Files.SPFilter);
        System.out.println(Graphs);
        long times      = 0;
        double time_avg = 0;
        double results  = 0;
        double results_avg = 0;

        for (int i = 0; i < Graphs.size(); i++) {
            /* Pasear por cada archivo preprocesando */
            Graph G = new Graph(Graphs.getFullFile(i));
            //TSP.m = G.getM();
            System.out.println("file: " + G.getFileSource());
            time_avg = 0;
            results_avg = 0;
            for(int j = 0; j < TSP_Prepare.cantMuestras; j++){
                times = 0;
                //NO SEGUI HACIENDOLO PORQUE NO VALE LA PENA
                //DIJKSTRA SE EJECUTA MUY RAPIDO Y POR LO TANTO ES MUY POCO LO QUE SE AHORRA
                for(int k = 0; k<G.getM().getRows(); k++){
                    for(int l = 0; l<G.getM().getColumns(); l++){
                        long begin = System.currentTimeMillis();
                        double length = Dijkstra.dijkstra(G, k, l);
                        long end = System.currentTimeMillis();
                        times = (end - begin);
                        System.out.println("("+k+","+l+") = "+length);
                    }
                }

                //times = 0;
                //results = 0;
                /* Por la cantidad de iteraciones*/
                //backtracking
                /*TSP.MIN = Double.MAX_VALUE;
                TSP.visited = new int[G.getM().getColumns()];
                long begin = System.currentTimeMillis();
                TSP.tsp(0,0,TSP.visited.length,0.0);
                long end = System.currentTimeMillis();
                times = (end - begin);
                results = TSP.MIN;
                if(results > 1000000){//Asumo que un resultado mayor que 1000000 (un millon) es invalido
                    System.out.println("El resultado no es valido");
                    Files.writeToFile("results/TSP/backtracking/"+Graphs.getFile(i), -1 + "\t" + -1);
                    break;
                }
                System.out.println("times = "+times+"\tresults = "+ results);
                //end backtracking
                time_avg += times;
                results_avg += results;*/
            }
            //System.out.printf("Average = %f ", time_avg / TSP_Prepare.cantMuestras);
            //System.out.printf("Error = %f \n", results_avg / TSP_Prepare.cantMuestras);
            //Guardar los resultados en un archivo
            //Files.writeToFile("results/TSP/backtracking/"+Graphs.getFile(i),time_avg / TSP_Prepare.cantMuestras + "\t" + results_avg / TSP_Prepare.cantMuestras);
        }
    }
}
