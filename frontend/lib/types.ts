export interface Service {
    id: number
    name: string
    description: string
    price: number
    duration: number // Duración en minutos
}

export interface Barber {
    id: number
    firstName: string
    lastName: string
    specialty: string
    photo?: string
    services?: Service[] // Servicios que ofrece el barbero
    schedule?: Schedule[] // Horarios disponibles del barbero
}

export interface Schedule {
    dayOfWeek: number // 0= Domingo, 1= Lunes, 2= Martes, etc.
    startTime: string // Formato HH:mm
    endTime: string // Formato HH:mm
}

export interface Client {
    id: number
    firstName: string
    lastName: string
    email: string
    phone: string
    userId?: number // Si esta registrado como cliente.
}

export interface Appointment {
    id: number
    client: Client
    barber: Barber
    service: Service
    date: string // Formato YYYY-MM-DD
    time: string // Formato HH:mm
    status: 'scheduled' | 'completed' | 'canceled'
    notes?: string
}

export interface TimeSlot {
    startTime: string // Formato HH:mm
    endTime: string // Formato HH:mm
    available: boolean
}