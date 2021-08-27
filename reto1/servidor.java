

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcmju
 */
public class servidor {

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
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void run() {
            try {
                String cadena;
                while (true) {
                    cadena = entrada.readUTF();
                    // Se mantiene escuchando hasta que lea el comando "close"
                    System.out.println("Mensaje recibido: " + cadena + " (" + socket.getInetAddress().toString() + ")");
                    // Responde al cliente
                    salida.writeUTF("Servidor: recibido, cliente " + socket.getInetAddress().toString());
                    if (cadena.equalsIgnoreCase("close")) {
                        // Cierre de conexión con el cliente
                        System.out.println("Fin de la conexión con " + socket.getInetAddress().toString());
                        socket.close();
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket servidor;
        //Puerto que el ServerSocket va a escuchar
        int puerto = 5000;
        Thread hilo;
        // Socket del cliente
        Socket cliente;
        servidor = new ServerSocket(puerto);
        while (true) {
            System.out.println("Espernado por request del cliente");
            cliente = servidor.accept();
            // Hilo para atender la conexión de varios clientes simultáneos
            hilo = new Thread(new handlerClient(cliente));
            hilo.start();
        }
    }

}
