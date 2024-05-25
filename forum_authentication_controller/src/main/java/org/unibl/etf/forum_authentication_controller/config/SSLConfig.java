package org.unibl.etf.forum_authentication_controller.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Formatter;

@Configuration
public class SSLConfig {
    @Value("${custom.trust-store-password}")
    public String truststorePassword;
    @Value("${custom.trust-store}")
    public String truststore;

    @Value("${custom.trust-store-type}")
    public String truststoreType;
    @Value("${custom.crl-list-path}")
    public String crlListPath;

    @PostConstruct
    private void configureSSL() {
        //set to TLSv1.1 or TLSv1.2
        //System.setProperty("https.protocols", "TLSv1.1");

        Resource resource = new DefaultResourceLoader().getResource(truststore);
        System.out.println(resource);
        //  Get the absolute path of the resource
        String truststoreAbsolutePath = null;
        try {
            truststoreAbsolutePath = resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Truststore file not found");
        }

        InputStream storeStream = null;

        if(truststoreAbsolutePath != null){
            System.setProperty("javax.net.ssl.trustStore", truststoreAbsolutePath);
            System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
            try{
                storeStream = resource.getInputStream();
            }catch (IOException io){

            }

        }
        try {
            if(storeStream == null) storeStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(truststore);
            setSSLFactories(storeStream, truststoreType, truststorePassword.toCharArray(), null, crlListPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getInputStreamLength(InputStream inputStream) throws IOException {
        long count = 0;
        byte[] buffer = new byte[1024]; // Buffer size for reading
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            count += bytesRead;
        }
        return count;
    }

    private static void setSSLFactories(InputStream trustStream, String keystoreType, char[] keyStorePassword, char[] keyPassword, String crlListPath) throws Exception
    {

        SSLContext sslContext = SSLContext.getInstance("SSL");

        KeyStore trustStore = KeyStore.getInstance(keystoreType);
        trustStore.load(trustStream, keyStorePassword);

        System.out.println("truststore size: " +  trustStore.size());

        TrustManagerFactory trustFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustFactory.init(trustStore);

        X509TrustManager defaultTrustManager = (X509TrustManager) trustFactory.getTrustManagers()[0];

        TrustManager[] customTrustManagers = new TrustManager[]{
                new X509TrustManager() {
                    private final long TEN_MINUTES_MS = 10 * 60 * 1000;
                    private long lastCheckTimeMillis = System.currentTimeMillis() - TEN_MINUTES_MS;
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return defaultTrustManager.getAcceptedIssuers();
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        defaultTrustManager.checkClientTrusted(chain, authType);
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        defaultTrustManager.checkServerTrusted(chain, authType);

                        long currentTimeMillis = System.currentTimeMillis();
                        long timeDifference = currentTimeMillis - lastCheckTimeMillis;

                        if (timeDifference >= TEN_MINUTES_MS) {

                            checkIfAnyCertificateInChainIsBlocked(chain, crlListPath);
                            // Perform your CRL check/update operations here
                            lastCheckTimeMillis = currentTimeMillis; // Update the last check time
                        } else {

                        }
                    }
                }
        };

        sslContext.init(null, customTrustManagers, null);
        SSLContext.setDefault(sslContext);


    }

    private static String getHashingAlgorithmForBlockedCertsFile(String line){
        return line.substring(10);
    }

    private static String generateHash(String algorithm, String textToHash) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = messageDigest.digest(textToHash.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static void checkIfAnyCertificateInChainIsBlocked(X509Certificate[] chain, String crlListPath) throws CertificateException{
        System.out.println("yo " + chain.length);
        InputStream blockedCertsStream = null;
        try{
            Resource resource = new DefaultResourceLoader().getResource(crlListPath);
            blockedCertsStream = resource.getInputStream();
        }
        catch(IOException e){

        }
        System.out.println("yo " + blockedCertsStream);
        BufferedReader br = null;
        try{

            if(blockedCertsStream == null) {
                blockedCertsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(crlListPath);

            }

            if(blockedCertsStream == null) {
                System.out.println(br);
                return;
            }
            br = new BufferedReader(new InputStreamReader(blockedCertsStream));
            String line;
            System.out.println(br);
            String hashAlgorithm =null;
            while ((line = br.readLine()) != null) {
                System.out.println(hashAlgorithm);
                if (line.startsWith("Algorithm=")) {
                    hashAlgorithm = getHashingAlgorithmForBlockedCertsFile(line);
                    System.out.println(hashAlgorithm);
                }
                if(hashAlgorithm == null){
                    break;
                }
                try{

                    for (X509Certificate cert : chain) {

                        if(line.equalsIgnoreCase(generateHash(hashAlgorithm,  cert.getSerialNumber().toString(16)))){
                            System.out.println("pronadjen");
                            throw new CertificateException("SSL connection terminated by custom trust manager");
                        }
                    }

                }
                catch(NoSuchAlgorithmException e){
                    break;
                }
            }


        }
        catch (IOException e){

        }
        finally {
            try{
                if(blockedCertsStream != null) blockedCertsStream.close();
                if(br != null) br.close();
            }catch(IOException io){

            }
        }






    }
}