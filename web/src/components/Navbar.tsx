import type React from "react"
import { Link, useLocation } from "react-router"
import { Button } from "@/components/ui/button"
import { Separator } from "@/components/ui/separator"
import { LogOut } from "lucide-react"

const Navbar: React.FC = () => {
  const location = useLocation()

  return (
    <nav className="flex items-center justify-between p-4 bg-gray-100">
      <div className="flex items-center space-x-4 flex-grow">
        <Link to="/dashboard" className="flex-grow">
          <Button variant={location.pathname === "/dashboard" ? "default" : "secondary"} className="w-full text-lg py-6">
            Course/Semester Dashboard
          </Button>
        </Link>
        <Link to="/schedules" className="flex-grow">
          <Button
            variant={location.pathname === "/schedules" ? "default" : "secondary"}
            className="w-full text-lg py-6"
          >
            View Schedules
          </Button>
        </Link>
      </div>
      <div className="flex items-center">
        <Separator orientation="vertical" className="h-6 mx-4" />
        <Button variant="secondary" className="text-lg py-6">
          <LogOut className="mr-2 h-5 w-5" />
          Logout
        </Button>
      </div>
    </nav>
  )
}

export default Navbar;