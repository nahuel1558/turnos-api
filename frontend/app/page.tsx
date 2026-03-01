import Image from "next/image";
import Link from "next/link";

export default function Home(){
  return(
    <main className="min-h-screen">
      {/* Hero Section - La primera impresion */}
      <section className="bg-gradient-to-r from-amber-600 to-amber-800 text-white">
        <div className="continer mx-auto px-4 py-16 md:py">
          <div className="max-w-2x1">
            <h1 className="text-4x1 md:text-6x1 font-bold mb-4">
              Tu estilo, nuestro arte.
            </h1>
            <p className="text-xl mb-8">
              En Barber, cada corte es una experiencia única.
              Barberos profesionales, ambiente tranquilo y los mejores productos.
            </p>
            <Link
              href="/reservar"
              className="bg-white text-amber-800 px-8 py-3 rounded-lg font-semibold text-lg hover:bg-amber-100 transition-colors">
                Reserva tu turno
              </Link>
          </div>
        </div>
      </section>

      {/* Seccion de servicios destacados */}
      <section className="py-16 bg-gray-50">
        <div className="container mx-auto px-4">
          <h2 className="text-3x1 font-bold text-center mb-12">Nuestros Servicios</h2>
          <div className="grid md:grid-cols-3 gap-8">
            {/* Serivcio 1 */}
            <div className="bg-white rounded-lg shadow-lg p-6 text-center">
              <div className="text-amber-600 text-4x1 mb-4">✂️</div>
              <h3 className="text-xl font-semibold mb-2">Corte Clásico</h3>
              <p className="text-gray-600">El corte tradicional que nunca pasa de moda</p>
            </div>
             {/* Servicio 2 */}
            <div className="bg-white rounded-lg shadow-lg p-6 text-center">
              <div className="text-amber-600 text-4xl mb-4">🪒</div>
              <h3 className="text-xl font-semibold mb-2">Arreglo de Barba</h3>
              <p className="text-gray-600">Perfilado y tratamiento con productos premium</p>
            </div>
            {/* Servicio 3 */}
            <div className="bg-white rounded-lg shadow-lg p-6 text-center">
              <div className="text-amber-600 text-4xl mb-4">💈</div>
              <h3 className="text-xl font-semibold mb-2">Corte + Barba</h3>
              <p className="text-gray-600">Combo completo para una imagen impecable</p>
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}