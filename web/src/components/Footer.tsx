import type React from "react"
import { ExternalLink } from "lucide-react"

const Footer: React.FC = () => {
  return (
    <footer className="bg-primary text-primary-foreground py-4">
      <div className="container mx-auto text-center">
        <p className="text-lg">
          Created by Kamil Woskowiak |{" "}
          <a
            href="https://github.com/kamilwoskowiak"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center hover:underline"
          >
            GitHub <ExternalLink className="ml-1 h-4 w-4" />
          </a>
          |{" "}
          <a
            href="https://ui.shadcn.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center hover:underline"
          >
            Shadcn <ExternalLink className="ml-1 h-4 w-4" />
          </a>
        </p>
      </div>
    </footer>
  )
}

export default Footer

