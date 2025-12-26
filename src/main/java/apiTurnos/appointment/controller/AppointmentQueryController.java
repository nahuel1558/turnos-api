package apiTurnos.appointment.controller;

import apiTurnos.appointment.query.AvailableSlotResponse;
import apiTurnos.appointment.query.GetAvailableSlotsHandler;
import apiTurnos.appointment.query.GetAvailableSlotsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentQueryController {

    private final GetAvailableSlotsHandler handler;

    @GetMapping("/available")
    public List<AvailableSlotResponse> available(
            @RequestParam Long barberId,
            @RequestParam Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return handler.handle(new GetAvailableSlotsQuery(barberId, serviceId, date));
    }
}

