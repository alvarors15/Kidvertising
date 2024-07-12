package com.example.Mediateca.Controllers;

import com.example.Mediateca.DTOs.AdDTO;
import com.example.Mediateca.DTOs.ChildRoleTypeDTO;
import com.example.Mediateca.DTOs.GenderRoleFemaleTypeDTO;
import com.example.Mediateca.DTOs.GenderRoleMaleTypeDTO;
import com.example.Mediateca.DTOs.KeyWordsDTO;
import com.example.Mediateca.DTOs.MediaDTO;
import com.example.Mediateca.DTOs.MediaTypeDTO;
import com.example.Mediateca.DTOs.UserDTO;
import com.example.Mediateca.DTOs.TopicDTO;
import com.example.Mediateca.Domain.AdDO;
import com.example.Mediateca.Domain.NotificationDO;
import com.example.Mediateca.Services.AdService;
import com.example.Mediateca.Services.ChildRoleTypeService;
import com.example.Mediateca.Services.GenderRoleFemaleTypeService;
import com.example.Mediateca.Services.GenderRoleMaleTypeService;
import com.example.Mediateca.Services.MediaService;
import com.example.Mediateca.Services.MediaTypeService;
import com.example.Mediateca.Services.NotificationService;
import com.example.Mediateca.Services.TopicService;
import com.example.Mediateca.Services.KeyWordsService;
import com.example.Mediateca.Services.UserService;
import com.example.Mediateca.Utils.FileStorageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controlador que maneja todas las operaciones relacionadas con los anuncios.
 * Contiene metodos para visualizar, editar, eliminar y crear anuncios, ademas
 * de para gestionar las categorias y visualizar
 * las estadisticas de los anuncios.
 */

@Controller
@RequestMapping("")
public class AdController {

    // Servicio de anuncios
    @Autowired
    private AdService adService;

    // Servicios las categorias
    @Autowired
    private ChildRoleTypeService childRoleTypeService;
    @Autowired
    private GenderRoleMaleTypeService genderRoleMaleTypeService;
    @Autowired
    private GenderRoleFemaleTypeService genderRoleFemaleTypeService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaTypeService mediaTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private KeyWordsService keyWordsService;

    // Servicio de notificaciones
    @Autowired
    private NotificationService notificationService;

