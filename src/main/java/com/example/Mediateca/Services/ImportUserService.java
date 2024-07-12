package com.example.Mediateca.Services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Mediateca.Domain.UserDO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Servicio para importar datos de usuario desde un archivo Excel.
 * Este servicio procesa archivos Excel subidos y crea usuarios en la base de
 * datos basandose en los datos extraidos.
 */
@Service
public class ImportUserService {

    @Autowired
    private UserService userService;

    /**
     * Crea un objeto UserDO con el nombre y el correo proporcionados,
     * asignando el rol de alumno por defecto.
     *
     * @param name  el nombre del usuario a crear.
     * @param email el correo del usuario a crear.
     * @return el UserDO creado y guardado en la base de datos.
     */
    private UserDO createUserDO(String name, String email) {
        UserDO user = new UserDO();
        user.setUsername(name);
        user.setEmail(email);
        user.setRol("ROLE_ALUMNO");
        userService.saveUser(user);

        return user;
    }

    /**
     * Procesa un archivo Excel, extrayendo los datos de los usuarios y creandolos
     * en la base de datos.
     * El Excel debe tener dos columnas, "Nombre" y "Correo"
     *
     * @param file el archivo Excel que contiene los datos de
     *             usuario.
     */
    public void processExcel(MultipartFile file) {
        try (InputStream excelFile = file.getInputStream(); Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            // La primera fila contiene los encabezados
            Row headerRow = iterator.hasNext() ? iterator.next() : null;

            int nombreColumnIndex = -1;
            int correoColumnIndex = -1;

            // Identifica los indices de las columnas basado en los encabezados
            if (headerRow != null) {
                Iterator<Cell> headerIterator = headerRow.iterator();
                while (headerIterator.hasNext()) {
                    Cell headerCell = headerIterator.next();
                    if ("NOMBRE".equalsIgnoreCase(headerCell.getStringCellValue())) {
                        nombreColumnIndex = headerCell.getColumnIndex();
                    } else if ("CORREO".equalsIgnoreCase(headerCell.getStringCellValue())) {
                        correoColumnIndex = headerCell.getColumnIndex();
                    }
                }
            }

            // Verifica que se encontraron los indices correctos
            if (nombreColumnIndex == -1 || correoColumnIndex == -1) {
                throw new RuntimeException("No se encontraron las columnas esperadas: NOMBRE o CORREO");
            }

            // Lista con los usuarios que no se han podido insertar por estar ya en la base
            // de datos
            Map<String, String> noInsertUsers = new HashMap<>();
            // Recorremos cada fila para extraer los datos
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Cell nombreCell = currentRow.getCell(nombreColumnIndex);
                Cell correoCell = currentRow.getCell(correoColumnIndex);

                String nombre = nombreCell != null ? nombreCell.getStringCellValue() : null;
                String correo = correoCell != null ? correoCell.getStringCellValue() : null;
                try {
                    // Creamos el usuario extraido de la fila
                    createUserDO(nombre, correo);
                } catch (DataIntegrityViolationException e) {
                    if (noInsertUsers.get(correo) != null) {
                        noInsertUsers.put(correo, nombre);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
