/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.app;
import antframework.common.Matrix;
import java.util.Vector;
/**
 *
 * @author enriqueareyan
 */
public class Traza {
    protected Vector<Integer> i;
    protected Vector<Matrix> M;

    Traza(){
        this.i = new Vector<Integer>();
        this.M = new Vector<Matrix>();
    }

    public void anadirTraza(int i,Matrix M){
        if(!this.i.contains(i)){
            this.i.add(i);
            this.M.add(M);
        }
    }

    public Matrix getFeromona(int i){
        return this.M.get(i);
    }

    public void imprimirTraza(){
        for(int j=0;j<this.i.size();j++){
            System.out.println("Traza de la iteracion:"+this.i.get(j));
            this.M.get(j).show();
        }
    }
}
