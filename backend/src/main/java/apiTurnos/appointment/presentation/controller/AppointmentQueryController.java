package apiTurnos.appointment.presentation.controller;

import apiTurnos.appointment.application.query.GetBarberAgendaHandler;
import apiTurnos.appointment.application.query.GetBarberAgendaQuery;
import apiTurnos.appointment.application.query.GetClientAppointmentsHandler;
import apiTurnos.appointment.application.query.GetClientAppointmentsQuery;
import apiTurnos.appointment.presentation.dto.response.AppointmentResponse;
import apiTurnos.appointment.presentation.dto.response.BarberAgendaItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentQueryController {

    private final GetBarberAgendaHandler getBarberAgendaHandler;
    private final GetClientAppointmentsHandler getClientAppointmentsHandler;

    /**
     * Agenda del barbero por día
     * Ej: GET /api/appointments/barbers/1/agenda?date=2026-02-09
     */
    @GetMapping("/barbers/{barberId}/agenda")
    public List<BarberAgendaItemResponse> getBarberAgenda(
            @PathVariable Long barberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return getBarberAgendaHandler.handle(new GetBarberAgendaQuery(barberId, date));
    }

    /**
     * Turnos de un cliente por rango
     * Ej: GET /api/appointments/clients/10?from=2026-02-01&to=2026-02-28
     */
    @GetMapping("/clients/{clientId}")
    public List<AppointmentResponse> getClientAppointments(
            @PathVariable Long clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return getClientAppointmentsHandler.handle(new GetClientAppointmentsQuery(clientId, from, to));
    }
}


