package cse110.middleware;
import java.io.*;

public class AudioFileWriter {
    private OutputStream outputStream;
    private String boundary;

    public AudioFileWriter(OutputStream outputStream, String boundary) {
        this.outputStream = outputStream;
        this.boundary = boundary;
    }

    public void writeParameter(String parameterName, String parameterValue) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n").getBytes());
        outputStream.write((parameterValue + "\r\n").getBytes());
    }

    public void writeFile(String fieldName, File file) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
    }
}
