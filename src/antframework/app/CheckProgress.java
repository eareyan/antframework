/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.app;
import antframework.common.Matrix;

/**
 *
 * @author enriqueareyan
 */
public class CheckProgress implements Runnable{
    protected antframework.algorithms.Metaheuristic R;
    protected Thread T;
    protected panelGrafo P;
    protected int iteraciones;

    public CheckProgress(int iteraciones,antframework.algorithms.Metaheuristic R,Thread T,panelGrafo P){
        this.R = R;
        this.T = T;
        this.P = P;
        this.iteraciones = iteraciones;
    }

    public void run(){
        while(this.T.isAlive()) {
            try{
                int iteracionActual = R.getCurrentIterationNumber();
                P.actualizarBarraProgreso(iteracionActual);
                Matrix M = new Matrix(R.getE().getPheromones().getRows(),R.getE().getPheromones().getColumns());
                R.getE().getPheromones().copy(M);
                P.T.anadirTraza(iteracionActual, M);
                Thread.sleep(1);
            }catch(InterruptedException e){
                System.out.println(e);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

}
