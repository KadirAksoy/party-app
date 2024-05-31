package com.kadiraksoy.partyapp.controller;
import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import com.kadiraksoy.partyapp.service.PartyService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping("/save")
    public ResponseEntity<PartyResponseDto> createParty(@RequestBody PartyRequestDto partyRequestDto) {
        PartyResponseDto createdParty = partyService.createParty(partyRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParty);
    }

    @PutMapping("/update/{partyId}")
    public ResponseEntity<PartyResponseDto> updateParty(@PathVariable Long partyId,
                                                        @RequestBody PartyRequestDto partyRequestDto) {
        PartyResponseDto updatedParty = partyService.updateParty(partyId, partyRequestDto);
        return ResponseEntity.ok(updatedParty);
    }

    @DeleteMapping("/delete/{partyId}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long partyId) {
        partyService.deleteParty(partyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getParty/{partyId}")
    public ResponseEntity<PartyResponseDto> getParty(@PathVariable Long partyId) {
        PartyResponseDto party = partyService.getParty(partyId);
        return ResponseEntity.ok(party);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PartyResponseDto>> getAllParties() {
        List<PartyResponseDto> parties = (List<PartyResponseDto>) partyService.getAll();
        return ResponseEntity.ok(parties);
    }

    @PostMapping("/{partyId}/attend")
    public ResponseEntity<PartyResponseDto> attendParty(@PathVariable Long partyId,
                                                        @RequestParam Long userId) throws MessagingException {
        PartyResponseDto party = partyService.attendParty(partyId, userId);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }


    @PostMapping("/{partyId}/leave")
    public ResponseEntity<PartyResponseDto> leaveParty(@PathVariable Long partyId,
                                                       @RequestParam Long userId) throws MessagingException {
        PartyResponseDto party = partyService.leaveParty(partyId, userId);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }


    @GetMapping("/getActive")
    public ResponseEntity<List<PartyResponseDto>> getActiveParties() {
        List<PartyResponseDto> parties = (List<PartyResponseDto>) partyService.getActiveParties();
        return ResponseEntity.ok(parties);
    }

    @GetMapping("/getByAdminId/{adminId}")
    public ResponseEntity<List<PartyResponseDto>> getPartiesByAdminId(@PathVariable Long adminId) {
        List<PartyResponseDto> parties = (List<PartyResponseDto>) partyService.getPartiesByAdminId(adminId);
        return ResponseEntity.ok(parties);
    }
}
