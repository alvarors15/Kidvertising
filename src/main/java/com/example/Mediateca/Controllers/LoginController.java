package com.example.Mediateca.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para manejar las operaciones relacionadas con la autenticacion
 * del usuario,
 * como iniciar sesion y gestionar contrasenias.
 */

@Controller
@RequestMapping("")
public class LoginController {

    /**
     * Muestra la página de inicio de sesión
     *
     * @param expiredToken    Indica si se muestra un mensaje de que el token para
     *                        resetear la contrasenia ha expirado.
     * @param resetSuccess    Indica si se muestra un mensaje de reseteo de
     *                        contraseña exitoso.
     * @param resetFailed     Indica si se muestra un mensaje de fallo en el reseteo
     *                        de contraseña.
     * @param registerSuccess Indica si se muestra un mensaje de registro exitoso.
     * @param emailSent       Indica si se muestra un mensaje de email enviado para
     *                        resetear la contrasenia.
     * @param model
     * @return login
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) Boolean expiredToken,
            @RequestParam(required = false) Boolean resetSuccess,
            @RequestParam(required = false) Boolean resetFailed,
            @RequestParam(required = false) Boolean registerSuccess,
            @RequestParam(required = false) Boolean emailSent,
            @RequestParam(required = false) Boolean userNotFound,
            Model model) {

        model.addAttribute("expiredToken", expiredToken != null && expiredToken);
        model.addAttribute("resetSuccess", resetSuccess != null && resetSuccess);
        model.addAttribute("resetFailed", resetFailed != null && resetFailed);
        model.addAttribute("registerSuccess", registerSuccess != null && registerSuccess);
        model.addAttribute("emailSent", emailSent != null && emailSent);
        model.addAttribute("userNotFound", userNotFound != null && userNotFound);

        return "login";
    }

    /**
     * Muestra la vista para resetear la contrasenia.
     * 
     * @param userNotFound Indica si no se ha encontrado el usuario en bbdd
     * @param model
     * @return nuevaPassword.html
     */
    @GetMapping("/nuevaPassword")
    public String newPassword(@RequestParam(required = false) Boolean userNotFound, Model model) {
        model.addAttribute("userNotFound", userNotFound != null && userNotFound);
        return "nuevaPassword.html";
    }

    /**
     * Muestra la vista para setear la contrasenia al registrarse.
     *
     * @return crearPassword.html
     */
    @GetMapping("/crearPassword")
    public String setPassword() {
        return "crearPassword.html";
    }

}
