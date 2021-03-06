/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.app;
import antframework.common.*;
/**
 *
 * @author enriqueareyan
 */

public class RAS extends antframework.test.SP.SP_RAS implements Runnable {
    private panelGrafo S;

    public RAS(){}

    public RAS(Graph G,panelGrafo S,int ants,int paramInitalNode,int paramDestinationNode,int iterations,double paramEvaporacion,double paramAlfa,double paramBeta,double paramEpsilon)throws Exception{
        super();
        this.S = S;
        /* Set enviroment */
        this.setE(new Enviroment(ants /*number of ants */, G /* Graph */ , true /* random pheromone trail */));
        /* Set params */
        this.setParam(initialNode  , paramInitalNode);
        this.setParam(destinationNode , paramDestinationNode);
        this.setParam(maxNumIterations , iterations);
        this.setParam(pheromonesEvaporationRate, paramEvaporacion);
        this.setParam(alpha, paramAlfa);
        this.setParam(beta, paramBeta);
        this.setParam(epsilon, paramEpsilon);
    }
    public void run(){
        try{
            long begin = System.currentTimeMillis();
            this.solve();
            long end = System.currentTimeMillis();
            //Reportar resultados
            S.reportResults("SH Rango",bestSolution.toString(),f(this.getBestSolution()),end-begin);
        }catch(Exception e){
            System.out.println("Error en RankAntSystem = "+e);
            S.reportFailure(e);
        }
    }
}
