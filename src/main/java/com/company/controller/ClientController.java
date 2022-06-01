package com.company.controller;


import com.company.dto.ClientDTO;
import com.company.dto.request.ClientRequestDTO;
import com.company.enums.EntityStatus;
import com.company.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/client")
@Api(tags = "Client")
public class ClientController {

    private final ClientService clientService;


    @PostMapping("")
    @ApiOperation(value = "Create client", notes = "Method used for create client")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientRequestDTO dto) {
        log.info("create client: {}",dto);
        return ResponseEntity.ok(clientService.create(dto));
    }


    @PutMapping("")
    @ApiOperation(value = "Update client", notes = "Method used for update client")
    public ResponseEntity<?> update(@RequestBody ClientRequestDTO dto) {
        log.info("update client: {}",dto);

        return ResponseEntity.ok(clientService.update(dto.getPhone(), dto));
    }

    @PutMapping("/{phoneNumber}")
    @ApiOperation(value = "Change status", notes = "Method used for change status")
    public ResponseEntity<?> changeStatus(@PathVariable("phoneNumber") String phoneNumber,
                                          @RequestParam("status") EntityStatus status) {
        log.info("change status : number {} , status {}" ,phoneNumber,status);

        return ResponseEntity.ok(clientService.changeStatus(phoneNumber, status));
    }


    @PutMapping("/{phoneNumber}/change")
    @ApiOperation(value = "Change phone", notes = "Method used for change phone")
    public ResponseEntity<?> changePhone(@PathVariable("phoneNumber") String phoneNumber,
                                         @RequestParam("newPhone") String newPhone) {
        log.info("change phone: old number {}, new number {}", phoneNumber, newPhone);
        return ResponseEntity.ok(clientService.changePhone(phoneNumber, newPhone));
    }

    @GetMapping("")
    @ApiOperation(value = "get clients", notes = "Method used for get clients ")
    public ResponseEntity<?> get(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "30") int size) {
        log.info("get clients");
        return ResponseEntity.ok(clientService.getList(page, size));
    }


    @GetMapping("/{phoneNumber}")
    @ApiOperation(value = "Get by phone", notes = "Method used for get buy phone")
    public ResponseEntity<?> getByPhone(@PathVariable("phoneNumber") String phone) {
        log.info("get by phone: {}", phone);
        return ResponseEntity.ok(clientService.getByPhoneNumber(phone));
    }


    @GetMapping("/clients")
    @ApiOperation(value = "get  clients", notes = "Method used for get clients by murojat qilgan profile")
    public ResponseEntity<?> getClients() {
        log.info("get clients");
        return ResponseEntity.ok(clientService.getProfileClients());
    }


}
