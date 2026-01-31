package apiTurnos.appointment.presentation.controller;

import apiTurnos.appointment.application.query.*;
import apiTurnos.appointment.presentation.dto.response.AppointmentSlotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller REST para consultas de turnos.
 * No realiza lógica de negocio, solo delega al handler.
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentQueryController {
    private final GetAvailableSlotsHandler getAvailableSlotsHandler;
    private final GetBarberAgendaHandler getBarberAgendaHandler;

    @GetMapping("/available-slots")
    public ResponseEntity<List<AvailableSlotResponse>> availableSlots(
            @RequestParam Long barberId,
            @RequestParam Long serviceId,
            @RequestParam LocalDate date
    ) {
        var query = new GetAvailableSlotsQuery(barberId, serviceId, date);
        return ResponseEntity.ok(getAvailableSlotsHandler.handle(query));
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<AppointmentSlotResponse>> agenda(
            @RequestParam Long barberId,
            @RequestParam LocalDate date
    ) {
        var query = new GetBarberAgendaQuery(barberId, date);
        return ResponseEntity.ok(getBarberAgendaHandler.handle(query));
    }
}

