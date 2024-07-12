package com.example.Mediateca.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Mediateca.DTOs.UserDTO;
import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.Services.UserService;

/**
 * Controlador que maneja todas las operaciones relacionadas con la gestion de
 * usuarios.
 * Proporciona funcionalidades para crear, actualizar, y eliminar usuarios, asi
 * como la gestion de roles.
 */

@Controller
@RequestMapping("")
public class UserController {

    // Servicio de usuarios
    @Autowired
    private UserService userService;

    /**
     * Muestra la página para crear un nuevo usuario, en ella se puede agregar un
     * usuario o ver el listado de usuarios en la aplicacion
     *
     * @param model
     * @return gestionarUsuarios.html
     */
    @GetMapping("/gestionarUsuarios")
    public String newUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO userDTO = userService.getUserByEmail(username);

        // Le pasamos el listado de usuarios excepto el de la sesion
        model.addAttribute("users", userService.getAllUsersButActual(userDTO.getId()));
        return "gestionarUsuarios.html";
    }

    /**
     * Agrega un nuevo usuario a la bbdd a partir de los datos indicados en el
     * formulario.
     * Registra el usuario si no existe y muestra un mensaje de éxito o error según
     * corresponda.
     *
     * @param model
     * @param userDTO            el DTO del usuario a guardar
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return redirect:/gestionarUsuarios
     */
    @PostMapping("/agregarUsuario")
    public String addUser(Model model, @ModelAttribute("user") UserDTO userDTO, RedirectAttributes redirectAttributes) {

        try {
            UserDO userDO = userService.getDOByEmail(userDTO.getEmail());
            // Si el usuario no esta en bbdd, lo almacenamos
            if (userDO == null) {
                userDO = new UserDO();
                userDO.fromDTO(userDTO);

                userService.saveUser(userDO);
                redirectAttributes.addFlashAttribute("successMessage", "Usuario añadido correctamente");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al añadir usuario");
        }

        return "redirect:/gestionarUsuarios"; // Nombre del archivo HTML en templates
    }

    /**
     * Cambia el rol de un usuario entre PROFESOR y ALUMNO.
     * Solo los profesores pueden realizar esta operación.
     *
     * @param userId id del usuario a actualizar
     * @param model
     */
    @PostMapping("/actualizarRol/{userId}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<String> updateUserRole(@PathVariable Integer userId, Model model) {
        try {
            UserDO userDO = userService.updateUserRol(userId);
            return ResponseEntity
                    .ok("{\"message\": \"Rol del usuario " + userDO.getUsername() + " actualizado correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Error al actualizar el rol del usuario.\"}");
        }
    }

    /**
     * Elimina una lista de usuarios seleccionados.
     *
     * @param userIds            lista de los ids de los usuarios a eliminar
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return redirect:/gestionarUsuarios
     */
    @PostMapping("/eliminarUsuarios")
    public String deleteSelectedUsers(@RequestParam List<Integer> userIds,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserList(userIds);
            redirectAttributes.addFlashAttribute("successMessage", "Usuarios eliminados correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar usuarios");
        }
        redirectAttributes.addFlashAttribute("fromDeleteUsers", true);
        return "redirect:/gestionarUsuarios";
    }
}
