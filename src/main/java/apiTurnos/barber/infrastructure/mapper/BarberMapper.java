package apiTurnos.barber.infrastructure.mapper;

import apiTurnos.barber.application.command.RegisterBarberCommand;
import apiTurnos.barber.application.command.UpdateBarberCommand;
import apiTurnos.barber.domain.model.Barber;
import apiTurnos.barber.presentation.dto.request.RegisterBarberRequest;
import apiTurnos.barber.presentation.dto.request.UpdateBarberRequest;
import apiTurnos.barber.presentation.dto.response.BarberResponseDTO;
import apiTurnos.barber.presentation.dto.response.BarberSimpleResponseDTO;
import apiTurnos.barber.presentation.dto.response.BarberScheduleResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;

@Component
public class BarberMapper {

    public BarberResponseDTO toBarberResponse(Barber barber) {
        if (barber == null) return null;

        return BarberResponseDTO.builder()
                .id(barber.getId())
                .userId(barber.getUserAccount() != null ? barber.getUserAccount().getId() : null)
                .displayName(barber.getDisplayName())
                .professionalTitle(barber.getProfessionalTitle())
                .bio(barber.getBio())
                .specialties(barber.getSpecialties())
                .userFullName(barber.getUserFullName())
                .userEmail(barber.getUserEmail())
                .userPhone(barber.getUserAccount() != null ? barber.getUserAccount().getPhone() : null)
                .workStartTime(barber.getWorkStartTime())
                .workEndTime(barber.getWorkEndTime())
                .breakStartTime(barber.getBreakStartTime())
                .breakEndTime(barber.getBreakEndTime())
                .workDays(barber.getWorkDays())
                .totalAppointments(barber.getTotalAppointments())
                .totalReviews(barber.getTotalReviews())
                .professionalStatus(barber.getProfessionalStatus())
                .active(barber.isActive())
                .createdAt(barber.getCreatedAt())
                .updatedAt(barber.getUpdatedAt())
                .build();
    }

    public BarberSimpleResponseDTO toSimpleResponseDTO(Barber barber) {
        if (barber == null) return null;

        return BarberSimpleResponseDTO.builder()
                .id(barber.getId())
                .displayName(barber.getDisplayName())
                .professionalTitle(barber.getProfessionalTitle())
                .specialties(barber.getSpecialties())
                .workStartTime(barber.getWorkStartTime())
                .workEndTime(barber.getWorkEndTime())
                .workDays(barber.getWorkDays())
                .professionalStatus(barber.getProfessionalStatus())
                .active(barber.isActive())
                .build();
    }

    public BarberScheduleResponseDTO toScheduleResponseDTO(Barber barber) {
        if (barber == null) return null;

        return BarberScheduleResponseDTO.builder()
                .barberId(barber.getId())
                .displayName(barber.getDisplayName())
                .workStartTime(barber.getWorkStartTime())
                .workEndTime(barber.getWorkEndTime())
                .breakStartTime(barber.getBreakStartTime())
                .breakEndTime(barber.getBreakEndTime())
                .workDays(barber.getWorkDays())
                .workDaysFormatted(formatWorkDays(barber.getWorkDays()))
                .build();
    }

    private String formatWorkDays(Set<Integer> workDays) {
        if (workDays == null || workDays.isEmpty()) return "No definido";

        String[] dayNames = {"Domingo", "Lunes", "Martes", "Miércoles",
                "Jueves", "Viernes", "Sábado"};

        return workDays.stream()
                .sorted()
                .map(day -> dayNames[day])
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    // Mapeo desde request a command
    public RegisterBarberCommand toRegisterCommand(RegisterBarberRequest request) {
        return RegisterBarberCommand.builder()
                .idUser(request.getUserId())
                .displayName(request.getDisplayName())
                .professionalTitle(request.getProfessionalTitle())
                .bio(request.getBio())
                .specialties(request.getSpecialties())
                .workStartTime(request.getWorkStartTime())
                .workEndTime(request.getWorkEndTime())
                .breakStartTime(request.getBreakStartTime())
                .breakEndTime(request.getBreakEndTime())
                .workDays(request.getWorkDays())
                .build();
    }

    public UpdateBarberCommand toUpdateCommand(Long barberId, UpdateBarberRequest request) {
        return UpdateBarberCommand.builder()
                .idBarber(barberId)
                .updateBarberRequest(request)
                .build();
    }
}