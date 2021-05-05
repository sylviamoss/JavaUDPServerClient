import java.io.*;
import java.net.*;

public class EZServer {
    final int PORT = 8000;

    private DatagramSocket socket;
    // TODO: instanciar a lista
    // Dica: Java possui mais de um tipo de lista: ArrayList, HashSet, etc...
    // Escolha o tipo que achar mais apropriado.

    public EZServer() throws SocketException {
        socket = new DatagramSocket(PORT);
    }

    public static void main(String[] args) {
        try {
            EZServer server = new EZServer();
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        System.out.println("Iniciando EZServer...");
        while (true) {
            byte[] buffer = new byte[512];
            DatagramPacket uuidMessage = new DatagramPacket(buffer, buffer.length);
            socket.receive(uuidMessage);

            String uuid = new String(buffer, 0, uuidMessage.getLength());
            System.out.println("Recebendo uuid: ");
            System.out.println(uuid);

            addUUID(uuid);

            InetAddress clientAddress = uuidMessage.getAddress();
            int clientPort = uuidMessage.getPort();

            DatagramPacket confirmation = new DatagramPacket(new byte[1], 1, clientAddress, clientPort);
            socket.send(confirmation);

            // TODO: Verificar se a lista de UUIDs contem 5 elementos para finalizar a conexão. 
            // Dica: 
            // if ( (tamanho da lista) == 5 ) {
            //      A conexão é finalizada com a linha abaixo.
            //      socket.close();  
            //      break;  
            // }
        }
        printUUIDS();
    }

    private void addUUID(String uuid) {
        // TODO: Adicionar o uuid na lista
        // Dica: a maneira de adicionar dependerá do tipo de lista que você escolheu
    }

    private void printUUIDS() {
        System.out.println();
        System.out.println("Os UUIDs de clientes são: ");
        // TODO: iterar sob a lista e imprimir os uuids um por um
        System.out.println();
    }
}