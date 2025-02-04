import type React from "react"
import Footer from "./Footer"
import CourseSemesterManager from "./CourseSemesterManager"
import Navbar from "./Navbar"

const DashboardPage: React.FC = () => {
  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      <main className="flex-grow container mx-auto py-8">
        <h1 className="text-3xl font-bold mb-6">Dashboard</h1>
        <CourseSemesterManager />
      </main>
      <Footer />
    </div>
  )
}

export default DashboardPage;
