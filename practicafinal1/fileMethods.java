/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal1;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

/**
 *
 * @author jcmju
 */
public class fileMethods {

    // Atributos: nombre del archivo y texto para guardar
    private String fileName;
    private String mensaje;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void writeFile() throws IOException {
        String newline = System.getProperty("line.separator");
        // Convierte el string en un array de bytes
        String s = this.mensaje+newline;
        byte data[] = s.getBytes();
        Path p = Paths.get(this.fileName);

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, APPEND))) {
            out.write(data, 0, data.length);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

}
