package com.example.monet.weaapp;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by nton on 4/9/2018.
 */


public class MyCSV extends AppCompatActivity {
   /* public void ReadFile (String fileName){
        AssetManager ast = getAssets();
        BufferedReader reader = null;
        String line = null;
        try{
            reader = new BufferedReader(new
                    InputStreamReader(ast.open(fileName),"UTF-8"));
            while ((line=reader.readLine()) != null){
                String[] separated = line.split(";");
                if(separated.length ==2){

                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if(reader != null) reader.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }*/
}
