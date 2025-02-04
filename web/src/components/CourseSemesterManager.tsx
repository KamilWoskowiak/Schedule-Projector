import type React from "react"
import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Checkbox } from "@/components/ui/checkbox"
import type { Course } from "../types/course"
import type { Semester } from "../types/semester"

const CourseSemesterManager: React.FC = () => {
  const [semesters, setSemesters] = useState<Semester[]>([])
  const [courses, setCourses] = useState<Course[]>([])
  const [newSemester, setNewSemester] = useState<Omit<Semester, "id">>({ name: "", credits: 0 })
  const [newCourse, setNewCourse] = useState<Omit<Course, "id">>({
    name: "",
    credits: 0,
    prerequisites: [],
    offeredIn: [],
  })
  const [semesterError, setSemesterError] = useState<string | null>(null)
  const [courseError, setCourseError] = useState<string | null>(null)

  const handleSemesterInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setNewSemester((prev) => ({ ...prev, [name]: name === "credits" ? Number.parseInt(value) || 0 : value }))
  }

  const handleCourseInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setNewCourse((prev) => ({ ...prev, [name]: name === "credits" ? Number.parseInt(value) || 0 : value }))
  }

  const addSemester = () => {
    if (!newSemester.name.trim()) {
      setSemesterError("Semester name cannot be empty")
      return
    }
    if (newSemester.credits <= 0) {
      setSemesterError("Credits must be a positive number")
      return
    }
    const newId = Date.now().toString()
    setSemesters((prev) => [...prev, { ...newSemester, id: newId }])
    setNewSemester({ name: "", credits: 0 })
    setSemesterError(null)
  }

  const addCourse = () => {
    if (!newCourse.name.trim()) {
      setCourseError("Course name cannot be empty")
      return
    }
    if (newCourse.credits <= 0) {
      setCourseError("Credits must be a positive number")
      return
    }
    const newId = Date.now().toString()
    setCourses((prev) => [...prev, { ...newCourse, id: newId }])
    setNewCourse({ name: "", credits: 0, prerequisites: [], offeredIn: [] })
    setCourseError(null)
  }

  const handlePrerequisiteChange = (courseId: string) => {
    setNewCourse((prev) => ({
      ...prev,
      prerequisites: prev.prerequisites.includes(courseId)
        ? prev.prerequisites.filter((id) => id !== courseId)
        : [...prev.prerequisites, courseId],
    }))
  }

  const handleSemesterChange = (semesterId: string) => {
    setNewCourse((prev) => ({
      ...prev,
      offeredIn: prev.offeredIn.includes(semesterId)
        ? prev.offeredIn.filter((id) => id !== semesterId)
        : [...prev.offeredIn, semesterId],
    }))
  }

  const editSemester = (semester: Semester) => {
    setNewSemester({ name: semester.name, credits: semester.credits })
    setSemesters((prev) => prev.filter((s) => s.id !== semester.id))
  }

  const editCourse = (course: Course) => {
    setNewCourse({
      name: course.name,
      credits: course.credits,
      prerequisites: course.prerequisites,
      offeredIn: course.offeredIn,
    })
    setCourses((prev) => prev.filter((c) => c.id !== course.id))
  }

  return (
    <div className="space-y-8">
      <Alert>
        <AlertDescription>
          Please enter semesters first, then enter courses in order of level. This allows you to correctly set
          prerequisites for each course.
        </AlertDescription>
      </Alert>

      <div className="grid md:grid-cols-2 gap-8">
        <Card>
          <CardHeader>
            <CardTitle>Semester Manager</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="flex space-x-4">
                <Input
                  name="name"
                  placeholder="Semester Name"
                  value={newSemester.name}
                  onChange={handleSemesterInputChange}
                />
                <Input
                  name="credits"
                  type="number"
                  placeholder="Credits"
                  value={newSemester.credits || ""}
                  onChange={handleSemesterInputChange}
                />
                <Button onClick={addSemester}>Add Semester</Button>
              </div>

              {semesterError && (
                <Alert variant="destructive">
                  <AlertTitle>Error</AlertTitle>
                  <AlertDescription>{semesterError}</AlertDescription>
                </Alert>
              )}

              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Semester Name</TableHead>
                    <TableHead>Credits</TableHead>
                    <TableHead>Action</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {semesters.map((semester) => (
                    <TableRow key={semester.id} className="cursor-pointer hover:bg-muted/50">
                      <TableCell>{semester.name}</TableCell>
                      <TableCell>{semester.credits}</TableCell>
                      <TableCell>
                        <Button variant="outline" size="sm" onClick={() => editSemester(semester)}>
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

        <Card>
          <CardHeader>
            <CardTitle>Course Manager</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <Input
                  name="name"
                  placeholder="Course Name"
                  value={newCourse.name}
                  onChange={handleCourseInputChange}
                />
                <Input
                  name="credits"
                  type="number"
                  placeholder="Credits"
                  value={newCourse.credits || ""}
                  onChange={handleCourseInputChange}
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
                        onCheckedChange={() => handlePrerequisiteChange(course.id)}
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
                        onCheckedChange={() => handleSemesterChange(semester.id)}
                      />
                      <label htmlFor={`semester-${semester.id}`} className="text-sm cursor-pointer select-none">
                        {semester.name}
                      </label>
                    </div>
                  ))}
                </div>
              </div>

              <Button onClick={addCourse}>Add Course</Button>

              {courseError && (
                <Alert variant="destructive">
                  <AlertTitle>Error</AlertTitle>
                  <AlertDescription>{courseError}</AlertDescription>
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
                        <Button variant="outline" size="sm" onClick={() => editCourse(course)}>
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
      </div>
    </div>
  )
}

export default CourseSemesterManager

