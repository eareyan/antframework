/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.experiments.prepare;
import antframework.experiments.common.Files;

/**
 *
 * @author enriqueareyan
 */
public class KG_Prepare {
    protected static int cantMuestras = 5;
    
    public static void main(String[] args) throws Exception{
        Files Graphs = new Files(Files.KGFilter);
        System.out.println(Graphs.toString());
    }
}
