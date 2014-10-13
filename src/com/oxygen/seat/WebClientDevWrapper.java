package com.oxygen.seat;

//重定义https和http，绕过证书错误
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;


    public class WebClientDevWrapper {

        public static DefaultHttpClient wrapClient(DefaultHttpClient base) {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                TrustManager tm = new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                };
                
                ctx.init(null, new TrustManager[] { tm }, null);
                
                
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory ssf = new SSLSocketFactory(trustStore);
                ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//                SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", ssf, 443));
                //registry.register(new Scheme("https", ssf, 8443));
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
                ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(base.getParams(),registry);
                return new DefaultHttpClient(mgr, base.getParams());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }