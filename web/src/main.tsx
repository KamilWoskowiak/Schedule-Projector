import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Route, Routes } from 'react-router'
import LandingPage from './components/LandingPage'
import './index.css'
import DashboardPage from './components/DashboardPage'
import SchedulesPage from './components/SchedulesPage'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
    <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/schedules" element={<SchedulesPage />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
