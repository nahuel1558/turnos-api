import { Barber, Service } from "@/lib/types"

interface Props {
    barbers: Barber[]
    service: Service
    onSelect: (barber: Barber) => void
    selectedBarber?: number
    onBack: () => void
}

export default function BarberSelector({ barbers, service, onSelect, selectedBarber, onBack }: Props) {
    return (
        <div>
            <div className="flex items-center mb-6">
                <button
                onClick={onBack}
                className="text-amber-600 hover:text-amber-800 mr-4">Volver</button>
                <h2 className="text-2xl font-bold">Elige tu barbero</h2>
            </div>

            <p className="text-gray-600 mb-4">Servicio seleccionado: <span className="font-semibold">{service.name}</span></p>

            {barbers.length === 0 ? (
                <p className="text-gray-600">No hay barberos disponibles para este servicio.</p>
            ):(
                <div className="grid md:grid-cols-2 gap-4">
                    {barbers.map(barber => (
                        <button
                            key={barber.id}
                            onClick={() => onSelect(barber)}
                            className={`p-4 rounded-lg border-2 transition-all ${
                                selectedBarber === barber.id ? 'border-blue-500 bg-blue-50' : 'border-gray-300 hover:border-gray-500'}`}
                            >
                            <div className="flex items-center space-x-4">
                            {barber.photo ? (
                                <img src={barber.photo} alt={barber.firstName} className="w-16 h-16 rounded-full object-cover" />
                            ) : (
                                <div className="w-16 h-16 rounded-full bg-gray-300 flex items-center justify-center text-gray-600">
                                    💈
                                </div>
                            )}
                                <div className="text-left">
                                    <h3 className="text-xl font-semibold">{barber.firstName} {barber.lastName}</h3>
                                    <p className="text-gray-600 text-sm">{barber.specialty}</p>
                                </div>
                            </div>    
                        </button>
                    ))}
                </div>
            )}
        </div>
    )
}