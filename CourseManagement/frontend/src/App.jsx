import { useState } from "react";
import { Link } from "react-router-dom";

import Login from "./Login";
import {
    getCourses,
    addCourse,
    updateCourse,
    deleteCourse
} from "./services/api";

function App() {

    const [loggedIn, setLoggedIn] = useState(
        !!localStorage.getItem("token")
    );

    const [courses, setCourses] = useState([]);
    const [search, setSearch] = useState("");
    const [message, setMessage] = useState("");

    // Add course states
    const [courseName, setCourseName] = useState("");
    const [courseDuration, setCourseDuration] = useState("");
    const [courseFee, setCourseFee] = useState("");

    // Edit course states
    const [editingId, setEditingId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editDuration, setEditDuration] = useState("");
    const [editFee, setEditFee] = useState("");

    const token = localStorage.getItem("token");

    // Get role from JWT
    const getRoleFromToken = () => {

        const currentToken = localStorage.getItem("token");

        if (!currentToken) {
            return "";
        }

        try {

            const payload = JSON.parse(
                atob(currentToken.split(".")[1])
            );

            return payload.role || "";

        } catch (error) {

            console.error("Invalid JWT:", error);
            return "";
        }
    };

    const role = getRoleFromToken();

    // Load courses
    const loadCourses = async () => {

        try {

            const data = await getCourses(token);

            setCourses(data);

            setMessage("Courses loaded successfully!");

        } catch (error) {

            setMessage(error.message);
        }
    };

    // Add course
    const handleAddCourse = async (e) => {

        e.preventDefault();

        try {

            const course = {
                name: courseName,
                duration: courseDuration,
                fee: Number(courseFee)
            };

            await addCourse(token, course);

            setMessage("Course added successfully!");

            setCourseName("");
            setCourseDuration("");
            setCourseFee("");

            await loadCourses();

        } catch (error) {

            setMessage(error.message);
        }
    };

    // Start editing
    const handleEdit = (course) => {

        setEditingId(course.id);
        setEditName(course.name);
        setEditDuration(course.duration);
        setEditFee(course.fee);
    };

    // Update course
    const handleUpdate = async (id) => {

        try {

            const course = {
                name: editName,
                duration: editDuration,
                fee: Number(editFee)
            };

            await updateCourse(token, id, course);

            setMessage("Course updated successfully!");

            setEditingId(null);

            await loadCourses();

        } catch (error) {

            setMessage(error.message);
        }
    };

    // Delete course
    const handleDelete = async (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this course?"
        );

        if (!confirmDelete) {
            return;
        }

        try {

            await deleteCourse(token, id);

            setMessage("Course deleted successfully!");

            await loadCourses();

        } catch (error) {

            setMessage(error.message);
        }
    };

    // Logout
    const handleLogout = () => {

        localStorage.removeItem("token");

        setLoggedIn(false);
        setCourses([]);
        setMessage("");
    };

    // If not logged in
    if (!loggedIn) {
        return <Login />;
    }

    // Search/filter courses
    const filteredCourses = courses.filter((course) =>
        course.name
            .toLowerCase()
            .includes(search.toLowerCase())
    );

    return (
        <div>

            <h1>Course Management Dashboard</h1>

            <p>
                Logged in as: <strong>{role}</strong>
            </p>

            <button onClick={loadCourses}>
                Load Courses
            </button>

            {" "}

            <button onClick={handleLogout}>
                Logout
            </button>

            <p>{message}</p>

            <hr />

            <h2>Courses</h2>

            {/* Search */}
            <input
                type="text"
                placeholder="Search courses..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            <br />
            <br />

            {/* Admin Controls */}
            {role === "ADMIN" && (

                <div>

                    <h2>Admin Controls</h2>

                    <h3>Add New Course</h3>

                    <form onSubmit={handleAddCourse}>

                        <div>
                            <label>Course Name: </label>

                            <input
                                type="text"
                                value={courseName}
                                onChange={(e) =>
                                    setCourseName(e.target.value)
                                }
                                required
                            />
                        </div>

                        <br />

                        <div>
                            <label>Duration: </label>

                            <input
                                type="text"
                                value={courseDuration}
                                onChange={(e) =>
                                    setCourseDuration(e.target.value)
                                }
                                required
                            />
                        </div>

                        <br />

                        <div>
                            <label>Fee: </label>

                            <input
                                type="number"
                                value={courseFee}
                                onChange={(e) =>
                                    setCourseFee(e.target.value)
                                }
                                min="1"
                                required
                            />
                        </div>

                        <br />

                        <button type="submit">
                            Add Course
                        </button>

                    </form>

                </div>
            )}

            <hr />

            {/* Course List */}
            {filteredCourses.length === 0 ? (

                <p>No courses loaded.</p>

            ) : (

                <div>

                    {filteredCourses.map((course) => (

                        <div key={course.id}>

                            {editingId === course.id ? (

                                <div>

                                    <h3>Edit Course</h3>

                                    <div>
                                        <label>Name: </label>

                                        <input
                                            type="text"
                                            value={editName}
                                            onChange={(e) =>
                                                setEditName(e.target.value)
                                            }
                                        />
                                    </div>

                                    <br />

                                    <div>
                                        <label>Duration: </label>

                                        <input
                                            type="text"
                                            value={editDuration}
                                            onChange={(e) =>
                                                setEditDuration(
                                                    e.target.value
                                                )
                                            }
                                        />
                                    </div>

                                    <br />

                                    <div>
                                        <label>Fee: </label>

                                        <input
                                            type="number"
                                            value={editFee}
                                            onChange={(e) =>
                                                setEditFee(e.target.value)
                                            }
                                            min="1"
                                        />
                                    </div>

                                    <br />

                                    <button
                                        onClick={() =>
                                            handleUpdate(course.id)
                                        }
                                    >
                                        Save
                                    </button>

                                    {" "}

                                    <button
                                        onClick={() =>
                                            setEditingId(null)
                                        }
                                    >
                                        Cancel
                                    </button>

                                </div>

                            ) : (

                                <div>

                                    <h3>{course.name}</h3>

                                    <p>
                                        <strong>ID:</strong>{" "}
                                        {course.id}
                                    </p>

                                    <p>
                                        <strong>Duration:</strong>{" "}
                                        {course.duration}
                                    </p>

                                    <p>
                                        <strong>Fee:</strong>{" "}
                                        ₹{course.fee}
                                    </p>

                                    <p>
                                        <strong>Payment Status:</strong>{" "}
                                        Pending
                                    </p>

                                    {/* Admin buttons */}
                                    {role === "ADMIN" && (

                                        <div>

                                            <button
                                                onClick={() =>
                                                    handleEdit(course)
                                                }
                                            >
                                                Edit
                                            </button>

                                            {" "}

                                            <button
                                                onClick={() =>
                                                    handleDelete(course.id)
                                                }
                                            >
                                                Delete
                                            </button>

                                        </div>
                                    )}

                                    <br />

                                    {/* Payment Route */}
                                    <Link
                                        to={`/payment/${course.id}`}
                                    >
                                        View Payment
                                    </Link>

                                </div>
                            )}

                            <hr />

                        </div>
                    ))}

                </div>
            )}

        </div>
    );
}

export default App;