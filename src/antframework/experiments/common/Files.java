/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.experiments.common;
import java.io.*;
import java.util.*;
/**
 *
 * @author enriqueareyan
 */
public class Files {
    public static final String defaultSrcDir = "graph/";

    public static final int NoFilter = 0;
    public static final int TSPFilter = 1;
    public static final int SPFilter = 2;
    public static final int KGFilter = 3;

    protected String srcDir;
    protected Vector<String> files;

    public Files(int Filter){
        this.setSrcDir(Files.defaultSrcDir);
        this.fetchFiles(Filter);
    }
    public Files(String srcDir, int Filter){
        this.setSrcDir(srcDir);
        this.fetchFiles(Filter);
    }
    public String getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(String srcDir) {
        this.srcDir = srcDir;
    }

    public String getFile(int i) {
        return files.get(i);
    }

    public String getFullFile(int i){
        return Files.defaultSrcDir + this.getFile(i);
    }

    public void setFiles(Vector<String> files) {
        this.files = files;
    }
    public int size(){
        return this.files.size();
    }
    public boolean filterForTSPFiles(String file){
        String prefix = file.substring(0, 2);
        if(prefix.compareTo("kg") == 0){ //Si es grafo de caballo, no se incluye
            return false;
        }else if(prefix.compareTo("cg") == 0){// Si es complete graph
            try{
                if(Integer.parseInt(file.substring(2, 4)) > 20 ){ // Si es mayor que 20 filtarlo
                    return false;
                }
            }catch(NumberFormatException e){//puede pasar cuando el grafo es 5x5
                return true;
            }
        }else{
            try{
                if(Integer.parseInt(file.substring(1, 3)) > 20 ){ // Si es mayor que 20 filtarlo
                    return false;
                }
            }catch(NumberFormatException e){//puede pasar cuando el grafo es 5x5
                return true;
            }
        }
        return true;
    }
    public boolean filterForSPFiles(String file){
        String prefix = file.substring(0, 2);
        if(prefix.compareTo("kg") == 0){ //Si es grafo de caballo, no se incluye
            try{
                if(Integer.parseInt(file.substring(2, 4)) >= 10 ){ // Si es mayor que 10 filtarlo
                    return false;
                }
            }catch(NumberFormatException e){//puede pasar cuando el grafo es 3x3, 5x5, 6x6 y 8x8
                return true;
            }
        }
        return true;
    }
    public boolean filterForKGFiles(String file){
        String prefix = file.substring(0, 2);
        if(prefix.compareTo("kg") == 0){ //Si es grafo de caballo
            return true;
        }else{
            return false;
        }
    }
    public void fetchFiles(int Filter){
        /* Buscar todos los grafos */
        File f1 = new File(this.getSrcDir());
        File[] strFilesDirs = f1.listFiles();
        Vector<String> graphLocations = new Vector<String>();
        for (int i = 0; i < strFilesDirs.length; i++) {
                if (strFilesDirs[i].isFile() && !strFilesDirs[i].getName().startsWith(".")) {
                    /* Filtrar segun sea el caso */
                        switch(Filter){
                            case 0: //Sin Filtro
                                graphLocations.add(strFilesDirs[i].getName());
                            break;
                            case Files.TSPFilter: //Filtro TSP
                                if(filterForTSPFiles(strFilesDirs[i].getName())){
                                    graphLocations.add(strFilesDirs[i].getName());
                                }
                            break;
                            case Files.SPFilter:
                                if(filterForSPFiles(strFilesDirs[i].getName())){
                                    graphLocations.add(strFilesDirs[i].getName());
                                }
                            case Files.KGFilter:
                                if(filterForKGFiles(strFilesDirs[i].getName())){
                                    graphLocations.add(strFilesDirs[i].getName());
                                }
                            break;
                        }
                }
        }
        this.setFiles(graphLocations);
    }
    public String parseFile(int i){
        String res = "";
        try{
            FileInputStream fstream = new FileInputStream(this.srcDir + "/" + this.getFile(i));
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
              //System.out.println (strLine);
              res = res + strLine + "\n";
            }
            in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
        //System.out.println();
        return res;
    }
    public static void writeToFile(String where, String data)throws Exception{
        Writer output = null;
        File file = new File(where);
        output = new BufferedWriter(new FileWriter(file));
        output.write(data);
        output.close();
    }
    public static cache readFromFile(String fileSrc) throws Exception{
        FileInputStream fstream = new FileInputStream(fileSrc);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine[];
        strLine = br.readLine().split("\t");
        in.close();
        Double time = Double.parseDouble(strLine[0]);
        cache res = new cache(time.longValue(), Double.parseDouble(strLine[1]));
        return res;
    }
    @Override
    public String toString(){
        String objToString = "";
        for(int i=0;i<this.size();i++){
            objToString = objToString + this.getFile(i) + "\n";
        }
        return objToString;
    }
    public static class cache{
        public long time;
        public double result;
        public cache(long time,double result){
            this.time = time;
            this.result = result;
        }
    }

}
