import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class demonstrates a simple HTTP POST action using basic/core Java classes.
 */
public class HttpPost {

    /**
     * Main entry point.
     * 
     * @param args arguments.
     */
    public static void main(String args[]) {
        // ### Example arguments ###
        // args = new String[] {
        //         "http://localhost:80/DataOperation",
        //         "application/xml",
        //         "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:acc=\"http://localhost:80/DataOperation/\"><soapenv:Header/><soapenv:Body><acc:data><in>123</in></acc:data></soapenv:Body></soapenv:Envelope>" };

        // Check Parameters.
        if (args.length != 3) {
            if (args.length > 0) {
                System.out.println("Invalid parameters.");
            }
            System.out.println("Usage   : HttpPost <url> <content-type> <postdata>");
            System.out.println("Example : HttpPost \"http://localhost:80/somewhere\" \"application/xml\" \"<rqst>1234567890</rqst>\"");
            return;
        }
        System.out.println("=====HttpPost=====");
        // Setup Logger.
        System.out.println("#Setup logger...");
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.FINEST);
        Logger logger = Logger.getLogger("sun.net.www.protocol.http.HttpURLConnection");
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.FINEST);
        // Initialize Variables.
        System.out.println("#Initialize variables...");
        String encoding = "UTF-8";
        OutputStream os = null;
        InputStream is = null;
        BufferedReader rd = null;
        String rspnline = null;
        try {
            URL link = new URL(args[0]);
            byte[] rqstdata = args[2].getBytes(encoding);
            // Prepare connection.
            System.out.println("#Prepare connection...");
            HttpURLConnection client = (HttpURLConnection) link.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", args[1] + "; charset=" + encoding);
            client.setRequestProperty("Content-Length", Integer.toString(rqstdata.length));
            client.setUseCaches(false);
            client.setDoInput(true);
            client.setDoOutput(true);
            os = new DataOutputStream(client.getOutputStream());
            // Send to server.
            System.out.println("#Send to server...");
            os.write(rqstdata);
            os.flush();
            // Receive from server.
            System.out.println("#Receive from server...");
            is = client.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is));
            while ((rspnline = rd.readLine()) != null) {
                System.out.println(rspnline);
            }
        } catch (Exception _ex) {
            // Exception.
            System.out.println("#Exception...");
            _ex.printStackTrace();
        } finally {
            // Close resources.
            System.out.println("#Close resources...");
            HttpPost.close(rd);
            HttpPost.close(is);
            HttpPost.close(os);
            // Finish.
            System.out.println("#Finish...");
        }
    }
    
    /**
     * Close the resource.
     * 
     * @param x the resource to be closed.
     */
    private static void close(Object x) {
        try {
            if (x != null) {
                Method closeMethod = x.getClass().getMethod("close");
                closeMethod.setAccessible(true);
                closeMethod.invoke(x);
            }
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        x = null;
    }
}
