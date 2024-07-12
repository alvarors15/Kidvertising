package com.example.Mediateca.Controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.Mediateca.DTOs.NotificationDTO;
import com.example.Mediateca.Domain.AdDO;
import com.example.Mediateca.Domain.NotificationDO;
import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.Services.AdService;
import com.example.Mediateca.Services.NotificationService;
import com.example.Mediateca.Services.UserService;

/**
 * Controlador para gestionar las notificaciones que se envian a los usuarios
 * Proporciona funcionalidades para crear solicitudes de edicion de anuncios,
 * mostrar notificaciones y aprobar, rechazar o eliminar estas.
 */
@Controller
@RequestMapping("/notificaciones")
public class NotificationController {

    // Servicio de notificaciones
    @Autowired
    private NotificationService notificationService;

    // Servicio de usuarios
    @Autowired
    private UserService userService;

    // Servicio de anuncios
    @Autowired
    private AdService adService;

    /**
     * Crea una notificacion de solicitud de edicion para un anuncio y
     * envia un correo a los profesores informandoles de esto.
     * Solo los alumnos pueden invocar este metodo
     *
     * @param adId               id del anuncio
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @param model
     * @return ResponseEntity con un mensaje JSON indicando el éxito o fallo de la
     *         operacion.
     */
    @PostMapping("/crearNotificacion/{adId}")
    @PreAuthorize("hasRole('ALUMNO')")
    public ResponseEntity<String> createEditRequestNotification(@PathVariable Long adId,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Obtenemos el usuario y el anuncio para agregarlos a la notificacion
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDO user = userService.getDOByEmail(email);

        AdDO ad = adService.getAdDOById(adId);
        redirectAttributes.addAttribute("adId", adId);
        try {
            // Almacenamos la notificacion en bbdd y enviamos un correo a los profesores
            NotificationDO notificationDO = notificationService.createEditRequestNotification(ad, user);
            notificationService.sendEmailToProfessor(notificationDO);
            return ResponseEntity
                    .ok("{\"message\": \"Notificación de solicitud de edición creada y enviada a los profesores.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"No se ha podido realizar la solicitud para editar el anuncio.\"}");
        }

    }

    /**
     * Muestra la vista con todas las notificaciones que ha recibido el usuario.
     *
     * @param model
     * @return notificaciones.html
     */
    @GetMapping("/")
    public String showNotifications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Asumiendo que el nombre de usuario es el email.
        UserDO user = userService.getDOByEmail(email);

        List<NotificationDTO> notificationList = new ArrayList<>();
        List<NotificationDTO> notificationCheckedList = new ArrayList<>();
        List<NotificationDTO> notificationPendingList = new ArrayList<>();
        if (user.getRol().equals("ROLE_ALUMNO")) {
            notificationList = notificationService.getAllByUserIdAndChecked(user.getId());
        } else {
            notificationList = notificationService.getAllWithStatusNotNull();
            notificationPendingList = notificationService.getAllByStatus("PENDING");

            if (notificationList != null) {
                notificationCheckedList.addAll(notificationList);
                if (notificationPendingList != null) {
                    notificationCheckedList.removeAll(notificationPendingList);
                }
            }
        }

        model.addAttribute("notificationList", notificationList);
        model.addAttribute("notificationCheckedList", notificationCheckedList);
        model.addAttribute("notificationPendingList", notificationPendingList);

        return "notificaciones.html";
    }

    /**
     * Aprueba una notificacion y actualiza el anuncio para hacerlo editable por el
     * alumno que lo creo
     * Envia un correo a dicho alumno para informarle.
     *
     * @param id                 id de la notificacion
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return redirect:/notificaciones/
     */
    @GetMapping("/aprobarNotificacion/{id}")
    public String approveNotification(@PathVariable int id, RedirectAttributes redirectAttributes) {
        NotificationDO notificationDO = notificationService.respondToNotification(id, true);

        AdDO ad = adService.getAdDOById(notificationDO.getAd().getId());
        if (ad != null) {
            ad.setModificationDate(Calendar.getInstance().getTime());
            adService.update(ad);
        }
        notificationService.sendEmailToAlumn(notificationDO);
        redirectAttributes.addFlashAttribute("successMessage", "Notificación aprobada con éxito.");
        return "redirect:/notificaciones/";
    }

    /**
     * Rechaza una notificacion y envia un correo al alumno que la envio para
     * informarle
     *
     * @param id                 id de la notificacion
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return redirect:/notificaciones/
     */
    @GetMapping("/rechazarNotificacion/{id}")
    public String rejectNotification(@PathVariable int id, RedirectAttributes redirectAttributes) {
        NotificationDO notificationDO = notificationService.respondToNotification(id, false);
        notificationService.sendEmailToAlumn(notificationDO);
        redirectAttributes.addFlashAttribute("errorMessage", "Notificación rechazada.");
        return "redirect:/notificaciones/";
    }

    /**
     * Elimina las notificaciones seleccionadas
     *
     * @param notificationIds    ids de las notificaciones a eliminar
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return redirect:/notificaciones/
     */
    @PostMapping("/rechazarNotificacion")
    public String deleteNotifications(@RequestParam List<Integer> notificationIds,
            RedirectAttributes redirectAttributes) {
        try {
            notificationService.deleteNotificationList(notificationIds);
            redirectAttributes.addFlashAttribute("successMessage", "Notificaciones eliminadas correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar notificaciones");
        }
        return "redirect:/notificaciones/";
    }
}