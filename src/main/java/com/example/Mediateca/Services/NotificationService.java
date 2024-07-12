package com.example.Mediateca.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.Mediateca.DTOs.NotificationDTO;
import com.example.Mediateca.Domain.AdDO;
import com.example.Mediateca.Domain.NotificationDO;
import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.Repositories.NotificationRepository.NotificationRepository;
import com.example.Mediateca.Repositories.UserRepository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio que gestiona las notificaciones para solicitar la edicion de
 * anuncios, incluyendo su creacion, resolucion y eliminacion.
 * Tambien gestiona el envio de correos electronicos para informar a alumnos y
 * profesores.
 */
@Service
@Transactional
public class NotificationService {

    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

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

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Crea una notificación de solicitud de edicion para un anuncio.
     *
     * @param ad   el anuncio para el que se solicita la edicion.
     * @param user el usuario que realiza la solicitud.
     * @return la notificacion creada.
     */
    public NotificationDO createEditRequestNotification(AdDO ad, UserDO user) {
        NotificationDO notification = new NotificationDO();
        notification.setUser(user);
        notification.setAd(ad);
        notification.setStatus("PENDING");
        notification.setCheckedDate(null);
        notificationRepository.save(notification);
        return notification;
    }

    /**
     * Responde a una notificacion, actualizando su estado a aprobado o
     * rechazado.
     *
     * @param notificationId el identificador de la notificacion a resolver.
     * @param approved       true si la solicitud es aprobada, false si es
     *                       rechazada.
     * @return la notificacion actualizada.
     */
    public NotificationDO respondToNotification(int notificationId, boolean approved) {
        NotificationDO notification = notificationRepository.findById(notificationId);
        notification.setStatus(approved ? "APPROVED" : "REJECTED");
        notification.setCheckedDate(new java.util.Date());
        notificationRepository.save(notification);
        return notification;
    }

    /**
     * Recupera una lista de notificaciones pendientes asociadas a un anuncio.
     *
     * @param adId el identificador del anuncio para el que se buscan notificaciones
     *             pendientes.
     * @return una lista de notificaciones.
     */
    public List<NotificationDO> getPendingNotificationByAd(Long adId) {
        List<NotificationDO> notificationList = notificationRepository.findPendingByAd(adId);
        return notificationList;
    }

    /**
     * Recupera todas las notificaciones resuelyas asociadas a un usuario.
     *
     * @param userId el identificador del usuario para el cual se recuperan las
     *               notificaciones.
     * @return una lista de notificaciones resueltas.
     */
    public List<NotificationDTO> getAllByUserIdAndChecked(Integer userId) {
        List<NotificationDO> notificationList = notificationRepository.findByUserIdAndChecked(userId);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (NotificationDO notificationDO : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.fromDO(notificationDO);
            notificationDTOList.add(notificationDTO);
        }
        return notificationDTOList;
    }

    /**
     * Recupera todas las notificaciones basadas en su estado actual.
     *
     * @param status el estado de las notificaciones a recuperar.
     * @return una lista de notificaciones en el estado esperado.
     */
    public List<NotificationDTO> getAllByStatus(String status) {
        List<NotificationDO> notificationList = notificationRepository.findByStatus(status);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (NotificationDO notificationDO : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.fromDO(notificationDO);
            notificationDTOList.add(notificationDTO);
        }
        return notificationDTOList;
    }

    /**
     * Recupera todas las notificaciones que tienen un estado asignado.
     *
     * @return una lista de notificaciones que representan las
     *         notificaciones con cualquier estado no nulo.
     */
    public List<NotificationDTO> getAllWithStatusNotNull() {
        List<NotificationDO> notificationList = notificationRepository.findAllWithStatusNotNull();
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (NotificationDO notificationDO : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.fromDO(notificationDO);
            notificationDTOList.add(notificationDTO);
        }
        return notificationDTOList;
    }

    @SuppressWarnings("null")
    public void deleteNotificationList(List<Integer> notificationList) {
        notificationRepository.deleteByIdList(notificationList);
    }

    /**
     * Envia un correo electronico a los profesores notificandoles de la solicitud
     * de edicion y de como pueden resolverla
     *
     * @param notification la notificacion.
     */
    @Async
    public void sendEmailToProfessor(NotificationDO notification) {
        List<UserDO> professorList = userRepository.findByRole("ROLE_PROFESOR");

        if (professorList != null && !professorList.isEmpty()) {
            for (UserDO professor : professorList) {
                if (professor.getEmail() != null && !professor.getEmail().isEmpty()) {
                    UserDO alumn = notification.getUser();
                    if (alumn == null) {
                        return;
                    }

                    String resetLink = "http://kidvertising.es/detalleAnuncio?adId="
                            + notification.getAd().getId();

                    this.mailSender = javaMailSender();
                    // Crear el mensaje de correo electronico
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom(mailUsername);
                    message.setTo(professor.getEmail());
                    message.setSubject("Solicitud De Permiso Para Editar Anuncio");
                    message.setText("El alumno " + alumn.getUsername()
                            + " ha solicita permiso para editar el siguiente anuncio :\n" + resetLink +
                            "\nPara poder resolver la petición, acceda al menú de 'Mis solicitudes' en la aplicación");

                    // Enviar el correo
                    mailSender.send(message);
                }
            }
        }

    }

    /**
     * Envia un correo electronico al alumno notificandole sobre la resolucion de su
     * solicitud de edicion.
     *
     * @param notification la notificacion que contiene los detalles de la
     *                     resolucion.
     */
    @Async
    public void sendEmailToAlumn(NotificationDO notification) {
        UserDO userDO = notification.getUser();
        if (userDO == null) {
            return;
        }

        String resetLink = "http://kidvertising.es/detalleAnuncio?adId="
                + notification.getAd().getId();

        String resolution = notification.getStatus().equals("APPROVED") ? "aprobada" : "rechazada";
        String messagetext = "Ha sido " + resolution + " su solicitud para poder editar el siguiente anuncio: \n"
                + resetLink;
        if (notification.getStatus().equals("APPROVED")) {
            messagetext += "\nDispone de 7 días desde la resolución para editar el anuncio";
        }

        this.mailSender = javaMailSender();
        // Crear el mensaje de correo electronico
        SimpleMailMessage message = new SimpleMailMessage();
        // Emisor
        message.setFrom(mailUsername);
        // Destinatario
        message.setTo(userDO.getEmail());
        // Titulo
        message.setSubject("Solicitud De Permiso Para Editar Anuncio Resuelta");
        // Mensaje
        message.setText(messagetext);

        // Enviar el correo
        mailSender.send(message);
    }

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

        // Añadir la propiedad para confiar en todos los certificados
        props.put("mail.smtp.ssl.trust", mailSmtpSslTrust);

        return mailSender;
    }
}