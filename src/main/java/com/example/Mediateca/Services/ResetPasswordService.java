package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.TokenResetPasswordDO;
import com.example.Mediateca.Repositories.TokenResetPasswordRepository.TokenResetPasswordRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Servicio que proporciona la logica para el manejo de tokens de
 * restablecimiento de contrasenia,
 * incluyendo la creacion, validacion y eliminacion de tokens, asi como el envio
 * de correos a los usuarios para la recuperacion de contrasenia.
 */
@Service
@Transactional
public class ResetPasswordService {

    @Autowired
    private TokenResetPasswordRepository tokenRepository;

    private JavaMailSender mailSender;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailStarttlsEnable;

    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private String mailStarttlsRequired;

    @Value("${spring.mail.properties.mail.debug}")
    private String mailDebug;

    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
    private String mailSmtpSslTrust;

    public ResetPasswordService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Guarda un nuevo token de restablecimiento de contrasenia en la base de datos.
     *
     * @param userId el identificador del usuario para el que se crea el token.
     * @param token  el token de restablecimiento de contrasenia.
     * @return el token creado.
     */
    @Transactional
    public TokenResetPasswordDO saveToken(Integer userId, String token) {
        TokenResetPasswordDO tokenResetPasswordDO = new TokenResetPasswordDO();
        tokenResetPasswordDO.setUserId(userId);
        tokenResetPasswordDO.setToken(token);
        tokenResetPasswordDO.setExpirationDate(LocalDateTime.now().plusDays(1)); // Token valido por 1 día
        tokenResetPasswordDO.setCreationDate(LocalDateTime.now());
        tokenResetPasswordDO.setUsed(false);

        return tokenRepository.save(tokenResetPasswordDO);
    }

    /**
     * Busca un token de restablecimiento no haya sido usado y
     * no haya expirado.
     *
     * @param token el token a buscar.
     * @return el token si es encontrado, de lo contrario
     *         retorna null.
     */
    public TokenResetPasswordDO findByTokenAndNotUsedAndNotExpired(String token) {
        return tokenRepository.findByTokenAndNotUsedAndNotExpired(token);
    }

    /**
     * Elimina el token
     *
     * @param tokenResetPasswordDO el TokenResetPasswordDO a eliminar.
     */
    @Transactional
    public void invalidateToken(TokenResetPasswordDO tokenResetPasswordDO) {
        tokenRepository.delete(tokenResetPasswordDO);
    }

    /**
     * Envia un correo al usuario de forma asincrona con un enlace para poder
     * reestablecer su contrasenia.
     *
     * @param token     el token de restablecimiento de contrasenia.
     * @param userEmail el correo del usuario al que se debe enviar el enlace.
     */
    @Async
    public void sendEmail(String token, String userEmail) {
        // Enlace para restablecer la contrasenia
        String resetLink = "https:kidvertising.es/resetPassword?token=" + token;

        this.mailSender = javaMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        // Emisor
        message.setFrom(mailUsername);
        // Destinatario
        message.setTo(userEmail);
        // Titulo
        message.setSubject("Solicitud de Restablecimiento de Contraseña");
        // Mensaje
        message.setText("Para restablecer tu contraseña, haz clic en el enlace a continuación:\n" + resetLink);

        // Enviar el correo
        mailSender.send(message);
    }

    /**
     * Configura el JavaMailSender utilizado para enviar correos.
     *
     * @return una instancia de JavaMailSender configurada.
     */
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        // Correo emisor
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailStarttlsEnable);
        props.put("mail.smtp.starttls.required", mailStarttlsRequired);
        props.put("mail.debug", mailDebug);

        // Aniadir la propiedad para confiar en todos los certificados
        props.put("mail.smtp.ssl.trust", mailSmtpSslTrust);

        return mailSender;
    }
}