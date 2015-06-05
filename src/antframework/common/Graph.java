/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.common;

import antframework.*;
import java.io.*;

/**
 * This class belong to the core classes of the Ant Framework.
 *
 * It represent the problem being solved.
 * It is composed of a Matrix containing the relation between nodes in the graph.
 *
 * @version 1
 * @author Enrique Areyan, enrique3 at gmail.com
 *
 */
public class Graph {

    /** The most important piece of information of this class: the matrix which represent the problem graph */
    private Matrix M;

    /** If the graph is loaded from a file, this will be a holder for the file's source location */
    protected String fileSource = "";

    /**
     *	Constructor. Creates a matrix from file <code>fileSource</code>
     *	@param fileSource location of file
     *	@throws Exception if an error ocurred loading file.
     */
    public Graph(String fileSource) throws Exception{
        this.setFileSource(fileSource);
        this.setM(new Matrix(fileSource));
    }

    /**
     *	Constructor. Creates a grpah (matrix) and initialize with the data on <code>data</code>
     *	@param r number of rows
     *	@param c number of cols
     *	@param d values to initialize the matrix.
     */
    public Graph(int r,int c,double[] d){
        this.setM(new Matrix(r,c,d));
    }

	/**
     * @return The matrix M
     */
    public Matrix getM() {
        return M;
    }

	/**
     * Sets the Matrix M which holds the graph information
     * @param M contains a matrix with the graph
     */
    public void setM(Matrix M) {
        this.M = M;
    }

	/**
     * @return The file source
     */
    public String getFileSource() {
        return fileSource;
    }

	/**
     * Sets the file source of the matrix to be loaded
     * @param fileSource contains the location of the file
     */
    public void setFileSource(String fileSource) {
        this.fileSource = fileSource;
    }

	public void save(String path) throws IOException{
		PrintStream out = new PrintStream(new FileOutputStream(path));
		out.println(M);
		out.close();
	}
}
