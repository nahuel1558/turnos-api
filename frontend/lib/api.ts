import axios from 'axios'
import { Appointment, Barber, Client, Service, TimeSlot } from './types'
import { get } from 'http'

const api = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
})

api.interceptors.request.use(
    (response) => response,
    (error) => {
        console.error('Api error:', error.response?.data || error.message)
        return Promise.reject(error)
    }
)

export const servicesApi = {
    getAll: () => api.get<Service[]>('/services/queries'),
    getById: (id: number) => api.get<Service>(`/services/queries/${id}`),
}

export const barbersApi = {
    getAll: () => api.get<Barber[]>('/barbers'),
    getById: (id: number) => api.get<Barber>(`/barbers/${id}`),
    getByService: (serviceId: number) => api.get<Barber[]>(`/barbers?serviceId=${serviceId}`),
    getAvailableTimeSlots: (barberId: number, date: string, serviceId: number) => api.get<TimeSlot[]>(`/barbers/${barberId}/available-slots`, {
        params: { date, serviceId }
    }),
}

export const appointmentsApi = {
    create: (data: Partial<Appointment>) => api.post('/appointments', data),
    getByClient: (clientId: number) => api.get<Appointment[]>(`/appointments/client/${clientId}`),
    cancel: (id: number) => api.patch(`/appointments/${id}/cancel`),
}

export const clientsApi = {
    create: (data: Partial<Client>) => api.post('/clients', data),
    getbyEmail: (email: string) => api.get<Client>(`/clients?email=${email}`),
}

export default api