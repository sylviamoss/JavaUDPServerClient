import java.io.*;
import java.net.*;
import java.util.*;
 
public class EZClient {
    static int PORT = 8000;
    static String HOSTNAME = "localhost";

    private DatagramSocket socket;
    private List<String> uuids = new ArrayList<String>();
    private Random random;
 
    public EZClient() throws SocketException {
        socket = new DatagramSocket();
        socket.setSoTimeout(5000);
        random = new Random();
        
        // Cadastra UUIDs dos clientes
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            uuids.add(uuid.toString());
        }
    }
 
    public static void main(String[] args) { 
        try {
            EZClient client = new EZClient();
            client.start();
        } catch (SocketTimeoutException ex) {
            System.out.println();
            System.out.println("Timeout! Server is closed. Bye!" );
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        } 
    }

    private void start() throws IOException {
        System.out.println("Iniciando EZClient at port "+ socket.getPort());
        while (true) {
            String uuid = getRandomUUID();
            byte[] buffer = uuid.getBytes();
 
            InetAddress address = InetAddress.getByName(HOSTNAME);
            DatagramPacket uuidMessage = new DatagramPacket(buffer, buffer.length, address, PORT);
            System.out.println("Enviando uuid: ");
            System.out.println(uuid);
            socket.send(uuidMessage);

            DatagramPacket confirmation = new DatagramPacket(new byte[1], 1);
            socket.receive(confirmation);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getRandomUUID() {
        int randomIndex = random.nextInt(uuids.size());
        String randomQuote = uuids.get(randomIndex);
        return randomQuote;
    }
}
