'use client'

import { useAppointment } from "@/hooks/useAppointment"
import { useEffect, useState } from "react"
import { servicesApi } from "@/lib/api"
import { Service, TimeSlot } from "@/lib/types"
import { ca, de } from "zod/locales"
import { ServiceSelector } from "@/components/appointment/ServiceSelector"
import BarberSelector from "@/components/appointment/BarberSelector"
import DateTimeSelector from "@/components/appointment/DateTimeSelector"
import ClientForm from "@/components/appointment/ClientForm"

export default function AppointmentPage() {
    const{
        step,
        selectedService,
        selectedBarber,
        selectedDate,
        selectedTimeSlot,
        availableBarbers,
        availableTimeSlots,
        loading,
        error,
        selectService,
        selectBarber,
        selectDateTime,
        goBack,
    } = useAppointment()

    const [services, setServices] = useState<Service[]>([])

    useEffect(() => {
        const fetchServices = async () => {
            try {
                const response = await servicesApi.getAll()
                setServices(response.data)
            } catch (error) {
                console.error("Error fetching services:", error)
            }
        }

        fetchServices()
    }, [])

    // Renderizado condicional según el paso actual
    const renderStep = () => {
        switch (step) {
            case 1:
                return (<ServiceSelector services={services} onSelect={selectService} selectedService={selectedService?.id}/>)

            case 2:
                return(
                    <BarberSelector 
                        barbers={availableBarbers}
                        service={selectedService!} 
                        onSelect={selectBarber}
                        selectedBarber={selectedBarber?.id}
                        onBack={goBack}
                    />
                )
            
            case 3:
                return(
                    <DateTimeSelector
                        barber={selectedBarber!}
                        service={selectedService!}
                        timeSlots={availableTimeSlots}
                        selectedDate={selectedDate}
                        selectedTimeSlot={selectedTimeSlot}
                        onDateSelect={(date: Date) => selectedTimeSlot && selectDateTime(date, selectedTimeSlot)}
                        onTimeSlotSelect={(timeSlot: TimeSlot) => selectedDate && selectDateTime(selectedDate, timeSlot)}
                        onBack={goBack}
                        loading={loading}
                    />
                )

            case 4:
                return (
                    <ClientForm
                    onBack={goBack}
                    loading={loading}
                    onSubmit={() => {}}
                    service={selectedService!}
                    barber={selectedBarber!}
                    date={selectedDate!}
                    timeSlot={selectedTimeSlot!}
                    />
                )

            default:
                return null
        }
    }

    return (
        <div className="container mx-auto px-4 py-8">
            {/* Barra de Progreso */}
            <div className="mb-8">
                <div className="flex justify-between items-center">
                    {[1, 2, 3, 4].map((stepNumber) => (
                        <div
                            key={stepNumber}
                            className={`flex-1 text-center ${step === stepNumber ? 'text-blue-500' : 'text-gray-500'}`}
                        >
                            <div className="relative">
                                <div className={`w-8 h-8 mx-auto rounded-full felx items-center justify-center border-2 ${step >= stepNumber ? 'bg-blue-500' : 'bg-gray-300'}`}>
                                    {stepNumber}
                                </div>
                                <div className="text-xs mt-1">
                                    {stepNumber === 1 && 'Servicio'}
                                    {stepNumber === 2 && 'Barbero'}
                                    {stepNumber === 3 && 'Fecha/Hora'}
                                    {stepNumber === 4 && 'Confirmar'}
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
                {/* Línea de conexion */}
                <div className="relative flex justify-between mt-2">
                    <div className="absolute top-0 left-0 h-1 bg-gray-200 w-full"></div>
                    <div className="absolute top-0 left-0 h-1 bg-amber-600 transition-all duration-300"
                    style={{ width: `${((step - 1) / 3) * 100}%` }}>
                    </div>
                </div>
            </div>

            {/* Mensaje de error */}
            {error && (
                <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                    {error}
                </div>
            )}

            {/* Contenido del paso actual */}
            <div className="bg-white rounded-lg shadow-lg p-6">
                {renderStep()}
            </div>
            </div>
    )
}