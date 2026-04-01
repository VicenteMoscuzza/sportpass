package com.sportpass.sportpass_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void enviarBienvenida(String email, String nombre) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("¡Bienvenido a SportPass! 🎟️");
            helper.setText(buildEmailBienvenida(nombre), true);

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error enviando email: " + e.getMessage());
        }
    }

    private String buildEmailBienvenida(String nombre) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <style>
                body { font-family: 'Helvetica Neue', Arial, sans-serif; background: #0a0a0a; color: #f0f0f0; margin: 0; padding: 0; }
                .container { max-width: 560px; margin: 40px auto; background: #111111; border: 1px solid #222222; border-radius: 16px; overflow: hidden; }
                .header { padding: 40px; border-bottom: 1px solid #222222; }
                .logo { font-size: 13px; letter-spacing: 0.1em; text-transform: uppercase; color: #e8ff47; font-family: monospace; }
                .body { padding: 40px; }
                h1 { font-size: 28px; font-weight: 300; margin: 0 0 16px; color: #f0f0f0; }
                p { font-size: 15px; color: #888888; line-height: 1.6; margin: 0 0 16px; }
                .btn { display: inline-block; background: #e8ff47; color: #0a0a0a; padding: 12px 28px; border-radius: 8px; text-decoration: none; font-weight: 500; font-size: 14px; margin-top: 8px; }
                .footer { padding: 24px 40px; border-top: 1px solid #222222; font-size: 12px; color: #444444; }
              </style>
            </head>
            <body>
              <div class="container">
                <div class="header">
                  <span class="logo">⬡ SportPass</span>
                </div>
                <div class="body">
                  <h1>Hola, %s 👋</h1>
                  <p>Tu cuenta fue creada exitosamente. Ya podés ver y comprar entradas para los próximos eventos deportivos.</p>
                  <p>Encontrá los mejores partidos, elegí tu zona y asegurá tu lugar en el estadio.</p>
                  <a href="http://localhost:4200" class="btn">Ver eventos →</a>
                </div>
                <div class="footer">
                  SportPass · Si no creaste esta cuenta, podés ignorar este mensaje.
                </div>
              </div>
            </body>
            </html>
            """.formatted(nombre);
    }
}