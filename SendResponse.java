import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SendResponse {
    static void sendOkResponseStream(OutputStream output, InputStream fis, String ext) throws Exception {
        Util.writeLine(output, "HTTP/1.0 200 OK");
        Util.writeLine(output, "Date: " + Util.getDateStringUtc());
        Util.writeLine(output, "Server: Java HTTP Server");
        Util.writeLine(output, "Connection: close");
        Util.writeLine(output, "Content-type: " + Util.contentTypeMap.get(ext));
        Util.writeLine(output, "");

        int ch;
        while ((ch = fis.read()) != -1) {
            output.write(ch);
        }
    }

    static void sendMovePermanentlyResponse(OutputStream output, String location) throws Exception {
        Util.writeLine(output, "HTTP/1.1 301 Moved Permanently");
        Util.writeLine(output, "Date: " + Util.getDateStringUtc());
        Util.writeLine(output, "Server: Java HTTP Server");
        Util.writeLine(output, "Location: " + location);
        Util.writeLine(output, "Connection: close");
        Util.writeLine(output, "");
    }

    static void sendNotFoundResponse(OutputStream output, String errorDocumentRoot) throws Exception {
        Util.writeLine(output, "HTTP/1.0 404 Not Found");
        Util.writeLine(output, "Date: " + Util.getDateStringUtc());
        Util.writeLine(output, "Server: Java HTTP Server");
        Util.writeLine(output, "Connection: close");
        Util.writeLine(output, "Content-type: text/html");
        Util.writeLine(output, "");

        try(InputStream fis = new FileInputStream(errorDocumentRoot)) {
            int ch;
            while ((ch = fis.read()) != -1) {
                output.write(ch);
            }
        }
    }
}
