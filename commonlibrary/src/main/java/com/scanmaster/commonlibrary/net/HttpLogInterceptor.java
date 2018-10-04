package com.scanmaster.commonlibrary.net;

import android.text.TextUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * author:  zhouchaoxiang
 * date:    2018/8/30
 * explain:
 */
public class HttpLogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /**
         * A {@link HttpLogInterceptor.Logger} defaults output appropriate for the current platform.
         */
        HttpLogInterceptor.Logger DEFAULT = new HttpLogInterceptor.Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public HttpLogInterceptor() {
        this(HttpLogInterceptor.Logger.DEFAULT);
    }

    public HttpLogInterceptor(HttpLogInterceptor.Logger logger) {
        this.logger = logger;
    }

    private final HttpLogInterceptor.Logger logger;

    private volatile HttpLogInterceptor.Level level = HttpLogInterceptor.Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public HttpLogInterceptor setLevel(HttpLogInterceptor.Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public HttpLogInterceptor.Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        HttpLogInterceptor.Level level = this.level;

        Request request = chain.request();
        if (level == HttpLogInterceptor.Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == HttpLogInterceptor.Level.BODY;
        boolean logHeaders = logBody || level == HttpLogInterceptor.Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        StringBuilder requestSb = new StringBuilder();
        requestSb.append(String.format("-->%s %s %s", request.method(), request.url(), (connection != null ? connection.protocol() : "")));
//        String requestStartMessage = "--> "
//                + request.method()
//                + ' ' + request.url()
//                + (connection != null ? " " + connection.protocol() : "");
        if (!logHeaders && hasRequestBody) {
            requestSb.append(String.format("(%s-byte body)", requestBody.contentLength()));
//            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
//        logger.log(requestStartMessage);

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    requestSb.append("\r\n    Content-Type: ").append(requestBody.contentType());
//                    logger.log("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    requestSb.append("\r\n    Content-Length: ").append(requestBody.contentLength());
//                    logger.log("Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    requestSb.append(String.format("\r\n    %s: %s", name, headers.value(i)));
//                    logger.log(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
                requestSb.append("\r\n    --> END ").append(request.method());
                logger.log(requestSb.toString());
//                logger.log("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                requestSb.append("\r\n    --> END ").append(request.method()).append(" (encoded body omitted)");
                logger.log(requestSb.toString());
//                logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

//                logger.log("");
                if (isPlaintext(buffer)) {
                    String str = buffer.readString(charset);
                    if (!TextUtils.isEmpty(str)) {
                        requestSb.append("\r\n    ").append(str);
                    }
                    requestSb.append(String.format("\r\n    --> END %s ( %s -byte body)", request.method(), requestBody.contentLength()));
                    logger.log(requestSb.toString());
//                    logger.log(buffer.readString(charset));
//                    logger.log("--> END " + request.method()
//                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    requestSb.append(String.format("\r\n    --> END %s (binary %s -byte body omitted)", request.method(), requestBody.contentLength()));
                    logger.log(requestSb.toString());
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        StringBuilder responseSb = new StringBuilder();
        responseSb.append(String.format("<--%s %s %s (%sms %s)", response.code() + "",
                (response.message().isEmpty() ? "" : ' ' + response.message()),
                response.request().url() + "", tookMs + "", (!logHeaders ? ", " + bodySize + " body" : "")));
//        logger.log("<-- "
//                + response.code()
//                + (response.message().isEmpty() ? "" : ' ' + response.message())
//                + ' ' + response.request().url()
//                + " (" + tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                responseSb.append(String.format("\r\n    %s: %s", headers.name(i), headers.value(i)));
//                logger.log(headers.name(i) + ": " + headers.value(i));
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                responseSb.append("\r\n    <-- END HTTP");
                logger.log(responseSb.toString());
//                logger.log("<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                responseSb.append("\r\n    <-- END HTTP (encoded body omitted)");
                logger.log(responseSb.toString());
//                logger.log("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
//                    logger.log("");
                    responseSb.append(String.format("\r\n    <-- END HTTP (binary %s-byte body omitted)", buffer.size()));
//                    logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    logger.log(responseSb.toString());
                    return response;
                }

                if (contentLength != 0) {

                    String str = buffer.clone().readString(charset);
                    if (!TextUtils.isEmpty(str)) {
                        responseSb.append("\r\n    ").append(str);
                    }
//                    logger.log("");
//                    logger.log(buffer.clone().readString(charset));
                }
                responseSb.append(String.format("\r\n    <-- END HTTP (%s-byte body)", buffer.size()));
//                logger.log("<-- END HTTP (" + buffer.size() + "-byte body)");
                logger.log(responseSb.toString());
            }
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
