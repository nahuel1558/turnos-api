import { Service } from "@/lib/types"

interface Props {
    services: Service[]
    onSelect: (service: Service) => void
    selectedService?: number
}

export function ServiceSelector({ services, onSelect, selectedService }: Props) {
    return (
        <div>
            <h2 className="text-2xl font-bold mb-6">Elige un servicio</h2>
            <div className="grid md:grid-cols-2 gap-4">
                {services.map(service => (
                    <button
                        key={service.id}
                        onClick={() => onSelect(service)}
                        className={`p-6 rounded-lg border-2 transition-all text-left ${
                            selectedService === service.id ? 'border-blue-500 bg-blue-50' : 'border-gray-300 hover:border-gray-500'}
                        `}
                        >
                        <h3 className="text-xl font-semibold mb-2">{service.name}</h3>
                        <p className="text-gray-600 mb-4">{service.description}</p>
                        <div className="flex justify-between text-amber-800">
                            <span className="font-bold">${service.price}</span>
                            <span className="text-sm">{service.duration} min</span>
                        </div>
                    </button>
                ))}
            </div>
        </div>
    )
}