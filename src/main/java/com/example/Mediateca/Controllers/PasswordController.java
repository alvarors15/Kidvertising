package com.example.Mediateca.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Mediateca.Domain.TokenResetPasswordDO;
import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.Services.ResetPasswordService;
import com.example.Mediateca.Services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * Controlador que maneja las operaciones relacionadas con la gestion de
 * contraseias de usuario,
 * como el reseteo de contraseñas olvidadas y la asignacion de contrasenias al
 * registrarse.
 */
@Controller
public class PasswordController {

    // Servicio para resetear la contrasenia
    @Autowired
    private ResetPasswordService tokenResetPasswordService;

    // Servicio de usuarios
    @Autowired
    private UserService userService;

    /**
     * Procesa la solicitud de restablecimiento de contrasenia, genera un token
     * seguro y lo envia por correo al usuario para permitir el restablecimiento de
     * la contrasenia.
     *
     * @param userEmail correo del usuario que quiere restablecer la contrasenia
     * @return login si el proceso es correcto, nuevaPassword.html si es incorrecto
     */
    @PostMapping("/reset-password")
    public String handlePasswordReset(@RequestParam("email") String userEmail) {

        UserDO userDO = userService.getDOByEmail(userEmail);
        if (userDO == null) {
            // Si el usuario no existe, devolvemos la pagina de login con un mensaje de
            // error
            return "redirect:/login?userNotFound=true";
        }
        // Generamos un token de seguridad y lo almacenamos en bbdd
        String token = generateSecureResetToken();
        storeResetToken(userDO.getId(), token);

        // Enviamos el email con el link para reestablecer la contrasenia
        tokenResetPasswordService.sendEmail(token, userEmail);

        // Redirigir a la pagina de login con un mensaje que indica que el correo ha
        // sido enviado
        return "redirect:/login?emailSent=true";
    }

    /**
     * Cambia la contrasenia del usuario si el token es correcto.
     * Verifica si el token no ha sido usado y no ha expirado, y permite al usuario
     * establecer una nueva contrasenia.
     *
     * @param token       el token de restablecimiento.
     * @param newPassword la nueva contraseña que el usuario desea establecer.
     * @return login
     */
    @PostMapping("/cambiarPassword")
    public String changePassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        // Validar el token y comprobar si ha expirado
        TokenResetPasswordDO tokenDO = tokenResetPasswordService.findByTokenAndNotUsedAndNotExpired(token);
        if (tokenDO != null) {
            // Se actualiza la contrasenia en bbdd
            userService.updateUserPasswordWithToken(tokenDO, newPassword);
            // Eliminar el token
            invalidateToken(tokenDO);
            // Si ha ido todo correcto mostramos la pagina de login con un mensaje de exito
            return "redirect:/login?resetSuccess=true";
        } else {
            // Si ha ocurrido algun problema mostramos la pagina de login con un mensaje de
            // error
            return "redirect:/login?resetFailed=true";
        }
    }

    /**
     * Muestra la pagina para que el usuario pueda restablecer su contrasenia, si el
     * token es valido.
     *
     * @param token el token de restablecimiento.
     * @param model
     * @return resetPassword.html si va todo correcto, login si hay algun error
     */
    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        // Validar el token y comprobar si ha expirado
        TokenResetPasswordDO tokenDO = tokenResetPasswordService.findByTokenAndNotUsedAndNotExpired(token);
        if (tokenDO != null) {
            model.addAttribute("token", token);
            // Si es correcto devolvemos la vista para restablecer la contrasenia
            return "resetPassword.html";
            // Si el token no es valido, devolvemos la pagina de login con un mensaje
            // informando de esto
        } else
            return "redirect:/login?expiredToken=true";
    }

    /**
     * Registra un nuevo usuario asignandole la contrasenia que este indica
     *
     * @param email       email del usuario.
     * @param newPassword contrasenia que indica el usuario.
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestParam String email,
            @RequestParam String newPassword) {
        Map<String, Object> response = new HashMap<>();
        UserDO user = userService.getDOByEmail(email);

        if (user == null) {
            response.put("success", false);
            response.put("message",
                    "No existe ningún usuario con ese correo en el sistema. Por favor, póngase en contacto con su profesor para darse de alta.");
            return ResponseEntity.ok(response);
        } else {
            if (user.getPassword() == null) {
                userService.updateUserPassword(user, newPassword);
                response.put("success", true);
                response.put("message", "Usuario registrado exitosamente.");
            } else {
                response.put("success", false);
                response.put("message",
                        "Este usuario ya se encuentra registrado, si no recuerda la contraseña pruebe a reestablecerla.");
            }
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Genera un token de restablecimiento de contraseña seguro utilizando UUID.
     *
     * @return el token.
     */
    private String generateSecureResetToken() {
        // Genera un UUID aleatorio
        return UUID.randomUUID().toString();
    }

    /**
     * Almacena el token en bbdd
     *
     * @param userId id del usuario
     * @param token  el token
     */
    public void storeResetToken(Integer userId, String token) {
        tokenResetPasswordService.saveToken(userId, token);
    }

    /**
     * Elimina el token de bbdd
     *
     * @param tokenDO el token.
     */
    private void invalidateToken(TokenResetPasswordDO tokenDO) {
        tokenResetPasswordService.invalidateToken(tokenDO);
    }
}