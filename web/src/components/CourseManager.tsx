import type React from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Checkbox } from "@/components/ui/checkbox"
import type { Course } from "../types/course"
import type { Semester } from "../types/semester"

interface CourseManagerProps {
  semesters: Semester[]
  courses: Course[]
  newCourse: Omit<Course, "id">
  error: string | null
  onInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void
  onPrerequisiteChange: (courseId: string) => void
  onSemesterChange: (semesterId: string) => void
  onAddCourse: () => void
  onEditCourse: (course: Course) => void
}

const CourseManager: React.FC<CourseManagerProps> = ({
  semesters,
  courses,
  newCourse,
  error,
  onInputChange,
  onPrerequisiteChange,
  onSemesterChange,
  onAddCourse,
  onEditCourse,
}) => {
  return (
    <Card className="w-full">
      <CardHeader>
        <CardTitle>Course Manager</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <Input name="name" placeholder="Course Name" value={newCourse.name} onChange={onInputChange} />
            <Input
              name="credits"
              type="number"
              placeholder="Credits"
              value={newCourse.credits || ""}
              onChange={onInputChange}
            />
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium">Prerequisites:</label>
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-2">
              {courses.map((course) => (
                <div key={course.id} className="flex items-center space-x-2">
                  <Checkbox
                    id={`prereq-${course.id}`}
                    checked={newCourse.prerequisites.includes(course.id)}
                    onCheckedChange={() => onPrerequisiteChange(course.id)}
                  />
                  <label htmlFor={`prereq-${course.id}`} className="text-sm cursor-pointer select-none">
                    {course.name}
                  </label>
                </div>
              ))}
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium">Offered in:</label>
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-2">
              {semesters.map((semester) => (
                <div key={semester.id} className="flex items-center space-x-2">
                  <Checkbox
                    id={`semester-${semester.id}`}
                    checked={newCourse.offeredIn.includes(semester.id)}
                    onCheckedChange={() => onSemesterChange(semester.id)}
                  />
                  <label htmlFor={`semester-${semester.id}`} className="text-sm cursor-pointer select-none">
                    {semester.name}
                  </label>
                </div>
              ))}
            </div>
          </div>

          <Button onClick={onAddCourse}>Add Course</Button>

          {error && (
            <Alert variant="destructive">
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Course Name</TableHead>
                <TableHead>Credits</TableHead>
                <TableHead>Prerequisites</TableHead>
                <TableHead>Offered In</TableHead>
                <TableHead>Action</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {courses.map((course) => (
                <TableRow key={course.id} className="cursor-pointer hover:bg-muted/50">
                  <TableCell>{course.name}</TableCell>
                  <TableCell>{course.credits}</TableCell>
                  <TableCell>
                    {course.prerequisites.map((id) => courses.find((c) => c.id === id)?.name).join(", ")}
                  </TableCell>
                  <TableCell>
                    {course.offeredIn.map((id) => semesters.find((s) => s.id === id)?.name).join(", ")}
                  </TableCell>
                  <TableCell>
                    <Button variant="outline" size="sm" onClick={() => onEditCourse(course)}>
                      Edit
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      </CardContent>
    </Card>
  )
}

export default CourseManager

