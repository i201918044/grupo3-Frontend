package pe.edu.cibertec.grupo3_Frontend.viewmodel;

import pe.edu.cibertec.grupo3_Frontend.dto.AlumnoResponse;

import java.util.List;

public record ListadoModel(List<AlumnoResponse> lista, String codigo, String mensaje) {
}
