package com.example.Mediateca.Controllers;

import com.example.Mediateca.DTOs.AdDTO;
import com.example.Mediateca.Services.AdService;
import com.example.Mediateca.Services.ImportUserService;
import com.example.Mediateca.Utils.ExcelGeneratorUtils;
import com.example.Mediateca.Utils.FileStorageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador que maneja la generacion, importacion y descarga de archivos
 * excel.
 * Este controlador soporta operaciones de subida de archivos para importar
 * y la descarga de archivos Excel junto con imágenes en un archivo ZIP.
 */
@Controller
@RequestMapping("")
public class ExcelController {

    // Servicio de anuncios
    @Autowired
    private AdService adService;

    // Servicio para importar los usuarios
    @Autowired
    private ImportUserService importUserService;

    // Servicio de almacenamiento de archivos multimedia
    @Autowired
    private FileStorageUtils fileStorageService;

    @Value("${app.baseUrl}")
    private String baseUrl;

    /**
     * Descarga un fichero Excel y el archivo multimedia asociado a un anuncio en un
     * archivo ZIP.
     * El Excel generado contiene los detalles del anuncio
     *
     * @param adId id del anuncio
     * @return ResponseEntity con el archivo ZIP si la operación es
     *         exitosa, o un mensaje de error
     *         si ocurre un problema durante el proceso
     */
    @GetMapping("/descargarAnuncio")
    public ResponseEntity<InputStreamResource> downloadExcelAndImage(@RequestParam(value = "adId") Long adId) {
        AdDTO ad = adService.getAdById(adId); // Asegúrate de que esto obtiene la información necesaria

        // Genera el Excel
        ExcelGeneratorUtils.generateExcel(ad);

        File excelFile = new File("anuncios.xlsx");
        String imageUrl = ad.getMediaUrl();
        String imageName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
        // Decodificamos el nombre del archivo multimedia
        String decodedImageName = fileStorageService.decodeFileName(imageName);

        // Crear archivo ZIP
        File zipFile = new File("anuncio.zip");
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
                FileInputStream excelFileInputStream = new FileInputStream(excelFile)) {

            // Agregar archivo Excel al ZIP
            ZipEntry excelZipEntry = new ZipEntry(excelFile.getName());
            zipOut.putNextEntry(excelZipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = excelFileInputStream.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.closeEntry();

            // Descargar y agregar la imagen al ZIP
            try (InputStream imageInputStream = new URL(baseUrl + imageUrl).openStream()) {
                ZipEntry imageZipEntry = new ZipEntry(decodedImageName);
                zipOut.putNextEntry(imageZipEntry);
                while ((length = imageInputStream.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                zipOut.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }

            zipOut.finish();

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Devolver el archivo ZIP
        try {
            InputStreamResource zipResource = new InputStreamResource(new FileInputStream(zipFile));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(zipFile.length())
                    .body(zipResource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Proporciona una plantilla de Excel para la importación de nuevos usuarios.
     *
     * @return ResponseEntity con la plantilla Excel
     *         de plantilla, o un mensaje de error
     *         si el archivo no se encuentra.
     */
    @GetMapping("/descargarPlantilla")
    public ResponseEntity<InputStreamResource> downloadExcelTemplate() {
        // Genera la plantilla de Excel
        ExcelGeneratorUtils.generateTemplateExcel();

        File excelFile = new File(
                "plantilla_usuarios.xlsx");
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(excelFile));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plantilla_usuarios.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(excelFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Maneja la subida de archivos Excel para la importación de nuevos usuario.
     * 
     * @param file               Excel que contiene los datos de los
     *                           usuarios a importar.
     * @param redirectAttributes atributos que se le pasan al llamar a otro metodo
     *                           del
     *                           controller
     * @return redirect:/gestionarUsuarios
     */
    @PostMapping("/cargarUsuarios")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String message = "";
        if (!file.isEmpty()) {
            try {
                importUserService.processExcel(file);
                message = "Usuarios añadidos correctamente";
            } catch (Exception e) {
                message = "Error al añadir los usuarios";
            }
        } else {
            message = "Por favor, seleccione un archivo para cargar.";
        }

        redirectAttributes.addFlashAttribute("successMessage", message);
        return "redirect:/gestionarUsuarios";
    }
}