    // Servicio de almacenaje de archivos
    @Autowired
    private FileStorageUtils fileStorageService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    /**
     * Redirecciona a inicio
     */
    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }

    /**
     * Muestra la pagina de inicio con los ultimos anuncios separados por
     * tipo multimedia (videos, imagenes y audios).
     *
     * @param model
     * @return inicio.html
     */
    @GetMapping("/inicio")
    public String init(Model model) {
        List<AdDTO> ads = adService.getAllAds();

        Map<String, List<AdDTO>> adByMultimediaMap = adService.getLastsMultimedia();

        model.addAttribute("ads", ads);

        model.addAttribute("videos", adByMultimediaMap.get("Videos"));
        model.addAttribute("images", adByMultimediaMap.get("Images"));
        model.addAttribute("audios", adByMultimediaMap.get("Audios"));

        return "inicio.html";
    }

    /**
     * Muestra la pagina de listado de anuncios
     *
     * @param message Mensaje opcional que puede ser mostrado en la vista
     * @param model
     * @return anuncios.html
     */
    @GetMapping("/anuncios")
    public String showAds(@RequestParam(value = "message", required = false) String message, Model model) {
        List<AdDTO> ads = adService.getAllAds();

        // Obtenemos todas las categorias para usar los filtros en la vista
        List<ChildRoleTypeDTO> childRoleTypeList = childRoleTypeService.getAll();
        List<GenderRoleMaleTypeDTO> genderRoleMaleTypeList = genderRoleMaleTypeService.getAll();
        List<GenderRoleFemaleTypeDTO> genderRoleFemaleTypeList = genderRoleFemaleTypeService.getAll();
        List<MediaDTO> mediaList = mediaService.getAll();
        List<MediaTypeDTO> mediaTypeList = mediaTypeService.getAll();
        List<TopicDTO> topicList = topicService.getAll();
        List<KeyWordsDTO> keyWordsList = keyWordsService.getAll();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        model.addAttribute("childRoleTypeList", childRoleTypeList);
        model.addAttribute("genderRoleMaleTypeList", genderRoleMaleTypeList);
        model.addAttribute("genderRoleFemaleTypeList", genderRoleFemaleTypeList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("mediaTypeList", mediaTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("keyWordsList", keyWordsList);
        model.addAttribute("currentYear", currentYear);
        // Indica que es la vista de anuncios general, no la de un usuario en particular
        model.addAttribute("isMyAds", Boolean.FALSE);

        model.addAttribute("ads", ads);

        // Se mostrara un mensaje cuando vengamos de eliminar un anuncio para informar
        // de su eliminacion
        if (message != null) {
            model.addAttribute("message", message);
        }

        return "anuncios.html";
    }

    /**
     * Muestra la pagina de detalle de un anuncio
     *
     * @param adId  id del anuncio
     * @param model
     * @return detalle.html
     */
    @GetMapping("/detalleAnuncio")
    public String showAdDetail(@RequestParam(value = "adId") Long adId, Model model) {

        AdDTO ad = adService.getAdById(adId);

        // Numero de menores que hay en el anuncio
        int numberOfChilds = 1;
        numberOfChilds += (ad.getChildRole2() != null) ? 1 : 0;
        numberOfChilds += (ad.getChildRole3() != null) ? 1 : 0;

        List<NotificationDO> notificationList = notificationService.getPendingNotificationByAd(adId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("ad", ad);

        // Parametros para saber si el anuncio es editable en caso de que el usuario sea
        // un anuncio
        model.addAttribute("isPendingAprovalEditing", notificationList != null && !notificationList.isEmpty());
        model.addAttribute("actualUserIsAuthor", ad.getUserEmail() != null && ad.getUserEmail().equals(username));

        model.addAttribute("numberOfChilds", numberOfChilds);

        // Devolvemos el nombre de la vista que mostrará los detalles del anuncio
        return "detalle.html";
    }

    /**
     * Muestra la pagina para crear un nuevo anuncio
     *
     * @param model
     * @return nuevoAnuncio.html
     */
    @GetMapping("/nuevoAnuncio")
    public String addNewAd(Model model) {

        AdDTO ad = new AdDTO();

        // Le pasamos todas las categorias para mostrarlas en el formulario
        List<ChildRoleTypeDTO> childRoleTypeList = childRoleTypeService.getAll();
        List<GenderRoleMaleTypeDTO> genderRoleMaleTypeList = genderRoleMaleTypeService.getAll();
        List<GenderRoleFemaleTypeDTO> genderRoleFemaleTypeList = genderRoleFemaleTypeService.getAll();
        List<MediaDTO> mediaList = mediaService.getAll();
        List<MediaTypeDTO> mediaTypeList = mediaTypeService.getAll();
        List<TopicDTO> topicList = topicService.getAll();
        List<KeyWordsDTO> keyWordsList = keyWordsService.getAll();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<Integer> years = IntStream.rangeClosed(1900, currentYear)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("years", years);

        model.addAttribute("ad", ad);
        model.addAttribute("childRoleTypeList", childRoleTypeList);
        model.addAttribute("genderRoleMaleTypeList", genderRoleMaleTypeList);
        model.addAttribute("genderRoleFemaleTypeList", genderRoleFemaleTypeList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("mediaTypeList", mediaTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("currentYear", years);
        model.addAttribute("keyWordsList", keyWordsList);

        return "nuevoAnuncio.html";
    }

    /**
     * Muestra la pagina para editar nuevo anuncio
     *
     * @param adId               id del anuncio
     * @param model
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return editarDetalles.html
     */
    @GetMapping("/detalleAnuncio/editar")
    public String editAdDetail(@RequestParam(value = "adId") Long adId, Model model,
            RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO userDTO = userService.getUserByEmail(username);

        AdDTO ad = adService.getAdById(adId);

        // Si el usuario es un alumno y el anuncio no es editable, redirigimos a la
        // pagina del detalle
        if (userDTO.getRol().equals("ROLE_ALUMNO") && !ad.getIsEditable()) {
            redirectAttributes.addAttribute("adId",
                    ad.getId());
            return "redirect:/detalleAnuncio";
        }

        // Numero de menores que hay en el anuncio
        int numberOfChilds = 1;
        numberOfChilds += (ad.getChildRole2() != null) ? 1 : 0;
        numberOfChilds += (ad.getChildRole3() != null) ? 1 : 0;

        // Le pasamos todas las categorias para mostrarlas en el formulario
        List<ChildRoleTypeDTO> childRoleTypeList = childRoleTypeService.getAll();
        List<GenderRoleMaleTypeDTO> genderRoleMaleTypeList = genderRoleMaleTypeService.getAll();
        List<GenderRoleFemaleTypeDTO> genderRoleFemaleTypeList = genderRoleFemaleTypeService.getAll();
        List<MediaDTO> mediaList = mediaService.getAll();
        List<MediaTypeDTO> mediaTypeList = mediaTypeService.getAll();
        List<TopicDTO> topicList = topicService.getAll();
        List<KeyWordsDTO> keyWordsList = keyWordsService.getAll();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<Integer> years = IntStream.rangeClosed(1900, currentYear)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("ad", ad);
        model.addAttribute("years", years);
        model.addAttribute("childRoleTypeList", childRoleTypeList);
        model.addAttribute("genderRoleMaleTypeList", genderRoleMaleTypeList);
        model.addAttribute("genderRoleFemaleTypeList", genderRoleFemaleTypeList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("mediaTypeList", mediaTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("keyWordsList", keyWordsList);
        model.addAttribute("keyWordsSelectedList", ad.getKeyWords());
        model.addAttribute("numberOfChilds", numberOfChilds);
        model.addAttribute("mediaUrl", ad.getMediaUrl());

        return "editarDetalles.html";
    }

    /**
     * Guarda el anuncio nuevo/editado en bbdd y muestra su pagina de detalle
     *
     * @param ad                 DTO del anuncio
     * @param keyWordsSelected   palabras clave seleccionadas para almacenarlas en
     *                           bbdd
     * @param file               archivo multimedia seleccionado
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @param model
     * @return redirect:/detalleAnuncio
     */
    @PostMapping("/guardarAnuncio")
    public String saveEditedAd(@ModelAttribute("ad") AdDTO ad,
            @RequestParam("keyWordsSelected") List<String> keyWordsSelected,
            @RequestParam(value = "file", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws InterruptedException {

        AdDO editedAD = adService.saveAd(ad, keyWordsSelected, file);

        redirectAttributes.addAttribute("adId",
                editedAD.getId());
        redirectAttributes.addFlashAttribute("sucess",
                "Se ha actualizado el anuncio correctamente");
        return "redirect:/detalleAnuncio";
    }

    /**
     * Elimina un anuncio y su archivo del sistema
     *
     * @param adId               id del anuncio
     * @param model
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     */
    @PostMapping("/eliminarAnuncio/{adId}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<String> deleteAd(@PathVariable Long adId, Model model,
            RedirectAttributes redirectAttrs)
            throws Exception {
        try {
            AdDO ad = adService.getAdDOById(adId);
            fileStorageService.delete(ad.getMediaUrl());
            adService.deleteAd(adId);
            return ResponseEntity
                    .ok("{\"message\": \"Se ha eliminado el anuncio correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Error al eliminar el anuncio.\"}");
        }
    }

    /**
     * Elimina todos los anuncios de un usuario
     *
     * @param userId             id del usuario
     * @param model
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     */
    @PostMapping("/eliminarAnuncios/{userId}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<String> deleteAllAds(@PathVariable Integer userId, Model model,
            RedirectAttributes redirectAttrs)
            throws Exception {
        try {
            Integer deletedCount = adService.deleteAllUserAds(userId);
            return ResponseEntity
                    .ok("{\"message\": \"Se han eliminado los " + deletedCount + " anuncios del usuario.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Error al eliminar los anuncios.\"}");
        }
    }

    /**
     * Muestra la pagina de mi listado de anuncios
     *
     * @param model
     * @return anuncios.html
     */
    @GetMapping("/misAnuncios")
    public String showMyAds(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO userDTO = userService.getUserByEmail(username);
        List<AdDTO> ads = adService.getByUser(userDTO.getId());

        List<ChildRoleTypeDTO> childRoleTypeList = childRoleTypeService.getAll();
        List<GenderRoleMaleTypeDTO> genderRoleMaleTypeList = genderRoleMaleTypeService.getAll();
        List<GenderRoleFemaleTypeDTO> genderRoleFemaleTypeList = genderRoleFemaleTypeService.getAll();
        List<MediaDTO> mediaList = mediaService.getAll();
        List<MediaTypeDTO> mediaTypeList = mediaTypeService.getAll();
        List<TopicDTO> topicList = topicService.getAll();
        List<KeyWordsDTO> keyWordsList = keyWordsService.getAll();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        model.addAttribute("childRoleTypeList", childRoleTypeList);
        model.addAttribute("genderRoleMaleTypeList", genderRoleMaleTypeList);
        model.addAttribute("genderRoleFemaleTypeList", genderRoleFemaleTypeList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("mediaTypeList", mediaTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("keyWordsList", keyWordsList);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("isMyAds", Boolean.TRUE);

        model.addAttribute("ads", ads);

        return "anuncios.html";
    }

    /**
     * Muestra la pagina del listado de anuncios de un usuario
     *
     * @param userId id del usuario
     * @param model
     * @return anuncios.html
     */
    @GetMapping("/anunciosUsuario")
    public String showAdsUser(@RequestParam(value = "userId") Integer userId, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO userDTOSession = userService.getUserByEmail(username);

        UserDTO userDTO = userService.getDTOById(userId);

        // Si el usuario es el de la sesion, redirigimos a la pagina de misAnuncios
        if (userId.equals(userDTOSession.getId())) {
            return "redirect:/misAnuncios";
        }

        List<AdDTO> ads = adService.getByUser(userDTO.getId());

        List<ChildRoleTypeDTO> childRoleTypeList = childRoleTypeService.getAll();
        List<GenderRoleMaleTypeDTO> genderRoleMaleTypeList = genderRoleMaleTypeService.getAll();
        List<GenderRoleFemaleTypeDTO> genderRoleFemaleTypeList = genderRoleFemaleTypeService.getAll();
        List<MediaDTO> mediaList = mediaService.getAll();
        List<MediaTypeDTO> mediaTypeList = mediaTypeService.getAll();
        List<TopicDTO> topicList = topicService.getAll();
        List<KeyWordsDTO> keyWordsList = keyWordsService.getAll();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        model.addAttribute("childRoleTypeList", childRoleTypeList);
        model.addAttribute("genderRoleMaleTypeList", genderRoleMaleTypeList);
        model.addAttribute("genderRoleFemaleTypeList", genderRoleFemaleTypeList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("mediaTypeList", mediaTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("keyWordsList", keyWordsList);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("isMyAds", Boolean.TRUE);
        model.addAttribute("user", userDTO);

        model.addAttribute("ads", ads);

        return "anuncios.html";
    }

    /**
     * Muestra la pagina para gestionar las categorias
     *
     * @param model
     * @return gestionarCategorias.html
     */
    @GetMapping("/gestionarCategorias")
    public String manageCategories(Model model) {
        List<ChildRoleTypeDTO> childRoleTypeList = childRoleTypeService.getAll();
        List<GenderRoleMaleTypeDTO> genderRoleMaleTypeList = genderRoleMaleTypeService.getAll();
        List<GenderRoleFemaleTypeDTO> genderRoleFemaleTypeList = genderRoleFemaleTypeService.getAll();
        List<MediaDTO> mediaList = mediaService.getAll();
        List<MediaTypeDTO> mediaTypeList = mediaTypeService.getAll();
        List<TopicDTO> topicList = topicService.getAll();
        List<KeyWordsDTO> keyWordsList = keyWordsService.getAll();

        model.addAttribute("childRoleTypeList", childRoleTypeList);
        model.addAttribute("genderRoleMaleTypeList", genderRoleMaleTypeList);
        model.addAttribute("genderRoleFemaleTypeList", genderRoleFemaleTypeList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("mediaTypeList", mediaTypeList);
        model.addAttribute("topicList", topicList);
        model.addAttribute("keyWordsList", keyWordsList);

        return "gestionarCategorias.html";
    }

    /**
     * Elimina las categorias seleccionadas de bbdd, devuelve la pagina para
     * gestionar las categorias
     *
     * @param categoryIds        ids de las categorias a eliminar
     * @param categoryType       tipo de categoria
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return gestionarCategorias.html
     */
    @PostMapping("/eliminarCategorias")
    public String deleteCategories(@RequestParam List<Integer> categoryIds,
            @RequestParam String categoryType,
            RedirectAttributes redirectAttrs) {

        // Llamamos al service correspondiente de cada tipo de categoria para
        // eliminarlas
        try {
            switch (categoryType) {
                case "genderRoleFemaleType":
                    genderRoleFemaleTypeService.deleteByIdList(categoryIds);
                    break;
                case "genderRoleMaleType":
                    genderRoleMaleTypeService.deleteByIdList(categoryIds);
                    break;
                case "childRoleType":
                    childRoleTypeService.deleteByIdList(categoryIds);
                    break;
                case "topic":
                    topicService.deleteByIdList(categoryIds);
                    break;
                case "mediaType":
                    mediaTypeService.deleteByIdList(categoryIds);
                    break;
                case "media":
                    mediaService.deleteByIdList(categoryIds);
                    break;
                case "keyWord":
                    keyWordsService.deleteByIdList(categoryIds);
                    break;
                default:
                    throw new IllegalArgumentException("Categoría desconocida: " + categoryType);
            }
            redirectAttrs.addFlashAttribute("successMessage", "Categorías eliminadas correctamente");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Error al eliminar las categorías");
        }
        redirectAttrs.addFlashAttribute("fromDeleteUsers", true);
        return "redirect:/gestionarCategorias";
    }

    /**
     * Muetsra la pagina de metricas y graficos de los anuncios, para ver sus
     * estadisticas
     *
     * @param model
     * @return metricas.html
     */
    @GetMapping("/metricas")
    public String metrics(Model model) {

        Integer totalAds = adService.countAllAds();

        Map<String, Map<String, Integer>> mapAllCounts = adService.getAllCounts();

        model.addAttribute("mapGenderRelation", mapAllCounts.get("RELACION GENERO"));
        model.addAttribute("mapMaleRole", mapAllCounts.get("ROL HOMBRE"));
        model.addAttribute("mapFemaleRole", mapAllCounts.get("ROL MUJER"));
        model.addAttribute("mapChildRole", mapAllCounts.get("ROL MENOR"));
        model.addAttribute("mapChildGender", mapAllCounts.get("GENERO MENOR"));
        model.addAttribute("mapProtagonism", mapAllCounts.get("PROTAGONISMO"));
        model.addAttribute("mapTopic", mapAllCounts.get("TEMATICA"));
        model.addAttribute("mapMedia", mapAllCounts.get("MEDIO"));
        model.addAttribute("mapMediaType", mapAllCounts.get("TIPO MEDIO"));
        model.addAttribute("mapAdvertisingType", mapAllCounts.get("TIPO PUBLICIDAD"));
        model.addAttribute("mapYear", mapAllCounts.get("ANIO"));
        model.addAttribute("totalAds", totalAds);

        return "metricas.html";
    }

    /**
     * Muetsra la pagina que ofrece la informacion sobre quienes somos y cual es el
     * objetivo de esta aplicacion
     *
     * @param model
     * @return quienesSomos.html
     */
    @GetMapping("/quienesSomos")
    public String whoWeAre(Model model) {
        return "quienesSomos.html";
    }
}