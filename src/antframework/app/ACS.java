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

public class ACS extends antframework.test.SP.SP_ACS implements Runnable {
    private panelGrafo S;

    public ACS(){}

    public ACS(Graph G,panelGrafo S,int ants,int paramInitalNode,int paramDestinationNode,int iterations,double paramBeta,double paramCandidatos, double paramR0, double paramTau, double paramRo_1,double paramRo_2)throws Exception{
        super();
        this.S = S;
        /* Set enviroment */
        this.setE(new Enviroment(ants /*number of ants */, G /* Graph */ , true /* random pheromone trail */));
        /* Set params */
        this.setParam(initialNode  , paramInitalNode);
        this.setParam(destinationNode , paramDestinationNode);
        this.setParam(maxNumIterations , iterations);
        this.setParam(beta , paramBeta);
        this.setParam(maxCandidates , paramCandidatos);
        this.setParam(r_0 , paramR0);
        this.setParam(ro_1 , paramRo_1);
        this.setParam(ro_2 , paramRo_2);
        this.setParam(tau_0 , paramTau);
    }
    public void run(){
        try{
            long begin = System.currentTimeMillis();
            this.solve();
            long end = System.currentTimeMillis();
            //Reportar resultados
            S.reportResults("SCH",bestSolution.toString(),f(this.getBestSolution()),end-begin);
        }catch(Exception e){
            System.out.println("Error en AntColonySystem = "+e);
            S.reportFailure(e);
        }
    }
}
