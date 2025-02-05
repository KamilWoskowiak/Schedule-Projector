import type React from "react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { FaGithub, FaGoogle } from "react-icons/fa"
import Footer from "./Footer"

const LandingPage: React.FC = () => {
  return (
    <div className="min-h-screen bg-background text-foreground flex flex-col">
      <main className="flex-grow flex items-center justify-center p-8">
        <div className="max-w-7xl w-full grid grid-cols-1 lg:grid-cols-2 gap-16">
          <div className="flex flex-col justify-center space-y-8">
            <h1 className="text-6xl font-bold mb-6 text-primary leading-tight">Plan Your College Journey Efficiently</h1>
            <p className="text-2xl mb-8">
              Figure out how you might want to distribute your workload throughout your time in college. This application is especially helpful for those enrolled in multiple programs.
            </p>
            <ul className="list-none space-y-6 mb-8">
              {[
                "Input your course requirements and semester preferences",
                "Automatically generate the most efficient schedule",
                "Adjust and customize your plan as needed",
                "Track your progress towards graduation",
              ].map((item, index) => (
                <li key={index} className="flex items-center">
                  <span className="text-primary mr-3 text-3xl">•</span>
                  <span className="text-xl">{item}</span>
                </li>
              ))}
            </ul>
            <p className="text-2xl font-semibold">
              Start planning your academic success today and make the most of your college experience!
            </p>
          </div>
          <div className="flex items-center justify-center">
            <Card className="w-full max-w-xl shadow-lg border-primary/20 border-2">
              <CardHeader className="space-y-4">
                <CardTitle className="text-4xl font-bold text-primary">Get Started</CardTitle>
                <CardDescription className="text-xl">Sign in to create your optimized course schedule</CardDescription>
              </CardHeader>
              <CardContent className="space-y-8">
                <Button className="w-full text-xl py-8 bg-primary text-primary-foreground hover:bg-primary/90">
                  <FaGithub className="mr-3 h-6 w-6" />
                  Sign in with GitHub
                </Button>
                <Button className="w-full text-xl py-8 bg-primary text-primary-foreground hover:bg-primary/90">
                  <FaGoogle className="mr-3 h-6 w-6" />
                  Sign in with Google
                </Button>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
      <Footer />
    </div>
  )
}

export default LandingPage