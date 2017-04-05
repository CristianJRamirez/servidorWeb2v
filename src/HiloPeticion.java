import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.*;

import java.io.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by 45858000w on 08/02/17.
 */
public class HiloPeticion extends Thread{

    private Socket newSocket;
    public PrintWriter salida;

    public HiloPeticion(Socket newSocket) {

        this.newSocket = newSocket;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            //antiguo(is);

            //nuevo();

            nuevo();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    void retornaFichero(String sfichero)
    {
        System.out.println("Recuperamos el fichero " + sfichero);

        // comprobamos si tiene una barra al principio
        if (sfichero.startsWith("/"))
        {
            sfichero = sfichero.substring(1) ;
        }

        // si acaba en /, le retornamos el index.htm de ese directorio
        // si la cadena esta vacia, no retorna el index.htm principal
        if (sfichero.endsWith("/") || sfichero.equals(""))
        {
            sfichero = sfichero + "index.htm" ;
        }

        try
        {

            // Ahora leemos el fichero y lo retornamos
            File mifichero = new File(sfichero) ;

            if (mifichero.exists())
            {
                salida.println("HTTP/1.0 200 ok");//no se puede quitar esta linea, sino no funciona
                salida.println("Servidor OK! --->>>  Server: Cristian Javier Ramirez -> Date: " + new Date());
                salida.println("Content-Type: text/html");
                salida.println("Content-Length: " + mifichero.length());
                salida.println("\n");



                BufferedReader ficheroLocal = new BufferedReader(new FileReader(mifichero));


                String linea = "";

                do
                {
                    linea = ficheroLocal.readLine();

                    if (linea != null )
                    {
                        // sleep(500);
                        salida.println(linea);
                    }
                }
                while (linea != null);

                System.out.println("fin envio fichero");

                ficheroLocal.close();
                salida.close();

            }  // fin de si el fiechero existe
            else
            {
                System.out.println("No encuentro el fichero " + mifichero.toString());
                salida.println("HTTP/1.0 400 ok");
                salida.close();
            }

        }
        catch(Exception e)
        {
            System.out.println("Error al retornar fichero");
        }

    }

    /**
     * Metodo nuevo para imprimir por la web algo pero no se queda fijo
     * @throws IOException
     */
    void antiguo(InputStream is) throws IOException {
        is = newSocket.getInputStream();

        OutputStream os = newSocket.getOutputStream();

        byte[] mensaje = new byte[50];
        is.read(mensaje);


        System.out.println("  -> Mensaje recibido : "+ new String (mensaje));

        PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(newSocket.getOutputStream())), true);

        salida.println("  -> El resultado es :-> " + new String(mensaje).trim());
        salida.println("  -> IP: " + newSocket.getInetAddress());

        System.out.println("  -> IP: " + newSocket.getInetAddress());

        System.out.println("  -> Cerrando el socket");

        newSocket.close();

        System.out.println("Cerrando el socket servidor");


        }

    /**
     * Metodo nuevo para imprimir por la web algo y que se quede fijo
     * @throws IOException
     */
    void nuevov2() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
        salida = new PrintWriter(new OutputStreamWriter(newSocket.getOutputStream(), "8859_1"), true);


        String cadena = "";        // cadena donde almacenamos las lineas que leemos
        int i = 0;                // lo usaremos para que cierto codigo solo se ejecute una vez

        do {
            cadena = in.readLine();

            if (cadena != null) {
                // sleep(500);
                System.out.println(" --" + cadena + "- ");
            }

            if (i == 0) // la primera linea nos dice que fichero hay que descargar
            {
                i++;
                StringTokenizer st = new StringTokenizer(cadena);

                if ((st.countTokens() >= 2) && st.nextToken().equals("GET")) {
                    // salida.println("  -> El resultado es :-> " + new String(mensaje).trim());
                    salida.println("  -> IP: " + newSocket.getInetAddress());

                    salida.println("  -> Cristian J : web modificada" );

                    System.out.println(" LOG: -> IP: " + newSocket.getInetAddress());

                    System.out.println("  LOG: Cerrando el socket");

                    newSocket.close();

                    System.out.println(" LOG: Cerrando el socket servidor");
                } else {
                    salida.println("LOG: 400 Petición Incorrecta");
                }
            }

        }
        while (cadena != null && cadena.length() != 0);

    }

    /**
     * Metodo para escribir en la web con un archivo html
     * @throws IOException
     */
    void nuevo() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
        salida = new PrintWriter(new OutputStreamWriter(newSocket.getOutputStream(), "8859_1"), true);

        String cadena = "";        // cadena donde almacenamos las lineas que leemos
        int i = 0;                // lo usaremos para que cierto codigo solo se ejecute una vez

        do {
            cadena = in.readLine();

            if (cadena != null) {
                // sleep(500);
                System.out.println(" --" + cadena + "- ");
            }

            if (i == 0) // la primera linea nos dice que fichero hay que descargar
            {
                i++;
                StringTokenizer st = new StringTokenizer(cadena);

                if ((st.countTokens() >= 2) && st.nextToken().equals("GET")) {

                    String añadir = null;

                    añadir="  -> IP: " + newSocket.getInetAddress();

                    añadir+="  \n-> Cristian J : web modificada" ;

                    añadir += "\n</body>\n" ;

                    archivos(añadir);

                    retornaFichero(st.nextToken());
                } else {
                    salida.println("400 Petición Incorrecta");
                }
            }

        }
        while (cadena != null && cadena.length() != 0);
    }


    void archivos(String añadir) throws IOException {
        Archivos arch = new Archivos();
        File fNuevo = new File("index.htm");

        //arch.Escribir(fNuevo,"arclos");
        arch.modificar(fNuevo,"</body>",añadir);
    }
}
