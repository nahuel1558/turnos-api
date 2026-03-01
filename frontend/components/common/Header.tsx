'use client'

import Link from 'next/link'
import {useState } from 'react'

export default function Header() {
    const [isMenuOpen, setIsMenuOpen] = useState(false)

    return (
        <header className="bg-amber-800 text-white shadow-lg">
            <nav className='container mx-auto px-4 py-4'>
                <div className='flex justify-between items-center'>
                    {/* Logo */}
                    <Link href='/' className='text-2xl font-bold'>
                        BarberShop
                    </Link>

                    {/* Menu para desktop */}
                    <div className='hidden md:flex space-x-6'>
                        <Link href="/" className='hover:text-amber-200 transition'>
                            Inicio
                        </Link>
                        <Link href="/services" className='hover:text-amber-200 transition'>
                            Servicios
                        </Link>
                        <Link href="/barbers" className='hover:text-amber-200 transition'>
                            Barberos
                        </Link>
                        <Link href="/appointment" className='hover:text-amber-200 transition'>
                            Reservar
                        </Link>
                    </div>

                    {/* Botón para mobile */}
                    <button 
                        className='md:hidden focus:outline-none'
                        onClick={() => setIsMenuOpen(!isMenuOpen)}
                    >
                        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16m-7 6h7" />
                        </svg>
                    </button>
                </div>

                {/* Menú para mobile */}
                {isMenuOpen && (
                    <div className='md:hidden mt-4 space-y-2'>
                        <Link href="/" className='block py-2 hover:text-amber-200 transition'>
                            Inicio
                        </Link>
                        <Link href="/services" className='block py-2 hover:text-amber-200 transition'>
                            Servicios
                        </Link>
                        <Link href="/barbers" className='block py-2 hover:text-amber-200 transition'>
                            Barberos
                        </Link>
                        <Link href="/appointment" className='block py-2 hover:text-amber-200 transition'>
                            Reservar
                        </Link>
                    </div>
                )}
            </nav>
        </header>
    )
}