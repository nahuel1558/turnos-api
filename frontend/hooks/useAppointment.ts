import { useState } from "react";
import { Service, Barber, TimeSlot, Client, Appointment } from "@/lib/types";
import { servicesApi, barbersApi, appointmentsApi, clientsApi } from "@/lib/api";
import { set } from "zod";
import { da } from "zod/locales";

interface AppointmentState {
    step: 1 | 2 | 3 | 4
    selectedService: Service | null
    selectedBarber: Barber | null
    selectedDate: Date | null
    selectedTimeSlot: TimeSlot | null
    clientData: Partial<Client>
    availableBarbers: Barber[]
    availableTimeSlots: TimeSlot[]
    loading: boolean
    error: string | null
}

export function useAppointment() {
    const [state, setState] = useState<AppointmentState>({
        step: 1,
        selectedService: null,
        selectedBarber: null,
        selectedDate: null,
        selectedTimeSlot: null,
        clientData: {},
        availableBarbers: [],
        availableTimeSlots: [],
        loading: false,
        error: null,
    })

    //Paso 1: Seleccionar servicio
    const selectService = async (service: Service) => {
        setState(prev => ({ ...prev, selectedService: service, loading: true, error: null }))
        try {
            const response = await barbersApi.getByService(service.id)
            setState(prev => ({ ...prev, availableBarbers: response.data, step: 2 }))
        } catch (error) {
            setState(prev => ({ ...prev, error: 'Error al cargar barberos disponibles', loading: false }))
        }
    }

    //Paso 2: Seleccionar barbero
    const selectBarber = (barber: Barber) => {
        setState(prev => ({ ...prev, selectedBarber: barber, step: 3 }))
    }

    //Paso 3: Seleccionar fecha y hora
    const selectDateTime = async (date: Date, timeSlot: TimeSlot) => {
        if (!state.selectedBarber || !state.selectedService) return

        setState(prev => ({ ...prev, selectedDate: date, selectedTimeSlot: timeSlot, loading: true, error: null }))
        try {
            const response = await barbersApi.getAvailableTimeSlots(state.selectedBarber.id, date.toISOString().split('T')[0], state.selectedService.id)
            setState(prev => ({ ...prev, availableTimeSlots: response.data, step: 4 }))
        } catch (error) {
            setState(prev => ({ ...prev, error: 'Error al cargar horarios disponibles', loading: false }))
        }
    }

    //Paso 4: Completar datos del cliente y confirmar cita
    const confirmAppointment = async (clientData: Partial<Client>) => {
        if (!state.selectedBarber || !state.selectedService || !state.selectedDate || !state.selectedTimeSlot) {
            return
        }

        setState(prev => ({ ...prev, clientData, loading: true, error: null }))
        try {
            // Verificar si el cliente ya existe
            let client = null
            try {
                const response = await clientsApi.getbyEmail(clientData.email!)
                client = response.data
            } catch {
                // Si no existe, crear nuevo cliente
                const response = await clientsApi.create(clientData)
                client = response.data
            }

            // Crear la cita
            const appointmentData: Partial<Appointment> = {
                client: client,
                barber: state.selectedBarber,
                service: state.selectedService,
                status: 'scheduled' as const,
            }
            
            await appointmentsApi.create(appointmentData)
            setState(prev => ({ ...prev, loading: false }))

            return true
        } catch (error) {
            setState(prev => ({ ...prev, error: 'Error al crear la reserva. Por favor, intenta de nuevo.', loading: false }))
        }
    }

    // Función para reiniciar volver al paso anterior
    const goBack = () => {
        setState(prev => ({ ...prev, step: (prev.step - 1) as AppointmentState['step'] }))
    }
    return {
        ...state,
        selectService,
        selectBarber,
        selectDateTime,
        confirmAppointment,
        goBack,
    }
}
