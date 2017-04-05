/**
 * Created by 45858000w on 05/04/17.
 */


import java.io.*;
import java.util.Random;

/****************************************************************
 * Modificar un fichero de texto, consiste en leer una archivo "Y"
 *  y escribir su contenido en uno nuevo llamad "x", excepto la
 *  cadena a modificar que se remplaza con la cadena nueva
 *
 *  Luego se borra el fichero inicial y se re-nobra el nuevo
 *  fichero con el nombre del archivo inicial
 *
 *****************************************************************/
public class Archivos {


    /*************************************************************
     * El metodo "Escribir" cuenta con dos parametros "fFichero"
     * (Donde creamos y especificamos la ruta y el nombre del archivo
     * y "cadena" (Donde le pasamos los caracteres que queremos que
     * sean almacenados en el archivo
     **************************************************************/
    void Escribir(File fFichero, String cadena)
    {
        // Declaramos un buffer de escritura
        BufferedWriter bw;

        try
        {
            // Comprobamos si el archivo no existe y si es asi creamos uno nuevo.
            if(!fFichero.exists())
            {
                fFichero.createNewFile();
            }

            // Luego de haber creado el archivo procedemos a escribir dentro de el.
            bw = new BufferedWriter(new FileWriter(fFichero,true));
            bw.write(cadena+"\n");
            bw.close();

        }catch(Exception e)
        {
            System.out.println(e);
        }

    }

    /***************************************************************
     * El metodo "Borrar" como su nombre lo indica, nos ayuda a
     * borrar un fichero previamente creado, este metodo cuenta con
     * un parametro, el cual es el nombre del fichero que deseamos
     * borrar
     * ***************************************************************/
    void borrar (File Ffichero)
    {
        try
        {
            // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
            if(Ffichero.exists())
            {
                Ffichero.delete();
                System.out.println("Ficherro Borrado");
            }

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /***************************************************************
     * Metodo "Modificar", este cuenta con tres parametros que debemos
     * pasarle para su correcto funcionamiento, los cuales son:
     *
     * fAntiguo: Este nos ayuda a saber cual es y donde esta el archivo
     * que deseamos modificar
     *
     * aCadena: Aqui debemos especificar la cadena de caracteres que
     * deseamos modificar o cambiar
     *
     * nCadena: Por ultimo escribimos el nuevo dato que va a sustituir
     * el existente.
     * ******************************************************************/
    void modificar(File fAntiguo,String aCadena,String nCadena) throws IOException {
       /*
            Las dos lienas de codigo siguientes, basicamente lo que hacen es generar un numero aleatorio y
            asignarselos a una nueva variable "nFnuevo" (Nombre Fichero Nuevo) la cual es igual a la ruta
            del directorio padre "fAntiguo" mas  la palabra auxiliar seguido del numero aletorio y la extension
            del archivo nuevo
       * */
        Random numaleatorio = new Random(3816L);
        String nFnuevo = "index2.htm";

        // Creo un nuevo archivo
        File fNuevo= new File(nFnuevo);
        // Declaro un nuevo buffer de lectura
        BufferedReader br;
        try
        {
            /*Valido si el fichero antiguo que pasamos como parametro existe, si es asi procedo a leer el
            contenido que hay dentro de el
             */

            if(fAntiguo.exists())
            {
                br = new BufferedReader(new FileReader(fAntiguo));

                String linea;

                /* Mientras el contenido del archivo sea diferente de null procedo a comprar  la linea a modificar con
                lo que hay dentro del archivo, si linea es igual a aCadena escribo el contenido de aCadena en mi nuevo
                fichero(Auxiliar) de lo contrario escribo el mismo contenido que ya tenia el antiguo fichero en mi fichero auxiliar

                 */
                while((linea=br.readLine()) != null)
                {
                    if(linea.equals(aCadena))
                    {
                        Escribir(fNuevo,nCadena);
                    }
                    else
                    {
                        Escribir(fNuevo,linea);
                    }
                }

                // Cierro el buffer de lectura
                br.close();

                // Capturo el nombre del fichero antiguo
                String nAntiguo = fAntiguo.getName();
                // Borro el fichero antiguo
                borrar(fAntiguo);
                //Renombro el fichero auxiliar con el nombre del fichero antiguo
                fNuevo.renameTo(fAntiguo);




            }
            else
            {
                System.out.println("Fichero no Existe");
            }

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

}

