package ufv.dis.plantillaBack.back;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LectorJSON {

    ArrayList<IP> listaIP;
    public LectorJSON() throws FileNotFoundException {
        this.listaIP = LectorJSON();
    }

    //Se declara un método que lee el json y lo convierte en un objeto de la clase datosZonasBasicas a través de la librería Gson
    public ArrayList<IP> LectorJSON() throws FileNotFoundException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader br = new BufferedReader(new FileReader("LocalizaIP.json"));
        Type listType = new TypeToken<ArrayList<IP>>(){}.getType();
        listaIP = gson.fromJson(br, listType);
        return listaIP;
    }
}
