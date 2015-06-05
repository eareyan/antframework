/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.experiments.prepare;
import antframework.experiments.common.*;

/**
 *
 * @author enriqueareyan
 */
public class Statistics {
    public static void main(String[] args) throws Exception{
        String src = "results/TSP/2";
        Files Graphs = new Files(src,Files.NoFilter);
        System.out.println("Generando Estadisticas de los siguientes archivos...");
        System.out.println(Graphs + "---");
        
        //int cantAlgoritmos = 7;
        int cantAlgoritmos = 8;
        resAlgo[] resultados = new resAlgo[cantAlgoritmos];
        String finalOutputTimeAverage  = "", finalOutputTimeBest = "" , finalOutputErrorBest = "", finalOutputErrorAverage = "";

        for(int i=0;i<Graphs.size();i++){
            String res = Graphs.parseFile(i);
            String[] muestras = res.split("\n");
            int cantMuestras = muestras.length;

            for(int z = 0;z<cantAlgoritmos;z++){
                resultados[z] = new resAlgo(cantMuestras);
            }
            //Por cada Muestra
            for(int j=0;j<cantMuestras;j++){
                String[] resAlgo = muestras[j].split("\t");
                int cantResAlgo = resAlgo.length;
                if(cantResAlgo != (cantAlgoritmos * 2)){
                    throw new Exception("===========No Estan todos los algoritmos "+cantResAlgo+"!= "+(cantAlgoritmos*2)+" => "+Graphs.getFile(i)+"===============");
                }
                //Por cada resultado
                int t = 0;
                for(int k=0;k<cantResAlgo;k = k + 2 ){
                    resultados[t].time[j] = Double.parseDouble(resAlgo[k]);
                    resultados[t].error[j] = Double.parseDouble(resAlgo[k+1]);
                    t++;
                }
            }
            String outputHeader = Graphs.getFile(i) + "\t";
            String outputBestTime = "",outputBestError = "",outputTimeAverage = "",outputErrorAverage = "";
            for(int h=0;h<cantAlgoritmos;h++){
                //Time average
                outputTimeAverage = outputTimeAverage + resultados[h].timeAverage() + "\t";
                //Best time
                outputBestTime = outputBestTime + resultados[h].bestTime() + "\t";
                //Error Average
                outputErrorAverage = outputErrorAverage + resultados[h].errorAverage() + "\t";
                //Best error
                outputBestError = outputBestError + resultados[h].bestError() + "\t";
            }
            finalOutputTimeAverage = finalOutputTimeAverage + outputHeader + outputTimeAverage + "\n";
            finalOutputTimeBest = finalOutputTimeBest + outputHeader + outputBestTime + "\n";
            finalOutputErrorAverage = finalOutputErrorAverage + outputHeader + outputErrorAverage + "\n";
            finalOutputErrorBest = finalOutputErrorBest + outputHeader + outputBestError + "\n";
        }
        System.out.println("Tiempo Promedio \n "+finalOutputTimeAverage);
        System.out.println("Mejor Tiempo \n "+finalOutputTimeBest);
        System.out.println("Error Promedio \n "+finalOutputErrorAverage);
        System.out.println("Mejor Error \n "+finalOutputErrorBest);
        Files.writeToFile( src + "/statistics.csv", "Tiempo Promedio \n "+finalOutputTimeAverage + "Mejor Tiempo \n "+finalOutputTimeBest + "Error Promedio \n "+finalOutputErrorAverage + "Mejor Error \n "+finalOutputErrorBest);
    }
    static class resAlgo{
        public double[] time;
        public double[] error;
        public int n;

        public resAlgo(int n){
            this.n = n;
            this.time = new double[n];
            this.error = new double[n];
        }
        public double timeAverage(){
            double sum = 0;
            for(int i=0;i<this.n;i++){
                sum += this.time[i];
            }
            return sum/n;
        }
        public double errorAverage(){
            double sum = 0;
            for(int i=0;i<this.n;i++){
                sum += this.error[i];
            }
            return sum/n;
        }
        public double bestTime(){
            double best = Double.MAX_VALUE;
            for(int i=0;i<this.n;i++){
                if(this.time[i] < best){
                    best = this.time[i];
                }
            }
            return best;
        }
        public double bestError(){
            double best = Double.MAX_VALUE;
            for(int i=0;i<this.n;i++){
                if(this.error[i] < best){
                    best = this.error[i];
                }
            }
            return best;
        }
        @Override
        public String toString(){
            String res = "";
            for(int i=0;i<this.n;i++){
                res = res + this.time[i] + "\t";
            }
            res = res + "\n";
            for(int i=0;i<this.n;i++){
                res = res + this.error[i] + "\t";
            }
            return res;
        }
    }
}
