
import { useState } from "react";
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

    // Add Course states
    const [courseName, setCourseName] = useState("");
    const [courseDuration, setCourseDuration] = useState("");
    const [courseFee, setCourseFee] = useState("");

    // Edit Course states
    const [editingId, setEditingId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editDuration, setEditDuration] = useState("");
    const [editFee, setEditFee] = useState("");

    const token = localStorage.getItem("token");


    // Get user role from JWT
    const getUserRole = () => {

        if (!token) {
            return "";
        }

        try {

            const payload = JSON.parse(
                atob(token.split(".")[1])
            );

            return payload.role || "";

        } catch (error) {

            return "";
        }
    };


    const role = getUserRole();


    // Load Courses
    const loadCourses = async () => {

        try {

            const data = await getCourses(token);

            setCourses(data);

            setMessage("Courses loaded successfully!");

        } catch (error) {

            setMessage(error.message);
        }
    };


    // Add Course
    const handleAddCourse = async (e) => {

        e.preventDefault();

        try {

            const newCourse = {
                name: courseName,
                duration: courseDuration,
                fee: Number(courseFee)
            };

            await addCourse(token, newCourse);

            setMessage("Course added successfully!");

            // Clear form
            setCourseName("");
            setCourseDuration("");
            setCourseFee("");

            // Refresh course list
            await loadCourses();

        } catch (error) {

            setMessage(error.message);
        }
    };


    // Start Edit
    const startEdit = (course) => {

        setEditingId(course.id);

        setEditName(course.name || "");
        setEditDuration(course.duration || "");
        setEditFee(course.fee ?? "");

        setMessage("");
    };


    // Cancel Edit
    const cancelEdit = () => {

        setEditingId(null);

        setEditName("");
        setEditDuration("");
        setEditFee("");

        setMessage("");
    };


    // Update Course
    const handleUpdateCourse = async (e) => {

        e.preventDefault();

        try {

            const updatedCourse = {
                name: editName,
                duration: editDuration,
                fee: Number(editFee)
            };

            await updateCourse(
                token,
                editingId,
                updatedCourse
            );

            setMessage("Course updated successfully!");

            cancelEdit();

            await loadCourses();

        } catch (error) {

            setMessage(error.message);
        }
    };


    // Delete Course
    const handleDeleteCourse = async (id) => {

        const confirmed = window.confirm(
            "Are you sure you want to delete this course?"
        );

        if (!confirmed) {
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
    const logout = () => {

        localStorage.removeItem("token");

        setLoggedIn(false);

        setCourses([]);

        setSearch("");

        setMessage("");

        setEditingId(null);
    };


    // Search / Filter
    const filteredCourses = courses.filter((course) =>
        course.name
            ?.toLowerCase()
            .includes(search.toLowerCase())
    );


    // Login Page
    if (!loggedIn) {

        return <Login />;
    }


    // Dashboard
    return (

        <div>

            <h1>
                Course Management Dashboard
            </h1>


            {/* Logged-in User */}

            <p>
                Logged in as:{" "}
                <strong>
                    {role || "User"}
                </strong>
            </p>


            {/* Dashboard Buttons */}

            <button onClick={loadCourses}>
                Load Courses
            </button>

            {" "}

            <button onClick={logout}>
                Logout
            </button>


            {/* Message */}

            <p>
                {message}
            </p>


            <hr />


            {/* Courses */}

            <h2>
                Courses
            </h2>


            {/* Search */}

            <input
                type="text"
                placeholder="Search courses..."
                value={search}
                onChange={(e) =>
                    setSearch(e.target.value)
                }
            />


            <br />
            <br />


            {/* ADMIN CONTROLS */}

            {role === "ADMIN" && (

                <div>

                    <h3>
                        Admin Controls
                    </h3>


                    {/* ADD COURSE */}

                    <h4>
                        Add New Course
                    </h4>


                    <form onSubmit={handleAddCourse}>

                        <div>

                            <label>
                                Course Name:{" "}
                            </label>

                            <input
                                type="text"
                                value={courseName}
                                onChange={(e) =>
                                    setCourseName(
                                        e.target.value
                                    )
                                }
                                required
                            />

                        </div>


                        <br />


                        <div>

                            <label>
                                Duration:{" "}
                            </label>

                            <input
                                type="text"
                                value={courseDuration}
                                onChange={(e) =>
                                    setCourseDuration(
                                        e.target.value
                                    )
                                }
                                required
                            />

                        </div>


                        <br />


                        <div>

                            <label>
                                Fee:{" "}
                            </label>

                            <input
                                type="number"
                                value={courseFee}
                                onChange={(e) =>
                                    setCourseFee(
                                        e.target.value
                                    )
                                }
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


            {/* USER CONTROLS */}

            {role !== "ADMIN" && (

                <div>

                    <h3>
                        User Controls
                    </h3>

                    <p>
                        You have permission to view courses.
                    </p>

                </div>
            )}


            <hr />


            {/* COURSE LIST */}

            {courses.length === 0 ? (

                <p>
                    No courses loaded.
                </p>

            ) : filteredCourses.length === 0 ? (

                <p>
                    No matching courses found.
                </p>

            ) : (

                <ul>

                    {filteredCourses.map((course) => (

                        <li key={course.id}>

                            <strong>
                                {course.name}
                            </strong>

                            {" - "}

                            Duration:{" "}
                            {course.duration}

                            {" - "}

                            Fee: ₹{course.fee}


                            {/* ADMIN BUTTONS */}

                            {role === "ADMIN" && (

                                <span>

                                    {" "}

                                    <button
                                        onClick={() =>
                                            startEdit(course)
                                        }
                                    >
                                        Edit
                                    </button>

                                    {" "}

                                    <button
                                        onClick={() =>
                                            handleDeleteCourse(
                                                course.id
                                            )
                                        }
                                    >
                                        Delete
                                    </button>

                                </span>
                            )}


                            {/* PAYMENT STATUS */}

                            <br />

                            <strong>
                                Payment Status:
                            </strong>{" "}
                            Pending

                            {" "}

                            <a
                                href={`/payment/${course.id}`}
                            >
                                View Payment
                            </a>


                            {/* EDIT FORM */}

                            {editingId === course.id && (

                                <div>

                                    <br />

                                    <h4>
                                        Edit Course
                                    </h4>


                                    <form
                                        onSubmit={
                                            handleUpdateCourse
                                        }
                                    >

                                        <div>

                                            <label>
                                                Course Name:{" "}
                                            </label>

                                            <input
                                                type="text"
                                                value={editName}
                                                onChange={(e) =>
                                                    setEditName(
                                                        e.target.value
                                                    )
                                                }
                                                required
                                            />

                                        </div>


                                        <br />


                                        <div>

                                            <label>
                                                Duration:{" "}
                                            </label>

                                            <input
                                                type="text"
                                                value={editDuration}
                                                onChange={(e) =>
                                                    setEditDuration(
                                                        e.target.value
                                                    )
                                                }
                                                required
                                            />

                                        </div>


                                        <br />


                                        <div>

                                            <label>
                                                Fee:{" "}
                                            </label>

                                            <input
                                                type="number"
                                                value={editFee}
                                                onChange={(e) =>
                                                    setEditFee(
                                                        e.target.value
                                                    )
                                                }
                                                required
                                            />

                                        </div>


                                        <br />


                                        <button type="submit">
                                            Save Changes
                                        </button>

                                        {" "}

                                        <button
                                            type="button"
                                            onClick={cancelEdit}
                                        >
                                            Cancel
                                        </button>

                                    </form>

                                </div>
                            )}

                        </li>
                    ))}

                </ul>
            )}

        </div>
    );
}

export default App;

