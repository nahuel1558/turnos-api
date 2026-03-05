import { Barber, Service, TimeSlot } from "@/lib/types"
import { useState } from "react"

interface Props {
    barber: Barber
    service: Service
    timeSlots: TimeSlot[]
    selectedDate: Date | null
    selectedTimeSlot?: TimeSlot | null
    onDateSelect: (date: Date) => void
    onTimeSlotSelect: (timeSlot: TimeSlot) => void
    onBack: () => void
    loading: boolean
}

export default function DateTimeSelector({ 
    barber, 
    service, 
    timeSlots, 
    selectedDate, 
    selectedTimeSlot, 
    onDateSelect, 
    onTimeSlotSelect, 
    onBack, 
    loading 
}: Props) {
    const [currentMonth, setCurrentMonth] = useState(new Date().getMonth())

    const getDays = () => {
        const days = []
        const today = new Date()
        for (let i = 0; i < 14; i++){
            const date = new Date(today)
            date.setDate(today.getDate() + i)
            days.push(date)
        }
        return days
    }

    const formatDate = (date: Date) => {
        return date.toLocaleDateString('es-AR', { weekday: 'short', day: 'numeric', month: 'short' })
    }

    const isDateSelected = (date: Date) => {
        return selectedDate?.toDateString() === date.toDateString()
    }

    return (
        <div>
            <div className="flex items-center mb-6">
                <button
                onClick={onBack}
                className="text-amber-600 hover:text-amber-800 mr-4">Volver</button>
                <h2 className="text-2xl font-bold">Elige fecha y hora</h2>
            </div>

            <div className="mb-6 p-4 bg-amber-50 rounded-lg">
                <p className="text-gray-600">Servicio seleccionado: <span className="font-semibold">{service.name}</span></p>
                <p className="text-gray-600">Barbero seleccionado: <span className="font-semibold">{barber.firstName} {barber.lastName}</span></p>
            </div>

            {/* Date Selector */}

            <div className="mb-6">
                <h3 className="text-lg font-semibold mb-3">Selecciona una fecha</h3>
                <div className="grid grid-cols-2 md:grid-cols-4 gap-2">
                    {getDays().map(date => (
                        <button
                        key={date.toISOString()}
                        onClick={() => onDateSelect(date)}
                        disabled={loading}
                        className={`p-3 rounded-lg border text-center ${isDateSelected(date) 
                        ? 'border-blue-500 bg-blue-50' 
                        : 'border-gray-300 hover:border-gray-500'} 
                        ${loading ? 'cursor-not-allowed opacity-50' : ''}`}>
                            {formatDate(date)}
                        </button>
                    ))}
                </div>
            </div>

            {/* Time Slot Selector */}

            {selectedDate && (
                <div>
                    <h3 className="text-lg font-semibold mb-3">Horarios Disponibles</h3>
                    {loading ? (
                        <p className="text-gray-600">Cargando horarios...</p>
                    ) : timeSlots.length === 0 ? (
                        <p className="text-gray-600">No hay horarios disponibles para esta fecha.</p>
                    ) : (
                        <div className="grid grid-cols-2 md:grid-cols-4 gap-2">
                            {timeSlots.map(slot => (
                                <button
                                key={slot.startTime}
                                onClick={() => onTimeSlotSelect(slot)}
                                className={`p-3 rounded-lg border text-center ${
                                    selectedTimeSlot?.startTime === slot.startTime
                                    ? 'border-blue-500 bg-blue-50'
                                    : 'border-gray-300 hover:border-gray-500'}`}>
                                    {new Date(slot.startTime).toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' })}
                                </button>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </div>
    )
}