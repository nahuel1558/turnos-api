import type { Metadata } from 'next';
import { Inter } from 'next/font/google'
import './globals.css'
import Header from '@/components/common/Header'
import Footer from '@/components/common/Footer'
import React from 'react';

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
    title: 'BarberShop - Reserva tu turno online.',
    description: 'La mejor barbería de la ciudad. Reserva tu turno con nuestros profesionales.',
}

export default function RootLayout({
    children,
}: {
    children: React.ReactNode
}) {
    return(
        <html lang='es'>
            <body className={inter.className}>
                <Header />
                <main className='min-h-screen'>
                    {children}
                </main>
                <Footer />
            </body>
        </html>
    )
}
