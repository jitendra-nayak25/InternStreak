
const API_URL = "http://localhost:8080";


// LOGIN
export async function loginUser(username, password) {

    const response = await fetch(
        `${API_URL}/api/auth/login`,
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: username,
                password: password
            })
        }
    );

    const token = await response.text();

    if (!response.ok) {
        throw new Error(token || "Login failed");
    }

    return {
        token: token
    };
}


// GET ALL COURSES
export async function getCourses(token) {

    const response = await fetch(
        `${API_URL}/courses`,
        {
            method: "GET",

            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        }
    );

    const data = await response.json();

    if (!response.ok) {
        throw new Error(
            data.message || "Failed to fetch courses"
        );
    }

    return data;
}


// ADD COURSE
export async function addCourse(token, course) {

    const response = await fetch(
        `${API_URL}/courses`,
        {
            method: "POST",

            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },

            body: JSON.stringify(course)
        }
    );

    const data = await response.json();

    if (!response.ok) {
        throw new Error(
            data.message || "Failed to add course"
        );
    }

    return data;
}


// UPDATE COURSE
export async function updateCourse(token, id, course) {

    const response = await fetch(
        `${API_URL}/courses/${id}`,
        {
            method: "PUT",

            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },

            body: JSON.stringify(course)
        }
    );

    const data = await response.json();

    if (!response.ok) {
        throw new Error(
            data.message || "Failed to update course"
        );
    }

    return data;
}


// DELETE COURSE
export async function deleteCourse(token, id) {

    const response = await fetch(
        `${API_URL}/courses/${id}`,
        {
            method: "DELETE",

            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    const data = await response.text();

    if (!response.ok) {
        throw new Error(
            data || "Failed to delete course"
        );
    }

    return data;
}

