import {useForm} from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Service, Barber, TimeSlot } from "@/lib/types";

const clientSchema = z.object({
    firstName: z.string().min(2, "El nombre debe tener al menos 2 caracteres"),
    lastName: z.string().min(2, "El apellido debe tener al menos 2 caracteres"),
    email: z.string().email("Debe ser un email válido"),
    phone: z.string().min(10, "El teléfono debe tener al menos 10 dígitos"),
})

type ClientFormData = z.infer<typeof clientSchema>

interface Props {
    onSubmit: (data: ClientFormData) => void
    service: Service
    barber: Barber
    timeSlot: TimeSlot
    loading: boolean
    date: Date
    onBack: () => void
}

export default function ClientForm({ onSubmit, service, barber, timeSlot, loading, date, onBack }: Props) {
    const { register, handleSubmit, formState: { errors } } = useForm<ClientFormData>({
        resolver: zodResolver(clientSchema),})

    const formatDate = date.toLocaleDateString('es-AR', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    })

    return (
        <div>
            <div className="flex items-center mb-6">
                <button
                    onClick={onBack}
                    className="text-amber-600 hover:text-amber-800 mr-4" disabled={loading}>
                    Volver
                </button>
                <h2 className="text-2xl font-bold">Ingresa tus datos</h2>
            </div>

        {/* Resumen de la reserva */}

        <div className="mb-6 p-4 bg-amber-50 rounded-lg">
            <h3 className="text-lg font-semibold mb-2">Resumen de tu reserva</h3>
            <p>📋 Servicio: {service.name}</p>
            <p>💈 Barbero: {barber.firstName} {barber.lastName}</p>
            <p>📅 Fecha: {formatDate}</p>
            <p>⏰ Hora: {timeSlot.startTime}</p>
            <p className="font-semibold mt-2">Total: ${service.price.toFixed(2)}</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div className="grid md:grid-cols-2 gap-4">
            {/* Nombre */}
            <div>
                <label className="block text-sm font-medium text-gray-700">Nombre</label>
                <input
                    type="text"
                    {...register("firstName")}
                    className={`mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-amber-500 focus:border-amber-500 ${errors.firstName ? 'border-red-500' : ''}`}
                    disabled={loading}
                />
                {errors.firstName && <p className="text-red-500 text-sm mt-1">{errors.firstName.message}</p>}
            </div>

            {/* Apellido */}
            <div>
                <label className="block text-sm font-medium text-gray-700">Apellido</label>
                <input
                    type="text"
                    {...register("lastName")}
                    className="w-full p-2 border boder-gray-300 rounded-lg focus:ring-amber-500 focus border-amber-500"
                    disabled={loading}
                />
                {errors.lastName && <p className="text-red-500 text-sm mt-1">{errors.lastName.message}</p>}
            </div>
        </div>

        {/* Email */}
        <div>
            <label className="block text-sm font-medium text-gray-700">Email</label>
            <input
                {...register("email")}
                type="email"
                className="w-full p-2 border-gray-300 rounded-lg focus:ring-amber-500 focus:border-amber-500"
                disabled={loading}
            />
            {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>}
        </div>

        {/* Teléfono */}
        <div>
            <label className="block text-sm font-medium text-gray-700">Teléfono</label>
            <input
                {...register("phone")}
                className="w-full p-2 border border-gray-300 rounded-lg focus:ring-amber-500 focus:border-amber-500"
                disabled={loading}
            />
            {errors.phone && <p className="text-red-500 text-sm mt-1">{errors.phone.message}</p>}
        </div>

        {/* Botón de envío */}
        <div>
            <button
                type="submit"
                className="w-full bg-amber-600 text-white py-2 px-4 rounded-lg hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500"
                disabled={loading}
            >
                {loading ? 'Enviando...' : 'Confirmar Reserva'}
            </button>
        </div>
    </form>
</div>
    )
}