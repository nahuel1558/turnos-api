export default function Footer() {
    return (
        <footer className="bg-gray-800 text-white mt-auto">
            <div className="container mx-auto px-4 py-8">
                <div className="grid md:grid-cols-3 gap-8">
                    {/* Información de contacto */}
                    <div>
                        <h3 className="text-lg font-semibold mb-4">Contacto</h3>
                        <p>Dirección 📍 Calle Falsa 123, Ciudad</p>
                        <p>Teléfono 📞 (123) 456-7890</p>
                        <p>Email ✉️ contacto_desainers@gmail.com</p>
                    </div>

                    {/* Horarios de atención */}
                    <div>
                        <h3 className="text-lg font-semibold mb-4">Horarios</h3>
                        <p>Lunes a Viernes: 9:00 AM - 8:00 PM</p>
                        <p>Sábados: 10:00 AM - 6:00 PM</p>
                        <p>Domingos: Cerrado</p>
                    </div>

                    {/* Redes sociales */}
                    <div>
                        <h3 className="text-lg font-semibold mb-4">Seguinos</h3>
                        <div className="flex space-x-4">
                            <a href="#" className="hover:text-amber-400 transition">Facebook</a>
                            <a href="#" className="hover:text-amber-400 transition">Instagram</a>
                            <a href="#" className="hover:text-amber-400 transition">Twitter</a>
                        </div>
                    </div>
                </div>
                <div className="border-t border-gray-700 mt-8 pt-4 text-center text-gray-400">
                    <p>&copy; {new Date().getFullYear()} BarberShop. Todos los derechos reservados.</p>
                </div>
            </div>
        </footer>
    )
}