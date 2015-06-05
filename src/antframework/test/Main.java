/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.test;

import antframework.test.SP.*;
import antframework.test.TSP.*;
import java.io.* ;

/**
 *
 * @author enriqueareyan
 */
public class Main {
    public static void main(String[] args) {
        try{
       //TEST SHORTEST PATH
            /* Test AntSystem */
            SP_AS AntSystemProblem = new SP_AS("/Users/enriqueareyan/Documents/Universidad/Tesis/Framework/AntFramework/graph/cg5x5_0.graph",0,1,0);
            AntSystemProblem.solve();  /* Solve problem (run algorithm) */


            /* Test Elitist-AntSystem */
            //SP_EAS ElitistAntSystemProblem = new SP_EAS();
            //ElitistAntSystemProblem.solve();  /* Solve problem (run algorithm) */

            /* Test Rank-AntSystem */
            //SP_RAS RankAntSystemProblem = new SP_RAS();
            //RankAntSystemProblem.solve();  /* Solve problem (run algorithm) */

            /* Test AntColonySystem */
            //SP_ACS AntColonySystem = new SP_ACS();
            //AntColonySystem.solve();  /* Solve problem (run algorithm) */

            /* Test ANTQ */
            //SP_ANTQ ANTQ = new SP_ANTQ();
            //ANTQ.solve();  /* Solve problem (run algorithm) */

            /* Test MMAS */
            //SP_MMAS MMAS = new SP_MMAS();
            //MMAS.solve();  /* Solve problem (run algorithm) */

        //Test TSP
            /* Test AntSystem */
            //TSP_AS AntSystemProblem = new TSP_AS();
            //AntSystemProblem.solve();  /* Solve problem (run algorithm) */
            
            /* Test Elitist-AntSystem */
            //TSP_EAS ElitistAntSystemProblem = new TSP_EAS();
            //ElitistAntSystemProblem.solve();  /* Solve problem (run algorithm) */

            /* Test Rank-AntSystem */
            //TSP_RAS RankAntSystemProblem = new TSP_RAS();
            //RankAntSystemProblem.solve();  /* Solve problem (run algorithm) */

            /* Test AntColonySystem */
            //TSP_ACS AntColonySystem = new TSP_ACS();
            //AntColonySystem.solve();  /* Solve problem (run algorithm) */

            /* Test ANTQ */
            //TSP_ANTQ ANTQ = new TSP_ANTQ();
            //ANTQ.solve();  /* Solve problem (run algorithm) */

            /* Test MMAS */
            //TSP_MMAS MMAS = new TSP_MMAS();
            //MMAS.solve();  /* Solve problem (run algorithm) */
        }catch(Exception e){
            System.out.println("Some exception occurred, here are the details: \n\n\t" + e.toString());
        }
    }

}
