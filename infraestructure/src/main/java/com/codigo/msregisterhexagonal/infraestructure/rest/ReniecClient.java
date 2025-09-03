package com.codigo.msregisterhexagonal.infraestructure.rest;

import com.codigo.msregisterhexagonal.infraestructure.response.ResponseReniec;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reniecClient", url = "https://api.apis.net.pe/v2/reniec/")
public interface ReniecClient {

    @GetMapping("/dni")
    ResponseReniec getInfoReniec(@RequestParam("numero") String numero,
                                 @RequestHeader("Authorization") String authorization);

}
