package com.example.Mediateca.Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.example.Mediateca.DTOs.AdDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase utilitaria para generar archivos Excel.
 */
@Component
public class ExcelGeneratorUtils {

    /**
     * Genera un archivo Excel con los detalles de un anuncio.
     *
     * @param ad el anuncio del cual se va a generar el Excel
     */
    public static void generateExcel(AdDTO ad) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Anuncios");

            // Lista para los encabezados de columna
            List<String> headers = new ArrayList<>(Arrays.asList(
                    "Año", "Anunciante", "Producto", "Nombre del Medio", "Tipo de Publicidad",
                    "Medio", "Presupuesto", "Rol de Género Hombre", "Rol de Género (Mujer)", "Relación de Género",
                    "Género del Menor 1", "Rol del Menor 1", "Protagonismo 1"));

            // Agregar encabezados para menores adicionales si existen
            if (ad.getChildRole2() != null) {
                headers.addAll(Arrays.asList("Género del Menor 2", "Rol del Menor 2", "Protagonismo 2"));
            }
            if (ad.getChildRole3() != null) {
                headers.addAll(Arrays.asList("Género del Menor 3", "Rol del Menor 3", "Protagonismo 3"));
            }

            // Agregar encabezados que siguen a los menores
            headers.addAll(Arrays.asList("Temática", "Palabras Clave", "Target", "Objetivo", "Sinopsis"));

            // Crear fila de encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            // Datos del anuncio
            Row row = sheet.createRow(1);
            int colIndex = 0;
            row.createCell(colIndex++).setCellValue(ad.getYear());
            row.createCell(colIndex++).setCellValue(ad.getAdvertiser());
            row.createCell(colIndex++).setCellValue(ad.getProduct());
            row.createCell(colIndex++).setCellValue(ad.getMediaName());
            row.createCell(colIndex++).setCellValue(ad.getAdType());
            row.createCell(colIndex++).setCellValue(ad.getMedia());
            row.createCell(colIndex++).setCellValue(ad.getBudget());
            row.createCell(colIndex++).setCellValue(ad.getGenderRoleMale());
            row.createCell(colIndex++).setCellValue(ad.getGenderRoleFemale());
            row.createCell(colIndex++).setCellValue(ad.getGenderRelation());
            row.createCell(colIndex++).setCellValue(ad.getChildGender());
            row.createCell(colIndex++).setCellValue(ad.getChildRole());
            row.createCell(colIndex++).setCellValue(ad.getProtagonism());

            // Datos para menores adicionales
            if (ad.getChildRole2() != null) {
                row.createCell(colIndex++).setCellValue(ad.getChildGender2());
                row.createCell(colIndex++).setCellValue(ad.getChildRole2());
                row.createCell(colIndex++).setCellValue(ad.getProtagonism2());
            }
            if (ad.getChildRole3() != null) {
                row.createCell(colIndex++).setCellValue(ad.getChildGender3());
                row.createCell(colIndex++).setCellValue(ad.getChildRole3());
                row.createCell(colIndex++).setCellValue(ad.getProtagonism3());
            }

            // Datos finales despues de los menores
            row.createCell(colIndex++).setCellValue(ad.getTopicName());
            row.createCell(colIndex++).setCellValue(ad.getKeyWords());
            row.createCell(colIndex++).setCellValue(ad.getTarget());
            row.createCell(colIndex++).setCellValue(ad.getObjective());
            row.createCell(colIndex++).setCellValue(ad.getSynopsis());

            // Guardar el archivo
            try (FileOutputStream outputStream = new FileOutputStream("anuncios.xlsx")) {
                workbook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera una plantilla Excel para agregar nuevos usuarios.
     * Esta plantilla incluye los encabezados de 'NOMBRE' y 'CORREO' para
     * rellenarlos con los datos de los nuevos usuarios.
     */
    public static void generateTemplateExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Plantilla");

            // Lista para los encabezados de columna
            List<String> headers = Arrays.asList("NOMBRE", "CORREO");

            // Crear fila de encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            // Ajustar el tamanio de las columnas a los encabezados
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar el archivo
            try (FileOutputStream outputStream = new FileOutputStream("plantilla_usuarios.xlsx")) {
                workbook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
