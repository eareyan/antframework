/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.test;
import antframework.test.SP.*;
import antframework.experiments.Dijkstra;
import antframework.common.*;

/**
 *
 * @author enriqueareyan
 */
public class TSP_Test {
    public static void main(String[] args) {
        try{
            String graphLocation = "/Users/enriqueareyan/Documents/Universidad/Tesis/Framework/AntFramework/graph/g20x20_0.7[1].graph";
            Graph g = new Graph(graphLocation);
            int ng = (g.getM().getRows() * g.getM().getColumns()) - g.getM().getRows();
            double error = 0, total_error = 0;
            for(int i=0;i<5/*g.getM().getRows()*/;i++){
                for(int j=0;j<5/*g.getM().getColumns()*/;j++){
                    if(i!=j){
                        double lenght = Dijkstra.dijkstra(g,i,j);
                        SP_AS AntSystemProblem = new SP_AS(graphLocation,i,j,3);
                        AntSystemProblem.solve();  /* Solve problem (run algorithm) */
                        error = (lenght - AntSystemProblem.f(AntSystemProblem.getBestSolution())) * (lenght - AntSystemProblem.f(AntSystemProblem.getBestSolution()));
                        total_error += error;
                        System.out.print("("+i+","+j+") \t DK = "+lenght+"\t");
                        System.out.println("AS = "+AntSystemProblem.f(AntSystemProblem.getBestSolution())+"\t error = "+error);
                    }
                }
            }
            System.out.println("total_error = "+total_error);
            System.out.println("ng = "+ng);
            System.out.println("total_error / ng = " + total_error / ng);
        }catch(Exception e){
            System.out.println("Some exception occurred, here are the details: \n\n\t" + e.toString());
        }
    }
}
