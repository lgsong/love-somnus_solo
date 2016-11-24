package com.somnus.solo.support.http;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionManager {
	
	private transient static Logger log = LoggerFactory.getLogger(HttpConnectionManager.class);

	private static PoolingHttpClientConnectionManager poolConnManager;

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 800;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int REQUEST_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_PER_ROUTE = 400;
	/**
	 * 读取超时时间
	 */
	public final static int SO_TIMEOUT = 10000;

	static {
		try {
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http",PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslsf).build();
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			poolConnManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
			poolConnManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SO_TIMEOUT).build();
			poolConnManager.setDefaultSocketConfig(socketConfig);
		} catch (KeyManagementException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		} catch (KeyStoreException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(poolConnManager)
				.setConnectionManagerShared(true)
				.build();;
		if (poolConnManager != null && poolConnManager.getTotalStats() != null) {
			log.info("now client pool "+ poolConnManager.getTotalStats().toString());
		}
		/*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
		return httpClient;
	}
	
	public static void main(String[] args) {
		getHttpClient();
	}
}
