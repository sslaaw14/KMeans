/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Kmeans2 {
    static int[][] setData = new int[75][3];
    static int[][] Data = new int[75][3];
    static int[][] datafix = new int[75][3];
    static int[][] centroid = new int[5][2];
    static int[][] newCentroid = new int[5][2];
    static int cluster;
    //static double[] distance = new double[1000];
    //static double average[][];
    static boolean status = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner inputk = new Scanner(System.in);
        
        System.out.println("Input jumlah cluster :");
        cluster = inputk.nextInt();
        
        readData();
        getRandom();
        datafix = clustering();
        for(int i=0;i<75;i++){
            for(int j=0;j<3;j++){
                System.out.println(datafix[i][j]);
            }
            System.out.println("\n");
        }
    }
    
    public static void readData() {
        try 
        { 
            FileInputStream fstream_school = new FileInputStream("F:\\Kuliah\\Semester 5\\Machine Learning\\ruspini.txt"); 
            DataInputStream data_input = new DataInputStream(fstream_school); 
            BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input)); 
            String str_line; 
            
            int baris=0;
            int kolom=0;
            String[] split = null;
            while ((str_line = buffer.readLine()) != null) 
            { 
                split = str_line.split(",");
                for (kolom=0; kolom<split.length;kolom++) {
                    int data = Integer.parseInt(split[kolom]);
                    setData[baris][kolom] = data;
                }
                baris++;
            }
        } catch(IOException ex) {}
    }
    
    public static void getRandom() {
        Random acak = new Random();
        for(int i=0;i<cluster;i++){
            centroid[i][0]=acak.nextInt(100);
            centroid[i][1]=acak.nextInt(100);
        }
        
        System.out.println("Centroid Lama : ");
        for(int i=0;i<cluster;i++){
            for(int j=0;j<centroid[i].length;j++){
                System.out.println(centroid[i][j]);
            }
            System.out.println("\n");
        }
    }
    
    public static int[][] clustering(){
        while(status!=true){
            allDistance();
            System.out.println("Centroid Baru :");
            getNewCentroid();
            status = similarity();
        }
        
        return Data;
    }
    
    public static void allDistance(){
        double distance[] = new double[cluster];
        int i=0;
        for(int k=0;k<setData.length;k++){
            for(int j=0;j<centroid.length;j++){
                distance[j] =  getDistance(k, j);
            }
            i=findCentroid(distance);
            Data[k][0] = setData[k][0];
            Data[k][1] = setData[k][1];
            Data[k][2] = i;       
        }
    }
    
    public static double getDistance(int k, int l){
        double totalDistance = 0;
        double jarakx = 0;
        double jaraky = 0;
        
        jarakx = Math.pow(setData[k][0] - centroid[l][0], 2);
        jaraky = Math.pow(setData[k][1] - centroid[l][1], 2);
        
        totalDistance = Math.sqrt(jarakx+jaraky);
        
        return totalDistance;
    }
    
    public static int findCentroid(double[] jarak){
        double minimum = 0;
        int i = 0;
        for(int k=0;k<jarak.length;k++){
            if(minimum==0){
                    minimum = jarak[k];
                }
                
                if(jarak[k] < minimum){
                    minimum = jarak[k];
                    i = k+1;
                }
        }
        
        return i;
    }
    
    public static void getNewCentroid(){
        float sumx, sumy;
        int i;
        
        for(int j=0; j<cluster; j++){
            sumx=0;
            sumy=0;
            i=0;
            for(int l=0;l<Data.length;l++){
                if(Data[l][2]==j){
                    sumx = sumx + Data[l][0];
                    sumy = sumy + Data[l][1];
                    i++;
                }
            }
            newCentroid[i][0] = (int) (sumx/Data.length);
            newCentroid[i][1] = (int) (sumy/Data.length);
            System.out.println(newCentroid[j][0]+" "+newCentroid[j][1]);
        }
        
    }
    
    public static boolean similarity(){
        boolean stat=true;
        for(int i=0;i <cluster; i++){
            for(int j=0;j<2;j++){
                if(centroid[i][j]!=newCentroid[i][j]){
                    stat=false;
                    centroid[i][j] = newCentroid[i][j];
                }
            }
        }   
        return stat;
    }
}
