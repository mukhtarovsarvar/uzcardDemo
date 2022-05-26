package com.company.controller;

import com.company.dto.ClientDTO;
import com.company.dto.reponse.ClientResponseDTO;
import com.company.enums.EntityStatus;
import com.company.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")

public class ClientController {

    private final ClientService clientService;


    @PostMapping("")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientResponseDTO dto) {
        return ResponseEntity.ok(clientService.create(dto));
    }


    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody ClientResponseDTO dto) {
        return ResponseEntity.ok(clientService.update(dto.getPhone(), dto));
    }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<?> changeStatus(@PathVariable("phoneNumber") String phoneNumber,
                                          @RequestParam("status") EntityStatus status) {

        return ResponseEntity.ok(clientService.changeStatus(phoneNumber,status));
    }


    @PutMapping("/{phoneNumber}/change")
    public ResponseEntity<?> changePhone(@PathVariable("phoneNumber") String phoneNumber,
                                          @RequestParam("newPhone") String newPhone) {

        return ResponseEntity.ok(clientService.changePhone(phoneNumber,newPhone));
    }

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam(value = "page",defaultValue = "0") int page,
                                 @RequestParam(value = "size",defaultValue = "30") int size){
        return ResponseEntity.ok(clientService.getList(page, size));
    }


    @GetMapping("/{phoneNumber}")
    public ResponseEntity<?> getByPhone(@PathVariable("phoneNumber") String phone){
        return ResponseEntity.ok(clientService.getById(phone));
    }

    @GetMapping("/clients")
    public ResponseEntity<?> getClients(){
        return ResponseEntity.ok(clientService.getProfileClients());
    }


}
