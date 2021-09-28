/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcmju
 */
public class serverNodeN1 {

    public static void main(String[] args) throws IOException {

        // Puerto del nodo
        ServerSocket servidor;
        int puerto = 5000;
        servidor = new ServerSocket(puerto);

        Thread hilo;

        // Socket del cliente
        Socket cliente;

        while (true) {
            System.out.println("Waiting ...");
            cliente = servidor.accept();

            // Para manejar posibles múltiples conexiones al nodo
            hilo = new Thread(new handlerClient(cliente));
            hilo.start();
        }
    }

    public static class handlerClient implements Runnable {

        DataInputStream entrada;
        DataOutputStream salida;

        // Socket del cliente
        Socket socket = null;

        public handlerClient(Socket s) {
            socket = s;
            try {
                entrada = new DataInputStream(socket.getInputStream());
                salida = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(serverNodeN1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            try {
                String cadena;
                while (true) {
                    StringBuffer sb = new StringBuffer(40);
                    cadena = entrada.readUTF();
                    System.out.println("Mensaje recibido: " + cadena);
                    int cl = cadena.length();
                    System.out.println("cl "+cl);
                    
                    // Objeto de la clase fileMethods
                    fileMethods archivo = new fileMethods();
                    
                    // Escribe el mensaje en el archivo
                    archivo.setMensaje(cadena);
                    archivo.setFileName("./archivoDeTramas.txt");
                    archivo.writeFile();
                    
                    switch (cl) {
                        // Save request desde el cliente
                        case 36:
                            // Identificar al cliente
                            
                            
                            String c1 = cadena.substring(0, 12);
                            String c2 = cadena.substring(12, 24);
                            String c3 = cadena.substring(24, 36);
                            
                            c1 = sb.append("d").toString();
                            System.out.println("c1 "+c1);
                            System.out.println("c2 "+c2);
                            System.out.println("c3 "+c3);
                            break;
                        
                        // Search request desde el cliente
                        case 11:
                            System.out.println("case 11");
                            break;
                        
                        // Save request desde otro nodo
                        case 15:
                            System.out.println("case 15");
                            break;
                    }
                    // Responde al cliente
                    salida.writeUTF("OK");
                }
            } catch (IOException ex) {
                Logger.getLogger(serverNodeN1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
