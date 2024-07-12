package com.example.Mediateca.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Clase utilitaria para gestionar el almacenamiento de archivos en el sistema.
 * Los archivos se almacenan en una ubicacion definida en la configuracion de la
 * aplicacion.
 */
@Component
public class FileStorageUtils {

    private final int MAX_RETRIES = 5;

    private final int RETRY_DELAY = 500;

    @Value("${upload.path}")
    private String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    /**
     * Almacena un archivo cargado en el sistema y devuelve la
     * ruta del archivo almacenado.
     * Normaliza y codifica el nombre del archivo para asegurar compatibilidad y
     * evitar colisiones.
     *
     * @param file el archivo multipart a almacenar.
     * @return la ruta al archivo almacenado.
     * @throws RuntimeException si el archivo esta vacio o no se puede almacenar.
     */
    public String store(MultipartFile file) {
        String newFilename = null;
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Error al guardar archivo. Error: El archivo está vacío.");
            }
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String fileExtension = "";
                int dotIndex = originalFilename.lastIndexOf('.');
                if (dotIndex > 0) {
                    fileExtension = originalFilename.substring(dotIndex).toLowerCase();
                    String filename = originalFilename.substring(0, dotIndex);
                    String normalizeFilename = normalize(filename);
                    String encodedFilename = URLEncoder.encode(normalizeFilename + "_" + UUID.randomUUID().toString(),
                            StandardCharsets.UTF_8.toString());
                    // Añadir un UUID al nombre del archivo
                    newFilename = encodedFilename + fileExtension;
                }
                Path storagePath = Paths.get(uploadPath).resolve(newFilename);
                Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo. Error: " + e.getMessage());
        }

        isFileAccessible("/resources/img/" + newFilename);
        return "/resources/img/" + newFilename;
    }

    /**
     * Elimina un archivo en el sistema segun su ruta.
     *
     * @param filePath la ruta del archivo a eliminar.
     * @throws RuntimeException si el archivo no se puede eliminar.
     */
    public void delete(String filePath) {
        try {
            String filename = Paths.get(filePath).getFileName().toString();
            Path pathToDelete = Paths.get(uploadPath).resolve(filename);
            if (Files.exists(pathToDelete)) {
                Files.delete(pathToDelete);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar archivo. Error: " + e.getMessage());
        }
    }

    /**
     * Normaliza el nombre de un archivo eliminando acentos y caracteres especiales
     * y reemplazandolos con guiones bajos.
     *
     * @param input el nombre del archivo a normalizar.
     * @return el nombre del archivo normalizado.
     */
    public static String normalize(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String cleanRoute = pattern.matcher(normalized).replaceAll("");

        // Reemplaza caracteres no ASCII (adicional), espacios y caracteres especiales
        cleanRoute = cleanRoute.replaceAll("[^\\p{Alnum}\\-]", "_");
        cleanRoute = cleanRoute.replaceAll("[^\\p{ASCII}]", "");
        cleanRoute = cleanRoute.replaceAll("\\s+", "_");

        cleanRoute = cleanRoute.replaceAll("^[^a-zA-Z0-9]+", "");

        return cleanRoute;
    }

    /**
     * Verifica si un archivo es accesible haciendo solicitudes HTTP.
     *
     * @param fileUrl la URL del archivo a verificar.
     * @return true si el archivo es accesible, false en caso contrario.
     */
    public boolean isFileAccessible(String fileUrl) {
        RestTemplate restTemplate = new RestTemplate();
        int retryCount = 0;
        boolean isAccessible = false;

        while (!isAccessible && retryCount < MAX_RETRIES) {
            try {
                // Intenta obtener solo los headers para verificar la existencia
                restTemplate.headForHeaders(fileUrl);
                // Si no lanza excepción, el archivo está accesible
                isAccessible = true;
            } catch (Exception e) {
                retryCount++;
                try {
                    // Espera un tiempo antes de reintentar
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        return isAccessible;
    }

    /**
     * Decodifica un nombre de archivo codificado, manteniendo la extension
     * original.
     *
     * @param encodedFileName el nombre del archivo codificado.
     * @return el nombre del archivo decodificado.
     */
    public String decodeFileName(String encodedFileName) {
        String decodedFilenameFinal = encodedFileName;
        if (encodedFileName != null) {
            int dotIndex = encodedFileName.lastIndexOf('.');
            int startCodeIndex = encodedFileName.lastIndexOf('_');
            if (dotIndex > 0 && startCodeIndex > 0) {
                String fileExtension = encodedFileName.substring(dotIndex);
                String decodedFilename = encodedFileName.substring(0, startCodeIndex);

                decodedFilenameFinal = decodedFilename + fileExtension;
            }
        }
        return decodedFilenameFinal;
    }
}