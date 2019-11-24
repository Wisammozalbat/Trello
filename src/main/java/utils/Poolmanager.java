/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;

public class Poolmanager {

    private static ArrayList<DBConnection> pool = new ArrayList<>();
    private static Poolmanager newPM;
    private static DBConnection conex = new DBConnection();
    private static int grow = 5, top = 10, limit = 80;

    private Poolmanager(DBConnection conex) {
        Poolmanager.conex = conex;
        Inicializacion(top, grow, limit);
    }

    public static Poolmanager getSingletonPM() {
        if (newPM == null) {
            return (newPM = new Poolmanager(conex));
        } else {
            System.out.println("Este objeto ya se ha instanciado.");
            return null;
        }
    }

    private void Inicializacion(int maximo, int aumento, int limite) {
        Poolmanager.top = maximo;
        Poolmanager.grow = aumento;
        Poolmanager.limit = limite;
        for (int i = 0; i < top; i++) {
            Poolmanager.pool.add(conex);
        }
    }

    public synchronized DBConnection getConex() {
        if (!Poolmanager.pool.isEmpty()) {
            return connectToDB();
        } else if (top < limit) {
            top = top + grow;
            for (int i = 0; i < grow; i++) {
                Poolmanager.pool.add(conex);
            }
            return connectToDB();
        } else {
            detener();
            return connectToDB();
        }
    }

    private void detener() {
        while (Poolmanager.pool.isEmpty()) {
            System.out.print("");
        }
    }

    private synchronized DBConnection connectToDB() {
        DBConnection conect;
        conect = Poolmanager.pool.get(0);
        Poolmanager.pool.remove(0);
        return conect;
    }

    public static int conexionesActivas() {
        int ActiveCon = top - Poolmanager.pool.size();
        return ActiveCon;
    }

    public static synchronized void returnConexDisponibles() {
        Poolmanager.pool.add(conex);
    }
    
}
