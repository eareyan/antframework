/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.experiments.prepare;
import antframework.experiments.common.Files;
import antframework.experiments.TSP;
import antframework.common.*;


/**
 *
 * @author enriqueareyan
 */
public class TSP_Prepare {
    
    protected static int cantMuestras = 5;

    public static void main(String[] args) throws Exception{
        Files Graphs = new Files(Files.TSPFilter);
        System.out.println(Graphs);
        long times      = 0;
        double time_avg = 0;
        double results  = 0;
        double results_avg = 0;

        for (int i = 21; i < Graphs.size(); i++) {
            /* Pasear por cada archivo preprocesando */
            Graph G = new Graph(Graphs.getFullFile(i));
            TSP.m = G.getM();
            System.out.println("file: " + G.getFileSource());
            time_avg = 0;
            results_avg = 0;
            for(int j = 0; j < TSP_Prepare.cantMuestras; j++){
                times = 0;
                results = 0;
                /* Por la cantidad de iteraciones*/
                //backtracking
                TSP.MIN = Double.MAX_VALUE;
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
                results_avg += results;
            }
            System.out.printf("Average = %f ", time_avg / TSP_Prepare.cantMuestras);
            System.out.printf("Error = %f \n", results_avg / TSP_Prepare.cantMuestras);
            //Guardar los resultados en un archivo
            Files.writeToFile("results/TSP/backtracking/"+Graphs.getFile(i),time_avg / TSP_Prepare.cantMuestras + "\t" + results_avg / TSP_Prepare.cantMuestras);
        }
    }
}
