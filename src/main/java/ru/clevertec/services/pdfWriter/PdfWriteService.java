package ru.clevertec.services.pdfWriter;

import java.util.List;

public interface PdfWriteService {
    void writeToPdfFile(List<Object> check);
}
