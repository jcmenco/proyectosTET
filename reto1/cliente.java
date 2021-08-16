//package practica1tet;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author jcmju
 */
public class cliente {

    public static void main(String[] args) {

        String host = "ec2-3-236-190-69.compute-1.amazonaws.com";
        int puerto = 5000;
        DataInputStream entrada;
        DataOutputStream salida;
        Socket cliente;

        try {
            cliente = new Socket(host, puerto);
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());

            System.out.println("Escriba algo:");
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String mensaje = br.readLine();

            // Envía el mensaje al servidor
            salida.writeUTF(mensaje);

            while (true) {
                // Lee y muestra en consola la respuesta del servidor
                String respuesta = entrada.readUTF();
                System.out.println(respuesta);

                System.out.println("Escriba algo");
                mensaje = br.readLine();
                salida.writeUTF(mensaje);

                if (mensaje.equalsIgnoreCase("close")) {
                    break;
                }
            }

            System.out.println("Fin del envío");
            cliente.close();

        } catch (IOException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
