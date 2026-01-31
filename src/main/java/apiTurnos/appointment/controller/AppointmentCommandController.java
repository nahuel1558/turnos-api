package apiTurnos.appointment.controller;

import apiTurnos.appointment.command.*;
import apiTurnos.appointment.dto.request.CreateAppointmentRequest;
import apiTurnos.appointment.dto.request.UpdateAppointmentRequest;
import apiTurnos.appointment.dto.response.AppointmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller CQRS - Commands.
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentCommandController {
    private final AppointmentCommandHandler appointmentCommandHandler;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody CreateAppointmentRequest request) {
        var cmd = new CreateAppointmentCommand(
                request.userId(),
                request.barberId(),
                request.serviceId(),
                request.date(),
                request.startTime()
        );
        return ResponseEntity.ok(appointmentCommandHandler.handle(cmd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id,
                                                      @Valid @RequestBody UpdateAppointmentRequest request) {
        var cmd = new UpdateAppointmentCommand(id, request.serviceId(), request.startTime());
        return ResponseEntity.ok(appointmentCommandHandler.handle(cmd));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentCommandHandler.handle(new CancelAppointmentCommand(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentCommandHandler.handle(new DeleteAppointmentCommand(id));
        return ResponseEntity.noContent().build();
    }
}
