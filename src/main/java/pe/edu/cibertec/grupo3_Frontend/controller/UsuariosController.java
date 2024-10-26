package pe.edu.cibertec.grupo3_Frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.grupo3_Frontend.client.AutenticacionClient;
import pe.edu.cibertec.grupo3_Frontend.dto.AlumnoResponse;
import pe.edu.cibertec.grupo3_Frontend.dto.ListadoResponseDTO;
import pe.edu.cibertec.grupo3_Frontend.dto.LoginRequestDTO;
import pe.edu.cibertec.grupo3_Frontend.dto.LoginResponseDTO;
import pe.edu.cibertec.grupo3_Frontend.viewmodel.ListadoModel;
import pe.edu.cibertec.grupo3_Frontend.viewmodel.LoginModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UsuariosController {

    @Autowired
    AutenticacionClient autenticacionClient;

    @Autowired
    RestTemplate restTemplateListado;

    @GetMapping("/login")
    public String login(Model m) {
        LoginModel loginModel = new LoginModel("00", "");
        m.addAttribute("loginModel", loginModel);
        return  "login";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam String codigoAlumno, @RequestParam String password, Model m) {
        boolean isValid =
                codigoAlumno == null || codigoAlumno.isBlank()
                || password == null || password.isBlank();
        if (isValid) {
            LoginModel loginModel = new LoginModel("03", "Error: Campos requeridos.");
            m.addAttribute("loginModel", loginModel);
            return "login";
        }

        try {
            LoginRequestDTO request = new LoginRequestDTO(codigoAlumno, password);
            ResponseEntity<LoginResponseDTO> response = autenticacionClient.login(request);
            LoginResponseDTO loginResponseDTO = response.getBody();

            //LOGIN FALLIDO
            if (!(loginResponseDTO.codigo().equals("00"))) {
                LoginModel loginModel = new LoginModel(loginResponseDTO.codigo(), loginResponseDTO.mensaje());
                m.addAttribute("loginModel", loginModel);
                return "login";
            }

            //LOGIN EXITOSO
            //LoginModel loginModel = new LoginModel("03", "Ingreso exitoso");
            //m.addAttribute("loginModel", loginModel);
            //redireccionar a listado de integrantes

            //String url = "http://localhost:8081/listado/alumnos";
            String url = "https://demo-azure-backend.azurewebsites.net/listado/alumnos";
            ListadoResponseDTO listadoResponseDTO = restTemplateListado.getForObject(url, ListadoResponseDTO.class);
            ListadoModel listadoModel = new ListadoModel(listadoResponseDTO.lista(), "00", "");
            m.addAttribute("listadoModel", listadoModel);
            return "listado";

        } catch (RestClientException restClientException) {
            ListadoModel listadoModel = new ListadoModel(null, "99", "Error: Problema interno");
            m.addAttribute("listadoModel", listadoModel);
            return "listado";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            LoginModel loginModel = new LoginModel("99", "Error: Problema interno.");
            m.addAttribute("loginModel", loginModel);
            return "login";
        }
    }

    @GetMapping("/listado")
    public String listado(Model m) {
        try {
            //String url = "http://localhost:8081/listado/alumnos";
            String url = "https://demo-azure-backend.azurewebsites.net/listado/alumnos";
            ListadoResponseDTO listadoResponseDTO = restTemplateListado.getForObject(url, ListadoResponseDTO.class);
            ListadoModel listadoModel = new ListadoModel(listadoResponseDTO.lista(), "00", "");
            m.addAttribute("listadoModel", listadoModel);
            return "listado";
        } catch (Exception ex) {
            ListadoModel listadoModel = new ListadoModel(null, "99", "Error: Problema interno");
            m.addAttribute("listadoModel", listadoModel);
            return "listado";
        }
    }
}
