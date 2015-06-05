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

public class AS extends antframework.test.SP.SP_AS implements Runnable {
    private panelGrafo S;

    public AS(){}

    public AS(Graph G,panelGrafo S,int ants,int paramInitalNode,int paramDestinationNode,int iterations,double paramEvaporacion,double paramAlfa,double paramBeta)throws Exception{
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
    }
    public void run(){
        try{
            long begin = System.currentTimeMillis();
            this.solve();
            long end = System.currentTimeMillis();
            //Reportar resultados
            S.reportResults("SH",bestSolution.toString(),f(this.getBestSolution()),end-begin);
        }catch(Exception e){
            System.out.println("Error en AntSystem = "+e);
            S.reportFailure(e);
        }
    }
}
