package pe.edu.cibertec.grupo3_Frontend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.cibertec.grupo3_Frontend.config.FeignClientConfig;
import pe.edu.cibertec.grupo3_Frontend.dto.LoginRequestDTO;
import pe.edu.cibertec.grupo3_Frontend.dto.LoginResponseDTO;

//@FeignClient(name = "autenticacion", url = "http://localhost:8081/autenticar", configuration = FeignClientConfig.class)
@FeignClient(name = "autenticacion", url = "https://demo-azure-backend.azurewebsites.net/autenticar", configuration = FeignClientConfig.class)
public interface AutenticacionClient {

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO);
}
