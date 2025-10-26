package com.miaudote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;

@RestController
public class ConnectivityTestController {

    @GetMapping("/test-smtp-conn")
    public ResponseEntity<String> testSmtpConnection(
            @RequestParam(defaultValue = "smtp.gmail.com") String host,
            @RequestParam(defaultValue = "587") int port,
            @RequestParam(defaultValue = "5000") int timeoutMillis) {

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMillis);
            return ResponseEntity.ok("SUCCESS: connected to " + host + ":" + port);
        } catch (Exception e) {
            String msg = String.format("FAILED to connect to %s:%d — %s: %s",
                    host, port, e.getClass().getSimpleName(), e.getMessage());
            return ResponseEntity.status(502).body(msg);
        }
    }
}
